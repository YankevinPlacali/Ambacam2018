package com.spectech.ambacambackend.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import com.spectech.ambacambackend.AmbacamBackendApplicationTests;
import com.spectech.ambacambackend.model.EntityTest;
import com.spectech.ambacambackend.repository.EntityTestRepository;

public class AclSecurityTest extends AmbacamBackendApplicationTests {

	@Autowired
	EntityTestRepository repository;

	EntityTest entityTest1;
	EntityTest entityTest2;
	EntityTest entityTest3;
	EntityTest entityTest4;

	@Before
	public void setup() {

	}

	@After
	public void cleanup() {

	}

	@Test
	@WithMockUser(username = "manager")
	public void givenUserManager_whenFindAllMessage_thenReturnFirstMessage() {
		List<EntityTest> details = repository.findAll();
		assertNotNull(details);
		assertEquals(1, details.size());
		assertEquals(FIRST_MESSAGE_ID, details.get(0).getId());
	}

	@Test
	@WithMockUser(username = "manager")
	public void givenUserManager_whenFind1stMessageByIdAndUpdateItsContent_thenOK() {
		EntityTest firstMessage = repository.findById(FIRST_MESSAGE_ID);
		assertNotNull(firstMessage);
		assertEquals(FIRST_MESSAGE_ID, firstMessage.getId());

		firstMessage.setContent(EDITTED_CONTENT);
		repository.save(firstMessage);

		EntityTest editedFirstMessage = repository.findById(FIRST_MESSAGE_ID);
		assertNotNull(editedFirstMessage);
		assertEquals(FIRST_MESSAGE_ID, editedFirstMessage.getId());
		assertEquals(EDITTED_CONTENT, editedFirstMessage.getContent());
	}

	@Test
	@WithMockUser(username = "hr")
	public void givenUsernameHr_whenFindMessageById2_thenOK() {
		EntityTest secondMessage = repository.findById(SECOND_MESSAGE_ID);
		assertNotNull(secondMessage);
		assertEquals(SECOND_MESSAGE_ID, secondMessage.getId());
	}

	@Test(expected = AccessDeniedException.class)
	@WithMockUser(username = "hr")
	public void givenUsernameHr_whenUpdateMessageWithId2_thenFail() {
		EntityTest secondMessage = new EntityTest();
		secondMessage.setId(SECOND_MESSAGE_ID);
		secondMessage.setContent(EDITTED_CONTENT);
		repository.save(secondMessage);
	}

	@Test
	@WithMockUser(roles = { "EDITOR" })
	public void givenRoleEditor_whenFindAllMessage_thenReturn3Message() {
		List<EntityTest> details = repository.findAll();
		assertNotNull(details);
		assertEquals(3, details.size());
	}

	@Test
	@WithMockUser(roles = { "EDITOR" })
	public void givenRoleEditor_whenUpdateThirdMessage_thenOK() {
		EntityTest thirdMessage = new EntityTest();
		thirdMessage.setId(THIRD_MESSAGE_ID);
		thirdMessage.setContent(EDITTED_CONTENT);
		repository.save(thirdMessage);
	}

	@Test(expected = AccessDeniedException.class)
	@WithMockUser(roles = { "EDITOR" })
	public void givenRoleEditor_whenFind1stMessageByIdAndUpdateContent_thenFail() {
		EntityTest firstMessage = repository.findById(FIRST_MESSAGE_ID);
		assertNotNull(firstMessage);
		assertEquals(FIRST_MESSAGE_ID, firstMessage.getId());
		firstMessage.setContent(EDITTED_CONTENT);
		repository.save(firstMessage);
	}
}
