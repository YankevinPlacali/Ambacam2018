package com.gemini.ambacam.rest.autorites;

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
import com.gemini.ambacam.model.Autorite;
import com.gemini.ambacam.repository.AutoriteRepository;
import com.gemini.ambacam.rest.ApiConstants;

import io.restassured.http.ContentType;

public class AutoritesResourceIT extends ItBase {

	@Autowired
	private AutoriteRepository repository;

	private Autorite autorite1;

	private Autorite autorite2;

	@Before
	public void setup() throws Exception {
		super.setup();

		// create autorite1
		autorite1 = buildAutorite();
		autorite1 = repository.save(autorite1);
		// create autorite 2
		autorite2 = repository.save(buildAutorite());
	}

	@Override
	@After
	public void cleanup() throws Exception {
		repository.deleteAll();
		super.cleanup();
	}

	@Test
	public void create() {
		Autorite create = buildAutorite();
		int id = given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.AUTORITE_COLLECTION)
				.then().log().body().statusCode(200).extract().body().path("id");

		// check that the autorite has been saved
		Autorite actual = repository.findOne(Integer.toUnsignedLong(id));
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getNom(), is(equalTo(create.getNom())));
		assertThat(actual.getPrenom(), is(equalTo(create.getPrenom())));
		assertThat(actual.getPoste(), is(equalTo(create.getPoste())));
	}

	@Test
	public void createNomNull() {
		Autorite create = buildAutorite();
		create.setNom(null);
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.AUTORITE_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createNomVide() {
		Autorite create = buildAutorite();
		create.setNom("");
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.AUTORITE_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createPosteVide() {
		Autorite create = buildAutorite();
		create.setPoste("");
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.AUTORITE_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createPosteNull() {
		Autorite create = buildAutorite();
		create.setPoste(null);
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.AUTORITE_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void list() {
		given().get(ApiConstants.AUTORITE_COLLECTION).then().log().body().statusCode(200).body("size()", is(equalTo(2)))
				.body("id", containsInAnyOrder(autorite1.getId().intValue(), autorite2.getId().intValue()))
				.body("nom", containsInAnyOrder(autorite1.getNom(), autorite2.getNom()))
				.body("prenom", containsInAnyOrder(autorite1.getPrenom(), autorite2.getPrenom()))
				.body("poste", containsInAnyOrder(autorite1.getPoste(), autorite2.getPoste()));
	}

	@Test
	public void get() {
		given().get(ApiConstants.AUTORITE_ITEM, autorite2.getId()).then().log().body().statusCode(200)
				.body("id", is(equalTo(autorite2.getId().intValue()))).body("nom", is(equalTo(autorite2.getNom())))
				.body("prenom", is(equalTo(autorite2.getPrenom()))).body("poste", is(equalTo(autorite2.getPoste())));
	}

	@Test
	public void getNotFound() {
		given().get(ApiConstants.AUTORITE_ITEM, random.nextLong()).then().statusCode(404);
	}

	@Test
	public void delete() {
		given().delete(ApiConstants.AUTORITE_ITEM, autorite1.getId()).then().statusCode(200);

		// check that the autorite has been deleted
		Autorite actual = repository.findOne(autorite1.getId());
		assertThat(actual, is(nullValue()));

	}

	@Test
	public void deleteNotFound() {
		given().delete(ApiConstants.AUTORITE_ITEM, random.nextLong()).then().statusCode(404);
	}

	@Test
	public void update() {
		Autorite update = buildAutorite();
		given().contentType(ContentType.JSON).body(update).put(ApiConstants.AUTORITE_ITEM, autorite2.getId()).then()
				.log().body().statusCode(200);

		// check that the autorite has been saved
		Autorite actual = repository.findOne(autorite2.getId());
		assertThat(actual.getId(), is(equalTo(autorite2.getId())));
		assertThat(actual.getNom(), is((equalTo(update.getNom()))));
		assertThat(actual.getPrenom(), is((equalTo(update.getPrenom()))));
		assertThat(actual.getPoste(), is((equalTo(update.getPoste()))));
	}

	@Test
	public void updateNotFound() {
		Autorite update = buildAutorite();
		given().contentType(ContentType.JSON).body(update).put(ApiConstants.AUTORITE_ITEM, random.nextLong()).then()
				.log().body().statusCode(404);
	}

	@Test
	public void updateMemeNom() {
		Autorite update = buildAutorite();
		update.setNom(autorite1.getNom());
		given().contentType(ContentType.JSON).body(update).log().body()
				.put(ApiConstants.AUTORITE_ITEM, autorite1.getId()).then().log().body().statusCode(200);

		// check that the autorite has been saved
		Autorite actual = repository.findOne(autorite1.getId());
		assertThat(actual.getNom(), is((equalTo(update.getNom()))));
	}

}
