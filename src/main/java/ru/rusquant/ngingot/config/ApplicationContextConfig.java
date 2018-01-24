package ru.rusquant.ngingot.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

import static ru.rusquant.ngingot.utils.DateSerializer.DATE_PATTERN;

@Configuration // Говорит о том, что данный класс является java-конфигом
@EnableWebMvc // разрешает использование MVC
@PropertySource(value = "classpath:application.properties") // Расположение пропертей приложения
@ComponentScan({
        "ru.rusquant.ngingot.controller",
        "ru.rusquant.ngingot.service"
}) // Указывает где искать Spring-бины
public class ApplicationContextConfig extends WebMvcConfigurerAdapter {

    /** Регистрируем расположение статичных ресурсов: html, css, js... **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/app/**").addResourceLocations("/app/");
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
        registry.addResourceHandler("/vendor/**").addResourceLocations("/vendor/");
    }

    /** Регистрируем бин резолвера представление и говорим где ему искать вьюхи **/
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/");
        viewResolver.setSuffix(".jsp"); // точкой входа являектся index.jsp
        return viewResolver;
    }

    /** Конфигурируем Jackson MapperBuilder **/
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

        ObjectMapper mapper = builder.build();
        mapper.configOverride(java.util.Date.class)
                .setFormat(JsonFormat.Value.forPattern(DATE_PATTERN));
        mapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);

        converters.add(new MappingJackson2HttpMessageConverter(mapper));
    }
}
