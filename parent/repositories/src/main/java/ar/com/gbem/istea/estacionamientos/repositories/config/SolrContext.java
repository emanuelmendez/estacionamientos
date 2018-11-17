package ar.com.gbem.istea.estacionamientos.repositories.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;

@Configuration
public class SolrContext {

	@Value("${spring.data.solr.host}")
	private String solrHost;

	@Bean
	public SolrClient solrClient() {
		return new HttpSolrClient.Builder(solrHost).build();
	}

	@Bean
	public SolrTemplate solrTemplate(SolrClient client) throws Exception {
		return new SolrTemplate(client);
	}
}
