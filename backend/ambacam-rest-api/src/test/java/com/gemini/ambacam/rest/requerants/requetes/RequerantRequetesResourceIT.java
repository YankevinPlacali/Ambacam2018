package com.gemini.ambacam.rest.requerants.requetes;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import com.gemini.ambacam.ItBase;
import com.gemini.ambacam.configuration.AppSettings;
import com.gemini.ambacam.model.Operateur;
import com.gemini.ambacam.model.Pays;
import com.gemini.ambacam.model.Requerant;
import com.gemini.ambacam.model.Requete;
import com.gemini.ambacam.model.RequeteGroupe;
import com.gemini.ambacam.model.StatusRequete;
import com.gemini.ambacam.model.TypeRequete;
import com.gemini.ambacam.repository.OperateurRepository;
import com.gemini.ambacam.repository.PaysRepository;
import com.gemini.ambacam.repository.RequerantRepository;
import com.gemini.ambacam.repository.RequeteGroupeRepository;
import com.gemini.ambacam.repository.RequeteRepository;
import com.gemini.ambacam.repository.StatusRequeteRepository;
import com.gemini.ambacam.repository.TypeRequeteRepository;
import com.gemini.ambacam.rest.ApiConstants;
import com.gemini.ambacam.service.RequeteService;

public class RequerantRequetesResourceIT extends ItBase {

	@Autowired
	private TypeRequeteRepository typeRequeteRepository;

	@Autowired
	private StatusRequeteRepository statusRequeteRepository;

	@Autowired
	private RequeteGroupeRepository requeteGroupeRepository;

	@Autowired
	private PaysRepository paysRepository;

	@Autowired
	private OperateurRepository operateurRepository;

	@Autowired
	private RequerantRepository requerantRepository;

	@Autowired
	private RequeteRepository requeteRepository;

	@Autowired
	private RequeteService requeteService;

	@Mock
	private AppSettings appSettings;

	private TypeRequete typeRequete;

	private StatusRequete statusRequete;

	private RequeteGroupe requeteGroupe;

	private Pays pays;

	private Operateur operateur1;
	private Operateur operateur2;

	private Requerant requerant1;
	private Requerant requerant2;

	private Requete requete1;
	private Requete requete2;
	private Requete requete3;
	private Requete requete4;

	@Override
	@Before
	public void setup() throws Exception {
		super.setup();

		typeRequete = typeRequeteRepository.save(buildTypeRequete().nom("type1"));

		statusRequete = statusRequeteRepository.save(buildStatusRequete().nom("status1"));

		pays = paysRepository.save(buildPays());

		operateur1 = operateurRepository.save(buildOperateur().nom("operateur1").nationalite(pays));
		operateur2 = operateurRepository.save(buildOperateur().nom("operateur2").nationalite(pays));

		requeteGroupe = buildRequeteGroupe();
		requeteGroupe.setCreePar(operateur1);
		requeteGroupe = requeteGroupeRepository.save(requeteGroupe);

		requerant1 = requerantRepository.save(buildRequerant().nom("requerant1").creePar(operateur1).nationalite(pays));
		requerant2 = requerantRepository.save(buildRequerant().nom("requerant2").creePar(operateur2).nationalite(pays));

		// create requetes
		requete1 = buildRequete();
		requete1.setRequeteGroupe(requeteGroupe);
		requete1.setRequerant(requerant1);
		requete1.setType(typeRequete);
		requete1.setStatus(statusRequete);
		requete1.setOperateur(operateur1);
		requete1 = requeteRepository.save(requete1);

		Thread.sleep(100);

		requete2 = buildRequete();
		requete2.setRequeteGroupe(requeteGroupe);
		requete2.setRequerant(requerant1);
		requete2.setType(typeRequete);
		requete2.setStatus(statusRequete);
		requete2.setOperateur(operateur1);
		requete2 = requeteRepository.save(requete2);

		Thread.sleep(100);

		requete3 = buildRequete();
		requete3.setRequeteGroupe(requeteGroupe);
		requete3.setRequerant(requerant1);
		requete3.setType(typeRequete);
		requete3.setStatus(statusRequete);
		requete3.setOperateur(operateur2);
		requete3 = requeteRepository.save(requete3);

		Thread.sleep(100);

		requete4 = buildRequete();
		requete4.setRequeteGroupe(requeteGroupe);
		requete4.setRequerant(requerant2);
		requete4.setType(typeRequete);
		requete4.setStatus(statusRequete);
		requete4.setOperateur(operateur2);
		requete4 = requeteRepository.save(requete4);

		simulateNominalBehaviors();

	}

	@Override
	@After
	public void cleanup() throws Exception {

		requeteRepository.deleteAll();
		requeteGroupeRepository.deleteAll();
		requerantRepository.deleteAll();
		statusRequeteRepository.deleteAll();
		typeRequeteRepository.deleteAll();
		operateurRepository.deleteAll();
		paysRepository.deleteAll();

		super.cleanup();
	}

	@Test
	public void get() {
		given().get(ApiConstants.REQUERANT_REQUETE_ITEM, requerant1.getId(), requete1.getId()).then().log().body()
				.statusCode(200).body("id", is(equalTo(requete1.getId().intValue())))
				.body("type.id", is(equalTo(typeRequete.getId().intValue())))
				.body("status.id", is(equalTo(statusRequete.getId().intValue())))
				.body("date", is(equalTo(requete1.getCreeLe().getTime())))
				.body("requerant.id", is(equalTo(requerant1.getId().intValue())))
				.body("operateur.id", is(equalTo(operateur1.getId().intValue())));
	}

	@Test
	public void getNotFound() {
		given().get(ApiConstants.REQUERANT_REQUETE_ITEM, requerant1.getId(), random.nextLong()).then().statusCode(404);
	}

	@Test
	public void listByRequerantFirstPageDefaultLimit() {
		given().queryParam("page", 0).get(ApiConstants.REQUERANT_REQUETE_COLLECTION, requerant1.getId()).then().log()
				.body().statusCode(200).body("size()", is(equalTo(1)))
				.body("id", containsInAnyOrder(requete1.getId().intValue()))
				.body("find{it.id==" + requete1.getId().intValue() + "}.type.id",
						is(equalTo(typeRequete.getId().intValue())))
				.body("find{it.id==" + requete1.getId().intValue() + "}.status.id",
						is(equalTo(statusRequete.getId().intValue())))
				.body("find{it.id==" + requete1.getId().intValue() + "}.date",
						is(equalTo(requete1.getCreeLe().getTime())))
				.body("find{it.id==" + requete1.getId().intValue() + "}.requerant.id",
						is(equalTo(requerant1.getId().intValue())))
				.body("find{it.id==" + requete1.getId().intValue() + "}.operateur.id",
						is(equalTo(operateur1.getId().intValue())));
		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void listByRequerantLastPageDefaultLimit() {
		given().queryParam("page", 2).get(ApiConstants.REQUERANT_REQUETE_COLLECTION, requerant1.getId()).then().log()
				.body().statusCode(200).body("size()", is(equalTo(1)))
				.body("id", containsInAnyOrder(requete3.getId().intValue()));
		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	@Test
	public void listByRequerant() {
		given().queryParam("limit", 3).get(ApiConstants.REQUERANT_REQUETE_COLLECTION, requerant1.getId()).then().log()
				.body().statusCode(200).body("size()", is(equalTo(3)))
				.body("id",
						containsInAnyOrder(requete1.getId().intValue(), requete2.getId().intValue(),
								requete3.getId().intValue()))
				.body("find{it.id==" + requete1.getId().intValue() + "}.type.id",
						is(equalTo(typeRequete.getId().intValue())))
				.body("find{it.id==" + requete1.getId().intValue() + "}.status.id",
						is(equalTo(statusRequete.getId().intValue())))
				.body("find{it.id==" + requete1.getId().intValue() + "}.date",
						is(equalTo(requete1.getCreeLe().getTime())))
				.body("find{it.id==" + requete1.getId().intValue() + "}.requerant.id",
						is(equalTo(requerant1.getId().intValue())))
				.body("find{it.id==" + requete1.getId().intValue() + "}.operateur.id",
						is(equalTo(operateur1.getId().intValue())));
		verify(appSettings, times(1)).getSearchDefaultPageSize();
	}

	private void simulateNominalBehaviors() {

		// Simulate the page size to simplify the tests
		when(appSettings.getSearchDefaultPageSize()).thenReturn(1);
		requeteService.setAppSettings(appSettings);
	}

}
