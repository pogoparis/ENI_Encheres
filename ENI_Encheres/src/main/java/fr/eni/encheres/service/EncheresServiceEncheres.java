package fr.eni.encheres.service;

import java.security.Principal;

import fr.eni.encheres.bo.ArticleVendu;

public interface EncheresServiceEncheres {
	
	public Boolean affichageDuBouton(ArticleVendu article, Principal user);

}
