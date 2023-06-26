package fr.eni.encheres.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.dao.EncheresDaoArticlesVendus;

@Service
public class EncheresServiceArticlesVendusImpl implements EncheresServiceArticlesVendus {
	
	EncheresDaoArticlesVendus encheresDaoArticlesVendus;
	
	

	public EncheresServiceArticlesVendusImpl(EncheresDaoArticlesVendus encheresDaoArticlesVendus) {
		this.encheresDaoArticlesVendus = encheresDaoArticlesVendus;
	}



	@Override
	public List<ArticleVendu> findAllArticleVendu() {
		return encheresDaoArticlesVendus.getAllArticleVendu();
	}

}
