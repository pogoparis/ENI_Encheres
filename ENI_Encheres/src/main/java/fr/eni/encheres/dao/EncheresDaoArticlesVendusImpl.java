package fr.eni.encheres.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.ArticleVendu;

@Repository
public class EncheresDaoArticlesVendusImpl implements EncheresDaoArticlesVendus {
	
	final static String SELECT_ALL_ARTICLEVENDU = "select * from ARTICLES_VENDUS";
	
	private NamedParameterJdbcTemplate namedParameterjdbcTemplate;
	private EncheresDaoCategories encheresDaoCategories;
	private EncheresDaoUtilisateurs encheresDaoUtilisateurs;
	
	

	public EncheresDaoArticlesVendusImpl(NamedParameterJdbcTemplate namedParameterjdbcTemplate, EncheresDaoCategories encheresDaoCategories, EncheresDaoUtilisateurs encheresDaoUtilisateurs) {
		this.namedParameterjdbcTemplate = namedParameterjdbcTemplate;
		this.encheresDaoCategories = encheresDaoCategories;
		this.encheresDaoUtilisateurs=encheresDaoUtilisateurs;
	}



	@Override
	public List<ArticleVendu> getAllArticleVendu() {
		
		List<ArticleVendu> liste;
		liste = namedParameterjdbcTemplate.query(SELECT_ALL_ARTICLEVENDU, new ArticleVenduRowMapper(this, encheresDaoCategories, encheresDaoUtilisateurs));
		System.out.println(liste);
		return liste;
	}

}
