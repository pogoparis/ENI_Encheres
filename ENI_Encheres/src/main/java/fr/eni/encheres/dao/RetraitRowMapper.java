package fr.eni.encheres.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fr.eni.encheres.bo.Retrait;

public class RetraitRowMapper implements RowMapper<Retrait> {
	
	EncheresDaoArticlesVendus encheresDaoArticlesVendus;
	
	

	public RetraitRowMapper(EncheresDaoArticlesVendus encheresDaoArticlesVendus) {
		this.encheresDaoArticlesVendus = encheresDaoArticlesVendus;
	}



	@Override
	public Retrait mapRow(ResultSet rs, int rowNum) throws SQLException {
		Retrait retrait = new Retrait();
		retrait.setArticle(encheresDaoArticlesVendus.getArticleById(rs.getInt(1)));
		retrait.setRue(rs.getString(2));
		retrait.setCode_postal(rs.getString(3));
		retrait.setVille(rs.getString(4));
		return retrait;
	}

}
