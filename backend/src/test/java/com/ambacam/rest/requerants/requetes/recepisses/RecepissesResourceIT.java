package com.ambacam.rest.requerants.requetes.recepisses;

import static io.restassured.RestAssured.given;
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
import com.ambacam.model.Operateur;
import com.ambacam.model.Pays;
import com.ambacam.model.Recepisse;
import com.ambacam.model.Requerant;
import com.ambacam.model.Requete;
import com.ambacam.model.RequeteGroupe;
import com.ambacam.model.StatusRequete;
import com.ambacam.model.TypeRequete;
import com.ambacam.repository.OperateurRepository;
import com.ambacam.repository.PaysRepository;
import com.ambacam.repository.RecepisseRepository;
import com.ambacam.repository.RequerantRepository;
import com.ambacam.repository.RequeteGroupeRepository;
import com.ambacam.repository.RequeteRepository;
import com.ambacam.repository.StatusRequeteRepository;
import com.ambacam.repository.TypeRequeteRepository;
import com.ambacam.rest.ApiConstants;
import com.ambacam.transfert.recepisses.RecepisseCreateTO;
import com.ambacam.transfert.recepisses.RecepisseUpdateTO;

import io.restassured.http.ContentType;

public class RecepissesResourceIT extends ItBase {

	@Autowired
	private RecepisseRepository repository;

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

	private Recepisse recepisse1;

	private Recepisse recepisse2;

	private TypeRequete typeRequete;

	private StatusRequete statusRequete;

	private RequeteGroupe requeteGroupe;

	private Pays pays;

	private Operateur operateur;

	private Requerant requerant;

	private Requete requete;

	@Override
	@Before
	public void setup() throws Exception {
		super.setup();

		typeRequete = typeRequeteRepository.save(buildTypeRequete());

		statusRequete = statusRequeteRepository.save(buildStatusRequete());

		pays = paysRepository.save(buildPays());

		operateur = operateurRepository.save(buildOperateur().nom("operateur").nationalite(pays));

		requeteGroupe = buildRequeteGroupe();
		requeteGroupe.setCreePar(operateur);
		requeteGroupe = requeteGroupeRepository.save(requeteGroupe);

		requerant = requerantRepository.save(buildRequerant().nom("requerant").creePar(operateur).nationalite(pays));

		// create requete
		requete = buildRequete();
		requete.setRequeteGroupe(requeteGroupe);
		requete.setRequerant(requerant);
		requete.setType(typeRequete);
		requete.setStatus(statusRequete);
		requete.setOperateur(operateur);

		requete = requeteRepository.save(requete);

		// create recepisse
		recepisse1 = repository.save(buildRecepisse().requete(requete));

		recepisse2 = repository.save(buildRecepisse().requete(requete));

	}

	@Override
	@After
	public void cleanup() throws Exception {

		repository.deleteAll();
		requeteRepository.deleteAll();
		requeteGroupeRepository.deleteAll();
		requerantRepository.deleteAll();
		statusRequeteRepository.deleteAll();
		typeRequeteRepository.deleteAll();
		operateurRepository.deleteAll();
		paysRepository.deleteAll();

		super.cleanup();
	}

	@Test
	public void create() {

		RecepisseCreateTO create = buildRecepisseCreateTO(random.nextLong());

		int id = given().contentType(ContentType.JSON).body(create).log().body()
				.post(ApiConstants.RECEPISSE_COLLECTION, requerant.getId(), requete.getId()).then().log().body()
				.statusCode(200).extract().body().path("id");

		// check that the recepisse has been saved
		Recepisse actual = repository.findOne(Integer.toUnsignedLong(id));
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getNumero(), is(equalTo(create.getNumero())));
		assertThat(actual.getRequete().getId(), is(equalTo(requete.getId())));
	}

	@Test
	public void createNumeroNull() {

		RecepisseCreateTO create = buildRecepisseCreateTO(null);

		given().contentType(ContentType.JSON).body(create).log().body()
				.post(ApiConstants.RECEPISSE_COLLECTION,requerant.getId(), requete.getId()).then().log().body()
				.statusCode(400);
	}

	@Test
	public void createRequeteNotFound() {

		RecepisseCreateTO create = buildRecepisseCreateTO(random.nextLong());

		given().contentType(ContentType.JSON).body(create).log().body()
				.post(ApiConstants.RECEPISSE_COLLECTION, requerant.getId(), random.nextLong()).then().log().body()
				.statusCode(404);
	}

	@Test
	public void list() {
		given().get(ApiConstants.RECEPISSE_COLLECTION, requerant.getId(), requete.getId()).then().log().body()
				.statusCode(200).body("size()", is(equalTo(2)))
				.body("id", containsInAnyOrder(recepisse1.getId().intValue(), recepisse2.getId().intValue()))
				.body("find{it.id==" + recepisse1.getId().intValue() + "}.numero", is(equalTo(recepisse1.getNumero())));
	}

	@Test
	public void get() {
		given().get(ApiConstants.RECEPISSE_ITEM, requerant.getId(), requete.getId(), recepisse1.getId()).then().log()
				.body().statusCode(200).body("id", is(equalTo(recepisse1.getId().intValue())))
				.body("numero", is(equalTo(recepisse1.getNumero())));
	}

	@Test
	public void getNotFound() {
		given().get(ApiConstants.RECEPISSE_ITEM, requerant.getId(), requete.getId(), random.nextLong()).then()
				.statusCode(404);
	}

	@Test
	public void delete() {

		given().delete(ApiConstants.RECEPISSE_ITEM, requerant.getId(), requete.getId(), recepisse2.getId()).then()
				.statusCode(200);

		// check that the requerant has been deleted
		Recepisse actual = repository.findOne(recepisse2.getId());
		assertThat(actual, is(nullValue()));

	}

	@Test
	public void deleteNotFound() {
		given().delete(ApiConstants.RECEPISSE_ITEM, requerant.getId(), requete.getId(), random.nextLong()).then()
				.statusCode(404);
	}

	@Test
	public void update() {

		RecepisseUpdateTO update = buildRecepisseUpdateTO();

		given().contentType(ContentType.JSON).body(update)
				.put(ApiConstants.RECEPISSE_ITEM, requerant.getId(), requete.getId(), recepisse1.getId()).then().log()
				.body().statusCode(200);

		// check that the requerant has been saved
		Recepisse actual = repository.findOne(recepisse1.getId());
		assertThat(actual, is(notNullValue()));
		// check updated properties
		assertThat(actual.getNumero(), is(equalTo(update.getNumero())));
	}

	@Test
	public void updateNumeroNull() {

		RecepisseUpdateTO update = buildRecepisseUpdateTO().numero(null);

		given().contentType(ContentType.JSON).body(update)
				.put(ApiConstants.RECEPISSE_ITEM, requerant.getId(), requete.getId(), recepisse1.getId()).then().log()
				.body().statusCode(400);
	}

}
