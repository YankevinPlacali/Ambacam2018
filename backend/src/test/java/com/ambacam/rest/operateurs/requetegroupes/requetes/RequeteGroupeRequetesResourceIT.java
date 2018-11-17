package com.ambacam.rest.operateurs.requetegroupes.requetes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ambacam.ItBase;
import com.ambacam.model.Operateur;
import com.ambacam.model.Pays;
import com.ambacam.model.Requerant;
import com.ambacam.model.Requete;
import com.ambacam.model.RequeteGroupe;
import com.ambacam.model.StatusRequete;
import com.ambacam.model.TypeRequete;
import com.ambacam.repository.OperateurRepository;
import com.ambacam.repository.PaysRepository;
import com.ambacam.repository.RequerantRepository;
import com.ambacam.repository.RequeteGroupeRepository;
import com.ambacam.repository.RequeteRepository;
import com.ambacam.repository.StatusRequeteRepository;
import com.ambacam.repository.TypeRequeteRepository;
import com.ambacam.rest.ApiConstants;
import com.ambacam.transfert.requetes.AssignStatusTO;

import io.restassured.http.ContentType;

public class RequeteGroupeRequetesResourceIT extends ItBase {

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

	private TypeRequete typeRequete1;
	private TypeRequete typeRequete2;
	private TypeRequete typeRequete3;

	private StatusRequete statusRequete;
	private StatusRequete updateStatus;

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

		typeRequete1 = typeRequeteRepository.save(buildTypeRequete().nom("type1"));
		typeRequete2 = typeRequeteRepository.save(buildTypeRequete().nom("type2"));
		typeRequete3 = typeRequeteRepository.save(buildTypeRequete().nom("type3"));

		statusRequete = statusRequeteRepository.save(buildStatusRequete().nom("status1"));
		updateStatus = statusRequeteRepository.save(buildStatusRequete().nom("updateStatus"));

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
		requete1.setType(typeRequete1);
		requete1.setStatus(statusRequete);
		requete1.setOperateur(operateur1);
		requete1 = requeteRepository.save(requete1);

		requete2 = buildRequete();
		requete2.setRequeteGroupe(requeteGroupe);
		requete2.setRequerant(requerant1);
		requete2.setType(typeRequete2);
		requete2.setStatus(statusRequete);
		requete2.setOperateur(operateur1);
		requete2 = requeteRepository.save(requete2);

		requete3 = buildRequete();
		requete3.setRequeteGroupe(requeteGroupe);
		requete3.setRequerant(requerant1);
		requete3.setType(typeRequete3);
		requete3.setStatus(statusRequete);
		requete3.setOperateur(operateur2);
		requete3 = requeteRepository.save(requete3);

		requete4 = buildRequete();
		requete4.setRequerant(requerant2);
		requete4.setType(typeRequete1);
		requete4.setStatus(statusRequete);
		requete4.setOperateur(operateur2);
		requete4 = requeteRepository.save(requete4);

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
	public void updateStatus() {

		AssignStatusTO assignStatusTO = new AssignStatusTO();
		assignStatusTO.setStatusId(updateStatus.getId());
		assignStatusTO.getRequeteIds().add(requete1.getId());
		assignStatusTO.getRequeteIds().add(requete2.getId());

		preLoadedGiven.contentType(ContentType.JSON).body(assignStatusTO)
				.put(ApiConstants.REQUETE_GROUPE_REQUETE_STATUS, operateur1.getId(), requeteGroupe.getId()).then().log()
				.body().statusCode(200);

		// check the status of requete1
		requete1 = requeteRepository.findOne(requete1.getId());
		assertThat(requete1.getStatus().getId(), is(equalTo(updateStatus.getId())));
		assertThat(requete1.getStatus().getNom(), is(equalTo("updateStatus")));
		// check the status of requete2
		requete2 = requeteRepository.findOne(requete2.getId());
		assertThat(requete2.getStatus().getId(), is(equalTo(updateStatus.getId())));
		assertThat(requete2.getStatus().getNom(), is(equalTo("updateStatus")));
		// check the status of requete3
		requete3 = requeteRepository.findOne(requete3.getId());
		assertThat(requete3.getStatus().getId(), is(equalTo(statusRequete.getId())));
		assertThat(requete3.getStatus().getNom(), is(equalTo("status1")));
	}

	@Test
	public void updateStatusNotFound() {

		AssignStatusTO assignStatusTO = new AssignStatusTO();
		assignStatusTO.setStatusId(updateStatus.getId());
		assignStatusTO.getRequeteIds().add(requete1.getId());
		assignStatusTO.getRequeteIds().add(requete2.getId());

		preLoadedGiven.contentType(ContentType.JSON).body(assignStatusTO)
				.put(ApiConstants.REQUETE_GROUPE_REQUETE_STATUS, operateur1.getId(), random.nextLong()).then().log()
				.body().statusCode(404);
	}

	@Test
	public void updateStatusStatusNotExist() {

		AssignStatusTO assignStatusTO = new AssignStatusTO();
		assignStatusTO.setStatusId(random.nextLong());
		assignStatusTO.getRequeteIds().add(requete1.getId());
		assignStatusTO.getRequeteIds().add(requete2.getId());

		preLoadedGiven.contentType(ContentType.JSON).body(assignStatusTO)
				.put(ApiConstants.REQUETE_GROUPE_REQUETE_STATUS, operateur1.getId(), requeteGroupe.getId()).then().log()
				.body().statusCode(400);
	}

	@Test
	public void updateStatusStatusNull() {

		AssignStatusTO assignStatusTO = new AssignStatusTO();
		assignStatusTO.setStatusId(null);
		assignStatusTO.getRequeteIds().add(requete1.getId());
		assignStatusTO.getRequeteIds().add(requete2.getId());

		preLoadedGiven.contentType(ContentType.JSON).body(assignStatusTO)
				.put(ApiConstants.REQUETE_GROUPE_REQUETE_STATUS, operateur1.getId(), requeteGroupe.getId()).then().log()
				.body().statusCode(400);
	}

	@Test
	public void updateStatusStatusAlreadySet() {

		AssignStatusTO assignStatusTO = new AssignStatusTO();
		assignStatusTO.setStatusId(statusRequete.getId());
		assignStatusTO.getRequeteIds().add(requete1.getId());
		assignStatusTO.getRequeteIds().add(requete2.getId());

		preLoadedGiven.contentType(ContentType.JSON).body(assignStatusTO)
				.put(ApiConstants.REQUETE_GROUPE_REQUETE_STATUS, operateur1.getId(), requeteGroupe.getId()).then().log()
				.body().statusCode(400);
	}

	@Test
	public void updateStatusRequeteNotExist() {

		AssignStatusTO assignStatusTO = new AssignStatusTO();
		assignStatusTO.setStatusId(updateStatus.getId());
		assignStatusTO.getRequeteIds().add(random.nextLong());
		assignStatusTO.getRequeteIds().add(requete2.getId());

		preLoadedGiven.contentType(ContentType.JSON).body(assignStatusTO)
				.put(ApiConstants.REQUETE_GROUPE_REQUETE_STATUS, operateur1.getId(), requeteGroupe.getId()).then().log()
				.body().statusCode(400);
	}

	@Test
	public void updateStatusRequeteNotAppear() {

		AssignStatusTO assignStatusTO = new AssignStatusTO();
		assignStatusTO.setStatusId(updateStatus.getId());
		assignStatusTO.getRequeteIds().add(requete1.getId());
		assignStatusTO.getRequeteIds().add(requete4.getId());

		preLoadedGiven.contentType(ContentType.JSON).body(assignStatusTO)
				.put(ApiConstants.REQUETE_GROUPE_REQUETE_STATUS, operateur1.getId(), requeteGroupe.getId()).then().log()
				.body().statusCode(400);
	}

	@Test
	public void updateStatusListRequetesNull() {

		AssignStatusTO assignStatusTO = new AssignStatusTO();
		assignStatusTO.setStatusId(updateStatus.getId());
		assignStatusTO.setRequeteIds(null);

		preLoadedGiven.contentType(ContentType.JSON).body(assignStatusTO)
				.put(ApiConstants.REQUETE_GROUPE_REQUETE_STATUS, operateur1.getId(), requeteGroupe.getId()).then().log()
				.body().statusCode(400);
	}

	@Test
	public void updateStatusListRequetesEmpty() {

		AssignStatusTO assignStatusTO = new AssignStatusTO();
		assignStatusTO.setStatusId(updateStatus.getId());
		assignStatusTO.setRequeteIds(new HashSet<>());

		preLoadedGiven.contentType(ContentType.JSON).body(assignStatusTO)
				.put(ApiConstants.REQUETE_GROUPE_REQUETE_STATUS, operateur1.getId(), requeteGroupe.getId()).then().log()
				.body().statusCode(400);
	}
}