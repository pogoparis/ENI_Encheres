package fr.eni.encheres.dao;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

public interface EncheresDaoEncheres {

	void saveEnchere(Enchere enchere);
	 List<Enchere> findEncheresByArticle(ArticleVendu article);
	List<Enchere> getEncheresByUser(Utilisateur utilisateur);
	List<Enchere> getEncheresByUserByCategorie(Utilisateur utilisateur, Categorie categorie, String rechercheNom);
	List<Enchere> getEncheresByUserAndSearch(Utilisateur utilisateur, String rechercheNom);
}
