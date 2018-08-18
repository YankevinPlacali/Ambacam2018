package com.ambacam;

import static io.restassured.RestAssured.given;

import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.ws.rs.core.HttpHeaders;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.ambacam.configuration.AppSettings;
import com.ambacam.model.Action;
import com.ambacam.model.Autorite;
import com.ambacam.model.Log;
import com.ambacam.model.LogActeur;
import com.ambacam.model.MotifSuppression;
import com.ambacam.model.Operateur;
import com.ambacam.model.Pays;
import com.ambacam.model.Recepisse;
import com.ambacam.model.Requerant;
import com.ambacam.model.Requete;
import com.ambacam.model.RequeteGroupe;
import com.ambacam.model.Role;
import com.ambacam.model.StatusRequete;
import com.ambacam.model.TypeRequete;
import com.ambacam.repository.OperateurRepository;
import com.ambacam.repository.PaysRepository;
import com.ambacam.rest.ApiConstants;
import com.ambacam.transfert.operateurs.Operateur2OperateurCreatedTO;
import com.ambacam.transfert.operateurs.Operateur2OperateurUpdateTO;
import com.ambacam.transfert.operateurs.OperateurCreateTO;
import com.ambacam.transfert.operateurs.OperateurUpdateTO;
import com.ambacam.transfert.recepisses.RecepisseCreateTO;
import com.ambacam.transfert.recepisses.RecepisseUpdateTO;
import com.ambacam.transfert.requetegroupes.RequeteGroupeCreateTO;
import com.ambacam.transfert.requetegroupes.RequeteGroupeUpdateTO;
import com.ambacam.utils.RequeteGroupeUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ItBase {

	protected String AUTH_COLLECTION_PATH = "/oauth/token?grant_type=password&username=%s&password=%s";
	protected Random random = new Random();
	protected String token;
	protected Operateur defaultOperateur;
	protected Pays defaultPays;

	protected RequestSpecification preLoadedGiven;

	@LocalServerPort
	protected int port;

	@Autowired
	protected OperateurRepository operateurRepository;
	@Autowired
	protected PaysRepository paysRepository;
	@Autowired
	protected AppSettings appSettings;

	public void setup() throws Exception {
		RestAssured.port = port;

		// authenticate a default operateur
		authenticate();

		// a given() function pre-loaded with token
		preLoadedGiven = given().header("Authorization", String.format("Bearer %s", token));

	}

	private void authenticate() {

		// create and save pays
		defaultPays = paysRepository.save(new Pays().nom(UUID.randomUUID().toString()));

		// create and save operateur
		defaultOperateur = operateurRepository.save(new Operateur().nom("defaultOperateur")
				.nationalite(defaultPays).sexe("f").username("operateur-" + UUID.randomUUID().toString())
				.password("password-" + UUID.randomUUID().toString()));
		token = given().auth().basic(appSettings.getAuthorizationUsername(), appSettings.getAuthorizationSecret())
				.when()
				.post(String.format(AUTH_COLLECTION_PATH, defaultOperateur.getUsername(),
						defaultOperateur.getPassword()))
				.then().log().body().statusCode(200).extract().body().path("access_token");

	}

	public void cleanup() throws Exception {

		// revoke the generated token
		revoke();

		operateurRepository.deleteAll();
		paysRepository.deleteAll();
	}

	private void revoke() {

		given().header("Authorization", String.format("Bearer %s", token)).delete(ApiConstants.REVOKE_TOKEN).then()
				.statusCode(200);
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

	protected Requerant buildRequerant() {
		return new Requerant().nom("nom-" + UUID.randomUUID()).prenom("prenom-" + UUID.randomUUID())
				.dateNaissance(new Date()).sexe("sexe-" + UUID.randomUUID())
				.profession("profession-" + UUID.randomUUID()).lieuNaissance("lieuNaissance-" + UUID.randomUUID());

	}

	protected RequeteGroupe buildRequeteGroupe(String nom, String description) {
		return new RequeteGroupe().nom(nom).description(description);
	}

	protected RequeteGroupe buildRequeteGroupe() {
		return buildRequeteGroupe(RequeteGroupeUtils.generateRequeteGroupeName(), "description-" + UUID.randomUUID());
	}

	protected RequeteGroupeCreateTO buildRequeteGroupeCreateTO(String description) {
		return new RequeteGroupeCreateTO().description(description);
	}

	protected RequeteGroupeCreateTO buildRequeteGroupeCreateTO() {
		return buildRequeteGroupeCreateTO("description-" + UUID.randomUUID());
	}

	protected RequeteGroupeUpdateTO buildRequeteGroupeUpdateTO(String description) {
		return new RequeteGroupeUpdateTO().description(description);
	}

	protected RequeteGroupeUpdateTO buildRequeteGroupeUpdateTO() {
		return buildRequeteGroupeUpdateTO("description-" + UUID.randomUUID());
	}

	protected Requete buildRequete() {
		Requete item = new Requete();
		return item;
	}

	protected Recepisse buildRecepisse() {
		Recepisse item = new Recepisse();
		item.setNumero(random.nextLong());
		return item;
	}

	protected RecepisseCreateTO buildRecepisseCreateTO(Long numero) {
		return new RecepisseCreateTO().numero(numero);
	}

	protected RecepisseUpdateTO buildRecepisseUpdateTO() {
		return new RecepisseUpdateTO().numero(random.nextLong());
	}

}
