package es.eoi.mundobancario;

import javax.persistence.EntityManagerFactory;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
@EnableScheduling
public class MundoBancarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MundoBancarioApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Bean
	public PlatformTransactionManager transactionManager()
	{
	    return new JpaTransactionManager(entityManagerFactory);
	}
}
