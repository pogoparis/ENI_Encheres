package fr.eni.encheres.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dao.EncheresDaoEncheres;

@Service
public class EncheresServiceEncheresImpl implements EncheresServiceEncheres {
	
	LocalDate datedujour;
	EncheresDaoEncheres enchereDaoEncheres;
	EncheresServiceUtilisateur encheresServiceUtilisateur;
	EncheresServiceArticlesVendus encheresServiceArticlesVendus;

	
	public EncheresServiceEncheresImpl(EncheresDaoEncheres enchereDaoEncheres,
			EncheresServiceUtilisateur encheresServiceUtilisateur,
			EncheresServiceArticlesVendus encheresServiceArticlesVendus) {
		this.enchereDaoEncheres = enchereDaoEncheres;
		this.encheresServiceUtilisateur = encheresServiceUtilisateur;
		this.encheresServiceArticlesVendus = encheresServiceArticlesVendus;
	}

	public Boolean affichageDuBouton(ArticleVendu article, Principal user) {
		datedujour = LocalDate.now();
		Boolean verifVendeur = verificationVendeur(article, user);
		Boolean verifDate = verificationDates(article);
		Boolean verifDernierEncherisseur = verificationDernierEncherisseur(article, user);
		Boolean verifSoldeSuffisant = verificationSoldeSuffisant(article, user);
		if (verifVendeur && verifDate && verifDernierEncherisseur && verifSoldeSuffisant) {
			return true;
		}
		return false;
	}

	private Boolean verificationSoldeSuffisant(ArticleVendu article, Principal user) {
		if (user == null) {
			return false;
		}
		if (article.getPrix_vente() >= encheresServiceUtilisateur.findUserByPseudo(user.getName()).getCredit()) {
			return false;
		}
		return true;
	}

	private Boolean verificationDernierEncherisseur(ArticleVendu article, Principal user) {
		
		if (getMeilleureEnchereByArticle(article) == null) {
			return true;
		}
		Utilisateur meilleurencherisseur = getMeilleureEnchereByArticle(article).getUtilisateur();
		if (user == null){
			return false;
		}
		Utilisateur utilisateuractuel = encheresServiceUtilisateur.findUserByPseudo(user.getName());
		if (meilleurencherisseur.getNo_utilisateur() == utilisateuractuel.getNo_utilisateur()) {
			return false;
		}
		return true;
	}

	private Boolean verificationDates(ArticleVendu article) {
		if (datedujour.isEqual(article.getDate_debut_encheres())) {
			return true;
		}
		if (datedujour.isEqual(article.getDate_fin_encheres())) {
			return true;
		}
		if ((datedujour.isAfter(article.getDate_debut_encheres()) && (datedujour.isBefore(article.getDate_fin_encheres())))) {
			return true;
		}
		return false;
	}

	private Boolean verificationVendeur(ArticleVendu article, Principal user) {
		if (user == null) {
			return false;
		}
		if (article.getUtilisateur().getNo_utilisateur() == encheresServiceUtilisateur.findUserByPseudo(user.getName()).getNo_utilisateur()){
			return false;
			}
		return true;
	}
	
	@Override
	public void creationEncheres(Enchere enchere) {
		datedujour = LocalDate.now();
		enchere.setDate_enchere(datedujour);
		enchereDaoEncheres.saveEnchere(enchere);
	}
	
	public List<Enchere> getEncheresByArticle (ArticleVendu articleVendu){
		return enchereDaoEncheres.findEncheresByArticle(articleVendu);
	}
	
	public Enchere getMeilleureEnchereByArticle (ArticleVendu article) {
		List<Enchere> liste = getEncheresByArticle(article);
		 if (liste.isEmpty()) {
		        return null;
		    }
		    Enchere plusHauteEnchere = liste.get(0);
		    for (Enchere enchere : liste) {
		        if (enchere.getMontant_enchere() > plusHauteEnchere.getMontant_enchere()) {
		        	plusHauteEnchere = enchere;
		        }
		    }
		    return plusHauteEnchere;
	}

	@Override
	public List<Enchere> getEncheresByUser(Utilisateur utilisateur) {
		return enchereDaoEncheres.getEncheresByUser(utilisateur);
	}

	@Override
	public void surencherir(Enchere enchere) {
		Enchere ancienneMeilleureEnchere = getMeilleureEnchereByArticle(enchere.getArticle());
		encheresServiceUtilisateur.remboursementDernierEncherisseur(ancienneMeilleureEnchere);
		creationEncheres(enchere);
		encheresServiceArticlesVendus.majPrixArticle(enchere);
		encheresServiceUtilisateur.majCreditUtilisateur(enchere);
	}



	

	
	

}
