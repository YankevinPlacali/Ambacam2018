package com.ambacam.rest.requerants;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.ambacam.model.Requerant;
import com.ambacam.repository.OperateurRepository;
import com.ambacam.repository.PaysRepository;
import com.ambacam.repository.RequerantRepository;
import com.ambacam.rest.ApiConstants;
import com.ambacam.search.requerants.RequerantCriteria;
import com.ambacam.service.RequerantService;

import io.restassured.http.ContentType;

public class RequerantsSearchResourceIT extends ItBase {

	@Autowired
	private RequerantRepository repository;

	@Autowired
	private OperateurRepository operateurRepository;

	@Autowired
	private PaysRepository paysRepository;

	@Autowired
	private RequerantService requerantService;

	@Mock
	private AppSettings appSettings;

	private Requerant requerant1;

	private Requerant requerant2;

	private Requerant requerant3;

	private Operateur creator1;

	private Operateur creator2;

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
		creator1 = operateurRepository.save(buildOperateur().nom("creator1").nationalite(pays1));

		// create creator2
		creator2 = operateurRepository.save(buildOperateur().nom("creator2").nationalite(pays2));

		// create requerants
		requerant1 = repository.save(buildRequerant().nom("keyWord").creePar(creator1).nationalite(pays1));

		Thread.sleep(1000);

		requerant2 = repository
				.save(buildRequerant().nom("requerant2").prenom("keyWord").creePar(creator1).nationalite(pays1));

		Thread.sleep(1000);

		requerant3 = repository.save(buildRequerant().nom("requerant3").creePar(creator2).nationalite(pays2)
				.dateNaissance(requerant1.getDateNaissance()));

		simulateNominalBehaviors();
	}

	@Override
	@After
	public void cleanup() throws Exception {
		repository.deleteAll();
		operateurRepository.deleteAll();
		paysRepository.deleteAll();
		super.cleanup();
	}

	@Test
	public void search() {
		RequerantCriteria criteria = new RequerantCriteria();

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.REQUERANT_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(2)))
				.body("requerants.size()", is(equalTo(2)))
				.body("requerants.id", contains(requerant1.getId().intValue(), requerant2.getId().intValue()))
				.body("requerants[0].id", is(equalTo(requerant1.getId().intValue())))
				.body("requerants[0].nom", is(equalTo(requerant1.getNom())))
				.body("requerants[0].prenom", is(equalTo(requerant1.getPrenom())))
				.body("requerants[0].dateNaissance", is(equalTo(requerant1.getDateNaissance().getTime())))
				.body("requerants[0].creeLe", is(equalTo(requerant1.getCreeLe().getTime())))
				.body("requerants[0].sexe", is(equalTo(requerant1.getSexe())))
				.body("requerants[0].profession", is(equalTo(requerant1.getProfession())))
				.body("requerants[0].nationalite", is(equalTo(requerant1.getNationalite().getNom())))
				.body("requerants[0].lieuNaissance", is(equalTo(requerant1.getLieuNaissance())));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchLimit() {
		RequerantCriteria criteria = new RequerantCriteria();

		// System under test
		given().queryParam("limit", 2).contentType(ContentType.JSON).body(criteria).log().body()
				.post(ApiConstants.REQUERANT_SEARCH_COLLECTION).then().log().body().statusCode(200)
				.body("page", is(equalTo(0))).body("totalPages", is(equalTo(2)))
				.body("requerants.size()", is(equalTo(2)))
				.body("requerants.id", contains(requerant1.getId().intValue(), requerant2.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchPage() {
		RequerantCriteria criteria = new RequerantCriteria();

		// System under test
		given().queryParam("page", 1).contentType(ContentType.JSON).body(criteria).log().body()
				.post(ApiConstants.REQUERANT_SEARCH_COLLECTION).then().log().body().statusCode(200)
				.body("page", is(equalTo(1))).body("totalPages", is(equalTo(2)))
				.body("requerants.size()", is(equalTo(1)))
				.body("requerants.id", contains(requerant3.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchLimitGreaterThanTotalNumberOfElement() {
		RequerantCriteria criteria = new RequerantCriteria();

		// System under test
		given().queryParam("limit", 10).contentType(ContentType.JSON).body(criteria).log().body()
				.post(ApiConstants.REQUERANT_SEARCH_COLLECTION).then().log().body().statusCode(200)
				.body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("requerants.size()", is(equalTo(3))).body("requerants.id", contains(requerant1.getId().intValue(),
						requerant2.getId().intValue(), requerant3.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchPageOutOfBound() {
		RequerantCriteria criteria = new RequerantCriteria();

		// System under test
		given().queryParam("page", 10).contentType(ContentType.JSON).body(criteria).log().body()
				.post(ApiConstants.REQUERANT_SEARCH_COLLECTION).then().log().body().statusCode(200)
				.body("page", is(equalTo(10))).body("totalPages", is(equalTo(2)))
				.body("requerants.size()", is(equalTo(0)));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByKeyword() {
		RequerantCriteria criteria = new RequerantCriteria();
		criteria.setKeyword("keyWord");

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.REQUERANT_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("requerants.size()", is(equalTo(2)))
				.body("requerants.id", contains(requerant1.getId().intValue(), requerant2.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByKeywordIgnoreUppercase() {
		RequerantCriteria criteria = new RequerantCriteria();
		criteria.setKeyword(requerant1.getPrenom().substring(10).toUpperCase());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.REQUERANT_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("requerants.size()", is(equalTo(1)))
				.body("requerants.id", contains(requerant1.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByKeywordIgnoreLowercase() {
		RequerantCriteria criteria = new RequerantCriteria();
		criteria.setKeyword(requerant1.getPrenom().substring(10).toLowerCase());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.REQUERANT_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("requerants.size()", is(equalTo(1)))
				.body("requerants.id", contains(requerant1.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByDateBefore() {
		RequerantCriteria criteria = new RequerantCriteria();
		criteria.setCreeLeBefore(requerant2.getCreeLe());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.REQUERANT_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("requerants.size()", is(equalTo(2)))
				.body("requerants.id", contains(requerant1.getId().intValue(), requerant2.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByDateAfter() {
		RequerantCriteria criteria = new RequerantCriteria();
		criteria.setCreeLeAfter(requerant2.getCreeLe());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.REQUERANT_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("requerants.size()", is(equalTo(2)))
				.body("requerants.id", contains(requerant2.getId().intValue(), requerant3.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByDateBeforeAndDateAfter() {
		DateTime after = new DateTime(requerant1.getCreeLe()).minusSeconds(1);
		RequerantCriteria criteria = new RequerantCriteria();
		criteria.setCreeLeBefore(requerant3.getCreeLe());
		criteria.setCreeLeAfter(after.toDate());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.REQUERANT_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(2)))
				.body("requerants.size()", is(equalTo(2)))
				.body("requerants.id", contains(requerant1.getId().intValue(), requerant2.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByCreatorId() {
		RequerantCriteria criteria = new RequerantCriteria();
		criteria.setCreatorId(creator1.getId());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.REQUERANT_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("requerants.size()", is(equalTo(2)))
				.body("requerants.id", contains(requerant1.getId().intValue(), requerant2.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByDateNaissance() {
		RequerantCriteria criteria = new RequerantCriteria();
		criteria.setDateNaissance(requerant1.getDateNaissance());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.REQUERANT_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("requerants.size()", is(equalTo(2)))
				.body("requerants.id", contains(requerant1.getId().intValue(), requerant3.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByDateNaissanceAndCreatorId() {
		RequerantCriteria criteria = new RequerantCriteria();
		criteria.setDateNaissance(requerant1.getDateNaissance());
		criteria.setCreatorId(creator1.getId());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.REQUERANT_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("requerants.size()", is(equalTo(1)))
				.body("requerants.id", contains(requerant1.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void searchByDateBeforeAndDateAfterAndKeyWordAnCreatorId() {
		RequerantCriteria criteria = new RequerantCriteria();
		criteria.setDateNaissance(requerant1.getDateNaissance());
		;

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.REQUERANT_SEARCH_COLLECTION)
				.then().log().body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("requerants.size()", is(equalTo(2)))
				.body("requerants.id", contains(requerant1.getId().intValue(), requerant3.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	private void simulateNominalBehaviors() {

		// Simulate the page size to simplify the tests
		when(appSettings.getSearchDefaultPageSize()).thenReturn(2);
		requerantService.setAppSettings(appSettings);
	}

}
