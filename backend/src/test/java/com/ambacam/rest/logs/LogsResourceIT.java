package com.ambacam.rest.logs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTimeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.ambacam.ItBase;
import com.ambacam.configuration.AppSettings;
import com.ambacam.model.Action;
import com.ambacam.model.Log;
import com.ambacam.model.LogActeur;
import com.ambacam.model.MotifSuppression;
import com.ambacam.repository.LogRepository;
import com.ambacam.rest.ApiConstants;
import com.ambacam.search.logs.LogCriteria;
import com.ambacam.service.LogService;

import io.restassured.http.ContentType;

public class LogsResourceIT extends ItBase {

	@Autowired
	private LogRepository repository;

	@Mock
	private AppSettings appSettings;

	@Autowired
	private LogService logService;

	private Object acteur1;

	private Object acteur2;

	private Action action1;

	private Action action2;

	private MotifSuppression motif1;

	private MotifSuppression motif2;

	private LogActeur logActeur1;

	private LogActeur logActeur2;

	private Map<String, String> properties1;

	private Map<String, String> properties2;

	private Log log1;

	private Log log2;

	@Before
	public void setup() throws Exception {

		MockitoAnnotations.initMocks(this);
		super.setup();

		// create actions
		action1 = new Action().nom("nom-" + random.nextLong());
		action2 = new Action().nom("nom-" + random.nextLong());

		// create properties
		properties1 = new HashMap<>();
		properties1.put("key11", "value11");
		properties1.put("key12", "value12");
		properties1.put("key13", "value13");

		properties2 = new HashMap<>();
		properties2.put("key21", "value21");
		properties2.put("key22", "value22");
		properties2.put("key23", "value23");

		// create acteurs
		acteur1 = new Object();
		acteur2 = new Object();

		logActeur1 = buildLogActeur(acteur1, properties1);
		logActeur2 = buildLogActeur(acteur2, properties2);

		// create motifs
		motif1 = new MotifSuppression().nom("nom-" + random.nextLong());
		motif2 = new MotifSuppression().nom("nom-" + random.nextLong());

		// create log1
		// ensure that log1 is always created firstly
		DateTimeUtils.setCurrentMillisFixed(1000);
		log1 = buildLog(logActeur1, logActeur2, action1, motif1);

		// ensure that log2 is always created secondly
		DateTimeUtils.setCurrentMillisFixed(2000);
		log2 = buildLog(logActeur2, logActeur1, action2, motif2);

		repository.save(Arrays.asList(log1, log2));

		simulateNominalBehaviors();
	}

	@Override
	@After
	public void cleanup() throws Exception {
		repository.deleteAll();

		// reset to the normal date time
		DateTimeUtils.setCurrentMillisSystem();
		super.cleanup();
	}

	@Test
	public void list() {
		LogCriteria criteria = new LogCriteria();

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.LOG_COLLECTION).then().log()
				.body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("content.size()", is(equalTo(2)))
				.body("content.id", contains(log1.getId().intValue(), log2.getId().intValue()))
				.body("content[0].id", is(equalTo(log1.getId().intValue())))
				.body("content[0].description", is(equalTo(log1.getDescription())));
	}

	@Test
	public void listLimit() {
		LogCriteria criteria = new LogCriteria();

		// ensure that log3 is always created thirdly
		DateTimeUtils.setCurrentMillisFixed(3000);
		Log log3 = buildLog(logActeur1, logActeur2, action1, motif1);

		// ensure that log4 is always created fourthly
		DateTimeUtils.setCurrentMillisFixed(4000);
		Log log4 = buildLog(logActeur2, logActeur1, action2, motif2);

		repository.save(Arrays.asList(log3, log4));

		// System under test
		given().queryParam("limit", 3).contentType(ContentType.JSON).body(criteria).log().body()
				.post(ApiConstants.LOG_COLLECTION).then().log().body().statusCode(200).body("page", is(equalTo(0)))
				.body("totalPages", is(equalTo(2))).body("content.size()", is(equalTo(3)))
				.body("content.id", contains(log1.getId().intValue(), log2.getId().intValue(), log3.getId().intValue()));
	}

	@Test
	public void listPage() {
		LogCriteria criteria = new LogCriteria();

		// ensure that log3 is always created thirdly
		DateTimeUtils.setCurrentMillisFixed(3000);
		Log log3 = buildLog(logActeur1, logActeur2, action1, motif1);

		// ensure that log4 is always created fourthly
		DateTimeUtils.setCurrentMillisFixed(4000);
		Log log4 = buildLog(logActeur2, logActeur1, action2, motif2);

		repository.save(Arrays.asList(log3, log4));

		// System under test
		given().queryParam("page", 1).contentType(ContentType.JSON).body(criteria).log().body()
				.post(ApiConstants.LOG_COLLECTION).then().log().body().statusCode(200).body("page", is(equalTo(1)))
				.body("totalPages", is(equalTo(2))).body("content.size()", is(equalTo(2)))
				.body("content.id", contains(log3.getId().intValue(), log4.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void listLimitPage() {
		LogCriteria criteria = new LogCriteria();

		// ensure that log3 is always created thirdly
		DateTimeUtils.setCurrentMillisFixed(3000);
		Log log3 = buildLog(logActeur1, logActeur2, action1, motif1);

		// ensure that log4 is always created fourthly
		DateTimeUtils.setCurrentMillisFixed(4000);
		Log log4 = buildLog(logActeur2, logActeur1, action2, motif2);

		repository.save(Arrays.asList(log3, log4));

		// System under test
		given().queryParam("limit", 3).queryParam("page", 1).contentType(ContentType.JSON).body(criteria).log().body()
				.post(ApiConstants.LOG_COLLECTION).then().log().body().statusCode(200).body("page", is(equalTo(1)))
				.body("totalPages", is(equalTo(2))).body("content.size()", is(equalTo(1)))
				.body("content.id", contains(log4.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void listLimitGreaterThanTotalNumberOfElement() {
		LogCriteria criteria = new LogCriteria();

		// ensure that log3 is always created thirdly
		DateTimeUtils.setCurrentMillisFixed(3000);
		Log log3 = buildLog(logActeur1, logActeur2, action1, motif1);

		// ensure that log4 is always created fourthly
		DateTimeUtils.setCurrentMillisFixed(4000);
		Log log4 = buildLog(logActeur2, logActeur1, action2, motif2);

		repository.save(Arrays.asList(log3, log4));

		// System under test
		given().queryParam("limit", 10).contentType(ContentType.JSON).body(criteria).log().body()
				.post(ApiConstants.LOG_COLLECTION).then().log().body().statusCode(200).body("page", is(equalTo(0)))
				.body("totalPages", is(equalTo(1))).body("content.size()", is(equalTo(4)))
				.body("content.id", contains(log1.getId().intValue(), log2.getId().intValue(), log3.getId().intValue(),
						log4.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void listPageOutOfBound() {
		LogCriteria criteria = new LogCriteria();

		// ensure that log3 is always created thirdly
		DateTimeUtils.setCurrentMillisFixed(3000);
		Log log3 = buildLog(logActeur1, logActeur2, action1, motif1);

		// ensure that log4 is always created fourthly
		DateTimeUtils.setCurrentMillisFixed(4000);
		Log log4 = buildLog(logActeur2, logActeur1, action2, motif2);

		repository.save(Arrays.asList(log3, log4));

		// System under test
		given().queryParam("page", 10).contentType(ContentType.JSON).body(criteria).log().body()
				.post(ApiConstants.LOG_COLLECTION).then().log().body().statusCode(200).body("page", is(equalTo(10)))
				.body("totalPages", is(equalTo(2))).body("content.size()", is(equalTo(0)));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void listByKeyword() {
		LogCriteria criteria = new LogCriteria();
		criteria.setKeyword(log1.getDescription().substring(30));

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.LOG_COLLECTION).then().log()
				.body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("content.size()", is(equalTo(1))).body("content.id", contains(log1.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void listByKeywordIgnoreUppercase() {
		LogCriteria criteria = new LogCriteria();
		criteria.setKeyword(log1.getDescription().substring(30).toUpperCase());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.LOG_COLLECTION).then().log()
				.body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("content.size()", is(equalTo(1))).body("content.id", contains(log1.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void listByKeywordIgnoreLowercase() {
		LogCriteria criteria = new LogCriteria();
		criteria.setKeyword(log1.getDescription().substring(30).toUpperCase());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.LOG_COLLECTION).then().log()
				.body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("content.size()", is(equalTo(1))).body("content.id", contains(log1.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void listByDateBefore() {
		LogCriteria criteria = new LogCriteria();

		// ensure that log3 is always created thirdly
		DateTimeUtils.setCurrentMillisFixed(3000);
		Log log3 = buildLog(logActeur1, logActeur2, action1, motif1);

		// ensure that log4 is always created fourthly
		DateTimeUtils.setCurrentMillisFixed(4000);
		Log log4 = buildLog(logActeur2, logActeur1, action2, motif2);

		repository.save(Arrays.asList(log3, log4));

		criteria.setDateBefore(log3.getDate());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.LOG_COLLECTION).then().log()
				.body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(2)))
				.body("content.size()", is(equalTo(2)))
				.body("content.id", contains(log1.getId().intValue(), log2.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void listByDateAfter() {
		LogCriteria criteria = new LogCriteria();

		// ensure that log3 is always created thirdly
		DateTimeUtils.setCurrentMillisFixed(3000);
		Log log3 = buildLog(logActeur1, logActeur2, action1, motif1);

		// ensure that log4 is always created fourthly
		DateTimeUtils.setCurrentMillisFixed(4000);
		Log log4 = buildLog(logActeur2, logActeur1, action2, motif2);

		repository.save(Arrays.asList(log3, log4));

		criteria.setDateAfter(log3.getDate());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.LOG_COLLECTION).then().log()
				.body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("content.size()", is(equalTo(2)))
				.body("content.id", contains(log3.getId().intValue(), log4.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void listByDateBeforeAndDateAfter() {
		LogCriteria criteria = new LogCriteria();

		// ensure that log3 is always created thirdly
		DateTimeUtils.setCurrentMillisFixed(3000);
		Log log3 = buildLog(logActeur1, logActeur2, action1, motif1);

		// ensure that log4 is always created fourthly
		DateTimeUtils.setCurrentMillisFixed(4000);
		Log log4 = buildLog(logActeur2, logActeur1, action2, motif2);

		repository.save(Arrays.asList(log3, log4));

		criteria.setDateBefore(log3.getDate());
		criteria.setDateAfter(log2.getDate());

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.LOG_COLLECTION).then().log()
				.body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("content.size()", is(equalTo(2)))
				.body("content.id", contains(log2.getId().intValue(), log3.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void listByDateBeforeAndDateAfterAndKeyWord() {
		LogCriteria criteria = new LogCriteria();

		// ensure that log3 is always created thirdly
		DateTimeUtils.setCurrentMillisFixed(3000);
		Log log3 = buildLog(logActeur1, logActeur2, action1, motif1);

		// ensure that log4 is always created fourthly
		DateTimeUtils.setCurrentMillisFixed(4000);
		Log log4 = buildLog(logActeur2, logActeur1, action2, motif2);

		repository.save(Arrays.asList(log3, log4));

		criteria.setDateBefore(log3.getDate());
		criteria.setDateAfter(log2.getDate());
		criteria.setKeyword(log2.getDescription().substring(30));

		// System under test
		given().contentType(ContentType.JSON).body(criteria).log().body().post(ApiConstants.LOG_COLLECTION).then().log()
				.body().statusCode(200).body("page", is(equalTo(0))).body("totalPages", is(equalTo(1)))
				.body("content.size()", is(equalTo(1))).body("content.id", contains(log2.getId().intValue()));

		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	private void simulateNominalBehaviors() {

		// Simulate the page size to simplify the tests
		when(appSettings.getSearchDefaultPageSize()).thenReturn(2);
		logService.setAppSettings(appSettings);
	}
}
