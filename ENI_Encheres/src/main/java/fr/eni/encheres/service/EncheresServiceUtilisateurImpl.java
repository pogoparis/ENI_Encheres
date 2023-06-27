package fr.eni.encheres.service;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dao.EncheresDaoUtilisateurs;

public class EncheresServiceUtilisateurImpl implements EncheresServiceUtilisateur{

	EncheresDaoUtilisateurs encheresDaoUtilisateurs;

	public EncheresServiceUtilisateurImpl(EncheresDaoUtilisateurs encheresDaoUtilisateurs) {
		this.encheresDaoUtilisateurs=encheresDaoUtilisateurs;
	}

	@Override
	public List<Utilisateur> findAllUtilisateurs() {
		// TODO Auto-generated method stub
		return encheresDaoUtilisateurs.getAllUtilisateurs();
	}
	
	@Override
	public void createUtilisateur(Utilisateur utilisateur) {
		encheresDaoUtilisateurs.createUtilisateur(utilisateur);
		
	}

	@Override
	public Utilisateur findUtilisateurById(Integer id) {
		// TODO Auto-generated method stub
		return encheresDaoUtilisateurs.getUtilisateurById(id);
	}



}
