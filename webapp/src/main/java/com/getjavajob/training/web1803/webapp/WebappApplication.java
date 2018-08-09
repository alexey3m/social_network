package com.getjavajob.training.web1803.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.servlet.ServletContext;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = {"com.getjavajob.training.web1803.*"})
@EnableJpaRepositories(basePackages = "com.getjavajob.training.web1803.dao")
public class WebappApplication extends SpringBootServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        servletContext.setInitParameter("com.sun.faces.expressionFactory", "org.apache.el.ExpressionFactoryImpl");
    }

    public static void main(String[] args) {
        SpringApplication.run(WebappApplication.class, args);
    }
}