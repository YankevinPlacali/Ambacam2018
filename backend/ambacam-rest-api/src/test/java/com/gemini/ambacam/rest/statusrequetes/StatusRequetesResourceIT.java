package com.gemini.ambacam.rest.statusrequetes;

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

import com.gemini.ambacam.ItBase;
import com.gemini.ambacam.model.StatusRequete;
import com.gemini.ambacam.repository.StatusRequeteRepository;
import com.gemini.ambacam.rest.ApiConstants;

import io.restassured.http.ContentType;

public class StatusRequetesResourceIT extends ItBase {

	@Autowired
	private StatusRequeteRepository repository;

	private StatusRequete statusRequete1;

	private StatusRequete statusRequete2;

	@Before
	public void setup() throws Exception {
		super.setup();

		// create statusRequete1
		statusRequete1 = buildStatusRequete();
		statusRequete1 = repository.save(statusRequete1);
		// create statusRequete 2
		statusRequete2 = repository.save(buildStatusRequete());
	}

	@Override
	@After
	public void cleanup() throws Exception {
		repository.deleteAll();
		super.cleanup();
	}

	@Test
	public void create() {
		StatusRequete create = buildStatusRequete();
		int id = given().contentType(ContentType.JSON)
				.body(create).log().body()
				.post(ApiConstants.STATUS_REQUETE_COLLECTION)
				.then().log().body()
				.statusCode(200).extract().body()
				.path("id");

		// check that the statusRequete has been saved
		StatusRequete actual = repository.findOne(Integer.toUnsignedLong(id));
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getNom(), is(equalTo(create.getNom())));
		assertThat(actual.getDescription(), is(equalTo(create.getDescription())));
	}

	@Test
	public void createNomNull() {
		StatusRequete create = buildStatusRequete();
		create.setNom(null);
		given().contentType(ContentType.JSON)
				.body(create).log().body()
				.post(ApiConstants.STATUS_REQUETE_COLLECTION)
				.then()
				.log().body()
				.statusCode(400);
	}

	@Test
	public void createNomVide() {
		StatusRequete create = buildStatusRequete();
		create.setNom("");
		given().contentType(ContentType.JSON)
				.body(create).log().body()
				.post(ApiConstants.STATUS_REQUETE_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void createMemeNom() {
		StatusRequete create = buildStatusRequete();
		create.setNom(statusRequete1.getNom());
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.STATUS_REQUETE_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void list() {
		given().get(ApiConstants.STATUS_REQUETE_COLLECTION).then().log().body().statusCode(200)
				.body("size()", is(equalTo(2)))
				.body("id", containsInAnyOrder(statusRequete1.getId().intValue(), statusRequete2.getId().intValue()));
	}

	@Test
	public void get() {
		given().get(ApiConstants.STATUS_REQUETE_ITEM, statusRequete2.getId()).then().log().body().statusCode(200)
				.body("id", is(equalTo(statusRequete2.getId().intValue())))
				.body("nom", is(equalTo(statusRequete2.getNom())))
				.body("description", is(equalTo(statusRequete2.getDescription())));
	}

	@Test
	public void getNotFound() {
		given().get(ApiConstants.STATUS_REQUETE_ITEM, random.nextLong()).then().statusCode(404);
	}

	@Test
	public void delete() {
		given().delete(ApiConstants.STATUS_REQUETE_ITEM, statusRequete1.getId()).then().statusCode(200);

		// check that the statusRequete has been deleted
		StatusRequete actual = repository.findOne(statusRequete1.getId());
		assertThat(actual, is(nullValue()));

	}

	@Test
	public void deleteNotFound() {
		given().delete(ApiConstants.STATUS_REQUETE_ITEM, random.nextLong()).then().statusCode(404);
	}

	@Test
	public void update() {
		StatusRequete update = buildStatusRequete();
		given().contentType(ContentType.JSON).body(update).put(ApiConstants.STATUS_REQUETE_ITEM, statusRequete2.getId())
				.then().log().body().statusCode(200);

		// check that the statusRequete has been saved
		StatusRequete actual = repository.findOne(statusRequete2.getId());
		assertThat(actual.getId(), is(equalTo(statusRequete2.getId())));
		assertThat(actual.getNom(), is((equalTo(update.getNom()))));
		assertThat(actual.getDescription(), is(equalTo(update.getDescription())));

	}

	@Test
	public void updateNotFound() {
		StatusRequete update = buildStatusRequete();
		given().contentType(ContentType.JSON).body(update).put(ApiConstants.STATUS_REQUETE_ITEM, random.nextLong())
				.then().log().body().statusCode(404);
	}

	@Test
	public void updateMemeNom() {
		StatusRequete update = buildStatusRequete();
		update.setNom(statusRequete1.getNom());
		given().contentType(ContentType.JSON).body(update).log().body()
				.put(ApiConstants.STATUS_REQUETE_ITEM, statusRequete1.getId()).then().log().body().statusCode(200);

		// check that the statusRequete has been saved
		StatusRequete actual = repository.findOne(statusRequete1.getId());
		assertThat(actual.getNom(), is((equalTo(update.getNom()))));
		assertThat(actual.getDescription(), is(equalTo(update.getDescription())));
	}

	@Test
	public void updateNomExists() {
		StatusRequete update = buildStatusRequete();
		update.setNom(statusRequete2.getNom());
		given().contentType(ContentType.JSON).body(update).log().body()
				.put(ApiConstants.STATUS_REQUETE_ITEM, statusRequete1.getId()).then().log().body().statusCode(400);
	}

}
