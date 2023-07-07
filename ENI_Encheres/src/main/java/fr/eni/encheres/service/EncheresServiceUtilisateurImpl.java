package fr.eni.encheres.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dao.EncheresDaoArticlesVendus;
import fr.eni.encheres.dao.EncheresDaoEncheres;
import fr.eni.encheres.dao.EncheresDaoUtilisateurs;

@Service
public class EncheresServiceUtilisateurImpl implements EncheresServiceUtilisateur {

	EncheresDaoUtilisateurs encheresDaoUtilisateurs;
	EncheresDaoEncheres encheresDaoEncheres;
	EncheresDaoArticlesVendus encheresDaoArticlesVendus;
	PasswordEncoder passwordEncoder;

	public EncheresServiceUtilisateurImpl(EncheresDaoUtilisateurs encheresDaoUtilisateurs,
			EncheresDaoEncheres encheresDaoEncheres, EncheresDaoArticlesVendus encheresDaoArticlesVendus,
			PasswordEncoder passwordEncoder) {
		this.encheresDaoUtilisateurs = encheresDaoUtilisateurs;
		this.encheresDaoEncheres = encheresDaoEncheres;
		this.encheresDaoArticlesVendus = encheresDaoArticlesVendus;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<Utilisateur> findAllUtilisateurs() {
		return encheresDaoUtilisateurs.getAllUtilisateurs();
	}

	@Override
	public void createUtilisateur(Utilisateur utilisateur) {
		if(utilisateur.getMotDePasse() != null) {
		utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
		}
		encheresDaoUtilisateurs.saveUtilisateur(utilisateur);
	}

	@Override
	public Utilisateur findUtilisateurById(Integer id) {
		Utilisateur utilisateur = encheresDaoUtilisateurs.getUtilisateurById(id);
		miseEnPlaceDesListes(utilisateur);
		return utilisateur;
	}

	public Utilisateur findUserByPseudo(String pseudo) {
		Utilisateur utilisateur = encheresDaoUtilisateurs.getUserByPseudo(pseudo);
		miseEnPlaceDesListes(utilisateur);
		return utilisateur;
	}

	public void deleteUser(Integer id) {
		encheresDaoUtilisateurs.delete(id);

	}

	public Boolean isPseudoUnique(String pseudo) {
		return encheresDaoUtilisateurs.isPseudoUnique(pseudo);
	}

	public Boolean isMailUnique(String email) {
		return encheresDaoUtilisateurs.isMailUnique(email);
	}

	public void miseEnPlaceDesListes(Utilisateur utilisateur) {

		List<ArticleVendu> listeArticlesVendus = encheresDaoArticlesVendus.getArticlesByUser(utilisateur);
		utilisateur.setListeArticlesVendus(listeArticlesVendus);

		List<Enchere> listeEncheres = new ArrayList<>();
		for (Enchere enchere : encheresDaoEncheres.getEncheresByUser(utilisateur)) {
			if (enchere.getArticle().getDate_fin_encheres().isAfter(LocalDate.now())
					|| enchere.getArticle().getDate_fin_encheres().isEqual(LocalDate.now())) {
				listeEncheres.add(enchere);
			}
		}
		utilisateur.setListeEncheres(listeEncheres);

		List<ArticleVendu> listeArticlesAchetes = new ArrayList<>();
		for (Enchere enchere : encheresDaoEncheres.getEncheresByUser(utilisateur)) {
			if ((enchere.getArticle().getDate_fin_encheres().isBefore(LocalDate.now()))
					&& (isMeilleurEncherisseur(utilisateur, enchere.getArticle()))) {
				listeArticlesAchetes.add(enchere.getArticle());
			}
		}
		utilisateur.setListeArticlesAchetes(listeArticlesAchetes);
	}

	public Boolean isMeilleurEncherisseur(Utilisateur utilisateur, ArticleVendu article) {
		List<Enchere> listeDesEncheres = encheresDaoEncheres.findEncheresByArticle(article);
		if (listeDesEncheres.isEmpty()) {
			return false;
		}
		Enchere plusHauteEnchere = listeDesEncheres.get(0);
		for (Enchere enchere : listeDesEncheres) {
			if (enchere.getMontant_enchere() > plusHauteEnchere.getMontant_enchere()) {
				plusHauteEnchere = enchere;
			}
		}
		if (plusHauteEnchere.getUtilisateur().getNo_utilisateur() == utilisateur.getNo_utilisateur()) {
			return true;
		}
		return false;
	}

	@Override
	public void miseAJourCredit(int valeur, Utilisateur utilisateur) {
		encheresDaoUtilisateurs.majCredit(valeur, utilisateur);

	}

}
