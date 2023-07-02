package fr.eni.encheres.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

@Repository
public class EncheresDaoArticlesVendusImpl implements EncheresDaoArticlesVendus {

	final static String SELECT_ALL_ARTICLEVENDU = "SELECT * from ARTICLES_VENDUS";
	final static String INSERT_ARTICLE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie)";
	final static String SELECT_ARTICLE_BY_ID = "SELECT * from ARTICLES_VENDUS WHERE no_article = :no_article";
	final static String UPDATE_PRIX_VENTE_ARTICLE_APRES_ENCHERE = "UPDATE ARTICLES_VENDUS set prix_vente=:prix_vente where no_article = :no_article"; 
	
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
		ArticleVendu src = new ArticleVendu(id);
		ArticleVendu article = namedParameterjdbcTemplate.queryForObject(SELECT_ARTICLE_BY_ID , new BeanPropertySqlParameterSource(src) ,new ArticleVenduRowMapper(this, encheresDaoCategories, encheresDaoUtilisateurs));
		return article;
	}

	@Override
	public void majPrixArticle(Enchere enchere) {
		MapSqlParameterSource articleMap = new MapSqlParameterSource();
		articleMap.addValue("prix_vente",enchere.getMontant_enchere());
		articleMap.addValue("no_article",enchere.getArticle().getNo_article());
		namedParameterjdbcTemplate.update(UPDATE_PRIX_VENTE_ARTICLE_APRES_ENCHERE,  articleMap);	
	}

	
	// RECHERCHE ARTICLES PAR NOM
	@Override
	public List<ArticleVendu> getArticleContainNom(String rechercheNom) {
		List<ArticleVendu> listeArticleTrouve;
		String sql = "SELECT * from ARTICLES_VENDUS WHERE nom_article LIKE :rechercheNom";
		Map<String, Object> params = new HashMap<>();
	    params.put("rechercheNom", "%" + rechercheNom + "%");
	    listeArticleTrouve = namedParameterjdbcTemplate.query(sql, params,
	            new ArticleVenduRowMapper(this, encheresDaoCategories, encheresDaoUtilisateurs));
	    return listeArticleTrouve;
	}
	 
	
	/******************************  RECHERCHE *******************************************/
	// recherche par categorie et contenant la recherche
	@Override
	public List<ArticleVendu> getArticleByCategorieContainNom(String rechercheNom, Categorie categorie) {
		List<ArticleVendu> listeArticleTrouve2;
		String sqlCat = "SELECT * from ARTICLES_VENDUS WHERE no_categorie=:no_categorie AND nom_article AND nom_article LIKE :rechercheNom";
		Map<String, Object> params = new HashMap<>();
		params.put("no_categorie", categorie.getNo_categorie());
	    params.put("rechercheNom", "%" + rechercheNom + "%");
	    listeArticleTrouve2 = namedParameterjdbcTemplate.query(sqlCat, params,
	            new ArticleVenduRowMapper(this, encheresDaoCategories, encheresDaoUtilisateurs));
	    return listeArticleTrouve2;
	}

	// recherche article par utilisateur
	@Override
	public List<ArticleVendu> getArticlesByUser(Utilisateur utilisateur, String rechercheNom) {
		List<ArticleVendu> listeArticleUser;
		String sql = "SELECT * from ARTICLES_VENDUS WHERE no_utilisateur=:no_utilisateur AND nom_article LIKE :rechercheNom";
		Map<String, Object> params = new HashMap<>();
		params.put("no_utilisateur", utilisateur.getNo_utilisateur());
		params.put("rechercheNom", "%" + rechercheNom + "%");
		listeArticleUser = namedParameterjdbcTemplate.query(sql, params,
	            new ArticleVenduRowMapper(this, encheresDaoCategories, encheresDaoUtilisateurs));
	    return listeArticleUser;
	}
	
	//recherche article par utilisateur et categorie
	@Override
	public List<ArticleVendu> getArticlesByUserByCategorie(Utilisateur utilisateur, Categorie categorie, String rechercheNom) {
		List<ArticleVendu> listeArticleUserCat;
		String sql = "SELECT * from ARTICLES_VENDUS WHERE no_utilisateur=:no_utilisateur AND no_categorie=:no_categorie AND nom_article LIKE :rechercheNom";
		Map<String, Object> params = new HashMap<>();
		params.put("no_utilisateur", utilisateur.getNo_utilisateur());
		params.put("no_categorie", categorie.getNo_categorie());
		params.put("rechercheNom", "%" + rechercheNom + "%");
		listeArticleUserCat = namedParameterjdbcTemplate.query(sql, params,
	            new ArticleVenduRowMapper(this, encheresDaoCategories, encheresDaoUtilisateurs));
	    return listeArticleUserCat;
	}

}
