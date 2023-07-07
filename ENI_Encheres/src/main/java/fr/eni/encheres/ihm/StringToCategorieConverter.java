package fr.eni.encheres.ihm;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.service.EncheresServiceCategorie;

@Component
public class StringToCategorieConverter implements Converter<String, Categorie> {

	EncheresServiceCategorie encheresServiceCategorie;

	public StringToCategorieConverter(EncheresServiceCategorie encheresServiceCategorie) {
		this.encheresServiceCategorie = encheresServiceCategorie;
	}

	@Override
	public Categorie convert(String source) {

		return encheresServiceCategorie.findCategorieById(Integer.parseInt(source));
	}

}
