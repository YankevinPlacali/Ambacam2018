package com.ambacam.rest.actions;

import com.ambacam.ItBase;
import com.ambacam.model.Action;
import com.ambacam.repository.ActionRepository;
import com.ambacam.rest.ApiConstants;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ActionsResourceIT extends ItBase {

	@Autowired
	private ActionRepository repository;

	private Action action1;

	private Action action2;

	@Before
	public void setup() throws Exception {
		super.setup();

		// create action1
		action1 = buildAction();
		action1 = repository.save(action1);
		// create action 2
		action2 = repository.save(buildAction());
	}

	@Override
	@After
	public void cleanup() throws Exception {
		repository.deleteAll();
		super.cleanup();
	}

	@Test
	public void create() {
		Action create = buildAction();
		int id = given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.ACTION_COLLECTION)
				.then().log().body().statusCode(200).extract().body().path("id");

		// check that the action has been saved
		Action actual = repository.findOne(Integer.toUnsignedLong(id));
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getNom(), is(equalTo(create.getNom())));
	}

	@Test
	public void createNomNull() {
		Action create = buildAction();
		create.setNom(null);
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.ACTION_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createNomVide() {
		Action create = buildAction();
		create.setNom("");
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.ACTION_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createMemeNom() {
		Action create = buildAction();
		create.setNom(action1.getNom());
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.ACTION_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void list() {
		given().get(ApiConstants.ACTION_COLLECTION).then().log().body().statusCode(200).body("size()", is(equalTo(2)))
				.body("id", containsInAnyOrder(action1.getId().intValue(), action2.getId().intValue()));
	}

	@Test
	public void get() {
		given().get(ApiConstants.ACTION_ITEM, action2.getId()).then().log().body().statusCode(200)
				.body("id", is(equalTo(action2.getId().intValue()))).body("nom", is(equalTo(action2.getNom())));
	}

	@Test
	public void getNotFound() {
		given().get(ApiConstants.ACTION_ITEM, random.nextLong()).then().statusCode(404);
	}

	@Test
	public void delete() {
		given().delete(ApiConstants.ACTION_ITEM, action1.getId()).then().statusCode(200);

		// check that the action has been deleted
		Action actual = repository.findOne(action1.getId());
		assertThat(actual, is(nullValue()));

	}

	@Test
	public void deleteNotFound() {
		given().delete(ApiConstants.ACTION_ITEM, random.nextLong()).then().statusCode(404);
	}

	@Test
	public void update() {
		Action update = buildAction();
		given().contentType(ContentType.JSON).body(update).put(ApiConstants.ACTION_ITEM, action2.getId()).then().log()
				.body().statusCode(200);

		// check that the action has been saved
		Action actual = repository.findOne(action2.getId());
		assertThat(actual.getId(), is(equalTo(action2.getId())));
		assertThat(actual.getNom(), is((equalTo(update.getNom()))));

	}

	@Test
	public void updateNotFound() {
		Action update = buildAction();
		given().contentType(ContentType.JSON).body(update).put(ApiConstants.ACTION_ITEM, random.nextLong()).then().log()
				.body().statusCode(404);
	}

	@Test
	public void updateMemeNom() {
		Action update = buildAction();
		update.setNom(action1.getNom());
		given().contentType(ContentType.JSON).body(update).log().body().put(ApiConstants.ACTION_ITEM, action1.getId())
				.then().log().body().statusCode(200);

		// check that the action has been saved
		Action actual = repository.findOne(action1.getId());
		assertThat(actual.getNom(), is((equalTo(update.getNom()))));
	}

	@Test
	public void updateNomExists() {
		Action update = buildAction();
		update.setNom(action2.getNom());
		given().contentType(ContentType.JSON).body(update).log().body().put(ApiConstants.ACTION_ITEM, action1.getId())
				.then().log().body().statusCode(400);
	}

}
