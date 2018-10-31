package com.example.demo;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.AclClass;
import com.example.demo.model.AclEntry;
import com.example.demo.model.AclObjectIdentity;
import com.example.demo.model.AclSid;
import com.example.demo.model.Contact;
import com.example.demo.repository.AclClassRepository;
import com.example.demo.repository.AclEntryRepository;
import com.example.demo.repository.AclObjectIdentityRepository;
import com.example.demo.repository.AclSidRepository;
import com.example.demo.repository.ContactRepository;

@EnableAuthorizationServer
@EnableResourceServer
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RestController
public class AuthServiceApplication {

	@Autowired
	private ContactRepository contactRepository;

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(AuthServiceApplication.class, args);
		ContactRepository contactRepository = ctx.getBean(ContactRepository.class);
		AclClassRepository aclClassRepository = ctx.getBean(AclClassRepository.class);
		AclEntryRepository aclEntryRepository = ctx.getBean(AclEntryRepository.class);
		AclObjectIdentityRepository aclObjectIdentityRepository = ctx.getBean(AclObjectIdentityRepository.class);
		AclSidRepository aclSidRepository = ctx.getBean(AclSidRepository.class);

		Contact contact1 = contactRepository.save(new Contact("contact1"));
		Contact contact2 = contactRepository.save(new Contact("contact2"));

		AclSid sidAdminTrue = aclSidRepository.save(new AclSid(true, "admin"));
		AclSid sidAdminFalse = aclSidRepository.save(new AclSid(false, "ROLE_ADMIN"));
		AclSid sidHervalTrue = aclSidRepository.save(new AclSid(true, "herval"));
		AclSid sidHervalFalse = aclSidRepository.save(new AclSid(false, "ROLE_USER"));

		AclClass aclClassContact = aclClassRepository.save(new AclClass("com.example.demo.model.Contact"));

		AclObjectIdentity aoi = aclObjectIdentityRepository
				.save(new AclObjectIdentity(aclClassContact, contact1.getId(), null, sidAdminTrue, false));

		AclEntry entryAdminTrue = aclEntryRepository.save(new AclEntry(aoi, 1, sidAdminTrue, 1, true, true, true));

	}

	@RequestMapping("/user")
	public Principal getUser(Principal user) {
		return user;
	}

	@RequestMapping("/contacts")
	@PostFilter("hasPermission(filterObject.id, 'com.example.demo.model.Contact', 'READ')")
	public List<Contact> list() {
		return contactRepository.findAll();
	}
}
