package fr.eni.encheres.dao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import fr.eni.encheres.bo.ArticleVendu;

@Repository
public class EncheresDaoArticlesVendusImpl implements EncheresDaoArticlesVendus {

	final static String SELECT_ALL_ARTICLEVENDU = "SELECT * from ARTICLES_VENDUS";
	final static String INSERT_ARTICLE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie)";
	final static String SELECT_ARTICLE_BY_ID = "SELECT * from ARTICLES_VENDUS WHERE no_article = :no_article";
	
	private NamedParameterJdbcTemplate namedParameterjdbcTemplate;
	private EncheresDaoCategories encheresDaoCategories;
	private EncheresDaoUtilisateurs encheresDaoUtilisateurs;

	public EncheresDaoArticlesVendusImpl(NamedParameterJdbcTemplate namedParameterjdbcTemplate,
			EncheresDaoCategories encheresDaoCategories, EncheresDaoUtilisateurs encheresDaoUtilisateurs) {
		this.namedParameterjdbcTemplate = namedParameterjdbcTemplate;
		this.encheresDaoCategories = encheresDaoCategories;
		this.encheresDaoUtilisateurs = encheresDaoUtilisateurs;
	}

	@Override
	public List<ArticleVendu> getAllArticleVendu() {

		List<ArticleVendu> liste;
		liste = namedParameterjdbcTemplate.query(SELECT_ALL_ARTICLEVENDU,
				new ArticleVenduRowMapper(this, encheresDaoCategories, encheresDaoUtilisateurs));
		System.out.println(liste);
		return liste;
	}

	@Override
	public void creerArticle(ArticleVendu article) {
		MapSqlParameterSource articleMap = new MapSqlParameterSource();
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		articleMap.addValue("nom_article", article.getNom_article());
		articleMap.addValue("description", article.getDescription());
		articleMap.addValue("date_debut_encheres", article.getDate_debut_encheres());
		articleMap.addValue("date_fin_encheres", article.getDate_fin_encheres());
		articleMap.addValue("prix_initial", article.getPrix_initial());
		articleMap.addValue("prix_vente", article.getPrix_initial());
		articleMap.addValue("no_utilisateur", article.getUtilisateur().getNo_utilisateur());
		articleMap.addValue("no_categorie", article.getCategorie().getNo_categorie());
		namedParameterjdbcTemplate.update(INSERT_ARTICLE, articleMap, keyHolder);
		article.setNo_article(keyHolder.getKey().intValue());
	}

	@Override
	public ArticleVendu getArticleById(Integer id) {
		System.out.println("METHODE GET ARTICLE BY ID ID= "+id);
		ArticleVendu src = new ArticleVendu(id);
		System.out.println(src);
		ArticleVendu article = namedParameterjdbcTemplate.queryForObject(SELECT_ARTICLE_BY_ID , new BeanPropertySqlParameterSource(src) ,new ArticleVenduRowMapper(this, encheresDaoCategories, encheresDaoUtilisateurs));
		System.out.println(article);
		return article;
	}

}
