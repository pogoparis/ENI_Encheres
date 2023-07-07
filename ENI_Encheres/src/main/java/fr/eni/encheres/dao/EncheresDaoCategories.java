package fr.eni.encheres.dao;

import java.util.List;

import fr.eni.encheres.bo.Categorie;

public interface EncheresDaoCategories {

	List<Categorie> getAllCategories();

	Categorie getCategorieById(Integer idCategorie);

}
