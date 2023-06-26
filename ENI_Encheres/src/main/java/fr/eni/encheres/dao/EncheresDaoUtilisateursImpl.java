package fr.eni.encheres.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Utilisateur;


@Repository
public class EncheresDaoUtilisateursImpl implements EncheresDaoUtilisateurs {

	public final static String SELECT_ALL_UTILISATEURS = "SELECT * FROM UTILISATEURS";
	public final static String SELECT_UTILISATEUR_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = :no_utilisateur";
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired 
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate=namedParameterJdbcTemplate;
	}
	
	
	@Override
	public List<Utilisateur> getAllUtilisateurs() {
		List<Utilisateur> utilisateurs;
		utilisateurs = namedParameterJdbcTemplate.query(SELECT_ALL_UTILISATEURS, new BeanPropertyRowMapper<>(Utilisateur.class));
		return utilisateurs;
	}

	@Override
	public Utilisateur getUtilisateurById(Integer idUtilisateur) {
		Utilisateur src = new Utilisateur(idUtilisateur);
		Utilisateur utilisateur = namedParameterJdbcTemplate.queryForObject(SELECT_UTILISATEUR_BY_ID, new BeanPropertySqlParameterSource(src), new BeanPropertyRowMapper<>(Utilisateur.class));
		return utilisateur;
	}

}
