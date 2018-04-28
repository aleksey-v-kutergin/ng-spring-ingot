package ru.rusquant.ngingot.security.jwt;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


/**
 *    В приложении присутствует два типа логики для обеспечения процесса аутентификации:
 *    1. Сервер аунтификации.
 *
 *      Певая отвечает непосредственно за проверку предоставляемых учетных данных
 *      и выдачу валидного токена
 *
 *    2. Сервер ресурсов.
 *
 *       Который проверяет наличие валидного токена в заголовках http-запросов к
 *       защищенным ресурсам. Токены он уже не выдает.
 *
 *    Данный провойдер предназначен для проверки валидности токена, с которым клиент REST API
 *    пытается получить доступ к защищенным ресурсам.
 *
 *    ВНИМАНИЕ!!!
 *    Данный провайдер не должен лазить в БД (или ее часть), в которой лежат данные для аунтификации.
 *    Вся информация должна браться из переданного токена!!!
 **/
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
        final String token = jwtAuthentication.getToken();
        try {
            // Проводим валидацию токена
            // 1. Протух ли токен
            if(this.jwtTokenHelper.isTokenExpired(token)) {
                throw new BadCredentialsException("Token has been expired.");
            }
            // 2. Достаем инфу по пользователю
            final JwtUserDetails userDetails = (JwtUserDetails) this.jwtTokenHelper.getUserDetails(token);

            // 3. Аккаунт не должен быть протухшим
            if(!userDetails.isAccountNonExpired()) {
                throw new AccountExpiredException("Account for user: " + userDetails.getUsername() + " has been expired.");
            }
            // 4. Пароль от аккаунта не должен быть протухшим
            if(!userDetails.isCredentialsNonExpired()) {
                throw new CredentialsExpiredException("Password for user: " + userDetails.getUsername() + " has been expired.");
            }

            // Пришедший на вход объект JwtAuthenticationToken, служит только для передачи токена в провайдер.
            // Если мы оказались здесь значит токен валиден и можно, используя информацию из токена,
            // создать объект аунтификации, кторый будет помещен в контекст для выполнеия операций на сервере
            JwtAuthenticationToken successAuthentication = new JwtAuthenticationToken(userDetails.getUsername(), userDetails.toClaims(), userDetails.getAuthorities());
            successAuthentication.setToken(token);
            return successAuthentication;
        } catch (RuntimeException e) {
            throw this.mapJwtToAuthenticationException(e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private AuthenticationException mapJwtToAuthenticationException(RuntimeException e) {
        String msg;
        if(e instanceof SignatureException) {
             msg = "Calculation a signature or verifying an existing signature of a JWT failed.";
        } else if(e instanceof UnsupportedJwtException) {
            msg = "Received JWT-token has a particular format/configuration that does not match the format expected by the application.";
        } else if(e instanceof MalformedJwtException) {
            msg = "Received JWT-token was not correctly constructed.";
        } else {
            msg = "Unable to parse invalid token.";
        }
        return new BadCredentialsException(msg, e);
    }

}
