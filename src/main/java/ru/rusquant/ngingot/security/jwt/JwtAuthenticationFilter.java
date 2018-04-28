package ru.rusquant.ngingot.security.jwt;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// Используется такой-же базовый класс как и у BasicAuthenticationFilter
// Собсвенно, все нужно сделать по аналогии с классом BasicAuthenticationFilter
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;
    private AuthenticationEntryPoint authenticationEntryPoint;

    // Имя заголовка Http-запроса для токена из пропертей приложения
    private String authHeaderName;

    // "Bearer "
    private String tokenScheme;

    private RememberMeServices rememberMeServices = new NullRememberMeServices();


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        Assert.notNull(authenticationManager,"authenticationManager cannot be null!");
        this.authenticationManager = authenticationManager;
    }


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   AuthenticationEntryPoint authenticationEntryPoint) {
        Assert.notNull(authenticationManager, "authenticationManager cannot be null!");
        Assert.notNull(authenticationEntryPoint, "authenticationEntryPoint cannot be null!");
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }


    @Override
    public void afterPropertiesSet() throws ServletException {
        Assert.notNull(this.authenticationManager, "authenticationManager is required!");
        Assert.notNull(this.authenticationEntryPoint, "authenticationEntryPoint is required!");
    }


    /**
     *    ВНИМАНИЕ!!!
     *    REST API НЕ хранит состояние на севере и воспринимает каждый http-запрос как первый.
     *    Поэтому аунтификация не хранится в контексте от запроса к запросу, а существует там только
     *    на время выполнения http-запроса.
     *
     *    Фильт должен проверить наличие в заголовке запроса токена. При его наличии, передать токен
     *    в провайдер, который должен его валидировать и создать аутификацию если с токеном все ок.
     *
     *    Дальше аунтификация просто сохраняется в контексте, чтобы, для выполнения запроса,
     *    могла отработать остальная часть логики бэкенда в соотвествие с правами клиента api.
     **/
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        this.logger.debug("Processing authentication for " + request.getRequestURL());
        final String authHeader = request.getHeader(this.authHeaderName);
        if(authHeader != null && authHeader.startsWith(this.tokenScheme + " ")) {
            // Отлично! Нам передали какой-то токен!
            String token = authHeader.substring(7);
            JwtAuthenticationToken jwtToken = new JwtAuthenticationToken(token);
            // Проверка скорее "для порядка" так как сессия
            // не хранится от запроса к запросу
            if(this.isAuthenticationRequired()) {
                try {
                    // Передаем токен манагеру для проведения процесса аутентификации
                    Authentication authResult = authenticationManager.authenticate(jwtToken);
                    this.logger.debug("JWT-authentication success: " + authResult);
                    this.onSuccessfulAuthentication(request, response, authResult);
                } catch (AuthenticationException failed) {
                    this.logger.debug("JWT-authentication failed: " + failed);
                    this.onUnsuccessfulAuthentication(request, response, failed);
                    return;
                }
            }
        } else {
            if(authHeader == null) {
                this.logger.debug("Authorization header not found in request!");
            } else {
                this.logger.debug("Authorization header does not use Bearer scheme!");
            }
            // На в любом случае нужно продолжить цепочку обработки реквеста
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isAuthenticationRequired() {
        // Проверяем, есть ли успешная аунтификация в контексте
        // Нет - значит нужно аунтифицировать клиента
        Authentication existAuth = SecurityContextHolder.getContext().getAuthentication();
        if(existAuth == null || !existAuth.isAuthenticated()) {
            return true;
        }

        // Обработка нетипичных ситуаций, когда в контексте присутствует анонимная аунтификация.
        // Это не должно случаться часто, так как BasicProcessionFilter расположен в цепи фильтров
        // раньше чем AnonymousAuthenticationFilter.
        // Тем не менее, существование обоих AnonymousAuthenticationToken и jwt-token в заголовке
        // реквеста говорят о необходимости повторной-аунтификации с использованием JWT
        if(existAuth instanceof AnonymousAuthenticationToken) {
            return true;
        }

        return false;
    }

    protected void onSuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Authentication authResult) throws IOException {
        // Если  аутентифкация удалась, кладем ее в контекст
        SecurityContextHolder.getContext().setAuthentication(authResult);
        rememberMeServices.loginSuccess(request, response, authResult);
    }

    protected void onUnsuccessfulAuthentication(HttpServletRequest request,
                                                HttpServletResponse response,
                                                AuthenticationException failed) throws ServletException, IOException {
        // 1. Аунтификация провалена. Чистим контекст безопасности remember-me сервис
        SecurityContextHolder.clearContext();
        rememberMeServices.loginFail(request, response);

        // 2. Активируем AuthenticationEntryPoint чтоб на клиент улетело "401 Unauthorized"
        this.authenticationEntryPoint.commence(request, response, failed);
    }

    public void setAuthHeaderName(String authHeaderName) {
        this.authHeaderName = authHeaderName;
    }

    public void setTokenScheme(String tokenScheme) {
        this.tokenScheme = tokenScheme;
    }
}
