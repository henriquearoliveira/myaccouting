package br.com.contability.configuracao;

import javax.sql.DataSource;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("cloud")
@Configuration
public class CloudSourceConfiguration extends AbstractCloudConfig {
	
	@Bean
    public DataSource dataSource() {
        return connectionFactory().dataSource();
    }

	/*@Bean
	public DataSource dataSource() {
		CloudFactory cloudFactory = new CloudFactory();
		Cloud cloud = cloudFactory.getCloud();
		String serviceID = cloud.getServiceInfos().toString();
		return cloud.getServiceConnector(serviceID, DataSource.class, null);
	}*/
	
	/*@Bean
    public DataSource datasSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost/db");
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        return dataSource;
    }*/

	/*
	 * @Bean public DataSource dataSource() { return
	 * cloud().getSingletonServiceConnector(DataSource.class, null); }
	 */

}
