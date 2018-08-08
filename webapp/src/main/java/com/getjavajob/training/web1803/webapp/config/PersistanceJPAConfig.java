package com.getjavajob.training.web1803.webapp.config;


import com.getjavajob.training.web1803.service.AccountService;
import com.getjavajob.training.web1803.service.GroupService;
import com.getjavajob.training.web1803.service.MessageService;
import com.getjavajob.training.web1803.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories("com.getjavajob.training.web1803.dao")
@Configuration
@ImportResource({"classpath*:context.xml"})
@EnableConfigurationProperties
public class PersistanceJPAConfig {

    @Autowired
    private Environment environment;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("com.getjavajob.training.web1803.common", "com.getjavajob.training.web1803.common.enums");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setJpaProperties(jpaProperties());
        return emf;
    }

    @Bean(destroyMethod="")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() throws NamingException {
        final JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        lookup.setResourceRef(true);
        return lookup.getDataSource("jdbc/socnet");
//        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
//        bean.setJndiName("jdbc/socnet");
//        bean.setProxyInterface(DataSource.class);
//        bean.setLookupOnStartup(false);
//        bean.afterPropertiesSet();
//        return (DataSource) bean.getObject();

//        Context ctx;
//        DataSource dataSource;
//        Hashtable<String, String> ht;
//
//        ht = new Hashtable<>();
//        ht.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
//        ctx = new InitialContext(ht);
//
//        dataSource = (DataSource) ctx.lookup("jdbc/socnet");
//        return dataSource;

//        Context initContext = new InitialContext();
//        Context envContext  = (Context) initContext.lookup("java:/comp/env");
//        DataSource datasource = (DataSource) envContext.lookup("jdbc/socnet");
//        return datasource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.id.new_generator_mappings", "false");
        return properties;
    }

    @Bean
    public AccountService accountService() {
        return new AccountService();
    }

    @Bean
    public GroupService groupService() {
        return new GroupService();
    }

    @Bean
    public MessageService messageService() {
        return new MessageService();
    }

    @Bean
    public RelationshipService relationshipService() {
        return new RelationshipService();
    }
}