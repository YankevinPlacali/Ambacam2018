package com.ambacam;

import static io.restassured.RestAssured.given;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.ws.rs.core.HttpHeaders;

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
import com.ambacam.model.Operateur;
import com.ambacam.model.Pays;
import com.ambacam.model.Role;
import com.ambacam.model.StatusRequete;
import com.ambacam.model.TypeRequete;
import com.ambacam.transfert.operateurs.Operateur2OperateurCreatedTO;
import com.ambacam.transfert.operateurs.Operateur2OperateurUpdateTO;
import com.ambacam.transfert.operateurs.OperateurCreateTO;
import com.ambacam.transfert.operateurs.OperateurUpdateTO;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

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

	@SuppressWarnings("unchecked")
	public <T> T createResource(T entity, String path, Object... arg1) {
		String location = given().contentType(ContentType.JSON).body(entity).log().body().post(path, arg1).then().log()
				.body().statusCode(201).extract().header(HttpHeaders.LOCATION);
		return (T) given().get(location).then().log().body().statusCode(200).extract().as(entity.getClass());
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

	protected TypeRequete buildTypeRequete() {

		TypeRequete typeRequete = new TypeRequete().nom("nom-" + random.nextLong())
				.description("description-" + random.nextLong());
		return typeRequete;
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

	protected Operateur buildOperateur() {
		Operateur item = new Operateur();
		item.setNom("nom-" + UUID.randomUUID());
		item.setPrenom("prenom-" + UUID.randomUUID());
		item.setSexe("sexe-" + UUID.randomUUID());
		item.setPassword("password-" + UUID.randomUUID());
		item.setUsername("username-" + UUID.randomUUID());
		return item;
	}

	protected OperateurCreateTO buildOperateurCreateTO() {
		return Operateur2OperateurCreatedTO.apply(buildOperateur());
	}

	protected OperateurUpdateTO buildOperateurUpdateTO() {
		return Operateur2OperateurUpdateTO.apply(buildOperateur());
	}

	protected Pays buildPays() {

		Pays pays = new Pays().nom("nom-" + random.nextLong()).description("description-" + random.nextLong());
		return pays;
	}

}
