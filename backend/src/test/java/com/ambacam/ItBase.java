package com.ambacam;

import java.util.Random;

import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.ambacam.model.Role;
import com.ambacam.model.StatusRequete;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ItBase {
	protected Random random = new Random();

	@LocalServerPort
	protected int port;

	public void setup() throws Exception {
		RestAssured.port = port;

	}

	public void cleanup() throws Exception {

	}

	protected Role buildRole() {

		Role role = new Role().nom("nom-" + random.nextLong()).description("description-" + random.nextLong());
		return role;
	}

	protected StatusRequete buildStatusRequete() {

		StatusRequete statusRequete = new StatusRequete().nom("nom-" + random.nextLong())
				.description("description-" + random.nextLong());
		return statusRequete;
	}
}
