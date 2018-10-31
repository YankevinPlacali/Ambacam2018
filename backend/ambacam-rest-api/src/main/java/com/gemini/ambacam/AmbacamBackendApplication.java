package com.gemini.ambacam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

import com.gemini.ambacam.configuration.AppSettings;
import com.gemini.ambacam.model.Operateur;
import com.gemini.ambacam.model.Pays;
import com.gemini.ambacam.repository.OperateurRepository;
import com.gemini.ambacam.repository.PaysRepository;

@EnableEurekaClient
@SpringBootApplication
public class AmbacamBackendApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(AmbacamBackendApplication.class, args);
		createAdminOperateur(ctx);
	}

	public static void createAdminOperateur(ApplicationContext ctx) {
		OperateurRepository operateurRepository = ctx.getBean(OperateurRepository.class);
		PaysRepository paysRepository = ctx.getBean(PaysRepository.class);
		AppSettings appSettings = ctx.getBean(AppSettings.class);

		Pays pays = null;
		if (paysRepository.count() != 0) {
			pays = paysRepository.findAll().get(0);
		} else {
			pays = new Pays().nom(appSettings.getDefaultCountryName())
					.description(appSettings.getDefaultCountryDescription());
			pays = paysRepository.save(pays);
		}

		Operateur operateur = new Operateur().nom(appSettings.getDefaultOperateurLastname())
				.prenom(appSettings.getDefaultOperateurFirstname()).username(appSettings.getDefaultOperateurUsername())
				.password(appSettings.getDefaultOperateurPassword()).sexe(appSettings.getDefaultOperateurGender())
				.nationalite(pays);

		if (operateurRepository.count() == 0) {
			operateur = operateurRepository.save(operateur);
		}
	}
}
