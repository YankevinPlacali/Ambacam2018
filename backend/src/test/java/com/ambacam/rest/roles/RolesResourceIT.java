/**
 * 
 */
package com.ambacam.rest.roles;

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
import com.ambacam.model.Role;
import com.ambacam.repository.RoleRepository;

import io.restassured.http.ContentType;

public class RolesResourceIT extends ItBase {
	private static final String COLLECTION_PATH = "/roles";
	private static final String ITEM_PATH = COLLECTION_PATH + "/{roleId}";

	@Autowired
	RoleRepository repository;

	Role role1;
	Role role2;

	@Before
	public void setup() throws Exception {
		super.setup();

		// create role1
		role1 = buildRole();
		role1 = repository.save(role1);
		// create role 2
		role2 = repository.save(buildRole());
	}

	@Override
	@After
	public void cleanup() throws Exception {
		repository.deleteAll();
		super.cleanup();
	}

	@Test
	public void create() {
		Role create = buildRole();
		int id = given().contentType(ContentType.JSON).body(create).log().body().post(COLLECTION_PATH).then().log()
				.body().statusCode(200).extract().body().path("id");

		// check that the role has been saved
		Role actual = repository.findOne(Integer.toUnsignedLong(id));
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getNom(), is(equalTo(create.getNom())));
		assertThat(actual.getDescription(), is(equalTo(create.getDescription())));
	}

	@Test
	public void createNomNull() {
		Role create = buildRole();
		create.setNom(null);
		given().contentType(ContentType.JSON).body(create).log().body().post(COLLECTION_PATH).then().log().body()
				.statusCode(400);
	}

	@Test
	public void createNomVide() {
		Role create = buildRole();
		create.setNom("");
		given().contentType(ContentType.JSON).body(create).log().body().post(COLLECTION_PATH).then().log().body()
				.statusCode(400);
	}

	@Test
	public void createMemeNom() {
		Role create = buildRole();
		create.setNom(role1.getNom());
		given().contentType(ContentType.JSON).body(create).log().body().post(COLLECTION_PATH).then().log().body()
				.statusCode(400);
	}

	@Test
	public void list() {
		given().get(COLLECTION_PATH).then().log().body().statusCode(200).body("size()", is(equalTo(2))).body("id",
				containsInAnyOrder(role1.getId().intValue(), role2.getId().intValue()));
	}

	@Test
	public void get() {
		given().get(ITEM_PATH, role2.getId()).then().log().body().statusCode(200)
				.body("id", is(equalTo(role2.getId().intValue()))).body("nom", is(equalTo(role2.getNom())))
				.body("description", is(equalTo(role2.getDescription())));
	}

	@Test
	public void getNotFound() {
		given().get(ITEM_PATH, random.nextLong()).then().statusCode(404);
	}

	@Test
	public void delete() {
		given().delete(ITEM_PATH, role1.getId()).then().statusCode(200);

		// check that the role has been deleted
		Role actual = repository.findOne(role1.getId());
		assertThat(actual, is(nullValue()));

	}

	@Test
	public void deleteNotFound() {
		given().delete(ITEM_PATH, random.nextLong()).then().statusCode(404);
	}

	@Test
	public void update() {
		Role update = buildRole();
		given().contentType(ContentType.JSON).body(update).put(ITEM_PATH, role2.getId()).then().log().body()
				.statusCode(200);

		// check that the role has been saved
		Role actual = repository.findOne(role2.getId());
		assertThat(actual.getId(), is(equalTo(role2.getId())));
		assertThat(actual.getNom(), is((equalTo(update.getNom()))));
		assertThat(actual.getDescription(), is(equalTo(update.getDescription())));

	}

	@Test
	public void updateNotFound() {
		Role update = buildRole();
		given().contentType(ContentType.JSON).body(update).put(ITEM_PATH, random.nextLong()).then().log().body()
				.statusCode(404);
	}

}
