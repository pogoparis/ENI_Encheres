package fr.eni.encheres.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Enchere;

@Repository
public class EncheresDaoEncheresImpl implements EncheresDaoEncheres {

	
	public final static String INSERT_ENCHERES ="INSERT INTO ENCHERES (date_enchere, montant_enchere, no_article, no_utilisateur) VALUES (:date_enchere, :montant_enchere, :no_article, :no_utilisateur)";
	private EncheresDaoUtilisateurs encheresDaoUtilisateurs;
	private EncheresDaoArticlesVendus encheresDaoArticlesVendus;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public EncheresDaoEncheresImpl(EncheresDaoUtilisateurs encheresDaoUtilisateurs,
			EncheresDaoArticlesVendus encheresDaoArticlesVendus,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.encheresDaoUtilisateurs = encheresDaoUtilisateurs;
		this.encheresDaoArticlesVendus = encheresDaoArticlesVendus;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}


	@Override
	public void saveEnchere(Enchere enchere) {
		//new EncheresRowMapper(encheresDaoArticlesVendus, encheresDaoUtilisateurs);
		MapSqlParameterSource enchereMap = new MapSqlParameterSource();	
		enchereMap.addValue("date_enchere", (enchere.getDate_enchere()));
		enchereMap.addValue("montant_enchere", enchere.getMontant_enchere());
		enchereMap.addValue("no_article", enchere.getArticle().getNo_article());
		enchereMap.addValue("no_utilisateur", enchere.getUtilisateur().getNo_utilisateur());	
		namedParameterJdbcTemplate.update(INSERT_ENCHERES, enchereMap);	
	}
	
	

}
