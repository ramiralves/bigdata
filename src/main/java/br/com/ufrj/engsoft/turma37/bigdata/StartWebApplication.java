package br.com.ufrj.engsoft.turma37.bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("br.com.ufrj.engsoft.turma37.bigdata")
@org.springframework.scheduling.annotation.EnableScheduling
public class StartWebApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(StartWebApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		/*
		 * Logger root =
		 * (Logger)LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
		 * root.setLevel(Level.OFF);
		 */
        return builder.sources(StartWebApplication.class);
    }

}