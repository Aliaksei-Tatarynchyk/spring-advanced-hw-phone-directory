package com.epam.phone.directory;

import static java.util.Collections.singletonList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/*
 *  https://spring.io/guides/gs/testing-web/
 *  @SpringBootApplication is a convenience annotation that adds all of the following:
 *   - @Configuration tags the class as a source of bean definitions for the application context.
 *   - @EnableAutoConfiguration tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
 *   - Normally you would add @EnableWebMvc for a Spring MVC app, but Spring Boot adds it automatically when it sees spring-webmvc on the classpath.
 *     This flags the application as a web application and activates key behaviors such as setting up a DispatcherServlet.
 *   - @ComponentScan tells Spring to look for other components, configurations, and services in the package, allowing it to find the WebController.
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
public class Application {

    // Need for correct work of importing JSP taglib to Freemarker - <#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
    public Application(FreeMarkerConfigurer freeMarkerConfigurer) {
        freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(singletonList("/META-INF/security.tld"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
