package br.com.ufrj.engsoft.turma37.bigdata.model.dao;

import javax.sql.DataSource;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@ComponentScan(basePackages = {"br.com.ufrj.engsoft.turma37.bigdata.model","br.com.ufrj.engsoft.turma37.bigdata.util.file.io","br.com.ufrj.engsoft.turma37.bigdata.util.xml.sql"})
@PropertySource("classpath:application.properties")
public class AppConfig {
	
	@Value("${spring.datasource.url}")
    private String url;
	
	@Value("${spring.datasource.username}")
    private String username;
	
	@Value("${spring.datasource.password}")
    private String password;	


    @Bean(name = "jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(getDatasource());
    }	
    
    @Bean(name = "namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(){
        return new NamedParameterJdbcTemplate(getDatasource());
    } 
    
    @Bean    
    public DataSource getDatasource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    } 
    
	@Value("${spring.spark.app.name}")
    private String appName;
		
	//local  => somente a maquina, sem uso de clusters
	//[*] => todos os cores do processador em questão
	@Value("${spring.spark.master.uri:local[*]}")
    private String masterUri;
    
    @Value("${spring.spark.home}")
    private String sparkHome;

    @Value("${spring.spark.warehouse.dir")
    private String warehouse;
       
    @Bean
    public SparkConf sparkConf() {
        SparkConf sparkConf = new SparkConf()
					                .setAppName(appName)
					                .setSparkHome(sparkHome)
					                .setMaster(masterUri);
         
        return sparkConf;
    }

    @Bean
    public JavaSparkContext javaSparkContext() {
        return new JavaSparkContext(sparkConf());
    }

    @Bean
    public SparkSession sparkSession() {
        return SparkSession
        			.builder()
	                .sparkContext(javaSparkContext().sc())
	                .appName(appName)
	                .config("spark.sql.warehouse.dir",warehouse)
	                .getOrCreate();
    }    
    	
}
