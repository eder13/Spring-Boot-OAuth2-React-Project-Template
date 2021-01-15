package com.springreact.template.controller;

import com.springreact.template.db.User;
import com.springreact.template.db.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;

@Controller
public class HomeController {

	private final UserRepository userRepository;

	public HomeController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@ResponseBody
	@GetMapping("/user")
	public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
		return Collections.singletonMap("email", principal.getAttribute("email"));
	}

	@GetMapping("/userid")
	public ResponseEntity<String> userId(@RequestParam("email") String email, @AuthenticationPrincipal OAuth2User principal) {

		// security check: users can only query their own id
		Map<String, Object> map = Collections.singletonMap("email", principal.getAttribute("email"));
		String currentUserMail = map.get("email").toString();

		if (!currentUserMail.equals(email)) {

			HttpHeaders respHeader = new HttpHeaders();
			respHeader.set("Content-Type", "application/json");

			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.headers(respHeader)
					.body("{" +
							"\"error\": " + "\"You do not have the privileges to search ids from other Users!\"" +
							"}");

		}

		User user = userRepository.findUserByEmail(email);
		HttpHeaders respHeader = new HttpHeaders();
		respHeader.set("Content-Type", "application/json");

		if (user != null) {
			return ResponseEntity.ok()
					.headers(respHeader)
					.body("{" +
							"\"id\": " + user.getId() +
							"}"
					);
		} else {
			return ResponseEntity.notFound().build();
		}
	}



	/// DEBUG: Getting accessToken from currently logged in user
	//  @ResponseBody
	//  @GetMapping("/access-token")
	//  public String accessToken(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
	//      return "{ " + "\"accessToken\":"  + " \"" + authorizedClient.getAccessToken().getTokenValue() + "\" " + "}";
	//  }

}