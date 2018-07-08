package com.ambacam.rest.operateurs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.ambacam.ItBase;
import com.ambacam.configuration.AppSettings;
import com.ambacam.model.Operateur;
import com.ambacam.model.Pays;
import com.ambacam.repository.OperateurRepository;
import com.ambacam.repository.PaysRepository;
import com.ambacam.rest.ApiConstants;
import com.ambacam.search.operateurs.OperateurCriteria;
import com.ambacam.service.OperateurService;

import io.restassured.http.ContentType;

public class OperateursSearchResourceIT extends ItBase {

	@Autowired
	private OperateurRepository repository;

	@Autowired
	private PaysRepository paysRepository;

	@Autowired
	private OperateurService operateurService;

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
		MockitoAnnotations.initMocks(this);

		super.setup();

		// create pays1
		pays1 = paysRepository.save(buildPays());
		// create pays2
		pays2 = paysRepository.save(buildPays());

		Thread.sleep(1000);
		// create creator1
		creator1 = repository.save(buildOperateur().nom("creator1").nationalite(pays1));

		Thread.sleep(1000);
		// create creator2
		creator2 = repository.save(buildOperateur().nom("creator2").nationalite(pays2));

		Thread.sleep(1000);
		// create operateur1
		operateur1 = buildOperateur().nom("operateur1");
		operateur1.setCreePar(creator1);
		operateur1.setNationalite(pays1);
		operateur1 = repository.save(operateur1);

		Thread.sleep(1000);
		// create operateur 2
		operateur2 = buildOperateur().nom("operateur2");
		operateur2.setCreePar(creator2);
		operateur2.setNationalite(pays2);
		operateur2 = repository.save(operateur2);

		simulateNominalBehaviors();
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
	public void search() {
		OperateurCriteria criteria = new OperateurCriteria();

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.OPERATEUR_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(2)))
				.body("content.size()", is(equalTo(2)))
				.body("content.id", contains(creator1.getId().intValue(), creator2.getId().intValue()))
				.body("content[0].id", is(equalTo(creator1.getId().intValue())))
				.body("content[0].nom", is(equalTo(creator1.getNom())))
				.body("content[0].prenom", is(equalTo(creator1.getPrenom())))
				.body("content[0].sexe", is(equalTo(creator1.getSexe())))
				.body("content[0].nationalite.id", is(equalTo(creator1.getNationalite().getId().intValue())))
				.body("content[0].creePar.id", is(nullValue()))
				.body("content[0].creeLe", is(equalTo(creator1.getCreeLe().getTime())))
				.body("content[0].roles.size()", is(equalTo(0)));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchLimit() {
		OperateurCriteria criteria = new OperateurCriteria();

		// System under test
		given().queryParam("limit", 3).contentType(ContentType.JSON).body(criteria).log().body()
				.post(ApiConstants.OPERATEUR_SEARCH_COLLECTION).then().log().body().statusCode(200)
				.body("page", is(equalTo(0))).body("totalPages", is(equalTo(2))).body("content.size()", is(equalTo(3)))
				.body("content.id",
						contains(creator1.getId().intValue(), creator2.getId().intValue(),
								operateur1.getId().intValue()))
				.body("content[0].id", is(equalTo(creator1.getId().intValue())))
				.body("content[0].nom", is(equalTo(creator1.getNom())))
				.body("content[0].prenom", is(equalTo(creator1.getPrenom())))
				.body("content[0].sexe", is(equalTo(creator1.getSexe())))
				.body("content[0].nationalite.id", is(equalTo(creator1.getNationalite().getId().intValue())))
				.body("content[0].creePar.id", is(nullValue()))
				.body("content[0].creeLe", is(equalTo(creator1.getCreeLe().getTime())))
				.body("content[0].roles.size()", is(equalTo(0)));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchPage() {
		OperateurCriteria criteria = new OperateurCriteria();

		// System under test
		given().queryParam("page", 1).contentType(ContentType.JSON).body(criteria).log().body()
				.post(ApiConstants.OPERATEUR_SEARCH_COLLECTION).then().log().body().statusCode(200)
				.body("page", is(equalTo(1))).body("totalPages", is(equalTo(2))).body("content.size()", is(equalTo(2)))
				.body("content.id", contains(operateur1.getId().intValue(), operateur2.getId().intValue()))
				.body("content[0].id", is(equalTo(operateur1.getId().intValue())))
				.body("content[0].nom", is(equalTo(operateur1.getNom())))
				.body("content[0].prenom", is(equalTo(operateur1.getPrenom())))
				.body("content[0].sexe", is(equalTo(operateur1.getSexe())))
				.body("content[0].nationalite.id", is(operateur1.getNationalite().getId().intValue()))
				.body("content[0].creePar.id", is(operateur1.getCreePar().getId().intValue()))
				.body("content[0].creeLe", is(equalTo(operateur1.getCreeLe().getTime())))
				.body("content[0].roles.size()", is(equalTo(0)));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchLimitGreaterThanTotalNumberOfElement() {
		OperateurCriteria criteria = new OperateurCriteria();

		// System under test
		given().queryParam("limit", 10).contentType(ContentType.JSON).body(criteria).log().body()
				.post(ApiConstants.OPERATEUR_SEARCH_COLLECTION).then().log().body().statusCode(200)
				.body("page", is(equalTo(0))).body("totalPages", is(equalTo(1))).body("content.size()", is(equalTo(4)))
				.body("content.id",
						contains(creator1.getId().intValue(), creator2.getId().intValue(),
								operateur1.getId().intValue(), operateur2.getId().intValue()))
				.body("content[0].id", is(equalTo(creator1.getId().intValue())))
				.body("content[0].nom", is(equalTo(creator1.getNom())))
				.body("content[0].prenom", is(equalTo(creator1.getPrenom())))
				.body("content[0].sexe", is(equalTo(creator1.getSexe())))
				.body("content[0].nationalite.id", is(equalTo(creator1.getNationalite().getId().intValue())))
				.body("content[0].creePar.id", is(nullValue()))
				.body("content[0].creeLe", is(equalTo(creator1.getCreeLe().getTime())))
				.body("content[0].roles.size()", is(equalTo(0)));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchPageOutOfBound() {
		OperateurCriteria criteria = new OperateurCriteria();

		// System under test
		given().queryParam("page", 10).contentType(ContentType.JSON).body(criteria).log().body()
				.post(ApiConstants.OPERATEUR_SEARCH_COLLECTION).then().log().body().statusCode(200)
				.body("page", is(equalTo(10))).body("totalPages", is(equalTo(2)))
				.body("content.size()", is(equalTo(0)));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByKeyword() {
		OperateurCriteria criteria = new OperateurCriteria();
		criteria.setKeyword("creator");

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.OPERATEUR_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("content.size()", is(equalTo(2)))
				.body("content.id", contains(creator1.getId().intValue(), creator2.getId().intValue()))
				.body("content[0].id", is(equalTo(creator1.getId().intValue())))
				.body("content[0].nom", is(equalTo(creator1.getNom())))
				.body("content[0].prenom", is(equalTo(creator1.getPrenom())))
				.body("content[0].sexe", is(equalTo(creator1.getSexe())))
				.body("content[0].nationalite.id", is(equalTo(creator1.getNationalite().getId().intValue())))
				.body("content[0].creePar.id", is(nullValue()))
				.body("content[0].creeLe", is(equalTo(creator1.getCreeLe().getTime())))
				.body("content[0].roles.size()", is(equalTo(0)));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByKeywordIgnoreUppercase() {
		OperateurCriteria criteria = new OperateurCriteria();
		criteria.setKeyword(operateur1.getPrenom().substring(10).toUpperCase());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.OPERATEUR_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("content.size()", is(equalTo(1))).body("content.id", contains(operateur1.getId().intValue()))
				.body("content[0].id", is(equalTo(operateur1.getId().intValue())))
				.body("content[0].nom", is(equalTo(operateur1.getNom())))
				.body("content[0].prenom", is(equalTo(operateur1.getPrenom())))
				.body("content[0].sexe", is(equalTo(operateur1.getSexe())))
				.body("content[0].nationalite.id", is(operateur1.getNationalite().getId().intValue()))
				.body("content[0].creePar.id", is(operateur1.getCreePar().getId().intValue()))
				.body("content[0].creeLe", is(equalTo(operateur1.getCreeLe().getTime())))
				.body("content[0].roles.size()", is(equalTo(0)));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByKeywordIgnoreLowercase() {
		OperateurCriteria criteria = new OperateurCriteria();
		criteria.setKeyword(operateur1.getPrenom().substring(10).toLowerCase());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.OPERATEUR_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("content.size()", is(equalTo(1))).body("content.id", contains(operateur1.getId().intValue()))
				.body("content[0].id", is(equalTo(operateur1.getId().intValue())))
				.body("content[0].nom", is(equalTo(operateur1.getNom())))
				.body("content[0].prenom", is(equalTo(operateur1.getPrenom())))
				.body("content[0].sexe", is(equalTo(operateur1.getSexe())))
				.body("content[0].nationalite.id", is(operateur1.getNationalite().getId().intValue()))
				.body("content[0].creePar.id", is(operateur1.getCreePar().getId().intValue()))
				.body("content[0].creeLe", is(equalTo(operateur1.getCreeLe().getTime())))
				.body("content[0].roles.size()", is(equalTo(0)));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByDateBefore() {
		OperateurCriteria criteria = new OperateurCriteria();
		criteria.setCreeLeBefore(creator2.getCreeLe());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.OPERATEUR_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("content.size()", is(equalTo(2)))
				.body("content.id", contains(creator1.getId().intValue(), creator2.getId().intValue()))
				.body("content[0].id", is(equalTo(creator1.getId().intValue())))
				.body("content[0].nom", is(equalTo(creator1.getNom())))
				.body("content[0].prenom", is(equalTo(creator1.getPrenom())))
				.body("content[0].sexe", is(equalTo(creator1.getSexe())))
				.body("content[0].nationalite.id", is(equalTo(creator1.getNationalite().getId().intValue())))
				.body("content[0].creePar.id", is(nullValue()))
				.body("content[0].creeLe", is(equalTo(creator1.getCreeLe().getTime())))
				.body("content[0].roles.size()", is(equalTo(0)));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByDateAfter() {
		OperateurCriteria criteria = new OperateurCriteria();
		criteria.setCreeLeAfter(operateur1.getCreeLe());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.OPERATEUR_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("content.size()", is(equalTo(2)))
				.body("content.id", contains(operateur1.getId().intValue(), operateur2.getId().intValue()))
				.body("content[0].id", is(equalTo(operateur1.getId().intValue())))
				.body("content[0].nom", is(equalTo(operateur1.getNom())))
				.body("content[0].prenom", is(equalTo(operateur1.getPrenom())))
				.body("content[0].sexe", is(equalTo(operateur1.getSexe())))
				.body("content[0].nationalite.id", is(operateur1.getNationalite().getId().intValue()))
				.body("content[0].creePar.id", is(operateur1.getCreePar().getId().intValue()))
				.body("content[0].creeLe", is(equalTo(operateur1.getCreeLe().getTime())))
				.body("content[0].roles.size()", is(equalTo(0)));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByDateBeforeAndDateAfter() {
		OperateurCriteria criteria = new OperateurCriteria();
		criteria.setCreeLeBefore(operateur2.getCreeLe());
		criteria.setCreeLeAfter(operateur1.getCreeLe());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.OPERATEUR_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("content.size()", is(equalTo(2)))
				.body("content.id", contains(operateur1.getId().intValue(), operateur2.getId().intValue()))
				.body("content[0].id", is(equalTo(operateur1.getId().intValue())))
				.body("content[0].nom", is(equalTo(operateur1.getNom())))
				.body("content[0].prenom", is(equalTo(operateur1.getPrenom())))
				.body("content[0].sexe", is(equalTo(operateur1.getSexe())))
				.body("content[0].nationalite.id", is(operateur1.getNationalite().getId().intValue()))
				.body("content[0].creePar.id", is(operateur1.getCreePar().getId().intValue()))
				.body("content[0].creeLe", is(equalTo(operateur1.getCreeLe().getTime())))
				.body("content[0].roles.size()", is(equalTo(0)));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByCreatorId() {
		OperateurCriteria criteria = new OperateurCriteria();
		criteria.setCreatorId(creator1.getId());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.OPERATEUR_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("content.size()", is(equalTo(1))).body("content.id", contains(operateur1.getId().intValue()))
				.body("content[0].id", is(equalTo(operateur1.getId().intValue())))
				.body("content[0].nom", is(equalTo(operateur1.getNom())))
				.body("content[0].prenom", is(equalTo(operateur1.getPrenom())))
				.body("content[0].sexe", is(equalTo(operateur1.getSexe())))
				.body("content[0].nationalite.id", is(operateur1.getNationalite().getId().intValue()))
				.body("content[0].creePar.id", is(operateur1.getCreePar().getId().intValue()))
				.body("content[0].creeLe", is(equalTo(operateur1.getCreeLe().getTime())))
				.body("content[0].roles.size()", is(equalTo(0)));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByDateBeforeAndDateAfterAndKeyWordAnCreatorId() {
		OperateurCriteria criteria = new OperateurCriteria();
		criteria.setCreeLeBefore(operateur2.getCreeLe());
		criteria.setCreeLeAfter(creator1.getCreeLe());
		criteria.setKeyword("prenom");
		criteria.setCreatorId(creator1.getId());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.OPERATEUR_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("content.size()", is(equalTo(1))).body("content.id", contains(operateur1.getId().intValue()))
				.body("content[0].id", is(equalTo(operateur1.getId().intValue())))
				.body("content[0].nom", is(equalTo(operateur1.getNom())))
				.body("content[0].prenom", is(equalTo(operateur1.getPrenom())))
				.body("content[0].sexe", is(equalTo(operateur1.getSexe())))
				.body("content[0].nationalite.id", is(operateur1.getNationalite().getId().intValue()))
				.body("content[0].creePar.id", is(operateur1.getCreePar().getId().intValue()))
				.body("content[0].creeLe", is(equalTo(operateur1.getCreeLe().getTime())))
				.body("content[0].roles.size()", is(equalTo(0)));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	private void simulateNominalBehaviors() {

		// Simulate the page size to simplify the tests
		when(appSettings.getSearchDefaultPageSize()).thenReturn(2);
		operateurService.setAppSettings(appSettings);
	}
}
