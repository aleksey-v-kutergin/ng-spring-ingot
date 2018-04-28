package ru.rusquant.ngingot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.rusquant.ngingot.domain.User;
import ru.rusquant.ngingot.security.jwt.JwtAuthenticationToken;
import ru.rusquant.ngingot.security.jwt.JwtTokenHelper;
import ru.rusquant.ngingot.security.jwt.JwtUserDetails;
import ru.rusquant.ngingot.security.encryption.RestEncryptionService;
import ru.rusquant.ngingot.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 *   Провайдер, обеспечивающий непосредственно аутентификации на основе логина
 *   и шифрованного пароля.
 *   Если проводить аналогии с OAuth - это сервер аунтификации.
 */
@Component
public class RestAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private RestEncryptionService encryptionService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String login = (String) authentication.getPrincipal();
        // C клиента нам приходит свертка: H( H(password) + salt ), где H - хэш-функция
        final String passwordAndSaltHash = (String) authentication.getCredentials();

        // Таким образом:
        // 0. На случай, если клиент в силу той или иной ошибки обратился на прямую на логин
        //    без этапа генерации соли, нужно проверить наличие соли, сгенерированной
        //    для этого логина
        if(!this.encryptionService.isSaltGenerated(login)) {
            String msg = "Incorrect authentication order! ";
            msg += "The step of generating a password encryption key was skipped.";
            throw new BadCredentialsException(msg);
        }
        // 1. Извлекаем хэш-пароля по соотвествующему ключу шифрования (соли)
        final String passwordHash = this.encryptionService.getPasswordHash(login, passwordAndSaltHash);
        // 2. Сопоставляем хэш пралоля с эталонным хэшем, хранящимя в БД
        //    Проверять на существование юзера с таким логином здесь уже нет смысла.
        //    Эта проверка проходит на этапе выдачи соли
        final User user = this.userService.findByLogin(login);
        // Типа в базе хранятся эталонные хэши паролей
        if (!user.getPassword().equals(passwordHash)) {
            throw new BadCredentialsException("Incorrect password!");
        }
        // 3. Токен нельзя выдавать пользователю с протухшим паролем.
        //    Пусть сначала сменить его
        if(user.isPasswordExpired()) {
            throw new CredentialsExpiredException("Password for user: " + user.getLogin() + " has been expired.");
        }
        // 4. Токен нельзя выдавать пользователю с протухшим аккаунтом.
        if(user.isAccountExpired()) {
            throw new AccountExpiredException("Account for user: " + user.getLogin() + " has been expired.");
        }
        // 5. Проверка блокировки учетки
        if (user.isLocked()) {
            throw new LockedException("Your account has been locked. Contact your administrator.");
        }

        List<GrantedAuthority> authorities = this.determineAuthorities(user);
        JwtUserDetails details = new JwtUserDetails(user, authorities);
        String token = this.jwtTokenHelper.generateToken(details);

        JwtAuthenticationToken successAuthentication = new JwtAuthenticationToken(details.getUsername(), details.toClaims(), authorities);
        successAuthentication.setToken(token);
        return successAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private List<GrantedAuthority> determineAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getAuthorities().forEach(authority -> {
            authorities.add( new SimpleGrantedAuthority(authority.getRole().name()) );
        });
        return authorities;
    }
}
