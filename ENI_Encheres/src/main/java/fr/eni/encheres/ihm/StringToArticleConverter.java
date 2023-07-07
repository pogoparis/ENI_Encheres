package fr.eni.encheres.ihm;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.service.EncheresServiceArticlesVendus;

@Component
public class StringToArticleConverter implements Converter<String, ArticleVendu> {

	EncheresServiceArticlesVendus encheresServiceArticlesVendus;

	public StringToArticleConverter(EncheresServiceArticlesVendus encheresServiceArticlesVendus) {
		this.encheresServiceArticlesVendus = encheresServiceArticlesVendus;
	}

	@Override
	public ArticleVendu convert(String source) {

		return encheresServiceArticlesVendus.findArticleById(Integer.parseInt(source));
	}

}
