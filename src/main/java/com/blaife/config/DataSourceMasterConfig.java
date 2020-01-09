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
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * 主数据源配置 master
// */
//@Configuration
//@MapperScan(basePackages = "com.blaife.mapper.master", sqlSessionTemplateRef = "masterSqlSessionTemplate")
//public class DataSourceMasterConfig {
//
//    /**
//     * 第一层 DataSource
//     * @return
//     */
//    @Bean(name = "masterDataSourse")
//    @ConfigurationProperties(prefix = "spring.datasource.master")
//    @Primary // 设置为主库
//    public DataSource masterDataSourse() {
//        return DataSourceBuilder.create().build();
//    }
//
//    /**
//     * 第二层 SqlSessionFactory
//     * @param dataSource
//     * @return
//     * @throws Exception
//     */
//    @Bean(name = "masterSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSourse") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/mapper/master/*.xml"));
//        return bean.getObject();
//    }
//
//    /**
//     * 第三层 创建事务
//     * @param dataSource
//     * @return
//     */
//    @Bean(name = "masterTransactionManager")
//    @Primary
//    public DataSourceTransactionManager masterTransactionManager(@Qualifier("masterDataSourse") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//
//    /**
//     * 第四层 包装到SqlSessionTemplate
//     * @param sqlSessionFactory
//     * @return
//     */
//    @Bean(name = "masterSqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//
//
//
//}
