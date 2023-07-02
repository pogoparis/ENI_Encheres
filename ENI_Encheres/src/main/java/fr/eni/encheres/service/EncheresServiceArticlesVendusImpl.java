package fr.eni.encheres.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dao.EncheresDaoArticlesVendus;
import fr.eni.encheres.dao.EncheresDaoEncheres;
import fr.eni.encheres.dao.EncheresDaoUtilisateurs;

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

	@Override
	public void createArticle(ArticleVendu article) {
		encheresDaoArticlesVendus.creerArticle(article);

	}

	@Override
	public ArticleVendu findArticleById(Integer id) {
		return encheresDaoArticlesVendus.getArticleById(id);
	}

	@Override
	public void majPrixArticle(Enchere enchere) {
		encheresDaoArticlesVendus.majPrixArticle(enchere);

	}

	
	/*********************** RECHERCHE ***************************/
	@Override
	public List<ArticleVendu> rechercheNonConnecte(String rechercheNom, Categorie categorie) {
		// l'option 99 du select indique qu'aucune catégorie n'a été choisie
		if (categorie.getNo_categorie() == 99) {
			return encheresDaoArticlesVendus.getArticleContainNom(rechercheNom);
		} else {
			return encheresDaoArticlesVendus.getArticleByCategorieContainNom(rechercheNom, categorie);
		}
	}

	// RECHERCHE AVEC UTILISATEUR CONNECTE
	@Override
	public List<ArticleVendu> rechercheConnecte(String rechercheNom, Categorie categorie, Utilisateur utilisateur,
			String optionArticle) {
		// IL FAUT FAIRE LES OPTIONS VENTE ET ACHAT
		if (categorie.getNo_categorie() == 99) {
			if (optionArticle.equals("ventes") ) {
				return encheresDaoArticlesVendus.getArticlesByUser(utilisateur, rechercheNom);
			} else if (optionArticle.equals("achats") ) {
				//**************************** A FAIRE************************************** //
				return null;
			} else {
				return encheresDaoArticlesVendus.getArticleContainNom(rechercheNom);
			}
		} else {
			if (optionArticle.equals("ventes")) {
				return encheresDaoArticlesVendus.getArticlesByUserByCategorie(utilisateur, categorie, rechercheNom);
			} else if (optionArticle.equals("achats")) {
				//************************** A FAIRE *******************************************// 			
				return null;
			} else {
				return encheresDaoArticlesVendus.getArticleByCategorieContainNom(rechercheNom, categorie);
			}
		}
	}
	
	
}
