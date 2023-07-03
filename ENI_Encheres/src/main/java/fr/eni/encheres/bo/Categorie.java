package fr.eni.encheres.bo;

public class Categorie {

	private Integer no_categorie;
	private String libelle;
	
	
	//CONSTRUCTEUR
//	public Categorie(Integer noCategorie, String libelle) {
//		this.no_categorie = noCategorie;
//		this.libelle = libelle;
//	}

	public Categorie() {
		// Constructeur vide
	}
	
	public Categorie(Integer idCategorie) {
		this.no_categorie = idCategorie;
	}

	//GETTER SETTER
	public Integer getNo_categorie() {
		return no_categorie;
	}

	public void setNo_categorie(Integer no_categorie) {
		this.no_categorie = no_categorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		return "N°" + no_categorie + " : " + libelle;
	}
	
	
	
	
	
}
