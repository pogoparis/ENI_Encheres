package fr.eni.encheres.dao;

import java.util.List;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

public interface EncheresDaoUtilisateurs {
	
	List<Utilisateur> getAllUtilisateurs();
	
	Utilisateur getUtilisateurById(Integer idUtilisateur);
	
	public Utilisateur getUserByPseudo(String pseudo);

	void saveUtilisateur(Utilisateur utilisateur);

	void majCreditUtilisateur(Enchere enchere);
	void delete(Integer id);

	void remboursementCreditUtilisateur(Enchere ancienneMeilleureEnchere);

}
