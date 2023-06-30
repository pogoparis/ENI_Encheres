package fr.eni.encheres.ihm;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.service.EncheresServiceUtilisateur;

@Component
public class StringToUtilisateurConverter implements Converter<String, Utilisateur> {

	EncheresServiceUtilisateur encheresServiceUtilisateur;
	
	
	
	public StringToUtilisateurConverter(EncheresServiceUtilisateur encheresServiceUtilisateur) {
		this.encheresServiceUtilisateur = encheresServiceUtilisateur;
	}



	@Override
	public Utilisateur convert(String source) {
		
		return encheresServiceUtilisateur.findUtilisateurById(Integer.parseInt(source));
	}

}
