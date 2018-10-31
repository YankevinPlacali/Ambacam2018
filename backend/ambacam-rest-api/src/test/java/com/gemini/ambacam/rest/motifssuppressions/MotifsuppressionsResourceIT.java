package com.gemini.ambacam.rest.motifssuppressions;

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
import com.gemini.ambacam.model.MotifSuppression;
import com.gemini.ambacam.repository.MotifSuppressionRepository;
import com.gemini.ambacam.rest.ApiConstants;

import io.restassured.http.ContentType;

public class MotifsuppressionsResourceIT extends ItBase {

	@Autowired
	private MotifSuppressionRepository repository;

	private MotifSuppression motifSuppression1;

	private MotifSuppression motifSuppression2;

	@Before
	public void setup() throws Exception {
		super.setup();

		// create motifSuppression1
		motifSuppression1 = buildMotifSuppression();
		motifSuppression1 = repository.save(motifSuppression1);
		// create motifSuppression 2
		motifSuppression2 = repository.save(buildMotifSuppression());
	}

	@Override
	@After
	public void cleanup() throws Exception {
		repository.deleteAll();
		super.cleanup();
	}

	@Test
	public void create() {
		MotifSuppression create = buildMotifSuppression();
		int id = given().contentType(ContentType.JSON).body(create).log().body()
				.post(ApiConstants.MOTIF_SUPPRESSION_COLLECTION).then().log().body().statusCode(200).extract().body()
				.path("id");

		// check that the motifSuppression has been saved
		MotifSuppression actual = repository.findOne(Integer.toUnsignedLong(id));
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getNom(), is(equalTo(create.getNom())));
		assertThat(actual.getDescription(), is(equalTo(create.getDescription())));
	}

	@Test
	public void createNomNull() {
		MotifSuppression create = buildMotifSuppression();
		create.setNom(null);
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.MOTIF_SUPPRESSION_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void createNomVide() {
		MotifSuppression create = buildMotifSuppression();
		create.setNom("");
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.MOTIF_SUPPRESSION_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void createMemeNom() {
		MotifSuppression create = buildMotifSuppression();
		create.setNom(motifSuppression1.getNom());
		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.MOTIF_SUPPRESSION_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void list() {
		given().get(ApiConstants.MOTIF_SUPPRESSION_COLLECTION).then().log().body().statusCode(200)
				.body("size()", is(equalTo(2))).body("id",
						containsInAnyOrder(motifSuppression1.getId().intValue(), motifSuppression2.getId().intValue()));
	}

	@Test
	public void get() {
		given().get(ApiConstants.MOTIF_SUPPRESSION_ITEM, motifSuppression2.getId()).then().log().body().statusCode(200)
				.body("id", is(equalTo(motifSuppression2.getId().intValue())))
				.body("nom", is(equalTo(motifSuppression2.getNom())))
				.body("description", is(equalTo(motifSuppression2.getDescription())));
	}

	@Test
	public void getNotFound() {
		given().get(ApiConstants.MOTIF_SUPPRESSION_ITEM, random.nextLong()).then().statusCode(404);
	}

	@Test
	public void delete() {
		given().delete(ApiConstants.MOTIF_SUPPRESSION_ITEM, motifSuppression1.getId()).then().statusCode(200);

		// check that the motifSuppression has been deleted
		MotifSuppression actual = repository.findOne(motifSuppression1.getId());
		assertThat(actual, is(nullValue()));

	}

	@Test
	public void deleteNotFound() {
		given().delete(ApiConstants.MOTIF_SUPPRESSION_ITEM, random.nextLong()).then().statusCode(404);
	}

	@Test
	public void update() {
		MotifSuppression update = buildMotifSuppression();
		given().contentType(ContentType.JSON).body(update)
				.put(ApiConstants.MOTIF_SUPPRESSION_ITEM, motifSuppression2.getId()).then().log().body()
				.statusCode(200);

		// check that the motifSuppression has been saved
		MotifSuppression actual = repository.findOne(motifSuppression2.getId());
		assertThat(actual.getId(), is(equalTo(motifSuppression2.getId())));
		assertThat(actual.getNom(), is((equalTo(update.getNom()))));
		assertThat(actual.getDescription(), is(equalTo(update.getDescription())));

	}

	@Test
	public void updateNotFound() {
		MotifSuppression update = buildMotifSuppression();
		given().contentType(ContentType.JSON).body(update).put(ApiConstants.MOTIF_SUPPRESSION_ITEM, random.nextLong())
				.then().log().body().statusCode(404);
	}

	@Test
	public void updateMemeNom() {
		MotifSuppression update = buildMotifSuppression();
		update.setNom(motifSuppression1.getNom());
		given().contentType(ContentType.JSON).body(update).log().body()
				.put(ApiConstants.MOTIF_SUPPRESSION_ITEM, motifSuppression1.getId()).then().log().body()
				.statusCode(200);

		// check that the motifSuppression has been saved
		MotifSuppression actual = repository.findOne(motifSuppression1.getId());
		assertThat(actual.getNom(), is((equalTo(update.getNom()))));
		assertThat(actual.getDescription(), is(equalTo(update.getDescription())));
	}

	@Test
	public void updateNomExists() {
		MotifSuppression update = buildMotifSuppression();
		update.setNom(motifSuppression2.getNom());
		given().contentType(ContentType.JSON).body(update).log().body()
				.put(ApiConstants.MOTIF_SUPPRESSION_ITEM, motifSuppression1.getId()).then().log().body()
				.statusCode(400);
	}

}
