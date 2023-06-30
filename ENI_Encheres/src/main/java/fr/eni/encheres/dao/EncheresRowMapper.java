package fr.eni.encheres.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import fr.eni.encheres.bo.Enchere;

public class EncheresRowMapper implements RowMapper<Enchere>{

	EncheresDaoArticlesVendus encheresDaoArticlesVendus;
	EncheresDaoUtilisateurs encheresDaoUtilisateurs;
	
	
	public EncheresRowMapper(EncheresDaoArticlesVendus encheresDaoArticlesVendus,
			EncheresDaoUtilisateurs encheresDaoUtilisateurs) {
		this.encheresDaoArticlesVendus = encheresDaoArticlesVendus;
		this.encheresDaoUtilisateurs = encheresDaoUtilisateurs;
	}


	@Override
	public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
		Enchere enchere = new Enchere();
		enchere.setDate_enchere(rs.getObject("date_enchere", LocalDate.class));
		enchere.setMontant_enchere(rs.getInt("montant_enchere"));
		enchere.setUtilisateur(encheresDaoUtilisateurs.getUtilisateurById(rs.getInt("no_utilisateur")));
		enchere.setArticle(encheresDaoArticlesVendus.getArticleById(rs.getInt("no_article")));
		return enchere;	
		}
	
	
	
}
