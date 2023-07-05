package fr.eni.encheres.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dao.EncheresDaoArticlesVendus;
import fr.eni.encheres.dao.EncheresDaoEncheres;

@Service
public class EncheresServiceEncheresImpl implements EncheresServiceEncheres {
	
	LocalDate datedujour;
	EncheresDaoEncheres enchereDaoEncheres;
	EncheresServiceUtilisateur encheresServiceUtilisateur;
	EncheresServiceArticlesVendus encheresServiceArticlesVendus;
	EncheresDaoArticlesVendus encheresDaoArticlesVendus;
//	private final TaskScheduler taskScheduler;

//	 private void verifierEncheresTerminees() {
//	        System.out.println("test");
//	    }
	
	//injecter au service TaskScheduler taskScheduler
	// et appeler la méthode planifierVerificationEncheresTerminees();
	public EncheresServiceEncheresImpl(EncheresDaoEncheres enchereDaoEncheres,
			EncheresServiceUtilisateur encheresServiceUtilisateur,
			EncheresServiceArticlesVendus encheresServiceArticlesVendus, EncheresDaoArticlesVendus encheresDaoArticlesVendus) {
		this.enchereDaoEncheres = enchereDaoEncheres;
		this.encheresServiceUtilisateur = encheresServiceUtilisateur;
		this.encheresServiceArticlesVendus = encheresServiceArticlesVendus;
		this.encheresDaoArticlesVendus = encheresDaoArticlesVendus;
	}

//	private void planifierVerificationEncheresTerminees() {
//        // Exemple de planification avec un délai fixe de 2 secondes
//        taskScheduler.schedule(this::verifierEncheresTerminees, new PeriodicTrigger(2, TimeUnit.SECONDS));
//    }
	
	public Boolean affichageDuBouton(ArticleVendu article, Principal user) {
		datedujour = LocalDate.now();
		if (article.isVenteTermine()) return false;
		Boolean verifVendeur = verificationVendeur(article, user);
		Boolean verifDate = verificationDatesEnchereEnCours(article);
		Boolean verifDernierEncherisseur = verificationDernierEncherisseur(article, user);
		Boolean verifSoldeSuffisant = verificationSoldeSuffisant(article, user);
		if (verifVendeur && verifDate && verifDernierEncherisseur && verifSoldeSuffisant) {
			return true;
		}
		return false;
	}
	
	public String messageBoutonEncherirIndisponible(ArticleVendu article, Principal user) {
		if (article.isVenteTermine()) return "Cette vente est terminée !";
		if (user == null) return "Connectez-vous pour enchérir sur cet article";
		if (!verificationVendeur(article, user)) return "Vous êtes le vendeur de cet article";
		if (!verificationDatesEnchereEnCours(article)) return "Cette enchère n'est pas en cours";
		if (!verificationDernierEncherisseur(article, user)) return "Vous avez déjà enchérit sur cet article !";
		if (!verificationSoldeSuffisant(article, user)) return "Vous n'avez pas assez de crédits pour enchérir sur ce produit";
		return null;
	}
	
	public Boolean affichageBoutonCloture(ArticleVendu article, Principal user) {
		if (article.isVenteTermine()) return false;
		Boolean verifMeilleurEncherisseur = verificationMeilleurEncherisseur(article, user);
		Boolean verifDate = verificationDatesEnchereTerminee(article);
		if (verifDate && verifMeilleurEncherisseur) {
			
			return true;
		}
		
		return false;
		
	}

	private Boolean verificationMeilleurEncherisseur(ArticleVendu article, Principal user) {
		if (user == null) return false;
		if (getMeilleureEnchereByArticle(article) == null) return false;
		if (encheresServiceUtilisateur.findUserByPseudo(user.getName()).getNo_utilisateur() == getMeilleureEnchereByArticle(article).getUtilisateur().getNo_utilisateur())
		{return true;}
		return false;
	}

	private Boolean verificationDatesEnchereTerminee(ArticleVendu article) {
		if (datedujour.isAfter(article.getDate_fin_encheres())) {
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

	private Boolean verificationDatesEnchereEnCours(ArticleVendu article) {
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
		if (ancienneMeilleureEnchere != null) {
			encheresServiceUtilisateur.miseAJourCredit(ancienneMeilleureEnchere.getMontant_enchere(), ancienneMeilleureEnchere.getUtilisateur());
		}
		creationEncheres(enchere);
		encheresServiceArticlesVendus.majPrixArticle(enchere);
		encheresServiceUtilisateur.miseAJourCredit(enchere.getMontant_enchere()*-1, enchere.getUtilisateur());
	}

	@Override
	public void conclureVente(ArticleVendu article) {
		encheresDaoArticlesVendus.miseAJourEtat(article);
		int gainVendeur = article.getPrix_vente();
		encheresServiceUtilisateur.miseAJourCredit(gainVendeur, article.getUtilisateur());
}



	
	

}
