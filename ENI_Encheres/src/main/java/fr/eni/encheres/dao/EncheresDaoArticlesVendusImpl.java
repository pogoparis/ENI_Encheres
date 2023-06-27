package fr.eni.encheres.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.ArticleVendu;

@Repository
public class EncheresDaoArticlesVendusImpl implements EncheresDaoArticlesVendus {

	final static String SELECT_ALL_ARTICLEVENDU = "select * from ARTICLES_VENDUS";
	final static String INSERT_ARTICLE = "INSERT INTO ARTCILES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie)";

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
//		ArticleVendu newArticle = new ArticleVendu(article.getNom_article(), article.getDescription(),
//				article.getDate_debut_encheres(), article.getDate_fin_encheres(), article.getPrix_initial(),
//				article.getPrix_vente(), article.getUtilisateur(), article.getCategorie());
		Map<String, Object> map = new HashMap<>();
		map.put("nom_article", article.getNom_article());
		map.put("description", article.getDescription());
		map.put("date_debut_encheres", article.getDate_debut_encheres());
		map.put("date_fin_encheres", article.getDate_fin_encheres());
		map.put("prix_initial", article.getPrix_initial());
		map.put("prix_vente", article.getPrix_vente());
		map.put("no_utilisateur", article.getUtilisateur().getNo_utilisateur());
		map.put("no_categorie", article.getCategorie().getNo_categorie());
		namedParameterjdbcTemplate.update(INSERT_ARTICLE, map);	
	}

	@Override
	public ArticleVendu getArticleById() {
		// TODO Auto-generated method stub
		return null;
	}

}
