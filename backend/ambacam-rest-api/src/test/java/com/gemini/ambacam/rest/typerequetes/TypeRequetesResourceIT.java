package com.gemini.ambacam.rest.typerequetes;

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
import com.gemini.ambacam.model.TypeRequete;
import com.gemini.ambacam.repository.TypeRequeteRepository;
import com.gemini.ambacam.rest.ApiConstants;

import io.restassured.http.ContentType;

public class TypeRequetesResourceIT extends ItBase {

	@Autowired
	private TypeRequeteRepository repository;

	private TypeRequete typeRequete1;

	private TypeRequete typeRequete2;

	@Before
	public void setup() throws Exception {
		super.setup();

		// create typeRequete1
		typeRequete1 = buildTypeRequete();
		typeRequete1 = repository.save(typeRequete1);
		// create typeRequete2
		typeRequete2 = repository.save(buildTypeRequete());
	}

	@Override
	@After
	public void cleanup() throws Exception {
		repository.deleteAll();
		super.cleanup();
	}

	@Test
	public void create() {
		TypeRequete create = buildTypeRequete();
		int id = given().contentType(ContentType.JSON).body(create).log().body()
				.post(ApiConstants.TYPE_REQUETE_COLLECTION).then().log().body().statusCode(200).extract().body()
				.path("id");

		// check that the typeRequete has been saved
		TypeRequete actual = repository.findOne(Integer.toUnsignedLong(id));
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getNom(), is(equalTo(create.getNom())));
		assertThat(actual.getDescription(), is(equalTo(create.getDescription())));
	}

	@Test
	public void createNomNull() {
		TypeRequete create = buildTypeRequete();
		create.setNom(null);
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.TYPE_REQUETE_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void createNomVide() {
		TypeRequete create = buildTypeRequete();
		create.setNom("");
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.TYPE_REQUETE_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void createMemeNom() {
		TypeRequete create = buildTypeRequete();
		create.setNom(typeRequete1.getNom());
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.TYPE_REQUETE_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void list() {
		given().get(ApiConstants.TYPE_REQUETE_COLLECTION).then().log().body().statusCode(200)
				.body("size()", is(equalTo(2)))
				.body("id", containsInAnyOrder(typeRequete1.getId().intValue(), typeRequete2.getId().intValue()))
				.body("nom", containsInAnyOrder(typeRequete1.getNom(), typeRequete2.getNom()))
				.body("description", containsInAnyOrder(typeRequete1.getDescription(), typeRequete2.getDescription()));
	}

	@Test
	public void get() {
		given().get(ApiConstants.TYPE_REQUETE_ITEM, typeRequete2.getId()).then().log().body().statusCode(200)
				.body("id", is(equalTo(typeRequete2.getId().intValue())))
				.body("nom", is(equalTo(typeRequete2.getNom())))
				.body("description", is(equalTo(typeRequete2.getDescription())));
	}

	@Test
	public void getNotFound() {
		given().get(ApiConstants.TYPE_REQUETE_ITEM, random.nextLong()).then().statusCode(404);
	}

	@Test
	public void delete() {
		given().delete(ApiConstants.TYPE_REQUETE_ITEM, typeRequete1.getId()).then().statusCode(200);

		// check that the typeRequete has been deleted
		TypeRequete actual = repository.findOne(typeRequete1.getId());
		assertThat(actual, is(nullValue()));

	}

	@Test
	public void deleteNotFound() {
		given().delete(ApiConstants.TYPE_REQUETE_ITEM, random.nextLong()).then().statusCode(404);
	}

	@Test
	public void update() {
		TypeRequete update = buildTypeRequete();
		given().contentType(ContentType.JSON).body(update).put(ApiConstants.TYPE_REQUETE_ITEM, typeRequete2.getId())
				.then().log().body().statusCode(200);

		// check that the typeRequete has been saved
		TypeRequete actual = repository.findOne(typeRequete2.getId());
		assertThat(actual.getId(), is(equalTo(typeRequete2.getId())));
		assertThat(actual.getNom(), is((equalTo(update.getNom()))));
		assertThat(actual.getDescription(), is(equalTo(update.getDescription())));

	}

	@Test
	public void updateNotFound() {
		TypeRequete update = buildTypeRequete();
		given().contentType(ContentType.JSON).body(update).put(ApiConstants.TYPE_REQUETE_ITEM, random.nextLong())
				.then().log().body().statusCode(404);
	}

	@Test
	public void updateMemeNom() {
		TypeRequete update = buildTypeRequete();
		update.setNom(typeRequete1.getNom());
		given().contentType(ContentType.JSON).body(update).log().body()
				.put(ApiConstants.TYPE_REQUETE_ITEM, typeRequete1.getId()).then().log().body().statusCode(200);

		// check that the typeRequete has been saved
		TypeRequete actual = repository.findOne(typeRequete1.getId());
		assertThat(actual.getNom(), is((equalTo(update.getNom()))));
		assertThat(actual.getDescription(), is(equalTo(update.getDescription())));
	}

	@Test
	public void updateNomExists() {
		TypeRequete update = buildTypeRequete();
		update.setNom(typeRequete2.getNom());
		given().contentType(ContentType.JSON).body(update).log().body()
				.put(ApiConstants.TYPE_REQUETE_ITEM, typeRequete1.getId()).then().log().body().statusCode(400);
	}

}
