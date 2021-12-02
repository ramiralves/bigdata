package br.com.ufrj.engsoft.turma37.bigdata.model.spark.process.log;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"br.com.ufrj.engsoft.turma37.bigdata.model.spark.process","br.com.ufrj.engsoft.turma37.bigdata.util.file.io"})
@PropertySource("classpath:application.properties")
public class AppConfig {
	
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
