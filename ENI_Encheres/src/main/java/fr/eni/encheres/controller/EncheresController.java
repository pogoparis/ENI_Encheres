package fr.eni.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class EncheresController {

	
	
	@GetMapping({"/","encheres"})
	public String afficherAccueil() {
		
		
		
		return "index";
		
	}
	
}
