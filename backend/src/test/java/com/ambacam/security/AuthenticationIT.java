package com.ambacam.security;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ambacam.ItBase;
import com.ambacam.model.Operateur;
import com.ambacam.model.Pays;
import com.ambacam.model.Role;
import com.ambacam.repository.OperateurRepository;
import com.ambacam.repository.PaysRepository;
import com.ambacam.repository.RoleRepository;
import com.ambacam.rest.ApiConstants;

public class AuthenticationIT extends ItBase {

	private Operateur operateur;
	private Pays pays;
	private Role role;

	@Autowired
	private OperateurRepository repository;
	@Autowired
	private PaysRepository paysRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Before
	public void setup() throws Exception {

		// create and save role
		role = roleRepository.save(new Role().nom(UUID.randomUUID().toString()));

		// create and save pays
		pays = paysRepository.save(new Pays().nom(UUID.randomUUID().toString()));

		// create and save operateur
		operateur = repository.save(new Operateur().nom(UUID.randomUUID().toString()).nationalite(pays).withRoles(role)
				.sexe("f").username(UUID.randomUUID().toString()).password(UUID.randomUUID().toString()));

		super.setup();

	}

	@Override
	@After
	public void cleanup() throws Exception {
		repository.deleteAll();
		paysRepository.deleteAll();
		roleRepository.deleteAll();
		super.cleanup();
	}

	@Test
	public void authenticate() {
		// SUT
		given().auth().basic(appSettings.getAuthorizationUsername(), appSettings.getAuthorizationSecret()).when()
				.post(String.format(AUTH_COLLECTION_PATH, operateur.getUsername(), operateur.getPassword())).then()
				.log().body().body("access_token", is(notNullValue())).body("token_type", is("bearer"))
				.body("expires_in", greaterThanOrEqualTo(appSettings.getAccessTokenValidity() - 2))
				.body("scope", is("read write trust")).statusCode(200);
	}

	@Test
	public void authenticateOperateurNotExist() {
		// SUT
		given().auth().basic(appSettings.getAuthorizationUsername(), appSettings.getAuthorizationSecret()).when()
				.post(String.format(AUTH_COLLECTION_PATH, UUID.randomUUID().toString(), operateur.getPassword())).then()
				.log().body().statusCode(401);
	}

	@Test
	public void authenticatePasswordInvalid() {
		// SUT
		given().auth().basic(appSettings.getAuthorizationUsername(), appSettings.getAuthorizationSecret()).when()
				.post(String.format(AUTH_COLLECTION_PATH, operateur.getUsername(), UUID.randomUUID().toString())).then()
				.log().body().statusCode(400);
	}

	@Test
	public void revoke() {
		// create token
		String access_token = given().auth()
				.basic(appSettings.getAuthorizationUsername(), appSettings.getAuthorizationSecret()).when()
				.post(String.format(AUTH_COLLECTION_PATH, operateur.getUsername(), operateur.getPassword())).then()
				.log().body().statusCode(200).extract().body().path("access_token");

		// SUT
		given().header("Authorization", String.format("Bearer %s", access_token)).delete(ApiConstants.REVOKE_TOKEN)
				.then().statusCode(200);

		// check if the the token has been well deleted
		given().header("Authorization", String.format("Bearer %s", access_token)).get("/").then().statusCode(401);
	}

	@Test
	public void revokeTokenNotExist() {
		// SUT
		given().header("Authorization", String.format("Bearer %s", UUID.randomUUID().toString()))
				.delete(ApiConstants.REVOKE_TOKEN).then().statusCode(401);
	}

	@Test
	public void revokeTokenNull() {

		String access_token = null;
		// SUT
		given().header("Authorization", String.format("Bearer %s", access_token)).delete(ApiConstants.REVOKE_TOKEN)
				.then().statusCode(401);
	}
}
