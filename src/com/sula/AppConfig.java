package com.sula;

import com.sula.business.custom.CustomerBO;
import com.sula.business.custom.ItemBO;
import com.sula.business.custom.OrderBO;
import com.sula.business.custom.impl.CustomerBOImpl;
import com.sula.db.HibernateUtil;
import com.sula.dto.CustomerDTO;
import com.sula.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.sula")
//@PropertySource(value = { "classpath:application.properties" },ignoreResourceNotFound=true) //If the property file is not found, then we will get FileNotFoundException. Sometimes we don’t want to throw exception because our application can work with default values too
public class AppConfig {

   /* @Bean("meberdaoimpl")
    public MemberDAOImpl memberdaoimpl(){
        return new MemberDAOImpl();
    }*/

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        /*DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(env.getRequiredProperty("jdbc.password"));*/

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/library");
        dataSource.setUsername("root");
        dataSource.setPassword("");


        return dataSource;
    }
    


    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        return jdbcTemplate;
    }


    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Session getSession(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        return session;
    }

   /* @Bean
    public ItemBO itemBO() {
        ItemBO itemBO = Appinitializer.ctx.getBean(ItemBO.class);
        return itemBO;
    } //it is illegal to define ItemBO Bean here as you have mentioned @Component on the ItemBOImpl Class. If you do so there are 2 beans
    of same type in the context and therefore spring will get an ambiguity issue*/


   @Bean
    public HibernateTemplate hibernateTemplate(){
       HibernateTemplate hibernateTemplate=new HibernateTemplate();
       hibernateTemplate.setSessionFactory(HibernateUtil.getSessionFactory());
       return hibernateTemplate;
   }

}
