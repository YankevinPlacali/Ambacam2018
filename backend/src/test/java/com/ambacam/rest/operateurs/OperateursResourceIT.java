package com.ambacam.rest.operateurs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.Arrays;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import com.ambacam.ItBase;
import com.ambacam.configuration.AppSettings;
import com.ambacam.model.Operateur;
import com.ambacam.model.Pays;
import com.ambacam.repository.OperateurRepository;
import com.ambacam.repository.PaysRepository;
import com.ambacam.rest.ApiConstants;
import com.ambacam.transfert.operateurs.OperateurCreateTO;
import com.ambacam.transfert.operateurs.OperateurUpdateTO;

import io.restassured.http.ContentType;

public class OperateursResourceIT extends ItBase {

	@Autowired
	private OperateurRepository repository;

	@Autowired
	private PaysRepository paysRepository;

	@Mock
	private AppSettings appSettings;

	private Operateur creator1;

	private Operateur creator2;

	private Operateur operateur1;

	private Operateur operateur2;

	private Pays pays1;

	private Pays pays2;

	@Override
	@Before
	public void setup() throws Exception {
		super.setup();

		// create pays1
		pays1 = paysRepository.save(buildPays());
		// create pays2
		pays2 = paysRepository.save(buildPays());

		// create creator1
		creator1 = repository.save(buildOperateur().nom("creator1").nationalite(pays1));

		// create creator2
		creator2 = repository.save(buildOperateur().nom("creator2").nationalite(pays2));

		// create operateur1
		operateur1 = buildOperateur().nom("operateur1");
		operateur1.setCreePar(creator1);
		operateur1.setNationalite(pays1);
		operateur1 = repository.save(operateur1);

		// create operateur 2
		operateur2 = buildOperateur().nom("operateur2");
		operateur2.setCreePar(creator2);
		operateur2.setNationalite(pays2);
		operateur2 = repository.save(operateur2);

	}

	@Override
	@After
	public void cleanup() throws Exception {
		operateur1.setCreePar(null);
		operateur2.setCreePar(null);
		repository.save(Arrays.asList(operateur1, operateur2));
		repository.deleteAll();
		paysRepository.deleteAll();
		super.cleanup();
	}

	@Test
	public void create() {

		OperateurCreateTO create = buildOperateurCreateTO();
		create.setCreatorId(creator1.getId());
		create.setPaysId(pays1.getId());

		DateTime before = new DateTime();

		int id = given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.OPERATEUR_COLLECTION)
				.then().log().body().statusCode(200).extract().body().path("id");

		DateTime after = new DateTime();

		// check that the operateur has been saved
		Operateur actual = repository.findOne(Integer.toUnsignedLong(id));
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getNom(), is(equalTo(create.getNom())));
		assertThat(actual.getPrenom(), is(equalTo(create.getPrenom())));
		assertThat(actual.getSexe(), is(equalTo(create.getSexe())));
		assertThat(actual.getUsername(), is(equalTo(create.getUsername())));
		assertThat(actual.getPassword(), is(equalTo(create.getPassword())));
		assertThat(actual.getNationalite().getId(), is(equalTo(pays1.getId())));
		assertThat(actual.getCreePar().getId(), is(equalTo(creator1.getId())));
		assertThat(before.isBefore(actual.getCreeLe().getTime()), is(equalTo(true)));
		assertThat(after.isAfter(actual.getCreeLe().getTime()), is(equalTo(true)));
	}

	@Test
	public void createNomNull() {

		OperateurCreateTO create = buildOperateurCreateTO();
		create.setNom(null);
		create.setCreatorId(creator1.getId());
		create.setPaysId(pays1.getId());

		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.OPERATEUR_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createNomVide() {

		OperateurCreateTO create = buildOperateurCreateTO();
		create.setNom("");
		create.setCreatorId(creator1.getId());
		create.setPaysId(pays1.getId());

		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.OPERATEUR_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createSexeNull() {

		OperateurCreateTO create = buildOperateurCreateTO();
		create.setSexe(null);
		create.setCreatorId(creator1.getId());
		create.setPaysId(pays1.getId());

		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.OPERATEUR_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createSexeVide() {

		OperateurCreateTO create = buildOperateurCreateTO();
		create.setSexe("");
		create.setCreatorId(creator1.getId());
		create.setPaysId(pays1.getId());

		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.OPERATEUR_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createUsernameNull() {

		OperateurCreateTO create = buildOperateurCreateTO();
		create.setUsername(null);
		create.setCreatorId(creator1.getId());
		create.setPaysId(pays1.getId());

		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.OPERATEUR_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createUsernameVide() {

		OperateurCreateTO create = buildOperateurCreateTO();
		create.setUsername("");
		create.setCreatorId(creator1.getId());
		create.setPaysId(pays1.getId());

		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.OPERATEUR_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createPasswordNull() {

		OperateurCreateTO create = buildOperateurCreateTO();
		create.setPassword(null);
		create.setCreatorId(creator1.getId());
		create.setPaysId(pays1.getId());

		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.OPERATEUR_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createPasswordVide() {

		OperateurCreateTO create = buildOperateurCreateTO();
		create.setPassword("");
		create.setCreatorId(creator1.getId());
		create.setPaysId(pays1.getId());

		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.OPERATEUR_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createNationaliteNull() {

		OperateurCreateTO create = buildOperateurCreateTO();
		create.setPaysId(null);
		create.setCreatorId(creator1.getId());

		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.OPERATEUR_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createNationaliteNotFound() {

		OperateurCreateTO create = buildOperateurCreateTO();
		create.setPaysId(random.nextLong());
		create.setCreatorId(creator1.getId());

		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.OPERATEUR_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createCreatorIdNull() {

		OperateurCreateTO create = buildOperateurCreateTO();
		create.setCreatorId(null);
		create.setPaysId(pays1.getId());

		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.OPERATEUR_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void createCreatorIdNotFound() {

		OperateurCreateTO create = buildOperateurCreateTO();
		create.setCreatorId(random.nextLong());
		create.setPaysId(pays1.getId());

		given().contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.OPERATEUR_COLLECTION).then()
				.log().body().statusCode(400);
	}

	@Test
	public void list() {
		given().get(ApiConstants.OPERATEUR_COLLECTION).then().log().body().statusCode(200)
				.body("size()", is(equalTo(4)))
				.body("id",
						containsInAnyOrder(operateur1.getId().intValue(), operateur2.getId().intValue(),
								creator1.getId().intValue(), creator2.getId().intValue()))
				.body("find{it.id==" + operateur1.getId().intValue() + "}.nom", is(equalTo(operateur1.getNom())))
				.body("find{it.id==" + operateur1.getId().intValue() + "}.prenom", is(equalTo(operateur1.getPrenom())))
				.body("find{it.id==" + operateur1.getId().intValue() + "}.sexe", is(equalTo(operateur1.getSexe())))
				.body("find{it.id==" + operateur1.getId().intValue() + "}.nationalite.id",
						is(equalTo(operateur1.getNationalite().getId().intValue())))
				.body("find{it.id==" + operateur1.getId().intValue() + "}.creePar.id",
						is(equalTo(operateur1.getCreePar().getId().intValue())))
				.body("find{it.id==" + operateur1.getId().intValue() + "}.creeLe",
						is(equalTo(operateur1.getCreeLe().getTime())))
				.body("find{it.id==" + operateur1.getId().intValue() + "}.roles.size()", is(equalTo(0)));
	}

	@Test
	public void get() {
		given().get(ApiConstants.OPERATEUR_ITEM, operateur2.getId()).then().log().body().statusCode(200)
				.body("id", is(equalTo(operateur2.getId().intValue()))).body("nom", is(equalTo(operateur2.getNom())))
				.body("prenom", is(equalTo(operateur2.getPrenom()))).body("sexe", is(equalTo(operateur2.getSexe())))
				.body("nationalite.id", is(equalTo(operateur2.getNationalite().getId().intValue())))
				.body("creePar.id", is(equalTo(operateur2.getCreePar().getId().intValue())))
				.body("creeLe", is(equalTo(operateur2.getCreeLe().getTime())));
	}

	@Test
	public void getNotFound() {
		given().get(ApiConstants.OPERATEUR_ITEM, random.nextLong()).then().statusCode(404);
	}

	@Test
	public void delete() {

		operateur2.setCreePar(operateur1);
		repository.save(operateur2);

		given().delete(ApiConstants.OPERATEUR_ITEM, operateur1.getId()).then().statusCode(200);

		// check that the operateur has been deleted
		Operateur actual = repository.findOne(operateur1.getId());
		assertThat(actual, is(nullValue()));

		Operateur actualOperateur2 = repository.findOne(operateur2.getId());

		// check unlink with created operateurs
		assertThat(actualOperateur2.getCreePar(), is(nullValue()));
	}

	@Test
	public void deleteNotFound() {
		given().delete(ApiConstants.OPERATEUR_ITEM, random.nextLong()).then().statusCode(404);
	}

	@Test
	public void update() {

		OperateurUpdateTO update = buildOperateurUpdateTO();

		update.setPaysId(pays1.getId());

		given().contentType(ContentType.JSON).body(update).put(ApiConstants.OPERATEUR_ITEM, operateur2.getId()).then()
				.log().body().statusCode(200);

		// check that the operateur has been saved
		Operateur actual = repository.findOne(operateur2.getId());
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getNom(), is(equalTo(update.getNom())));
		assertThat(actual.getPrenom(), is(equalTo(update.getPrenom())));
		assertThat(actual.getSexe(), is(equalTo(update.getSexe())));
		assertThat(actual.getUsername(), is(equalTo(operateur2.getUsername())));
		assertThat(actual.getPassword(), is(equalTo(operateur2.getPassword())));
		assertThat(actual.getNationalite().getId(), is(equalTo(pays1.getId())));
		assertThat(actual.getCreePar().getId(), is(equalTo(operateur2.getCreePar().getId())));
		assertThat(actual.getCreeLe().getTime(), is(equalTo(operateur2.getCreeLe().getTime())));

	}

	@Test
	public void updateNotFound() {

		OperateurUpdateTO update = buildOperateurUpdateTO();

		update.setPaysId(pays1.getId());

		given().contentType(ContentType.JSON).body(update).put(ApiConstants.OPERATEUR_ITEM, random.nextLong()).then()
				.log().body().statusCode(404);
	}

	@Test
	public void updateNomNull() {

		OperateurUpdateTO update = buildOperateurUpdateTO();

		update.setNom(null);
		update.setPaysId(pays1.getId());

		given().contentType(ContentType.JSON).body(update).put(ApiConstants.OPERATEUR_ITEM, operateur2.getId()).then()
				.log().body().statusCode(400);
	}

	@Test
	public void updateNomVide() {

		OperateurUpdateTO update = buildOperateurUpdateTO();

		update.setNom("");
		update.setPaysId(pays1.getId());

		given().contentType(ContentType.JSON).body(update).put(ApiConstants.OPERATEUR_ITEM, operateur2.getId()).then()
				.log().body().statusCode(400);
	}

	@Test
	public void updateSexeNull() {

		OperateurUpdateTO update = buildOperateurUpdateTO();

		update.setSexe(null);
		update.setPaysId(pays1.getId());

		given().contentType(ContentType.JSON).body(update).put(ApiConstants.OPERATEUR_ITEM, operateur2.getId()).then()
				.log().body().statusCode(400);
	}

	@Test
	public void updateSexeVide() {

		OperateurUpdateTO update = buildOperateurUpdateTO();

		update.setSexe("");
		update.setPaysId(pays1.getId());

		given().contentType(ContentType.JSON).body(update).put(ApiConstants.OPERATEUR_ITEM, operateur2.getId()).then()
				.log().body().statusCode(400);
	}

	@Test
	public void updateNationaliteNull() {

		OperateurUpdateTO update = buildOperateurUpdateTO();

		update.setPaysId(null);

		given().contentType(ContentType.JSON).body(update).put(ApiConstants.OPERATEUR_ITEM, operateur2.getId()).then()
				.log().body().statusCode(400);
	}

	@Test
	public void updateNationaliteNotFound() {

		OperateurUpdateTO update = buildOperateurUpdateTO();

		update.setPaysId(random.nextLong());

		given().contentType(ContentType.JSON).body(update).put(ApiConstants.OPERATEUR_ITEM, operateur2.getId()).then()
				.log().body().statusCode(400);
	}
}
