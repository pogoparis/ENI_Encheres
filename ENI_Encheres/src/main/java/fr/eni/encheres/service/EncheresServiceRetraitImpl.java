package fr.eni.encheres.service;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dao.EncheresDaoRetraits;

@Service
public class EncheresServiceRetraitImpl implements EncheresServiceRetrait {

	EncheresDaoRetraits encheresDaoRetraits;

	
	public EncheresServiceRetraitImpl(EncheresDaoRetraits encheresDaoRetraits) {
		this.encheresDaoRetraits = encheresDaoRetraits;
	}



	@Override
	public void createRetrait(Retrait retrait, ArticleVendu article) {
		
		encheresDaoRetraits.createRetrait(retrait , article);
	}
	
}
