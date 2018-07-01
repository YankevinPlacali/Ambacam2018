package com.ambacam.rest.operateurs.requetegroupes;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ambacam.ItBase;
import com.ambacam.model.Operateur;
import com.ambacam.model.Pays;
import com.ambacam.model.RequeteGroupe;
import com.ambacam.model.StatusRequeteValues;
import com.ambacam.repository.OperateurRepository;
import com.ambacam.repository.PaysRepository;
import com.ambacam.repository.RequeteGroupeRepository;
import com.ambacam.rest.ApiConstants;
import com.ambacam.transfert.requetegroupes.RequeteGroupeCreateTO;
import com.ambacam.transfert.requetegroupes.RequeteGroupeUpdateTO;
import com.ambacam.utils.DateUtils;

import io.restassured.http.ContentType;

public class RequeteGroupesResourceIT extends ItBase {

	@Autowired
	private OperateurRepository operateurRepository;

	@Autowired
	private RequeteGroupeRepository requeteGroupeRepository;

	@Autowired
	private PaysRepository paysRepository;

	private Operateur operateur;

	private RequeteGroupe requeteGroupe1;

	private RequeteGroupe requeteGroupe2;

	private Pays pays;

	@Override
	@Before
	public void setup() throws Exception {
		super.setup();

		// create pays
		pays = paysRepository.save(buildPays());

		// create operateur
		operateur = buildOperateur().nom("operateur");
		operateur.setNationalite(pays);
		operateur = operateurRepository.save(operateur);

		requeteGroupe1 = buildRequeteGroupe();
		requeteGroupe1.setCreePar(operateur);

		requeteGroupe2 = buildRequeteGroupe();
		requeteGroupe2.setCreePar(operateur);

		requeteGroupeRepository.save(Arrays.asList(requeteGroupe1, requeteGroupe2));
	}

	@Override
	@After
	public void cleanup() throws Exception {
		requeteGroupeRepository.deleteAll();
		operateurRepository.deleteAll();
		super.cleanup();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void create() {

		RequeteGroupeCreateTO requeteGroupeCreateTO = buildRequeteGroupeCreateTO();

		DateTime before = new DateTime();
		Date date = new Date();

		int id = given().contentType(ContentType.JSON).body(requeteGroupeCreateTO).log().body()
				.post(ApiConstants.OPERATEUR_REQUETE_GROUPE_COLLECTION, operateur.getId()).then().log().body()
				.statusCode(200).extract().body().path("id");

		DateTime after = new DateTime();

		// check that the requete groupe has been saved
		RequeteGroupe actual = requeteGroupeRepository.findOne(Integer.toUnsignedLong(id));
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getNom()
				.contains(String.format("%04d-%02d-%02d-%02d-%02d-", DateUtils.getActualYear(date), date.getMonth(),
						date.getDay(), date.getHours(), date.getMinutes(), date.getSeconds(),
						UUID.randomUUID().toString())),
				is(equalTo(true)));
		// check that the name pattern matched
		Pattern pattern = Pattern
				.compile("^([0-9]{4}\\-[0-9]{2}\\-[0-9]{2}\\-[0-9]{2}\\-[0-9]{2}\\-[0-9]{2}\\-[0-9a-zA-Z\\-]+)$");
		Matcher matcher = pattern.matcher(actual.getNom());
		assertThat(matcher.find(), is(equalTo(true)));
		assertThat(actual.getDescription(), is(equalTo(requeteGroupeCreateTO.getDescription())));
		assertThat(actual.getStatus(), is(equalTo(StatusRequeteValues.DRAFT)));
		assertThat(before.isBefore(actual.getCreeLe().getTime()), is(equalTo(true)));
		assertThat(after.isAfter(actual.getCreeLe().getTime()), is(equalTo(true)));
	}

	@Test
	public void createOperateurNotFound() {

		RequeteGroupeCreateTO requeteGroupeCreateTO = buildRequeteGroupeCreateTO();

		given().contentType(ContentType.JSON).body(requeteGroupeCreateTO).log().body()
				.post(ApiConstants.OPERATEUR_REQUETE_GROUPE_COLLECTION, random.nextLong()).then().log().body()
				.statusCode(404);
	}

	@Test
	public void list() {
		given().get(ApiConstants.OPERATEUR_REQUETE_GROUPE_COLLECTION, operateur.getId()).then().log().body()
				.statusCode(200).body("size()", is(equalTo(2)))
				.body("id", containsInAnyOrder(requeteGroupe1.getId().intValue(), requeteGroupe2.getId().intValue()))
				.body("find{it.id==" + requeteGroupe1.getId().intValue() + "}.nom",
						is(equalTo(requeteGroupe1.getNom())))
				.body("find{it.id==" + requeteGroupe1.getId().intValue() + "}.description",
						is(equalTo(requeteGroupe1.getDescription())))
				.body("find{it.id==" + requeteGroupe1.getId().intValue() + "}.creePar.id",
						is(equalTo(requeteGroupe1.getCreePar().getId().intValue())))
				.body("find{it.id==" + requeteGroupe1.getId().intValue() + "}.creeLe",
						is(equalTo(requeteGroupe1.getCreeLe().getTime())))
				.body("find{it.id==" + requeteGroupe1.getId().intValue() + "}.status",
						is(equalTo(requeteGroupe1.getStatus())));
	}

	@Test
	public void get() {
		given().get(ApiConstants.OPERATEUR_REQUETE_GROUPE_ITEM, operateur.getId(), requeteGroupe1.getId()).then().log()
				.body().statusCode(200).body("id", is(equalTo(requeteGroupe1.getId().intValue())))
				.body("nom", is(equalTo(requeteGroupe1.getNom())))
				.body("description", is(equalTo(requeteGroupe1.getDescription())))
				.body("creePar.id", is(equalTo(requeteGroupe1.getCreePar().getId().intValue())))
				.body("creeLe", is(equalTo(requeteGroupe1.getCreeLe().getTime())))
				.body("status", is(equalTo(requeteGroupe1.getStatus())));
	}

	@Test
	public void getNotFound() {
		given().get(ApiConstants.OPERATEUR_REQUETE_GROUPE_ITEM, operateur.getId(), random.nextLong()).then()
				.statusCode(404);
	}

	@Test
	public void delete() {

		given().delete(ApiConstants.OPERATEUR_REQUETE_GROUPE_ITEM, operateur.getId(), requeteGroupe1.getId()).then()
				.statusCode(200);

		// check that the requete groupe has been deleted
		Operateur actual = operateurRepository.findOne(requeteGroupe1.getId());
		assertThat(actual, is(nullValue()));
	}

	@Test
	public void deleteNotFound() {
		given().delete(ApiConstants.OPERATEUR_REQUETE_GROUPE_ITEM, operateur.getId(), random.nextLong()).then()
				.statusCode(404);
	}

	@Test
	public void update() {

		RequeteGroupeUpdateTO update = buildRequeteGroupeUpdateTO();

		RequeteGroupe requeteGroupe = requeteGroupeRepository.findOne(requeteGroupe1.getId());

		given().contentType(ContentType.JSON).body(update)
				.put(ApiConstants.OPERATEUR_REQUETE_GROUPE_ITEM, operateur.getId().intValue(), requeteGroupe1.getId())
				.then().log().body().statusCode(200);

		// check that the requete groupe has been saved
		RequeteGroupe actual = requeteGroupeRepository.findOne(requeteGroupe1.getId());
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getNom(), is(equalTo(requeteGroupe.getNom())));
		assertThat(actual.getDescription(), is(equalTo(update.getDescription())));
		assertThat(actual.getCreeLe(), is(equalTo(requeteGroupe.getCreeLe())));
		assertThat(actual.getCreePar().getId(), is(equalTo(requeteGroupe.getCreePar().getId())));
		assertThat(actual.getStatus(), is(equalTo(requeteGroupe.getStatus())));

	}

	@Test
	public void updateNotFound() {

		RequeteGroupeUpdateTO update = buildRequeteGroupeUpdateTO();

		given().contentType(ContentType.JSON).body(update)
				.put(ApiConstants.OPERATEUR_REQUETE_GROUPE_ITEM, operateur.getId(), random.nextLong()).then().log()
				.body().statusCode(404);
	}
}
