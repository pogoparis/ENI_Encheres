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

	// RECHERCHE AVEC UTILISATEUR CONNECTE QUI APPELLE LA METHODE DU DESSOUS
	@Override
	public List<ArticleVendu> rechercheConnecte(String rechercheNom, Categorie categorie, Utilisateur utilisateur,
			String optionArticle, String ventesEnCours, String ventesTerminees, String ventesNonDebutees) {
		if (categorie.getNo_categorie() == 99) {
			return methodedeRecherche(encheresDaoArticlesVendus.getArticlesByUserAndSearch(utilisateur, rechercheNom),
					rechercheNom, categorie, utilisateur, optionArticle, ventesEnCours, ventesTerminees,
					ventesNonDebutees);
		} else {
			return methodedeRecherche(
					encheresDaoArticlesVendus.getArticlesByUserByCategorie(utilisateur, categorie, rechercheNom),
					rechercheNom, categorie, utilisateur, optionArticle, ventesEnCours, ventesTerminees,
					ventesNonDebutees);
		}
	}
	
	// Methode de recherche qui prend en paramtetre une requete differente selon si categorie est select
	public List<ArticleVendu> methodedeRecherche(List<ArticleVendu> param, String rechercheNom, Categorie categorie,
		    Utilisateur utilisateur, String optionArticle, String ventesEnCours, String ventesTerminees,
		    String ventesNonDebutees) {
		    datedujour = LocalDate.now();
		    List<ArticleVendu> newList = new ArrayList<>();
		    if (optionArticle != null) {
		        if (optionArticle.equals("ventes")) {
		            if (ventesEnCours != null && ventesEnCours.equals("venteencours")) {
		                 filterArticlesEnCours(param, newList);
		            }
		            if (ventesTerminees != null && ventesTerminees.equals("ventesterminees")) {
		                 filterArticlesTerminees(param, newList);
		            }
		            if (ventesNonDebutees != null && ventesNonDebutees.equals("ventesnondebutees")) {
		                 filterArticlesNonDebutees(param, newList);
		            } if (ventesNonDebutees == null && ventesEnCours == null && ventesTerminees == null) {
		            	return encheresDaoArticlesVendus.getArticlesByUser(utilisateur);
		            }
		            return newList;
		        }
		        
		      //A FAIRE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		        if (optionArticle.equals("achats")) {
		            return getArticlesAchete(utilisateur);
		        }
		    }
		    
		    return rechercheNonConnecte(rechercheNom, categorie);
		}

		private List<ArticleVendu> filterArticlesEnCours(List<ArticleVendu> articles, List<ArticleVendu> newList) {   
		    for (ArticleVendu article : articles) {
		        if (article.getDate_debut_encheres().isBefore(datedujour) &&
		            article.getDate_fin_encheres().isAfter(datedujour)) {
		            newList.add(article);
		        }
		    }
		    return newList;
		}

		private List<ArticleVendu> filterArticlesTerminees(List<ArticleVendu> articles, List<ArticleVendu> newList) {
		    for (ArticleVendu article : articles) {
		        if (article.getDate_fin_encheres().isBefore(datedujour)) {
		            newList.add(article);
		        }
		    }
		    return newList;
		}

		private List<ArticleVendu> filterArticlesNonDebutees(List<ArticleVendu> articles, List<ArticleVendu> newList) {
		    for (ArticleVendu article : articles) {
		        if (article.getDate_debut_encheres().isAfter(datedujour)) {
		            newList.add(article);
		        }
		    }
		    return newList;
		}

		private List<ArticleVendu> getArticlesAchete(Utilisateur utilisateur) {
		    List<Enchere> listEncheres = utilisateur.getListeEncheres();
		    List<ArticleVendu> listeArticleAchete = new ArrayList<>();
		    for (Enchere enchere : listEncheres) {
		        listeArticleAchete.add(enchere.getArticle());
		    }
		    return listeArticleAchete;
		}

	

}
