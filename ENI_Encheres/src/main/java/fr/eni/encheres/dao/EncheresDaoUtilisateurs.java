package fr.eni.encheres.dao;

import java.util.List;


import fr.eni.encheres.bo.Utilisateur;

public interface EncheresDaoUtilisateurs {
	
	List<Utilisateur> getAllUtilisateurs();
	
	Utilisateur getUtilisateurById(Integer idUtilisateur);
	
	public Utilisateur getUserByPseudo(String pseudo);

}
