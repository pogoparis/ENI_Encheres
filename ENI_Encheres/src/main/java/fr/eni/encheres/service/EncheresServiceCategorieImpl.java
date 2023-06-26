package fr.eni.encheres.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dao.EncheresDaoCategories;

@Service
public class EncheresServiceCategorieImpl implements EncheresServiceCategorie {

	EncheresDaoCategories encheresDaoCategories;
	
	
	
	public EncheresServiceCategorieImpl(EncheresDaoCategories encheresDaoCategories) {
		super();
		this.encheresDaoCategories = encheresDaoCategories;
	}



	@Override
	public List<Categorie> findAllCategorie() {
		

		return encheresDaoCategories.getAllCategories();
	}



	@Override
	public Categorie findCategorieById(Integer idCategorie) {
		
		return encheresDaoCategories.getCategorieById(idCategorie);
	}

}
