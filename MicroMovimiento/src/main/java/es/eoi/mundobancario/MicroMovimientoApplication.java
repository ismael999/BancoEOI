package es.eoi.mundobancario;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages = "es.eoi.mundobancario")
@EnableDiscoveryClient
@EnableFeignClients
public class MicroMovimientoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroMovimientoApplication.class, args);
	}

	@Bean
	public ModelMapper modelMApper() {
		return new ModelMapper();
	}
}
