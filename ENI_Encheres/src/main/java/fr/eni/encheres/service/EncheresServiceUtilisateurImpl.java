package fr.eni.encheres.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dao.EncheresDaoUtilisateurs;

@Service
public class EncheresServiceUtilisateurImpl implements EncheresServiceUtilisateur{

	EncheresDaoUtilisateurs encheresDaoUtilisateurs;
	PasswordEncoder passwordEncoder ;

	public EncheresServiceUtilisateurImpl(EncheresDaoUtilisateurs encheresDaoUtilisateurs,PasswordEncoder passwordEncoder) {
		this.encheresDaoUtilisateurs=encheresDaoUtilisateurs;
		this.passwordEncoder =passwordEncoder;
	}

	@Override
	public List<Utilisateur> findAllUtilisateurs() {
		// TODO Auto-generated method stub
		return encheresDaoUtilisateurs.getAllUtilisateurs();
	}
	
	@Override
	public void createUtilisateur(Utilisateur utilisateur) {
		System.out.println("SERVICE "+utilisateur);
		utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
		encheresDaoUtilisateurs.saveUtilisateur(utilisateur);
		
	}

	@Override
	public Utilisateur findUtilisateurById(Integer id) {
		// TODO Auto-generated method stub
		return encheresDaoUtilisateurs.getUtilisateurById(id);
	}
	
	public Utilisateur findUserByPseudo(String pseudo) {
		return encheresDaoUtilisateurs.getUserByPseudo(pseudo);
	}

	@Override
	public void majCreditUtilisateur(Enchere enchere) {
		encheresDaoUtilisateurs.majCreditUtilisateur(enchere);
		
	}

	@Override
	public void deleteUser(Integer id) {
		encheresDaoUtilisateurs.delete(id);
		
	}



}
