package fr.eni.encheres.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import org.springframework.cglib.core.Local;
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
	EncheresDaoEncheres enchereDaoEncheres;
	LocalDate datedujour;

	public EncheresServiceArticlesVendusImpl(EncheresDaoArticlesVendus encheresDaoArticlesVendus,
			EncheresDaoEncheres enchereDaoEncheres) {
		this.encheresDaoArticlesVendus = encheresDaoArticlesVendus;
		this.enchereDaoEncheres = enchereDaoEncheres;
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

		if (categorie != null) {
			if (categorie.getNo_categorie() == 99) {
				return encheresDaoArticlesVendus.getArticleContainNom(rechercheNom);
			} else {
				return encheresDaoArticlesVendus.getArticleByCategorieContainNom(rechercheNom, categorie);
			}
		}
		return encheresDaoArticlesVendus.getArticleContainNom(rechercheNom);
	}

	// RECHERCHE AVEC UTILISATEUR CONNECTE
	@Override
	public List<ArticleVendu> rechercheConnecte(String rechercheNom, Categorie categorie, Utilisateur utilisateur,
			String optionArticle, String ventesEnCours, String ventesTerminees, String ventesNonDebutees) {
		System.out.println("entrée de la méthode ventes terminees=" + ventesTerminees);
		System.out.println("entrée de la méthode ventes en cours=" + ventesEnCours);
		System.out.println("entrée de la méthode  ventesNonDebutees=" + ventesNonDebutees);
		datedujour = LocalDate.now();

		if (optionArticle != null) {
			if (optionArticle.equals("ventes")) {
				if (ventesEnCours != null) {
					if (ventesEnCours.equals("venteencours")) {
						List<ArticleVendu> newList = new ArrayList<>();
						for (ArticleVendu articleAtrier : encheresDaoArticlesVendus.getArticlesByUser(utilisateur)) {
							if ((articleAtrier.getDate_debut_encheres().isBefore(datedujour))
									&& (articleAtrier.getDate_fin_encheres().isAfter(datedujour))) {
								newList.add(articleAtrier);
							}
						}
						return newList;
					}
				}
				if (ventesTerminees != null) {
					if (ventesTerminees.equals("ventesterminees")) {
						List<ArticleVendu> newList2 = new ArrayList<>();
						for (ArticleVendu articleAtrier : encheresDaoArticlesVendus.getArticlesByUser(utilisateur)) {
							if ((articleAtrier.getDate_fin_encheres().isBefore(datedujour))) {
								newList2.add(articleAtrier);
							}
						}
						return newList2;
					}
				}
				if (ventesNonDebutees != null) {
					if (ventesNonDebutees.equals("ventesnondebutees")) {
						List<ArticleVendu> newList = new ArrayList<>();
						for (ArticleVendu articleAtrier : encheresDaoArticlesVendus.getArticlesByUser(utilisateur)) {
							if ((articleAtrier.getDate_debut_encheres().isAfter(datedujour))) {
								newList.add(articleAtrier);
							}
						}
						return newList;
					}
				} 
				return encheresDaoArticlesVendus.getArticlesByUser(utilisateur);
			}
			/// A FAIRE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			if (optionArticle.equals("achats")) {
				List<Enchere> listEncheres = utilisateur.getListeEncheres();
				List<ArticleVendu> listeArticleAchete = new ArrayList<>();
				for (Enchere enchere : listEncheres) {
					listeArticleAchete.add(enchere.getArticle());
				}
				return listeArticleAchete;
			}
		}
		return rechercheNonConnecte(rechercheNom, categorie);
	}

}
