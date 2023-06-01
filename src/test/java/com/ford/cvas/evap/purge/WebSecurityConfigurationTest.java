//package com.ford.cvas.evap.purge;
//
//import com.ford.cloudnative.base.app.web.exception.handler.ExceptionHandlerConfiguration;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.web.reactive.server.WebTestClient;
//
//import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
//
//@SpringBootTest(webEnvironment = RANDOM_PORT)
//@Import(ExceptionHandlerConfiguration.class)
//@AutoConfigureWebTestClient(timeout = "30000")
//public class WebSecurityConfigurationTest {
//
//	@Autowired
//	private WebTestClient webClient;
//
//	@Test
//	public void should_allowSwaggerEndpoints_withoutAuthentication() {
//		webClient.get().uri("/swagger-ui/").exchange().expectStatus().is2xxSuccessful();
//		webClient.get().uri("/v2/api-docs").exchange().expectStatus().is2xxSuccessful();
//	}
//
//	@Test
//	public void should_notAllowOtherEndpoints_withoutAuthentication() {
//		webClient.get().uri("/other-does-not-exist").exchange().expectStatus().is4xxClientError();
//	}
//}
