package ru.rusquant.ngingot.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // По дефолту Spring в случае успешной аутентификации редиректит на home или на страницу, запрошенную
        // до инициализации процесса аутентификации. (Кэшится в RequestAwareCacheFilter).
        // Так как у нас REST API, такое поведение нас не устраивает и его нужно переопределить.
        // В случае с REST API делать сделсь ничего не нужно, так как некуда редиректить
        // Клиент в респонсе просто прилетит факт успешной аунтификации и токен
    }

}
