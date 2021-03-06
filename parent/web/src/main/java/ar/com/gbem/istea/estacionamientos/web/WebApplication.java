package ar.com.gbem.istea.estacionamientos.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@SpringBootApplication
@ComponentScan({ "ar.com.gbem.istea.estacionamientos" })
@EntityScan("ar.com.gbem.istea.estacionamientos.repositories")
@EnableJpaRepositories("ar.com.gbem.istea.estacionamientos.repositories")
@EnableSolrRepositories("ar.com.gbem.istea.estacionamientos.repositories")
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
