package com.ftninformatika.bisisoauth.web;

import com.ftninformatika.bisisauthentication.models.BisisUserDetailsImpl;
import com.ftninformatika.bisisauthentication.security.BisisUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class MainController {

	@Autowired
	@Qualifier("oauth2Clients")
	Map<String, Map<String, String>> oauth2Clients;

	@Autowired
	JwtDecoder jwtDecoder;

	@Autowired
	BisisUserDetailsService userDetailsService;

	@RequestMapping("/login")
	public String index(Model model, HttpServletRequest request) {
		String appName = getClientName((String)request.getSession().getAttribute("client_id"));
		model.addAttribute("applicationName", appName);
		return "login";
	}

	private String getClientName(String clientId) {
		String clientName = "NoAppName";
		for(Map.Entry<String, Map<String, String>> entry : oauth2Clients.entrySet()) {
			Map<String, String> value = entry.getValue();
			if (value.get("client-id").equals(clientId)) {
				clientName = value.get("client-name");
			}
		}
		return clientName;
	}

	@GetMapping("/oauth2/userinfo")
	public ResponseEntity<? extends Object> userinfo(@RequestHeader(name="Authorization") String token) {
		try {
			Jwt jwt = jwtDecoder.decode(token.substring(7));
			String username = jwt.getClaims().get("sub").toString();
			BisisUserDetailsImpl userDetails = (BisisUserDetailsImpl)userDetailsService.loadUserByUsername(username);
			UserInfo userInfo = new UserInfo();
			userInfo.setSub(userDetails.getUsername());
			userInfo.setName(userDetails.getName());
			userInfo.setGiven_name(userDetails.getFirstName());
			userInfo.setFamily_name(userDetails.getSurname());
			userInfo.setAge(userDetails.getAge());
			return ResponseEntity.ok(userInfo);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad OAuth2 request at UserInfo Endpoint");
		}

	}
}
