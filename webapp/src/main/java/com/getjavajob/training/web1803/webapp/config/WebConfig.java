package com.getjavajob.training.web1803.webapp.config;

import com.getjavajob.training.web1803.common.enums.PhoneType;
import com.getjavajob.training.web1803.webapp.convertors.PhoneTypeEditor;
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
    private final static long MAX_UPLOAD_FILE_SIZE_IN_BYTES = 10485760;

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
    static public CustomEditorConfigurer customEditorConfigurer() {
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
                resource.setProperty("username", "root");
                resource.setProperty("password", "12345678");
                resource.setProperty("url", "jdbc:mysql://localhost:3306/gjj_social_network?verifyServerCertificate=false" +
                        "&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&useUnicode=true" +
                        "&characterEncoding=utf8");
                resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
                resource.setProperty("driverClassName", "com.mysql.jdbc.Driver");
                context.getNamingResources().addResource(resource);
            }
        };
    }
}