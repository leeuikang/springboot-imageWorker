package com.example.image.db.db;

import javax.sql.DataSource;

import com.example.image.db.DBConfig;
import lombok.RequiredArgsConstructor;
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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(value="com.example.image.db.dao.two", sqlSessionFactoryRef="twoSqlSessionFactory")
@RequiredArgsConstructor
public class TwoMariaDBConfiguration {

    private final Environment env;
    private final ApplicationContext applicationContext;
    private final DBConfig dbConfiguration;

    @Bean(name = "twoDataSource")
    public DataSource twoDataSource() {
        HikariConfig hikariConfig = dbConfiguration.getHikariConfig();
        hikariConfig.setJdbcUrl(env.getProperty("spring.datasource-two.url"));
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "twoSqlSessionFactory")
    public SqlSessionFactory lgEg2SqlSessionFactory(@Qualifier("twoDataSource") DataSource twoDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(twoDataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/config/maria.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/config/mybatis/image.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSession2")
    public SqlSessionTemplate lgEg2SqlSessionTemplate(SqlSessionFactory twoSqlSessionFactory) {

        return new SqlSessionTemplate(twoSqlSessionFactory);
    }
}
