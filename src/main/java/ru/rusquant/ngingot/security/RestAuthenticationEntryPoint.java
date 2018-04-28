package ru.rusquant.ngingot.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // Метод commence(...) класса AuthenticationEntryPoint вызывается если
        // в фильтре безопасности было бырошенно AuthenticationException, то есть
        // если ни один из AuthenticationProvider-ров не вернул успешный Authentication
        // По-русски, это метод вызвается если аунтификация провалена из-за не кривых логина\пароля
        // или из-за того что клиент пытается получить доступ к защищенным ресурсам
        // не предоставив учетных данных в запросе
        // Дефолтным поведением Spring Security в этом случае будет редирект на страницу логина
        // В REST API страницы логина нет, поэтому мы создаем кастомную AuthenticationEntryPoint
        // для переопределения дефолтного поведения.
        // Для REST API в этом случае достаточно отправить обратно ошибку: 401 Unauthorized
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
