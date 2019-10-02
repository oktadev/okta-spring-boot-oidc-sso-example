package com.okta.examples.springbootoidcsso;

import com.okta.commons.lang.Assert;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.ResourceException;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringBootOidcSsoApplication {

    @Value("#{ @environment['okta.admin.name.first'] }")
    private String firstName;

    @Value("#{ @environment['okta.admin.name.last'] }")
    private String lastName;

    @Value("#{ @environment['okta.admin.email'] }")
    private String email;

    @Value("#{ @environment['okta.admin.password'] }")
    private String password;

    private static final Logger log = LoggerFactory.getLogger(SpringBootOidcSsoApplication.class);

    @PostConstruct
    void init() {
        try {
            Assert.hasText(firstName, "first name is required");
            Assert.hasText(lastName, "last name is required");
            Assert.hasText(email, "email name is required");
            Assert.hasText(password, "password name is required");
            // should pickup api token from environment
            Client client = Clients.builder().build();

            // try to lookup user by email
            User user;
            try {
                user = client.getUser(email);
                log.info("user: {} found.", email);
            } catch (ResourceException e) {
                log.info("user: {} not found. Will create.", email);
                user = UserBuilder.instance()
                    .setEmail(email)
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setPassword(password.toCharArray())
                    .buildAndCreate(client);
            }
        } catch (Exception e) {
            log.error("Unable to startup with configured user: {}. Error: {}", email, e.getMessage(), e);
        }
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringBootOidcSsoApplication.class, args);
    }

}
