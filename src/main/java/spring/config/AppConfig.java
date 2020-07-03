package spring.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    @Qualifier("bazaArchiwumPath")
    public String getbazaArchiwumPath() {
        return "C:\\fd\\Baza archiwum AB.xls";
//        return "\\\\archiwum\\baza archiwum\\Baza archiwum AB.xls";
    }

    @Bean
    @Qualifier("resultCopiesDirPath")
    public String getresultCopiesDirPath() {
        return "C:\\fd\\result copies\\";
    }
}
