package ru.rusquant.ngingot.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

import static ru.rusquant.ngingot.utils.DateSerializer.DATE_PATTERN;

@Configuration // Говорит о том, что данный класс является java-конфигом
@EnableWebMvc // разрешает использование MVC
@PropertySource(value = "classpath:application.properties") // Расположение пропертей приложения
@ComponentScan({
        "ru.rusquant.ngingot.controller",
        "ru.rusquant.ngingot.service"
}) // Указывает где искать Spring-бины
@EnableTransactionManagement // Активируем управление транзациями при помощи аннотаций
@EnableJpaRepositories("ru.rusquant.ngingot.repository") // Расположение репозиториев для общения с базой
public class ApplicationContextConfig extends WebMvcConfigurerAdapter {

    @Value(value = "${jdbc.datasource.url}")
    private String jdbcDatabaseUrl;

    @Value(value = "${jdbc.datasource.driverClassName}")
    private String jdbcDatabaseDriver;

    @Value(value = "${jdbc.datasource.username}")
    private String jdbcDatabaseUser;

    @Value(value = "${jdbc.datasource.password}")
    private String jdbcDatabasePassword;

    @Value(value = "${jpa.hibernate.dialect}")
    private String jpaHibernateDialect;

    @Value(value = "${jpa.hibernate.hbm2ddl.auto}")
    private String dbSchemaMode;

    @Value(value = "${jpa.hibernate.showSQL}")
    private Boolean showSql;

    @Value(value = "${jpa.hibernate.maxFetchDepth}")
    private Integer maxFetchDepth;


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

    /**
     *  Конфигурируем Jackson MapperBuilder
     *  @See https://spring.io/blog/2014/12/02/latest-jackson-integration-improvements-in-spring
     **/
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

        // Регитсрация собственных типов Hibernate (в том числе lazy-loading обёрток)
        Hibernate5Module hibernateModule = new Hibernate5Module();
        hibernateModule.disable(Hibernate5Module.Feature.FORCE_LAZY_LOADING);
        hibernateModule.enable(Hibernate5Module.Feature.FORCE_LAZY_LOADING);
        builder.modules(hibernateModule);

        ObjectMapper mapper = builder.build();
        mapper.configOverride(java.util.Date.class)
                .setFormat(JsonFormat.Value.forPattern(DATE_PATTERN));
        mapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);

        converters.add(new MappingJackson2HttpMessageConverter(mapper));
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(jdbcDatabaseUrl);
        dataSource.setDriverClassName(jdbcDatabaseDriver);
        dataSource.setUsername(jdbcDatabaseUser);
        dataSource.setPassword(jdbcDatabasePassword);

        // Создаем схему данных и инсертим некоторые начальные данные
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(false);
        populator.addScript(new ClassPathResource("dbschema.sql"));
        populator.addScript(new ClassPathResource("data.sql"));

        DatabasePopulatorUtils.execute(populator, dataSource);
        return dataSource;
    }


    /** Управление ORM **/
    @Autowired
    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", jpaHibernateDialect);
        properties.setProperty("hibernate.show_sql", String.valueOf(showSql));
        if(showSql != null && showSql) {
            properties.setProperty("hibernate.format_sql", String.valueOf(showSql));
        }

        // В качестве нашего JPA-вендора будет выступать хибер
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        // Эта настрока нужна при работе с боевой базой, созданной руками
        // Иначе хибернэйт уничтожит все труды БД-шника :)
        // vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaProperties(properties);
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("ru.rusquant.ngingot.domain");
        factory.setDataSource(dataSource);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    /** Управление транзакциями **/
    @Autowired
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
