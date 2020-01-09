//package com.blaife.config;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * 定时任务数据源配置 task
// */
//@Configuration
//@MapperScan(basePackages = "com.blaife.mapper.task", sqlSessionTemplateRef = "taskSqlSessionTemplate")
//public class DataSourceTaskConfig {
//
//    /**
//     * 第一层 DataSource
//     * @return
//     */
//    @Bean(name = "taskDataSourse")
//    @ConfigurationProperties(prefix = "spring.datasource.task")
//    public DataSource taskDataSourse() {
//        return DataSourceBuilder.create().build();
//    }
//
//    /**
//     * 第二层 SqlSessionFactory
//     * @param dataSource
//     * @return
//     * @throws Exception
//     */
//    @Bean(name = "taskSqlSessionFactory")
//    public SqlSessionFactory taskSqlSessionFactory(@Qualifier("taskDataSourse") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/mapper/task/*.xml"));
//        return bean.getObject();
//    }
//
//    /**
//     * 第三层 创建事务
//     * @param dataSource
//     * @return
//     */
//    @Bean(name = "taskTransactionManager")
//    public DataSourceTransactionManager taskTransactionManager(@Qualifier("taskDataSourse") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    /**
//     * 第四层 包装到SqlSessionTemplate
//     * @param sqlSessionFactory
//     * @return
//     */
//    @Bean(name = "taskSqlSessionTemplate")
//    public SqlSessionTemplate taskSqlSessionTemplate(@Qualifier("taskSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//}
