package br.com.SistemaControleInsumos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SistemaControleInsumosApplication {

	private static  Logger log = LoggerFactory.getLogger(SistemaControleInsumosApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SistemaControleInsumosApplication.class, args);
		log.info("Aplicação iniciada com sucesso!");
	}

}
