package fr.eni.encheres.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dao.EncheresDaoArticlesVendus;
import fr.eni.encheres.dao.EncheresDaoEncheres;

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
		if (categorie != null) {
			if (categorie.getNo_categorie() == 99) {
				if (optionArticle != null) {
					if (optionArticle.equals("ventes")) {
						System.out.println("pas de categorie !!!! et vente coché ");
						return methodedeRechercheVente(
								encheresDaoArticlesVendus.getArticlesByUserAndSearch(utilisateur, rechercheNom),
								rechercheNom, categorie, utilisateur, optionArticle, ventesEnCours, ventesTerminees,
								ventesNonDebutees);
					}
					if (optionArticle.equals("achats")) {
						return getArticlesAchete(utilisateur);
					}
				}
				return encheresDaoArticlesVendus.getArticleContainNom(rechercheNom);
			} else {
				if (optionArticle != null) {
					if (optionArticle.equals("ventes")) {
						System.out.println("j'ai une categorie et ventes de coché");
						return methodedeRechercheVente(
								encheresDaoArticlesVendus.getArticlesByUserByCategorie(utilisateur, categorie,
										rechercheNom),
								rechercheNom, categorie, utilisateur, optionArticle, ventesEnCours, ventesTerminees,
								ventesNonDebutees);
					} 
				} 
				return encheresDaoArticlesVendus.getArticleByCategorieContainNom(rechercheNom, categorie);
			}
		}
		return encheresDaoArticlesVendus.getArticleContainNom(rechercheNom);
	}

	// Methode de recherche qui prend en paramtetre une requete differente selon si
	// categorie est select
	public List<ArticleVendu> methodedeRechercheVente(List<ArticleVendu> param, String rechercheNom,
			Categorie categorie, Utilisateur utilisateur, String optionArticle, String ventesEnCours,
			String ventesTerminees, String ventesNonDebutees) {
		datedujour = LocalDate.now();
		List<ArticleVendu> newList = new ArrayList<>();
		if (ventesEnCours != null && ventesEnCours.equals("venteencours")) {
			filterArticlesEnCours(param, newList);
		}
		if (ventesTerminees != null && ventesTerminees.equals("ventesterminees")) {
			filterArticlesTerminees(param, newList);
		}
		if (ventesNonDebutees != null && ventesNonDebutees.equals("ventesnondebutees")) {
			filterArticlesNonDebutees(param, newList);
		}
		if (ventesNonDebutees == null && ventesEnCours == null && ventesTerminees == null) {
			filtrerAllArticle(param, newList);
		}
		return newList;
	}

	private List<ArticleVendu> filtrerAllArticle(List<ArticleVendu> param, List<ArticleVendu> newList) {
		for (ArticleVendu article : param) {
			newList.add(article);
		}
		return newList;
	}

	private List<ArticleVendu> filterArticlesEnCours(List<ArticleVendu> articles, List<ArticleVendu> newList) {
		for (ArticleVendu article : articles) {
			if (article.getDate_debut_encheres().isBefore(datedujour)
					&& article.getDate_fin_encheres().isAfter(datedujour)) {
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
		System.out.println(listEncheres);
		List<ArticleVendu> listeArticleAchete = new ArrayList<>();
		for (Enchere enchere : listEncheres) {
			listeArticleAchete.add(enchere.getArticle());
			System.out.println(enchere.getArticle());
		}
		System.out.println(listeArticleAchete);
		return listeArticleAchete;
	}

}
