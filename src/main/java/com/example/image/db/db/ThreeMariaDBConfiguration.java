package com.example.image.db.db;

import com.example.image.db.DBConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
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

import javax.sql.DataSource;

@Configuration
@MapperScan(value="com.example.image.db.dao.three", sqlSessionFactoryRef="threeSqlSessionFactory")
@RequiredArgsConstructor
public class ThreeMariaDBConfiguration {

    private final Environment env;

    private final ApplicationContext applicationContext;
    private final DBConfig dbConfiguration;

    @Bean(name = "threeDataSource")
    public DataSource threeDataSource() {
        HikariConfig hikariConfig = dbConfiguration.getHikariConfig();
        hikariConfig.setJdbcUrl(env.getProperty("spring.datasource-three.url"));
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "threeSqlSessionFactory")
    public SqlSessionFactory threeSqlSessionFactory(@Qualifier("threeDataSource") DataSource threeDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(threeDataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/config/maria.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/config/mybatis/image.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSession3")
    public SqlSessionTemplate threeSqlSessionTemplate(SqlSessionFactory threeSqlSessionFactory) {

        return new SqlSessionTemplate(threeSqlSessionFactory);
    }
}
