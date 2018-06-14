package com.ambacam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.boot.system.EmbeddedServerPortFileWriter;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class AmbacamBackendApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AmbacamBackendApplication.class);
	}

	public static void main(String[] args) {
		// SpringApplication.run(AmbacamBackendApplication.class, args);
		SpringApplication app = new SpringApplication(AmbacamBackendApplication.class);

		// add pid / port file writers
		app.addListeners(new ApplicationPidFileWriter());
		app.addListeners(new EmbeddedServerPortFileWriter());

		// run application
		app.run(args);
	}
}
