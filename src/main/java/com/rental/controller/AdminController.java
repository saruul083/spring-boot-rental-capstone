package com.rental.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@GetMapping("/admin")
	public String adminDasahboard(Principal principal, Model model) {
		
		model.addAttribute("email", principal.getName());
		
		return "admin/dashboard";
	}
}
