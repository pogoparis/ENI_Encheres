package fr.eni.encheres.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;

@Repository
public class EncheresDaoRetraitsImpl implements EncheresDaoRetraits {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	
	@Override
	public void createRetrait(Retrait retrait, ArticleVendu article) {
		
		
	}

}
