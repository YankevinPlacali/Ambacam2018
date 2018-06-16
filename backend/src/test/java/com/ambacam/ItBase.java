package com.ambacam;

import java.util.Map;
import java.util.Random;

import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.ambacam.model.Action;
import com.ambacam.model.Autorite;
import com.ambacam.model.Log;
import com.ambacam.model.LogActeur;
import com.ambacam.model.MotifSuppression;
import com.ambacam.model.Role;
import com.ambacam.model.StatusRequete;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ItBase {
	protected Random random = new Random();

	@LocalServerPort
	protected int port;

	public void setup() throws Exception {
		RestAssured.port = port;

	}

	public void cleanup() throws Exception {

	}

	protected Role buildRole() {

		Role role = new Role().nom("nom-" + random.nextLong()).description("description-" + random.nextLong());
		return role;
	}

	protected StatusRequete buildStatusRequete() {

		StatusRequete statusRequete = new StatusRequete().nom("nom-" + random.nextLong())
				.description("description-" + random.nextLong());
		return statusRequete;
	}

	protected MotifSuppression buildMotifSuppression() {

		MotifSuppression motifSuppression = new MotifSuppression().nom("nom-" + random.nextLong())
				.description("description-" + random.nextLong());
		return motifSuppression;
	}

	protected Action buildAction() {

		Action action = new Action().nom("nom-" + random.nextLong()).description("description-" + random.nextLong());
		return action;
	}

	protected Log buildLog(LogActeur acteurActif, LogActeur acteurPassif, Action action,
			MotifSuppression motifSuppression) {
		Log log = new Log(acteurActif, acteurActif, action, motifSuppression);
		return log;
	}

	protected LogActeur buildLogActeur(Object acteur, Map<String, String> properties) {

		LogActeur item = new LogActeur();
		item.setActeur(acteur);
		item.setProperties(properties);

		return item;
	}

	protected Autorite buildAutorite() {

		Autorite autorite = new Autorite().nom("nom-" + random.nextLong()).prenom("prenom-" + random.nextLong())
				.poste("poste-" + random.nextLong());
		return autorite;
	}
}
