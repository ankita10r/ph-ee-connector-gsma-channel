package org.mifos.connector;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "GSMA Channel connector APIs"))
public class GsmaChannelApplication {

	public static void main(String[] args) {
		SpringApplication.run(GsmaChannelApplication.class, args);
	}

}
