package fr.eni.encheres.bo;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ArticleVendu {


	//constantes etatVente
	private final static int CREEE = 0;
	private final static int EN_COURS = 1;
	private final static int ENCHERES_TERMINEES = 2;
	private final static int RETRAIT_EFFECTUE = 3;
	
	private Integer no_article;
	@NotBlank
	private String nom_article;
	
	@NotBlank
	private String description;
	
	private LocalDate date_debut_encheres;
	private LocalDate date_fin_encheres;
	
	@NotBlank
	@Min(0)
	private Integer prix_initial;
	
	@NotBlank
	@Min(0)
	private Integer prix_vente;
	
	private String etatVente;
	
	@NotBlank
	private Categorie categorie;
	
	private Utilisateur utilisateur;
	
	
	//Constructeur vide
	public ArticleVendu() {
		// TODO Auto-generated constructor stub
	}


	public ArticleVendu(String nom_article, String description, LocalDate date_debut_encheres,
			LocalDate date_fin_encheres, Integer prix_initial, Integer prix_vente, Utilisateur utilisateur,
			Categorie categorie) {
		this.nom_article = nom_article;
		this.description = description;
		this.date_debut_encheres = date_debut_encheres;
		this.date_fin_encheres = date_fin_encheres;
		this.prix_initial = prix_initial;
		this.prix_vente = prix_vente;
		this.utilisateur = utilisateur;
		this.categorie = categorie;
	}

	public ArticleVendu(Integer id) {
		this.no_article = id;
	}





	//Getter Setter
	public Integer getNo_article() {
		return no_article;
	}

	public void setNo_article(Integer no_article) {
		this.no_article = no_article;
	}

	public String getNom_article() {
		return nom_article;
	}

	public void setNom_article(String nom_article) {
		this.nom_article = nom_article;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate_debut_encheres() {
		return date_debut_encheres;
	}

	public void setDate_debut_encheres(LocalDate date_debut_encheres) {
		this.date_debut_encheres = date_debut_encheres;
	}

	public LocalDate getDate_fin_encheres() {
		return date_fin_encheres;
	}

	public void setDate_fin_encheres(LocalDate date_fin_encheres) {
		this.date_fin_encheres = date_fin_encheres;
	}

	public Integer getPrix_initial() {
		return prix_initial;
	}

	public void setPrix_initial(Integer prix_initial) {
		this.prix_initial = prix_initial;
	}

	public Integer getPrix_vente() {
		return prix_vente;
	}

	public void setPrix_vente(Integer prix_vente) {
		this.prix_vente = prix_vente;
	}

	public String getEtatVente() {
		return etatVente;
	}

	public void setEtatVente(String etatVente) {
		this.etatVente = etatVente;
	}
//Fin getter setter


	public Categorie getCategorie() {
		return categorie;
	}


	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}


	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Override
	public String toString() {
		return "ArticleVendu [no_article=" + no_article + ", nom_article=" + nom_article + ", description="
				+ description + ", date_debut_encheres=" + date_debut_encheres + ", date_fin_encheres="
				+ date_fin_encheres + ", prix_initial=" + prix_initial + ", prix_vente=" + prix_vente + ", etatVente="
				+ etatVente + ", categorie=" + categorie + ", utilisateur=" + utilisateur + "]";
	}

	
	
}
