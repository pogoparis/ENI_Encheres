package fr.eni.encheres.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import fr.eni.encheres.dao.EncheresDaoUtilisateurs;

public class UtilisateursDetailServiceCustom implements UserDetailsService {

	private EncheresDaoUtilisateurs encheresDaoUtilisateurs;

	public UtilisateursDetailServiceCustom(EncheresDaoUtilisateurs encheresDaoUtilisateurs) {
		this.encheresDaoUtilisateurs = encheresDaoUtilisateurs;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return encheresDaoUtilisateurs.getUserByPseudo(username);
	}

}
