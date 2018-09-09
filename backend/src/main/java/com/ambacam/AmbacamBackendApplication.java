package com.ambacam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ambacam.configuration.AppSettings;
import com.ambacam.model.AutorizationOperateur;
import com.ambacam.model.Operateur;
import com.ambacam.model.Pays;
import com.ambacam.repository.OperateurRepository;
import com.ambacam.repository.PaysRepository;

@SpringBootApplication
public class AmbacamBackendApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AmbacamBackendApplication.class);
	}

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(AmbacamBackendApplication.class, args);
		createAdminOperateur(ctx);
	}

	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, OperateurRepository repository)
			throws Exception {

		builder.userDetailsService(new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return new AutorizationOperateur(repository.findOneByUsername(username));
			}
		});
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
