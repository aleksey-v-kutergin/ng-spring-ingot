package ru.rusquant.ngingot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.rusquant.ngingot.security.RestAuthenticationEntryPoint;
import ru.rusquant.ngingot.security.jwt.JwtAuthenticationFilter;
import ru.rusquant.ngingot.security.jwt.JwtAuthenticationProvider;

@Configuration
@EnableWebSecurity
@ComponentScan("ru.rusquant.ngingot.security")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${rest.api.route.root}")
    private String apiRootRout;

    @Value("${rest.api.route.auth}")
    private String authRoute;

    @Value("${rest.api.route.auth.salt}")
    private String saltRoute;

    @Value("${rest.api.route.auth.login}")
    private String loginRoute;

    @Value("${rest.api.route.auth.logout}")
    private String logoutRoute;

    @Value("${rest.api.route.auth.refresh}")
    private String refreshTokenRoute;

    @Value("${jwt.auth.header.name}")
    private String authHeaderName;

    @Value("${jwt.token.scheme}")
    private String tokenScheme;


    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
            // Защиту от csrf-атак пока отключаем
            // Так как использование токена в заговоках само по себе защищает от них
            csrf().disable()
            // Регистрируем нашу кастомную entry-pint для обработки ошибок аунтификации
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
            .and()
                // Так как бэкенд позиционируется как REST-сервис,
                // по канонам REST он не должен хранить состояние клиента
                // то есть сессию клиента
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // Определяем какие ресурсы будут у нас защищенными
            .authorizeRequests()
                .antMatchers("/admin/h2/**").permitAll() // Доступ до консоли базы H2
                .antMatchers("/app/**").permitAll() // доступ к скриптам приложения
                .antMatchers("/assets/**").permitAll() // Доступ к статике приложения
                .antMatchers("/vendor/**").permitAll() // Доступ к скриптам либ
                // Обращение к REST API:
                // Открытая часть API для аунтификации
                .antMatchers(this.saltRoute + "/*").permitAll()
                .antMatchers(this.loginRoute + "/*").permitAll()
                // Для всех остальных реквестов наличие аутентификации обязательно
                .anyRequest().authenticated();

        // Регистрируем собственный фильтр, который булет проверять
        // наличие заговка с jwt-токеном в реквесте
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(this.authenticationManager(), this.authenticationEntryPoint);
        filter.setAuthHeaderName(this.authHeaderName);
        filter.setTokenScheme(this.tokenScheme);
        http.addFilterBefore(filter, BasicAuthenticationFilter.class);

        // Запрещаем кэширование страниц
        http
            .headers()
                .frameOptions().sameOrigin() // Иначе консоль H2-базы будет пустая
                .cacheControl();
    }

    /**
     *    permitAll() - лишь способ сказать что данный ресурс доступен
     *    пользователям с любыми ролями. Но это не отменяет действие фильтров
     *    безопасности. Поэтому нужно отдельно настроит игнор роутов, для которыйх
     *    не должны применятся фильтры Spring Security
     **/
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring().antMatchers(HttpMethod.GET, "/**")
        .and()
            .ignoring().antMatchers(HttpMethod.GET, "/*.html")
        .and()
                .ignoring().antMatchers(HttpMethod.GET, "/app/**")
        .and()
            .ignoring().antMatchers(HttpMethod.GET, "/assets/**")
        .and()
            .ignoring().antMatchers(HttpMethod.GET, "/vendor/**")
        .and()
            .ignoring().antMatchers(HttpMethod.GET, "/favicon.ico")
        .and()
            .ignoring().antMatchers(HttpMethod.GET, this.saltRoute)
        .and()
            .ignoring().antMatchers(HttpMethod.POST, this.loginRoute)
        .and()
            // Для процесса разработки и отладки
            // В продакшене консоль H2 бызы должна быть под защитой
            .ignoring().antMatchers("/admin/h2/**");
    }
}
