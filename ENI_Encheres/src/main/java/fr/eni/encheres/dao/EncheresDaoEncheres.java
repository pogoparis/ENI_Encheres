package fr.eni.encheres.dao;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;

public interface EncheresDaoEncheres {

	void saveEnchere(Enchere enchere);
	 List<Enchere> findEncheresByArticle(ArticleVendu article);
		

}
