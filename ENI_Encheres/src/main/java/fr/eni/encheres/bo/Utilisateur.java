package fr.eni.encheres.bo;

import java.util.List;

public class Utilisateur {
	
	private Integer no_Utilisateur;
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone; //A voir si on le passe en chiffre pour le manipuler
	private String rue;
	private String codePostal;
	private String ville;
	private String motDePasse;
	private Integer credit;
	private Boolean administrateur; //true si l'utilisateur est admin, false dans le cas contraire
	
	private List<ArticleVendu> listeArticlesAchetes;
	private List<ArticleVendu> listeArticlesVendus;
	
	private List<Enchere> listeEncheres;
	
	
	
	public Utilisateur() {
	}
	

	
	public Integer getNo_Utilisateur() {
		return no_Utilisateur;
	}

	public void setNo_Utilisateur(Integer no_Utilisateur) {
		this.no_Utilisateur = no_Utilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getRue() {
		return rue;
	}
	
	public void setRue(String rue) {
		this.rue = rue;
	}
	
	public String getCodePostal() {
		return codePostal;
	}
	
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	
	public String getVille() {
		return ville;
	}
	
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	public Integer getCredit() {
		return credit;
	}
	
	public void setCredit(Integer credit) {
		this.credit = credit;
	}
	
	public Boolean getAdministrateur() {
		return administrateur;
	}
	
	public void setAdministrateur(Boolean administrateur) {
		this.administrateur = administrateur;
	}



	public List<ArticleVendu> getListeArticlesAchetes() {
		return listeArticlesAchetes;
	}



	public void setListeArticlesAchetes(List<ArticleVendu> listeArticlesAchetes) {
		this.listeArticlesAchetes = listeArticlesAchetes;
	}



	public List<ArticleVendu> getListeArticlesVendus() {
		return listeArticlesVendus;
	}



	public void setListeArticlesVendus(List<ArticleVendu> listeArticlesVendus) {
		this.listeArticlesVendus = listeArticlesVendus;
	}



	public List<Enchere> getListeEncheres() {
		return listeEncheres;
	}



	public void setListeEncheres(List<Enchere> listeEncheres) {
		this.listeEncheres = listeEncheres;
	}
	
	
	
	

}
