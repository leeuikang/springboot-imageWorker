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
@MapperScan(value="com.example.image.db.dao.one", sqlSessionFactoryRef="oneSqlSessionFactory")
@RequiredArgsConstructor
public class OneMariaDBConfiguration {

    private final Environment env;

    private final ApplicationContext applicationContext;

    private final DBConfig dbConfiguration;

    @Bean(name = "oneDataSource")
    public DataSource oneDataSource() {
        HikariConfig hikariConfig = dbConfiguration.getHikariConfig();
        hikariConfig.setJdbcUrl(env.getProperty("spring.datasource-one.url"));
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "oneSqlSessionFactory")
    public SqlSessionFactory oneSqlSessionFactory(@Qualifier("oneDataSource") DataSource oneDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(oneDataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/config/maria.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/config/mybatis/image.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSession")
    public SqlSessionTemplate oneSqlSessionTemplate(SqlSessionFactory oneSqlSessionFactory) {

        return new SqlSessionTemplate(oneSqlSessionFactory);
    }
}
