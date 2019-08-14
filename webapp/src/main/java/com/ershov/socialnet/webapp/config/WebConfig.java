package com.ershov.socialnet.webapp.config;

import com.ershov.socialnet.common.enums.PhoneType;
import com.ershov.socialnet.webapp.convertors.PhoneTypeEditor;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

@ImportResource({"classpath*:/WEB-INF/security.xml"})
@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final long MAX_UPLOAD_FILE_SIZE_IN_BYTES = 10485760;

    @Value("${spring.mvc.view.prefix}")
    private String location;

    @Value("${spring.mvc.view.suffix}")
    private String suffix;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix(location);
        resolver.setSuffix(suffix);
        return resolver;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(MAX_UPLOAD_FILE_SIZE_IN_BYTES);
        return resolver;
    }

    @Bean
    public static CustomEditorConfigurer customEditorConfigurer() {
        CustomEditorConfigurer configurer = new CustomEditorConfigurer();
        Map<Class<?>, Class<? extends PropertyEditor>> customEditors = new HashMap<>();
        customEditors.put(PhoneType.class, PhoneTypeEditor.class);
        configurer.setCustomEditors(customEditors);
        return configurer;
    }

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();
                resource.setName("jdbc/socnet");
                resource.setType("javax.sql.DataSource");
                resource.setAuth("Container");
                resource.setProperty("username", "bbb5523adc7727");
                resource.setProperty("password", "c669d157");
                resource.setProperty("url", "jdbc:mysql://us-cdbr-iron-east-04.cleardb.net:3306/heroku_e005d7733619d8f?reconnect=true&characterEncoding=utf8");
                resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
                resource.setProperty("driverClassName", "com.mysql.jdbc.Driver");
                resource.setProperty("maxTotal", "10");
                resource.setProperty("maxIdle", "5");
                resource.setProperty("maxWaitMillis", "10000");
                resource.setProperty("testOnBorrow", "true");
                resource.setProperty("validationQuery", "SELECT 1");
                resource.setProperty("removeAbandoned", "true");
                context.getNamingResources().addResource(resource);
            }
        };
    }
}