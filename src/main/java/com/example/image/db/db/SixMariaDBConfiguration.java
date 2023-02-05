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
@ConditionalOnProperty(prefix = "spring.", name = "datasource-six.use", havingValue = "true", matchIfMissing = false)
@MapperScan(value="com.example.wspider_image.db.dao.six", sqlSessionFactoryRef="sixSqlSessionFactory")
public class SixMariaDBConfiguration {
    @Autowired
    Environment env;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DBConfig dbConfiguration;

    @Bean(name = "sixDataSource")
    public DataSource sixDataSource() {
        HikariConfig hikariConfig = dbConfiguration.getHikariConfig();
        hikariConfig.setJdbcUrl(env.getProperty("spring.datasource-lg-eg6.url"));
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "sixSqlSessionFactory")
    public SqlSessionFactory lgEgSqlSessionFactory(@Qualifier("sixDataSource") DataSource sixDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(sixDataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/config/maria.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/config/mybatis/image.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSession6")
    public SqlSessionTemplate sixSqlSessionTemplate(SqlSessionFactory sixSqlSessionFactory) {

        return new SqlSessionTemplate(sixSqlSessionFactory);
    }
}
