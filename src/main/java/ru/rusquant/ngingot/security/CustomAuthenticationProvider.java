package ru.rusquant.ngingot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.rusquant.ngingot.domain.User;
import ru.rusquant.ngingot.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 *  Поставщик конкртеной реализации механизмов проверки,
 *  что Вася Пупкин это Вася Пупкин
 **/
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User user = userService.findByLogin(login);
        if(user == null) {
            throw new UsernameNotFoundException("User with login " + login + " not found");
        }

        if(!user.getPassword().equals(password)) {
            throw new BadCredentialsException("Invalid password");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        authorities.add(authority);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password, authorities);
        token.setDetails(user);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
