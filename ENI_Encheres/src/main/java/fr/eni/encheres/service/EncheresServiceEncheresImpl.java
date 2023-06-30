package fr.eni.encheres.service;

import java.security.Principal;
import java.time.LocalDate;

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

	public EncheresServiceEncheresImpl(EncheresServiceUtilisateur encheresServiceUtilisateur, EncheresDaoEncheres enchereDaoEncheres) {
		this.enchereDaoEncheres = enchereDaoEncheres;
		this.encheresServiceUtilisateur = encheresServiceUtilisateur;
	}

	public Boolean affichageDuBouton(ArticleVendu article, Principal user) {
		datedujour = LocalDate.now();
		Boolean verifVendeur = verificationVendeur(article, user);
		Boolean verifDate = verificationDates(article);
		if (verifVendeur && verifDate) {
			return true;
		}
		return false;
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
		enchereDaoEncheres.saveEnchere(enchere);
	}



	

	
	

}
