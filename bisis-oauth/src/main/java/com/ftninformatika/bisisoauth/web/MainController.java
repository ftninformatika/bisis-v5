package com.ftninformatika.bisisoauth.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class MainController {

	@Autowired
	@Qualifier("oauth2Clients")
	Map<String, Map<String, String>> oauth2Clients;

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
}
