package ru.rusquant.ngingot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import ru.rusquant.ngingot.security.CustomAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@ComponentScan("ru.rusquant.ngingot.security")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("bill")
                .password("abc123")
                .roles("ADMIN");

        auth.inMemoryAuthentication()
                .withUser("tom")
                .password("abc123")
                .roles("USER");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http.csrf().disable() // Защиту от csrf-атак пока отключаем
                // Определяем какие ресурсы будут у нас защищенными
                .authorizeRequests()
                .antMatchers("/api/app/module/**").hasRole("ADMIN")
                .antMatchers("/api/user/**").hasRole("USER")
                .and()
                // Конфигурируем базовый механизм аунтификации
                .httpBasic().realmName("TEST_REALM").authenticationEntryPoint(getBasicAuthEntryPoint())
                .and()
                // Так как бэкенд позиционируется как REST-сервис,
                // по канонам REST он не должен хранить состояние клиента
                // то есть сессию клиента
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    @Bean
    public BasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    /**
     *    Разрешаем браузеру отсылать Pre-flight [OPTIONS] запросы.
     *    Речь идет о CROS-Origin Resource Sharing (CORS)
     *
     *    CORS - это механизм, кторый дает возможность web-приложению, используюя дополнительные заголовки в http-запросах,
     *    запрашивать ресурсы с домена, отличного от того домена, на которм крутится само приложение.
     *
     *    Например, web-приложение было загружено с домена http://domain-a.com и запрашивает css-ки (люьые другие ресурсы)
     *    с домена http://domain-b.com. Это называется Cross-origin HTTP Request.
     *
     *    При использовании этого механизма, браузер перед запросом целевого ресурса выполняет предварительный запрос,
     *    чтобы убедится что CORS-протокол поддерживается сервером.
     *
     *    Подробнее:
     *    https://developer.mozilla.org/ru/docs/Web/HTTP/CORS
     *    https://developer.mozilla.org/en-US/docs/Glossary/Preflight_request
     *    https://stackoverflow.com/questions/29954037/why-is-an-options-request-sent-and-can-i-disable-it
     **/
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
