package fr.eni.encheres.bo;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class Utilisateur implements UserDetails{
	
	private Integer no_utilisateur;
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone; 
	private String rue;
	private String codePostal;
	private String ville;
	private String motDePasse;
	private Integer credit;
	private Boolean administrateur; //true si l'utilisateur est admin, false dans le cas contraire
	
	private List<ArticleVendu> listeArticlesAchetes;
	private List<ArticleVendu> listeArticlesVendus;
	
	private List<Enchere> listeEncheres;
	
	
	// constructeur vide
	public Utilisateur() {
	}
	

	public Utilisateur(Integer idutilisateur) {
		this.no_utilisateur=idutilisateur;
	}


	public Utilisateur(String username) {
		this.pseudo = username;
	}


	// getters and setters
	public Integer getNo_utilisateur() {
		return no_utilisateur;
	}

	public void setNo_utilisateur(Integer no_utilisateur) {
		this.no_utilisateur = no_utilisateur;
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


	@Override
	public String toString() {
		return "Utilisateur [no_Utilisateur=" + no_utilisateur + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom="
				+ prenom + ", email=" + email + ", telephone=" + telephone + ", rue=" + rue + ", codePostal="
				+ codePostal + ", ville=" + ville + ", motDePasse=" + motDePasse + ", credit=" + credit
				+ ", administrateur=" + administrateur + ", listeArticlesAchetes=" + listeArticlesAchetes
				+ ", listeArticlesVendus=" + listeArticlesVendus + ", listeEncheres=" + listeEncheres + "]";
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getPassword() {
		
		return getMotDePasse();
	}


	@Override
	public String getUsername() {

		return getPseudo();
	}


	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}	

}
