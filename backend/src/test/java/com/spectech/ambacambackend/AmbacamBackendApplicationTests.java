package com.spectech.ambacambackend;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

@ContextConfiguration
@TestExecutionListeners(listeners = { ServletTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, WithSecurityContextTestExecutionListener.class })
public class AmbacamBackendApplicationTests extends AbstractJUnit4SpringContextTests {

	protected static Integer FIRST_MESSAGE_ID = 1;
	protected static Integer SECOND_MESSAGE_ID = 2;
	protected static Integer THIRD_MESSAGE_ID = 3;
	protected static String EDITTED_CONTENT = "EDITED";

	@Configuration
	@ComponentScan("com.spectech.ambacambackend.*")
	public static class SpringConfig {

	}
}
