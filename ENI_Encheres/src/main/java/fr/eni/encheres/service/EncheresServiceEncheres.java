package fr.eni.encheres.service;

import java.security.Principal;
import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;

public interface EncheresServiceEncheres {
	
	public Boolean affichageDuBouton(ArticleVendu article, Principal user);

	public void creationEncheres(Enchere enchere);
	
	public List<Enchere> getEncheresByArticle (ArticleVendu articleVendu);
	
	public Enchere getMeilleureEnchereByArticle (ArticleVendu article);
	
}
