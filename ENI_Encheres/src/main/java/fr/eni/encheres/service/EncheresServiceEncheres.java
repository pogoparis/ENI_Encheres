package fr.eni.encheres.service;

import java.security.Principal;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

public interface EncheresServiceEncheres {
	
	public Boolean affichageDuBouton(ArticleVendu article, Principal user);

	public void creationEncheres(Enchere enchere);

	

}
