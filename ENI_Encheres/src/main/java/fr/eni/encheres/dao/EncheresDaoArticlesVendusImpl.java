package fr.eni.encheres.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.ArticleVendu;

@Repository
public class EncheresDaoArticlesVendusImpl implements EncheresDaoArticlesVendus {
	
	final static String SELECT_ALL_ARTICLEVENDU = "select no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_categorie from ARTICLES_VENDUS";
	
	private NamedParameterJdbcTemplate namedParameterjdbcTemplate;
	private EncheresDaoCategories encheresDaoCategories;
	
	

	public EncheresDaoArticlesVendusImpl(NamedParameterJdbcTemplate namedParameterjdbcTemplate, EncheresDaoCategories encheresDaoCategories) {
		this.namedParameterjdbcTemplate = namedParameterjdbcTemplate;
		this.encheresDaoCategories = encheresDaoCategories;
	}



	@Override
	public List<ArticleVendu> getAllArticleVendu() {
		
		List<ArticleVendu> liste;
		liste = namedParameterjdbcTemplate.query(SELECT_ALL_ARTICLEVENDU, new ArticleVenduRowMapper(this, encheresDaoCategories));
		System.out.println(liste);
		return liste;
	}

}
