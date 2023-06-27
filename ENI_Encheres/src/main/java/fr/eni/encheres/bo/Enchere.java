package fr.eni.encheres.bo;

import java.time.LocalDate;

public class Enchere {

	private LocalDate date_enchere ;
	private Integer montant_enchere;
	private Utilisateur utilisateur;
	private ArticleVendu article;
	
	
	// COnstructeur
	public Enchere() {
		// Pour Spring
	}


	public Enchere(LocalDate date_enchere, Integer montant_enchere, Utilisateur utilisateur, ArticleVendu article) {
		super();
		this.date_enchere = date_enchere;
		this.montant_enchere = montant_enchere;
		this.utilisateur = utilisateur;
		this.article = article;
	}

	
	// GETTER SETTER
	public LocalDate getDate_enchere() {
		return date_enchere;
	}

	public void setDate_enchere(LocalDate date_enchere) {
		this.date_enchere = date_enchere;
	}

	public Integer getMontant_enchere() {
		return montant_enchere;
	}

	public void setMontant_enchere(Integer montant_enchere) {
		this.montant_enchere = montant_enchere;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public ArticleVendu getArticle() {
		return article;
	}

	public void setArticle(ArticleVendu article) {
		this.article = article;
	}


	@Override
	public String toString() {
		return "Enchere [date_enchere=" + date_enchere + ", montant_enchere=" + montant_enchere + ", utilisateur="
				+ utilisateur + ", article=" + article + "]";
	}
	
	
	
}
