package fr.eni.encheres.service;

import java.util.List;

import fr.eni.encheres.bo.Categorie;

public interface EncheresServiceCategorie {

	List<Categorie> findAllCategorie();

	Categorie findCategorieById(Integer idCategorie);
}
