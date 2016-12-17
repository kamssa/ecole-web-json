package ci.kossovo.ecole.web.models.personne;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ci.kossovo.ecole.entites.Administrateur;
import ci.kossovo.ecole.entites.Enseignant;
import ci.kossovo.ecole.entites.Etudiant;
import ci.kossovo.ecole.entites.Personne;
import ci.kossovo.ecole.entites.User;
import ci.kossovo.ecole.exceptions.InvalidPersonneException;
import ci.kossovo.ecole.metier.IPersonneMetier;

@Component
public class PersonneModel implements IPersonneMetier {
@Autowired
private IPersonneMetier personneMetier;

@Override
public Personne creer(Personne entity) throws InvalidPersonneException {
	// TODO Auto-generated method stub
	return personneMetier.creer(entity);
}

@Override
public Personne modifier(Personne entity) throws InvalidPersonneException {
	// TODO Auto-generated method stub
	return personneMetier.modifier(entity);
}

@Override
public List<Personne> findAll() {
	// TODO Auto-generated method stub
	return personneMetier.findAll();
}

@Override
public Personne chercherParId(Long id) {
	// TODO Auto-generated method stub
	return personneMetier.chercherParId(id);
}


@Override
public List<Etudiant> chercherParMartricule(String matricule) {
	// TODO Auto-generated method stub
	return personneMetier.chercherParMartricule(matricule);
}

@Override
public List<Enseignant> chercherParStatut(String statut) {
	// TODO Auto-generated method stub
	return personneMetier.chercherParStatut(statut);
}

@Override
public List<Administrateur> chercherParFonction(String fonction) {
	// TODO Auto-generated method stub
	return personneMetier.chercherParFonction(fonction);
}

@Override
public List<Etudiant> chercherEtudiantParMc(String mc) {
	// TODO Auto-generated method stub
	return personneMetier.chercherEtudiantParMc(mc);
}

@Override
public List<Enseignant> chercherEnseignantParMc(String mc) {
	// TODO Auto-generated method stub
	return personneMetier.chercherEnseignantParMc(mc);
}

@Override
public List<User> chercherUserParMc(String mc) {
	// TODO Auto-generated method stub
	return personneMetier.chercherUserParMc(mc);
}

@Override
public List<Etudiant> listeEtudiants() {
	// TODO Auto-generated method stub
	return personneMetier.listeEtudiants();
}

@Override
public List<User> listeUsers() {
	// TODO Auto-generated method stub
	return personneMetier.listeUsers();
}

@Override
public List<Enseignant> listeEnserignant() {
	// TODO Auto-generated method stub
	return personneMetier.listeEnserignant();
}

@Override
public void supprimer(Long id) {
	// TODO Auto-generated method stub
	personneMetier.supprimer(id);
}
}
