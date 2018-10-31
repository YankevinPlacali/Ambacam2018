package com.gemini.ambacam;

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

import com.gemini.ambacam.configuration.AppSettings;
import com.gemini.ambacam.model.Action;
import com.gemini.ambacam.model.Autorite;
import com.gemini.ambacam.model.Log;
import com.gemini.ambacam.model.LogActeur;
import com.gemini.ambacam.model.MotifSuppression;
import com.gemini.ambacam.model.Operateur;
import com.gemini.ambacam.model.Passport;
import com.gemini.ambacam.model.Pays;
import com.gemini.ambacam.model.Recepisse;
import com.gemini.ambacam.model.Requerant;
import com.gemini.ambacam.model.Requete;
import com.gemini.ambacam.model.RequeteGroupe;
import com.gemini.ambacam.model.Role;
import com.gemini.ambacam.model.StatusRequete;
import com.gemini.ambacam.model.TypeRequete;
import com.gemini.ambacam.repository.OperateurRepository;
import com.gemini.ambacam.repository.PaysRepository;
import com.gemini.ambacam.transfert.operateurs.Operateur2OperateurCreatedTO;
import com.gemini.ambacam.transfert.operateurs.Operateur2OperateurUpdateTO;
import com.gemini.ambacam.transfert.operateurs.OperateurCreateTO;
import com.gemini.ambacam.transfert.operateurs.OperateurUpdateTO;
import com.gemini.ambacam.transfert.passports.PassportCreateTO;
import com.gemini.ambacam.transfert.recepisses.RecepisseCreateTO;
import com.gemini.ambacam.transfert.recepisses.RecepisseUpdateTO;
import com.gemini.ambacam.transfert.requetegroupes.RequeteGroupeCreateTO;
import com.gemini.ambacam.transfert.requetegroupes.RequeteGroupeUpdateTO;
import com.gemini.ambacam.utils.RequeteGroupeUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ItBase {

	protected Random random = new Random();

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

	}

	public void cleanup() throws Exception {

		operateurRepository.deleteAll();
		paysRepository.deleteAll();
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

	protected Passport buildPassport(String numero, Date dateDelivrance, String lieuDelivrance, Autorite autorite,
			Requete requete) {
		Passport passport = new Passport();
		passport.setNumero(numero);
		passport.setDateDelivrance(dateDelivrance);
		passport.setLieuDelivrance(lieuDelivrance);
		passport.setDateExpiration(new Date(dateDelivrance.getTime() + random.nextLong()));
		passport.setDelivrePar(autorite);
		passport.setUrlPhoto("photoUrl-" + random.nextLong());
		passport.setRequete(requete);

		return passport;
	}

	protected Passport buildPassport(Autorite autorite, Requete requete) {
		return buildPassport("numero-" + random.nextLong(), new Date(), "lieuDelivrance-" + random.nextLong(), autorite,
				requete);
	}

	protected PassportCreateTO buildPassportCreateTO(String numero, Date dateDelivrance, Date dateExpiration,
			String lieuDelivrance, Long autoriteId) {
		return new PassportCreateTO(numero, dateDelivrance, dateExpiration, lieuDelivrance, autoriteId);
	}

	protected PassportCreateTO buildPassportCreateTO(Long autoriteId) {
		return new PassportCreateTO("numero-" + random.nextLong(), new Date(), new Date(),
				"lieuDelivrance-" + random.nextLong(), autoriteId);
	}
}
