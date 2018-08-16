package com.ambacam.rest.operateurs.requerants.requetes.passports;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ambacam.ItBase;
import com.ambacam.model.Autorite;
import com.ambacam.model.Operateur;
import com.ambacam.model.Passport;
import com.ambacam.model.Pays;
import com.ambacam.model.Requerant;
import com.ambacam.model.Requete;
import com.ambacam.model.RequeteGroupe;
import com.ambacam.model.StatusRequete;
import com.ambacam.model.TypeRequete;
import com.ambacam.repository.AutoriteRepository;
import com.ambacam.repository.OperateurRepository;
import com.ambacam.repository.PassportRepository;
import com.ambacam.repository.PaysRepository;
import com.ambacam.repository.RequerantRepository;
import com.ambacam.repository.RequeteGroupeRepository;
import com.ambacam.repository.RequeteRepository;
import com.ambacam.repository.StatusRequeteRepository;
import com.ambacam.repository.TypeRequeteRepository;
import com.ambacam.rest.ApiConstants;
import com.ambacam.transfert.passports.PassportCreateTO;

import io.restassured.http.ContentType;

public class PassportsResourceIT extends ItBase {

	@Autowired
	private TypeRequeteRepository typeRequeteRepository;

	@Autowired
	private StatusRequeteRepository statusRequeteRepository;

	@Autowired
	private RequeteGroupeRepository requeteGroupeRepository;

	@Autowired
	private PaysRepository paysRepository;

	@Autowired
	private OperateurRepository operateurRepository;

	@Autowired
	private RequerantRepository requerantRepository;

	@Autowired
	private RequeteRepository requeteRepository;

	@Autowired
	private PassportRepository passportRepository;

	@Autowired
	private AutoriteRepository autoriteRepository;

	private TypeRequete typeRequete;

	private StatusRequete statusRequete;

	private RequeteGroupe requeteGroupe;

	private Pays pays;

	private Operateur operateur;

	private Requerant requerant;

	private Requete requete1;
	private Requete requete2;

	private Passport passport1;
	private Passport passport2;
	private Passport passport3;

	private Autorite autorite;

	@Override
	@Before
	public void setup() throws Exception {
		super.setup();

		typeRequete = typeRequeteRepository.save(buildTypeRequete().nom("type1"));

		statusRequete = statusRequeteRepository.save(buildStatusRequete().nom("status1"));

		pays = paysRepository.save(buildPays());

		operateur = operateurRepository.save(buildOperateur().nom("operateur").nationalite(pays));

		requeteGroupe = buildRequeteGroupe();
		requeteGroupe.setCreePar(operateur);
		requeteGroupe = requeteGroupeRepository.save(requeteGroupe);

		requerant = requerantRepository.save(buildRequerant().nom("requerant").creePar(operateur).nationalite(pays));

		// create requetes
		requete1 = buildRequete();
		requete1.setRequeteGroupe(requeteGroupe);
		requete1.setRequerant(requerant);
		requete1.setType(typeRequete);
		requete1.setStatus(statusRequete);
		requete1.setOperateur(operateur);
		requete1 = requeteRepository.save(requete1);

		requete2 = buildRequete();
		requete2.setRequeteGroupe(requeteGroupe);
		requete2.setRequerant(requerant);
		requete2.setType(typeRequete);
		requete2.setStatus(statusRequete);
		requete2.setOperateur(operateur);
		requete2 = requeteRepository.save(requete2);

		// create autorite
		autorite = autoriteRepository.save(buildAutorite());

		// create passport
		passport1 = passportRepository.save(buildPassport(autorite, requete1));
		passport2 = passportRepository.save(buildPassport(autorite, requete1));
		passport3 = passportRepository.save(buildPassport(autorite, requete2));
	}

	@Override
	@After
	public void cleanup() throws Exception {
		passportRepository.deleteAll();
		requeteRepository.deleteAll();
		requeteGroupeRepository.deleteAll();
		requerantRepository.deleteAll();
		statusRequeteRepository.deleteAll();
		typeRequeteRepository.deleteAll();
		operateurRepository.deleteAll();
		paysRepository.deleteAll();
		autoriteRepository.deleteAll();

		super.cleanup();
	}

	@Test
	public void create() {
		PassportCreateTO createTO = buildPassportCreateTO(autorite.getId());

		int id = preLoadedGiven.contentType(ContentType.JSON).body(createTO).log().body()
				.post(ApiConstants.PASSPORT_COLLECTION, operateur.getId(), requerant.getId(), requete1.getId()).then()
				.log().body().statusCode(200).extract().body().path("id");

		// check that the passport has been saved
		Passport actual = passportRepository.findOne(Integer.toUnsignedLong(id));
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getNumero(), is(equalTo(createTO.getNumero())));
		assertThat(actual.getDateDelivrance().getTime(), is(equalTo(createTO.getDateDelivrance().getTime())));
		assertThat(actual.getDateExpiration().getTime(), is(equalTo(createTO.getDateExpiration().getTime())));
		assertThat(actual.getLieuDelivrance(), is(equalTo(createTO.getLieuDelivrance())));
		assertThat(actual.getDelivrePar().getId(), is(equalTo(autorite.getId())));
		assertThat(actual.getUrlPhoto(), is(nullValue()));
		assertThat(actual.getRequete().getId(), is(equalTo(requete1.getId())));

	}

	@Test
	public void createRequeteNotFound() {

		PassportCreateTO createTO = buildPassportCreateTO(autorite.getId());

		preLoadedGiven.contentType(ContentType.JSON).body(createTO).log().body()
				.post(ApiConstants.PASSPORT_COLLECTION, operateur.getId(), requerant.getId(), random.nextLong()).then()
				.log().body().statusCode(404);
	}

	@Test
	public void createAutoriteNotFound() {

		PassportCreateTO createTO = buildPassportCreateTO(random.nextLong());

		preLoadedGiven.contentType(ContentType.JSON).body(createTO).log().body()
				.post(ApiConstants.PASSPORT_COLLECTION, operateur.getId(), requerant.getId(), requete1.getId()).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createAutoriteIdNull() {

		PassportCreateTO createTO = buildPassportCreateTO(autorite.getId());
		createTO.setNumero(null);

		preLoadedGiven.contentType(ContentType.JSON).body(createTO).log().body()
				.post(ApiConstants.PASSPORT_COLLECTION, operateur.getId(), requerant.getId(), requete1.getId()).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createNumeroNull() {

		PassportCreateTO createTO = buildPassportCreateTO(null);

		preLoadedGiven.contentType(ContentType.JSON).body(createTO).log().body()
				.post(ApiConstants.PASSPORT_COLLECTION, operateur.getId(), requerant.getId(), requete1.getId()).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createDateDelivranceNull() {

		PassportCreateTO createTO = buildPassportCreateTO(autorite.getId());
		createTO.setDateDelivrance(null);

		preLoadedGiven.contentType(ContentType.JSON).body(createTO).log().body()
				.post(ApiConstants.PASSPORT_COLLECTION, operateur.getId(), requerant.getId(), requete1.getId()).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createDateExpirationNull() {

		PassportCreateTO createTO = buildPassportCreateTO(autorite.getId());
		createTO.setDateExpiration(null);

		preLoadedGiven.contentType(ContentType.JSON).body(createTO).log().body()
				.post(ApiConstants.PASSPORT_COLLECTION, operateur.getId(), requerant.getId(), requete1.getId()).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createLieuDelivranceNull() {

		PassportCreateTO createTO = buildPassportCreateTO(null);
		createTO.setLieuDelivrance(null);

		preLoadedGiven.contentType(ContentType.JSON).body(createTO).log().body()
				.post(ApiConstants.PASSPORT_COLLECTION, operateur.getId(), requerant.getId(), requete1.getId()).then()
				.log().body().statusCode(400);
	}

	@Test
	public void get() {
		preLoadedGiven
				.get(ApiConstants.PASSPORT_ITEM, operateur.getId(), requerant.getId(), requete1.getId(),
						passport1.getId())
				.then().log().body().statusCode(200).body("id", is(equalTo(passport1.getId().intValue())))
				.body("numero", is(equalTo(passport1.getNumero())))
				.body("dateDelivrance", is(equalTo(passport1.getDateDelivrance().getTime())))
				.body("dateExpiration", is(equalTo(passport1.getDateExpiration().getTime())))
				.body("lieuDelivrance", is(equalTo(passport1.getLieuDelivrance())))
				.body("delivrePar.id", is(equalTo(autorite.getId().intValue())))
				.body("urlPhoto", is(equalTo(passport1.getUrlPhoto())));
	}

	@Test
	public void getNotFound() {
		preLoadedGiven.get(ApiConstants.PASSPORT_ITEM, operateur.getId(), requerant.getId(), requete1.getId(),
				random.nextLong()).then().statusCode(404);
	}

	@Test
	public void delete() {

		preLoadedGiven.delete(ApiConstants.PASSPORT_ITEM, operateur.getId(), requerant.getId(), requete1.getId(),
				passport1.getId()).then().statusCode(200);

		// check that the passport has been deleted
		Passport actual = passportRepository.findOne(requete1.getId());
		assertThat(actual, is(nullValue()));

	}

	@Test
	public void deleteNotFound() {
		preLoadedGiven.delete(ApiConstants.PASSPORT_ITEM, operateur.getId(), requerant.getId(), requete1.getId(),
				random.nextLong()).then().statusCode(404);
	}

	@Test
	public void update() {

		Autorite updateAutorite = autoriteRepository.save(buildAutorite());

		PassportCreateTO updateTO = buildPassportCreateTO(updateAutorite.getId());
		updateTO.setNumero("numero updated");
		updateTO.setLieuDelivrance("Mbanga Mpongo");

		preLoadedGiven.contentType(ContentType.JSON).body(updateTO).put(ApiConstants.PASSPORT_ITEM, operateur.getId(),
				requerant.getId(), requete1.getId(), passport3.getId()).then().log().body().statusCode(200);

		// check that the passport has been saved
		Passport actual = passportRepository.findOne(passport3.getId());
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getNumero(), is(equalTo("numero updated")));
		assertThat(actual.getDateDelivrance().getTime(), is(equalTo(updateTO.getDateDelivrance().getTime())));
		assertThat(actual.getLieuDelivrance(), is(equalTo("Mbanga Mpongo")));
		assertThat(actual.getDelivrePar().getId(), is(equalTo(updateAutorite.getId())));
		assertThat(actual.getDateExpiration().getTime(), is(equalTo(updateTO.getDateExpiration().getTime())));

		// check updated properties
		assertThat(actual.getUrlPhoto(), is(equalTo(passport3.getUrlPhoto())));
		assertThat(actual.getRequete().getId(), is(equalTo(requete2.getId())));
	}

	@Test
	public void updatePassportNotFound() {

		Autorite updateAutorite = autoriteRepository.save(buildAutorite());

		PassportCreateTO updateTO = buildPassportCreateTO(updateAutorite.getId());
		updateTO.setNumero("numero updated");
		updateTO.setLieuDelivrance("Mbanga Mpongo");

		preLoadedGiven.contentType(ContentType.JSON).body(updateTO).put(ApiConstants.PASSPORT_ITEM, operateur.getId(),
				requerant.getId(), requete1.getId(), random.nextLong()).then().log().body().statusCode(404);
	}

	@Test
	public void updateAutoriteNotFound() {

		PassportCreateTO updateTO = buildPassportCreateTO(random.nextLong());
		updateTO.setNumero("numero updated");
		updateTO.setLieuDelivrance("Mbanga Mpongo");

		preLoadedGiven.contentType(ContentType.JSON).body(updateTO).put(ApiConstants.PASSPORT_ITEM, operateur.getId(),
				requerant.getId(), requete1.getId(), passport3.getId()).then().log().body().statusCode(400);
	}

	@Test
	public void updateAutoriteIdNull() {

		PassportCreateTO updateTO = buildPassportCreateTO(null);
		updateTO.setNumero("numero updated");
		updateTO.setLieuDelivrance("Mbanga Mpongo");

		preLoadedGiven.contentType(ContentType.JSON).body(updateTO).put(ApiConstants.PASSPORT_ITEM, operateur.getId(),
				requerant.getId(), requete1.getId(), passport3.getId()).then().log().body().statusCode(400);
	}

	@Test
	public void updateNumeroNull() {

		Autorite updateAutorite = autoriteRepository.save(buildAutorite());

		PassportCreateTO updateTO = buildPassportCreateTO(updateAutorite.getId());
		updateTO.setNumero(null);
		updateTO.setLieuDelivrance("Mbanga Mpongo");

		preLoadedGiven.contentType(ContentType.JSON).body(updateTO).put(ApiConstants.PASSPORT_ITEM, operateur.getId(),
				requerant.getId(), requete1.getId(), passport3.getId()).then().log().body().statusCode(400);
	}

	@Test
	public void updateDateDelivranceNull() {

		Autorite updateAutorite = autoriteRepository.save(buildAutorite());

		PassportCreateTO updateTO = buildPassportCreateTO(updateAutorite.getId());
		updateTO.setNumero("numero updated");
		updateTO.setLieuDelivrance("Mbanga Mpongo");
		updateTO.setDateDelivrance(null);

		preLoadedGiven.contentType(ContentType.JSON).body(updateTO).put(ApiConstants.PASSPORT_ITEM, operateur.getId(),
				requerant.getId(), requete1.getId(), passport3.getId()).then().log().body().statusCode(400);
	}

	@Test
	public void updateDateExpirationNull() {

		Autorite updateAutorite = autoriteRepository.save(buildAutorite());

		PassportCreateTO updateTO = buildPassportCreateTO(updateAutorite.getId());
		updateTO.setNumero("numero updated");
		updateTO.setLieuDelivrance("Mbanga Mpongo");
		updateTO.setDateExpiration(null);

		preLoadedGiven.contentType(ContentType.JSON).body(updateTO).put(ApiConstants.PASSPORT_ITEM, operateur.getId(),
				requerant.getId(), requete1.getId(), passport3.getId()).then().log().body().statusCode(400);
	}

	@Test
	public void updateLieuDelivranceNull() {

		Autorite updateAutorite = autoriteRepository.save(buildAutorite());

		PassportCreateTO updateTO = buildPassportCreateTO(updateAutorite.getId());
		updateTO.setNumero("numero updated");
		updateTO.setLieuDelivrance(null);

		preLoadedGiven.contentType(ContentType.JSON).body(updateTO).put(ApiConstants.PASSPORT_ITEM, operateur.getId(),
				requerant.getId(), requete1.getId(), passport3.getId()).then().log().body().statusCode(400);
	}

	@Test
	public void listByRequete() {
		preLoadedGiven.get(ApiConstants.PASSPORT_COLLECTION, operateur.getId(), requerant.getId(), requete1.getId())
				.then().log().body().statusCode(200).body("size()", is(equalTo(2)))
				.body("id", containsInAnyOrder(passport1.getId().intValue(), passport2.getId().intValue()))
				.body("find{it.id==" + passport1.getId().intValue() + "}.numero", is(equalTo(passport1.getNumero())))
				.body("find{it.id==" + passport1.getId().intValue() + "}.dateDelivrance",
						is(equalTo(passport1.getDateDelivrance().getTime())))
				.body("find{it.id==" + passport1.getId().intValue() + "}.dateExpiration",
						is(equalTo(passport1.getDateExpiration().getTime())))
				.body("find{it.id==" + passport1.getId().intValue() + "}.lieuDelivrance",
						is(equalTo(passport1.getLieuDelivrance())))
				.body("find{it.id==" + passport1.getId().intValue() + "}.delivrePar.id",
						is(equalTo(autorite.getId().intValue())))
				.body("find{it.id==" + passport1.getId().intValue() + "}.urlPhoto",
						is(equalTo(passport1.getUrlPhoto())));
	}

}
