package ru.rusquant.ngingot.config;

import org.h2.server.web.WebServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/** Java-аналог дескриптора развертывания web.xml **/
public class SpringWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Создаем и регистрируем контекст нашего вэб-приложения
        // В данном случае используется кофиг через анотации
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ApplicationContextConfig.class);

        // Созаем сервлет-диспатчер приложения на основе контекста
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        // Созадем сервлет для базы H2
        ServletRegistration.Dynamic h2Console = servletContext.addServlet("h2Console", new WebServlet());
        h2Console.setLoadOnStartup(2);
        h2Console.addMapping("/admin/h2/*");
    }
}
