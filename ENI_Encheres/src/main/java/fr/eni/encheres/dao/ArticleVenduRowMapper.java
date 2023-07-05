package fr.eni.encheres.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import fr.eni.encheres.bo.ArticleVendu;

public class ArticleVenduRowMapper implements RowMapper<ArticleVendu>{
	
	EncheresDaoArticlesVendus encheresDaoArticlesVendus;
	EncheresDaoCategories encheresDaoCategories;
	EncheresDaoUtilisateurs encheresDaoUtilisateurs;
	
	

	public ArticleVenduRowMapper(EncheresDaoArticlesVendus encheresDaoArticlesVendus, EncheresDaoCategories encheresDaoCategories, EncheresDaoUtilisateurs encheresDaoUtilisateurs) {
		this.encheresDaoArticlesVendus = encheresDaoArticlesVendus;
		this.encheresDaoCategories = encheresDaoCategories;
		this.encheresDaoUtilisateurs = encheresDaoUtilisateurs;
	}



	@Override
	public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArticleVendu article =new ArticleVendu();
		article.setNo_article(rs.getInt(1));
		article.setNom_article(rs.getString(2));
		article.setDescription(rs.getString(3));
		article.setDate_debut_encheres(rs.getObject(4, LocalDate.class));
		article.setDate_fin_encheres(rs.getObject(5, LocalDate.class));
		article.setPrix_initial(rs.getInt(6));
		article.setPrix_vente(rs.getInt(7));
		article.setUtilisateur(encheresDaoUtilisateurs.getUtilisateurById(rs.getInt(8)));
		article.setCategorie(encheresDaoCategories.getCategorieById(rs.getInt(9)));
		article.setVenteTermine(rs.getBoolean(10));
		
		return article;
	}
	
	

}
