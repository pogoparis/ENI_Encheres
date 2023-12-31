package fr.eni.encheres.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Utilisateur;


@Repository
public class EncheresDaoUtilisateursImpl implements EncheresDaoUtilisateurs {

	public final static String SELECT_ALL_UTILISATEURS = "SELECT * FROM UTILISATEURS";
	public final static String SELECT_UTILISATEUR_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = :no_utilisateur";
	final static String SELECT_USER_BY_USERNAME = "select * from UTILISATEURS where pseudo=:pseudo";
	final static String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, rue, code_postal, ville,mot_de_passe, credit, telephone, administrateur) VALUES (:pseudo, :nom, :prenom, :email, :rue, :code_postal, :ville,:mot_de_passe, :credit,:telephone, :administrateur)";
	final static String UPDATE_UTILISATEUR = "update UTILISATEURS set pseudo=:pseudo, nom=:nom, prenom=:prenom, email=:email, rue=:rue, code_postal=:code_postal, ville=:ville, credit=:credit,telephone=:telephone, administrateur=:administrateur where no_utilisateur=:no_utilisateur";
	final static String UPDATE_CREDIT_UTILISATEUR = "UPDATE UTILISATEURS set credit=:credit where no_utilisateur = :no_utilisateur"; 
	final static String DELETE_USER = "delete UTILISATEURS where no_utilisateur = :no_utilisateur";
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	

	@Autowired 
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate=namedParameterJdbcTemplate;
	}
	


	// Pour le moment cette méthode ne crée pas les listes d'articles et d'encheres propres a l'utilisateur
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
	
	public Utilisateur getUserByPseudo(String pseudo){
		Utilisateur src = new Utilisateur(pseudo);
		Utilisateur utilisateur = namedParameterJdbcTemplate.queryForObject(SELECT_USER_BY_USERNAME, new BeanPropertySqlParameterSource(src), new BeanPropertyRowMapper<>(Utilisateur.class));
		return utilisateur;
	}

	public boolean isPseudoUnique(String pseudo) {
        String sql = "SELECT COUNT(*) FROM UTILISATEURS WHERE pseudo = :pseudo";
        Map<String, Object> params = Collections.singletonMap("pseudo", pseudo);
        int count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return count == 0;
    }
	
	public boolean isMailUnique(String email) {
        String sql = "SELECT COUNT(*) FROM UTILISATEURS WHERE email = :email";
        Map<String, Object> params = Collections.singletonMap("email", email);
        int count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return count == 0;
    }
	
	
	// Enregistrement de l'utilisateur en BDD
	@Override
	public void saveUtilisateur(Utilisateur utilisateur) {
		
		System.out.println("----------------------------------------------------------------");
		
			Map<String, Object> map = new HashMap<>();
			if(utilisateur.getNo_utilisateur() != null) {
				map.put("no_utilisateur", utilisateur.getNo_utilisateur());
			}
			map.put("pseudo", utilisateur.getPseudo());
			map.put("nom", utilisateur.getNom());
			map.put("prenom", utilisateur.getPrenom());
			map.put("email", utilisateur.getEmail());
			map.put("telephone", utilisateur.getTelephone());
			map.put("rue", utilisateur.getRue());
			map.put("code_postal", utilisateur.getCodePostal());
			map.put("ville", utilisateur.getVille());
			if (utilisateur.getNo_utilisateur() == null) {
				map.put("mot_de_passe", utilisateur.getMotDePasse());
			}
			map.put("credit", 600);
			map.put("administrateur", 1);
			
			if (utilisateur.getNo_utilisateur() == null) {	
			namedParameterJdbcTemplate.update(INSERT_UTILISATEUR, map);	
			System.out.println("Mauvais passage");
			} else {
			namedParameterJdbcTemplate.update(UPDATE_UTILISATEUR, map);
			System.out.println("Bon passage");
			}			
		
	}


	
	
	@Override
	public void delete(Integer id) {
		Utilisateur src = new Utilisateur(id);
		namedParameterJdbcTemplate.update(DELETE_USER, new BeanPropertySqlParameterSource(src));
		
	}


	


	@Override
	public void majCredit(int valeur, Utilisateur utilisateur) {
		MapSqlParameterSource utilisateurMap = new MapSqlParameterSource();
		int nouveauCredit = utilisateur.getCredit()+valeur;
		utilisateur.setCredit(nouveauCredit);
		utilisateurMap.addValue("no_utilisateur", utilisateur.getNo_utilisateur());
		utilisateurMap.addValue("credit", utilisateur.getCredit());
		namedParameterJdbcTemplate.update(UPDATE_CREDIT_UTILISATEUR, utilisateurMap);
		
	}

}
