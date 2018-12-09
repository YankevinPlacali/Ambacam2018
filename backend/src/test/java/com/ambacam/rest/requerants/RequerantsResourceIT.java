package com.ambacam.rest.requerants;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ambacam.ItBase;
import com.ambacam.model.Operateur;
import com.ambacam.model.Pays;
import com.ambacam.model.Requerant;
import com.ambacam.model.Role;
import com.ambacam.repository.OperateurRepository;
import com.ambacam.repository.PaysRepository;
import com.ambacam.repository.RequerantRepository;
import com.ambacam.repository.RoleRepository;
import com.ambacam.rest.ApiConstants;
import com.ambacam.transfert.requerants.RequerantCreateTO;
import com.ambacam.transfert.requerants.RequerantUpdateTO;

import io.restassured.http.ContentType;

public class RequerantsResourceIT extends ItBase {

	@Autowired
	private RequerantRepository repository;

	@Autowired
	private OperateurRepository operateurRepository;

	@Autowired
	private PaysRepository paysRepository;

	@Autowired
	private RoleRepository roleRepository;

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
		requerant1 = repository.save(buildRequerant().creePar(creator1).nationalite(pays1));
		requerant2 = repository.save(buildRequerant().creePar(creator1).nationalite(pays1));
		requerant3 = repository.save(buildRequerant().creePar(creator2).nationalite(pays2));

	}

	@Override
	@After
	public void cleanup() throws Exception {
		repository.deleteAll();
		operateurRepository.deleteAll();
		paysRepository.deleteAll();
		roleRepository.deleteAll();
		super.cleanup();
	}

	@Test
	public void create() {

		RequerantCreateTO create = buildRequerantCreateTO(creator1.getId(), pays2.getId());

		DateTime before = new DateTime();

		int id = preLoadedGiven.contentType(ContentType.JSON).body(create).log().body()
				.post(ApiConstants.REQUERANT_COLLECTION).then().log().body().statusCode(200).extract().body()
				.path("id");

		DateTime after = new DateTime();

		// check that the requerant has been saved
		Requerant actual = repository.findOne(Integer.toUnsignedLong(id));
		assertThat(actual, is(notNullValue()));
		assertThat(actual.getNom(), is(equalTo(create.getNom())));
		assertThat(actual.getPrenom(), is(equalTo(create.getPrenom())));
		assertThat(actual.getDateNaissance().getTime(), is(equalTo(create.getDateNaissance().getTime())));
		assertThat(actual.getCreePar().getId(), is(equalTo(defaultOperateur.getId())));

		// check requerant roles
		// find roles
		List<Role> roles = roleRepository.findAllByRequerantsIn(actual);
		assertThat(roles.size(), is(equalTo(0)));

		assertThat(before.isBefore(actual.getCreeLe().getTime()), is(equalTo(true)));
		assertThat(after.isAfter(actual.getCreeLe().getTime()), is(equalTo(true)));
		assertThat(actual.getSexe(), is(equalTo(create.getSexe())));
		assertThat(actual.getProfession(), is(equalTo(create.getProfession())));
		assertThat(actual.getLieuNaissance(), is(equalTo(create.getLieuNaissance())));
		assertThat(actual.getNationalite().getId(), is(equalTo(pays2.getId())));

	}

	@Test
	public void createNationaliteNull() {

		RequerantCreateTO create = buildRequerantCreateTO(creator1.getId(), null);

		preLoadedGiven.contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.REQUERANT_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void createNationaliteNotFound() {

		RequerantCreateTO create = buildRequerantCreateTO(creator1.getId(), random.nextLong());

		preLoadedGiven.contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.REQUERANT_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void createNomNull() {

		RequerantCreateTO create = buildRequerantCreateTO(creator1.getId(), pays2.getId()).nom(null);

		preLoadedGiven.contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.REQUERANT_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void createNomVide() {

		RequerantCreateTO create = buildRequerantCreateTO(creator1.getId(), pays2.getId()).nom("");

		preLoadedGiven.contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.REQUERANT_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void createDateNaissanceNull() {

		RequerantCreateTO create = buildRequerantCreateTO(creator1.getId(), pays2.getId()).dateNaissance(null);

		preLoadedGiven.contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.REQUERANT_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void createSexeNull() {

		RequerantCreateTO create = buildRequerantCreateTO(creator1.getId(), pays2.getId()).sexe(null);

		preLoadedGiven.contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.REQUERANT_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void createSexeVide() {

		RequerantCreateTO create = buildRequerantCreateTO(creator1.getId(), pays2.getId()).sexe("");

		preLoadedGiven.contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.REQUERANT_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void createLieuNaissanceNull() {

		RequerantCreateTO create = buildRequerantCreateTO(creator1.getId(), pays2.getId()).lieuNaissance(null);

		preLoadedGiven.contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.REQUERANT_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void createLieuNaissanceVide() {

		RequerantCreateTO create = buildRequerantCreateTO(creator1.getId(), pays2.getId()).lieuNaissance("");

		preLoadedGiven.contentType(ContentType.JSON).body(create).log().body().post(ApiConstants.REQUERANT_COLLECTION)
				.then().log().body().statusCode(400);
	}

	@Test
	public void list() {
		preLoadedGiven.get(ApiConstants.REQUERANT_COLLECTION).then().log().body().statusCode(200)
				.body("size()", is(equalTo(3)))
				.body("id",
						containsInAnyOrder(requerant1.getId().intValue(), requerant2.getId().intValue(),
								requerant3.getId().intValue()))
				.body("find{it.id==" + requerant1.getId().intValue() + "}.nom", is(equalTo(requerant1.getNom())))
				.body("find{it.id==" + requerant1.getId().intValue() + "}.prenom", is(equalTo(requerant1.getPrenom())))
				.body("find{it.id==" + requerant1.getId().intValue() + "}.dateNaissance",
						is(equalTo(requerant1.getDateNaissance().getTime())))
				.body("find{it.id==" + requerant1.getId().intValue() + "}.creeLe",
						is(equalTo(requerant1.getCreeLe().getTime())))
				.body("find{it.id==" + requerant1.getId().intValue() + "}.sexe", is(equalTo(requerant1.getSexe())))
				.body("find{it.id==" + requerant1.getId().intValue() + "}.profession",
						is(equalTo(requerant1.getProfession())))
				.body("find{it.id==" + requerant1.getId().intValue() + "}.lieuNaissance",
						is(equalTo(requerant1.getLieuNaissance())))
				.body("find{it.id==" + requerant1.getId().intValue() + "}.nationalite",
						is(equalTo(requerant1.getNationalite().getNom())));
	}

	@Test
	public void get() {
		preLoadedGiven.get(ApiConstants.REQUERANT_ITEM, requerant1.getId()).then().log().body().statusCode(200)
				.body("id", is(equalTo(requerant1.getId().intValue()))).body("nom", is(equalTo(requerant1.getNom())))
				.body("prenom", is(equalTo(requerant1.getPrenom())))
				.body("dateNaissance", is(equalTo(requerant1.getDateNaissance().getTime())))
				.body("creeLe", is(equalTo(requerant1.getCreeLe().getTime())))
				.body("sexe", is(equalTo(requerant1.getSexe())))
				.body("profession", is(equalTo(requerant1.getProfession())))
				.body("lieuNaissance", is(equalTo(requerant1.getLieuNaissance())))
				.body("nationalite", is(equalTo(requerant1.getNationalite().getNom())));
	}

	@Test
	public void getNotFound() {
		preLoadedGiven.get(ApiConstants.REQUERANT_ITEM, random.nextLong()).then().statusCode(404);
	}

	@Test
	public void delete() {

		preLoadedGiven.delete(ApiConstants.REQUERANT_ITEM, requerant1.getId()).then().statusCode(200);

		// check that the requerant has been deleted
		Requerant actual = repository.findOne(requerant1.getId());
		assertThat(actual, is(nullValue()));

	}

	@Test
	public void deleteNotFound() {
		preLoadedGiven.delete(ApiConstants.REQUERANT_ITEM, random.nextLong()).then().statusCode(404);
	}

	@Test
	public void update() {

		RequerantUpdateTO update = buildRequerantUpdateTO(pays2.getId());

		preLoadedGiven.contentType(ContentType.JSON).body(update).put(ApiConstants.REQUERANT_ITEM, requerant1.getId())
				.then().log().body().statusCode(200);

		// check that the requerant has been saved
		Requerant actual = repository.findOne(requerant1.getId());
		assertThat(actual, is(notNullValue()));
		// check updated properties
		assertThat(actual.getNom(), is(equalTo(update.getNom())));
		assertThat(actual.getPrenom(), is(equalTo(update.getPrenom())));
		assertThat(actual.getSexe(), is(equalTo(update.getSexe())));
		assertThat(actual.getDateNaissance().getTime(), is(equalTo(update.getDateNaissance().getTime())));
		assertThat(actual.getProfession(), is(equalTo(update.getProfession())));
		assertThat(actual.getLieuNaissance(), is(equalTo(update.getLieuNaissance())));
		assertThat(actual.getNationalite().getId(), is(equalTo(update.getPaysId())));

		// check not updated properties

		assertThat(actual.getCreePar().getId(), is(equalTo(requerant1.getCreePar().getId())));
		assertThat(actual.getCreeLe().getTime(), is(equalTo(requerant1.getCreeLe().getTime())));
		// check requerant roles
		// find roles
		List<Role> roles = roleRepository.findAllByRequerantsIn(actual);
		assertThat(roles.size(), is(equalTo(0)));

	}

	@Test
	public void updateNomNull() {

		RequerantUpdateTO update = buildRequerantUpdateTO(pays2.getId()).nom(null);

		preLoadedGiven.contentType(ContentType.JSON).body(update).put(ApiConstants.REQUERANT_ITEM, requerant1.getId())
				.then().log().body().statusCode(400);
	}

	@Test
	public void updateNomVide() {

		RequerantUpdateTO update = buildRequerantUpdateTO(pays2.getId()).nom("");

		preLoadedGiven.contentType(ContentType.JSON).body(update).put(ApiConstants.REQUERANT_ITEM, requerant1.getId())
				.then().log().body().statusCode(400);
	}

	@Test
	public void updateDateNaissanceNull() {

		RequerantUpdateTO update = buildRequerantUpdateTO(pays2.getId()).dateNaissance(null);

		preLoadedGiven.contentType(ContentType.JSON).body(update).put(ApiConstants.REQUERANT_ITEM, requerant1.getId())
				.then().log().body().statusCode(400);
	}

	@Test
	public void updateSexeNull() {

		RequerantUpdateTO update = buildRequerantUpdateTO(pays2.getId()).sexe(null);

		preLoadedGiven.contentType(ContentType.JSON).body(update).put(ApiConstants.REQUERANT_ITEM, requerant1.getId())
				.then().log().body().statusCode(400);
	}

	@Test
	public void updateSexeVide() {

		RequerantUpdateTO update = buildRequerantUpdateTO(pays2.getId()).sexe("");

		preLoadedGiven.contentType(ContentType.JSON).body(update).put(ApiConstants.REQUERANT_ITEM, requerant1.getId())
				.then().log().body().statusCode(400);
	}

	@Test
	public void updateLieuNaissanceNull() {

		RequerantUpdateTO update = buildRequerantUpdateTO(pays2.getId()).lieuNaissance(null);

		preLoadedGiven.contentType(ContentType.JSON).body(update).put(ApiConstants.REQUERANT_ITEM, requerant1.getId())
				.then().log().body().statusCode(400);
	}

	@Test
	public void updateLieuNaissanceVide() {

		RequerantUpdateTO update = buildRequerantUpdateTO(pays2.getId()).lieuNaissance("");

		preLoadedGiven.contentType(ContentType.JSON).body(update).put(ApiConstants.REQUERANT_ITEM, requerant1.getId())
				.then().log().body().statusCode(400);
	}

	private RequerantCreateTO buildRequerantCreateTO(Long creatorId, Long paysId) {
		return new RequerantCreateTO().nom("nom-" + UUID.randomUUID()).prenom("prenom-" + UUID.randomUUID())
				.dateNaissance(new Date()).creatorId(creatorId).sexe("sexe-" + UUID.randomUUID())
				.profession("profession-" + UUID.randomUUID()).lieuNaissance("lieuNaissance-" + UUID.randomUUID())
				.paysId(paysId);
	}

	private RequerantUpdateTO buildRequerantUpdateTO(Long paysId) {
		return new RequerantUpdateTO().nom("nom-" + UUID.randomUUID()).prenom("prenom-" + UUID.randomUUID())
				.dateNaissance(new Date()).sexe("sexe-" + UUID.randomUUID())
				.profession("profession-" + UUID.randomUUID()).lieuNaissance("lieuNaissance-" + UUID.randomUUID())
				.paysId(paysId);
	}

}
