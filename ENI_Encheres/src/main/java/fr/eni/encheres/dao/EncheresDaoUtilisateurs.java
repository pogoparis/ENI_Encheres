package fr.eni.encheres.dao;

import java.security.Principal;
import java.util.List;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

public interface EncheresDaoUtilisateurs {
	
	List<Utilisateur> getAllUtilisateurs();
	
	Utilisateur getUtilisateurById(Integer idUtilisateur);
	
	public Utilisateur getUserByPseudo(String pseudo);

	void saveUtilisateur(Utilisateur utilisateur);


	void delete(Integer id);

	
	public boolean isPseudoUnique(String pseudo);
	
	public boolean isMailUnique(String email);

	void majCredit(int valeur, Utilisateur utilisateur);

}
