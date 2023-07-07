package fr.eni.encheres.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Categorie;

@Repository
public class EncheresDaoCategoriesImpl implements EncheresDaoCategories {

	public final static String SELECT_ALL_CATEGORIE = "SELECT * FROM CATEGORIES";
	public final static String SELECT_CATEGORIE_BY_ID = "SELECT * FROM CATEGORIES WHERE no_categorie = :no_categorie";

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public EncheresDaoCategoriesImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public List<Categorie> getAllCategories() {

		List<Categorie> liste;
		liste = namedParameterJdbcTemplate.query(SELECT_ALL_CATEGORIE, new BeanPropertyRowMapper<>(Categorie.class));

		return liste;
	}

	@Override
	public Categorie getCategorieById(Integer idCategorie) {
		Categorie src = new Categorie(idCategorie);
		Categorie categorie = namedParameterJdbcTemplate.queryForObject(SELECT_CATEGORIE_BY_ID,
				new BeanPropertySqlParameterSource(src), new BeanPropertyRowMapper<>(Categorie.class));
		return categorie;
	}

}
