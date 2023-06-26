package fr.eni.encheres.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.ArticleVendu;

@Repository
public class EncheresDaoArticlesVendusImpl implements EncheresDaoArticlesVendus {
	
	final static String SELECT_ALL_ARTICLEVENDU = "select nom_article from ARTICLES_VENDUS";
	
	private NamedParameterJdbcTemplate namedParameterjdbcTemplate;

	@Override
	public List<ArticleVendu> getAllArticleVendu() {
		
		List<ArticleVendu> liste;
		liste = namedParameterjdbcTemplate.query(SELECT_ALL_ARTICLEVENDU, new BeanPropertyRowMapper<>(ArticleVendu.class));
		System.out.println(liste);
		return liste;
	}

}
