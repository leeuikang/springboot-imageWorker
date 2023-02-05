package com.example.image.db.db;

import com.example.image.db.DBConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(prefix = "spring.", name = "datasource-lg-eg4.use", havingValue = "true", matchIfMissing = false)
@MapperScan(value="com.example.wspider_image.db.dao.lg.lg4", sqlSessionFactoryRef="lgEg4SqlSessionFactory")
public class FourMariaDBConfiguration {
    @Autowired
    Environment env;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DBConfig dbConfiguration;

    @Bean(name = "fourDataSource")
    public DataSource fourDataSource() {
        HikariConfig hikariConfig = dbConfiguration.getHikariConfig();
        hikariConfig.setJdbcUrl(env.getProperty("spring.datasource-four.url"));
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "fourSqlSessionFactory")
    public SqlSessionFactory fourSqlSessionFactory(@Qualifier("fourDataSource") DataSource fourDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(fourDataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/config/maria.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/config/mybatis/image.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSession4")
    public SqlSessionTemplate fourSqlSessionTemplate(SqlSessionFactory fourSqlSessionFactory) {
        return new SqlSessionTemplate(fourSqlSessionFactory);
    }
}
