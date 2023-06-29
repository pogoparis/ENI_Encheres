package fr.eni.encheres.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;

@Repository
public class EncheresDaoRetraitsImpl implements EncheresDaoRetraits {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private EncheresDaoArticlesVendus encheresDaoArticlesVendus;
	
	final static String INSERT_RETRAIT = "INSERT INTO RETRAITS (rue, code_postal, ville, no_article) VALUES (:rue, :code_postal, :ville, :no_article) ";
	final static String SELECT_RETRAIT_BY_ID = "select retraits.no_article, rue, code_postal, ville from retraits inner join ARTICLES_VENDUS on RETRAITS.no_article = ARTICLES_VENDUS.no_article where ARTICLES_VENDUS.no_article =:no_article";

	public EncheresDaoRetraitsImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
			EncheresDaoArticlesVendus encheresDaoArticlesVendus) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.encheresDaoArticlesVendus = encheresDaoArticlesVendus;
	}

	@Override
	public void createRetrait(Retrait retrait, ArticleVendu article) {
		Map<String, Object> map = new HashMap<>();		
		map.put("rue", retrait.getRue());
		map.put("code_postal", retrait.getCode_postal());
		map.put("ville", retrait.getVille());
		map.put("no_article", article.getNo_article());
		namedParameterJdbcTemplate.update(INSERT_RETRAIT, map);
	}
	
	public Retrait getRetraitByArticle(ArticleVendu article) {
		Retrait retrait = new Retrait();
		System.out.println("RETRAIT : "+retrait);
		retrait = namedParameterJdbcTemplate.queryForObject(SELECT_RETRAIT_BY_ID, new BeanPropertySqlParameterSource(article), new RetraitRowMapper(encheresDaoArticlesVendus));
		System.out.println("RETRAIT : "+retrait);	
		return retrait;
	}

}
