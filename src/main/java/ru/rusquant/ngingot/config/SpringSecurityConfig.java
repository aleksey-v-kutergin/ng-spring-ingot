package ru.rusquant.ngingot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ru.rusquant.ngingot.security.RestAuthenticationEntryPoint;
import ru.rusquant.ngingot.security.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
@ComponentScan("ru.rusquant.ngingot.security")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
            csrf().disable() // Защиту от csrf-атак пока отключаем
            // Определяем какие ресурсы будут у нас защищенными
            .authorizeRequests()
                .antMatchers("/app/**").permitAll() // доступ к скриптам приложения
                .antMatchers("/assets/**").permitAll() // Доступ к статике приложения
                .antMatchers("/vendor/**").permitAll() // Доступ к скриптам либ
                // Обращение к REST API:
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/logout").permitAll()
                .antMatchers("/api/app/module/**").permitAll()
                .antMatchers("/api/user/**").permitAll()
            .anyRequest().authenticated()
            .and()
            // Иначе нужно реализовывать свой фильтр, который будет отлавливать
            // наличие в заголовке http-запроса чего-то, подтверждающего что запрос
            // ушел от аунтифицированного пользователя
            // Пока что для этого будет использован BasicAuthenticationFilter
            .httpBasic()
                .realmName("REST_REALM")
                .authenticationEntryPoint(authenticationEntryPoint)
            .and()
            // Так как бэкенд позиционируется как REST-сервис,
            // по канонам REST он не должен хранить состояние клиента
            // то есть сессию клиента
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
