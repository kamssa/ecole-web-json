package ci.kossovo.ecole.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.kossovo.ecole.entites.Administrateur;
import ci.kossovo.ecole.entites.Adresse;
import ci.kossovo.ecole.entites.Enseignant;
import ci.kossovo.ecole.entites.Etudiant;
import ci.kossovo.ecole.entites.Personne;
import ci.kossovo.ecole.entites.User;
import ci.kossovo.ecole.exceptions.InvalidPersonneException;
import ci.kossovo.ecole.web.models.Reponse;
import ci.kossovo.ecole.web.models.personne.PersonneModel;
import ci.kossovo.ecole.web.models.personne.PostAjoutPersonne;
import ci.kossovo.ecole.web.models.personne.PostModifierPersonne;
import ci.kossovo.ecole.web.utilitaire.Static;

@RestController
@CrossOrigin
public class PersonneController {
	@Autowired
	private PersonneModel personneModel;

	@Autowired
	private ObjectMapper jsonMapper;

	private Reponse<Personne> getPersonneById(Long id) {
		Personne personne = null;
		try {
			personne = personneModel.chercherParId(id);
		} catch (RuntimeException e) {
			new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		if (personne == null) {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("la personne n'exixte pas", id));
			return new Reponse<Personne>(2, messages, null);
		}
		return new Reponse<Personne>(0, null, personne);
	}

	@PostMapping("/personnes")
	public String creer(@RequestBody PostAjoutPersonne p) throws JsonProcessingException {
		Reponse<Personne> reponse;
		Personne entity = new Personne(p.getTitre(), p.getCni(), p.getNom(), p.getPrenom());
		Adresse adr = new Adresse(p.getCodepostal(), p.getVille(), p.getEmail());
		entity.setAdresse(adr);
		try {
			reponse = new Reponse<Personne>(0, null, personneModel.creer(entity));
		} catch (InvalidPersonneException e) {
			// TODO Auto-generated catch block
			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@PutMapping("/personnes")
	public String modifier(@RequestBody PostModifierPersonne modif) throws JsonProcessingException {
		Reponse<Personne> reponsePersModif;
		Reponse<Personne> reponse;

		// on recupere la personnae a modifier

		reponsePersModif = getPersonneById(modif.getId());
		if (reponsePersModif.getStatut() != 0) {
			reponse = new Reponse<Personne>(reponsePersModif.getStatut(), reponsePersModif.getMessages(), null);

		}

		Personne entity = null;
		entity = reponsePersModif.getBody();
		entity.setTitre(modif.getTitre());
		entity.setNom(modif.getNom());
		entity.setPrenom(modif.getPrenom());
		entity.setCni(modif.getCni());
		Adresse adr = new Adresse(modif.getCodepostal(), modif.getEmail(), modif.getVille());
		entity.setAdresse(adr);
		try {
			reponse = new Reponse<Personne>(0, null, personneModel.modifier(entity));

		} catch (InvalidPersonneException e) {
			// TODO Auto-generated catch block
			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	@GetMapping("/personnes")
	public String findAll() throws JsonProcessingException {
		Reponse<List<Personne>> reponse;

		try {
			List<Personne> personneTous = personneModel.findAll();
			if (!personneTous.isEmpty()) {
				reponse = new Reponse<List<Personne>>(0, null, personneTous);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de personnes enregistrées");
				reponse = new Reponse<List<Personne>>(1, messages, null);
			}

		} catch (Exception e) {

			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@GetMapping("/personnes/{id}")
	public String chercherParId(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Personne> reponsePersId = null;
		Reponse<Personne> reponse = null;
		boolean erreur = false;

		if (!erreur) {
			reponsePersId = getPersonneById(id);
			if (reponsePersId.getStatut() != 0) {
				reponse = new Reponse<Personne>(reponsePersId.getStatut(), reponsePersId.getMessages(), null);
				erreur = true;
			}
		}
		if (!erreur) {

			try {
				reponse = new Reponse<Personne>(0, null, reponsePersId.getBody());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
			}

		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@DeleteMapping("/personnes/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Void> reponse = null;
		boolean erreur = false;
		if (!erreur) {
			Reponse<Personne> responseSup = getPersonneById(id);
			if (responseSup.getStatut() != 0) {
				reponse = new Reponse<>(responseSup.getStatut(), responseSup.getMessages(), null);
				erreur = true;

			}
		}
		if (!erreur) {
			// suppression
			try {
				personneModel.supprimer(id);
				List<String> messages = new ArrayList<>();
				messages.add("personne supprime");
				reponse = new Reponse<Void>(0, messages, null);
			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	List<Etudiant> chercherParMartricule(String matricule) {
		return personneModel.chercherParMartricule(matricule);
	}

	public List<Enseignant> chercherParStatut(String statut) {
		return personneModel.chercherParStatut(statut);
	}

	public List<Administrateur> chercherParFonction(String fonction) {
		return personneModel.chercherParFonction(fonction);
	}

	public List<Etudiant> chercherEtudiantParMc(String mc) {
		return personneModel.chercherEtudiantParMc(mc);
	}

	public List<Enseignant> chercherEnseignantParMc(String mc) {
		return personneModel.chercherEnseignantParMc(mc);
	}

	public List<User> chercherUserParMc(String mc) {
		return personneModel.chercherUserParMc(mc);
	}

	public List<Etudiant> listeEtudiants() {
		return personneModel.listeEtudiants();
	}

	public List<User> listeUsers() {
		return personneModel.listeUsers();
	}

	public List<Enseignant> listeEnserignant() {
		return personneModel.listeEnserignant();
	}

}
