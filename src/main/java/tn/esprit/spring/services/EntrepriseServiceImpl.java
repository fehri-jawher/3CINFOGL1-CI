package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {
	
	private static final Logger l = LogManager.getLogger(EntrepriseServiceImpl.class);

	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	
	public Entreprise ajouterEntreprise(Entreprise entreprise) {
		try{
			entrepriseRepoistory.save(entreprise);
			l.info("Entreprise added successfully");
			return entreprise;	
		}catch (Exception e){
			l.error("Error : " + e.getMessage());
		}
		
		return new Entreprise();
	}

	public int ajouterDepartement(Departement dep) {
		try{
			deptRepoistory.save(dep);
			l.info("Deapartment added successfully");
			return dep.getId();	
		}catch (Exception e){
			l.error("Error : " + e.getMessage());
		}
		
		return 0;
	}
	
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		//Le bout Master de cette relation N:1 est departement  
				//donc il faut rajouter l'entreprise a departement 
				// ==> c'est l'objet departement(le master) qui va mettre a jour l'association
				//Rappel : la classe qui contient mappedBy represente le bout Slave
				//Rappel : Dans une relation oneToMany le mappedBy doit etre du cote one.
		
				try{
					Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
					Departement depManagedEntity = deptRepoistory.findById(depId).get();
					depManagedEntity.setEntreprise(entrepriseManagedEntity);
					deptRepoistory.save(depManagedEntity);
					l.info("Deapartment affected successfully");
				}catch(Exception e){
					l.error("Error : " + e.getMessage());
				}
		
	}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		try{
			Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
			List<String> depNames = new ArrayList<>();
			for(Departement dep : entrepriseManagedEntity.getDepartements()){
				depNames.add(dep.getName());
			}
			l.info("Deapartment list is returned successfully");
			return depNames;
		}catch(Exception e){
			l.error("Error : " + e.getMessage());
		}
		
		return null;
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		try {
			entrepriseRepoistory.delete(entrepriseRepoistory.findById(entrepriseId).get());
			l.info("Entreprise deleted successfully");
		}catch(Exception e){
			l.error("Error : " + e.getMessage());
		}
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		try {
			deptRepoistory.delete(deptRepoistory.findById(depId).get());
			l.info("Department deleted successfully");
		}catch(Exception e){
			l.error("Error : " + e.getMessage());
		}
	}


	public Entreprise getEntrepriseById(int entrepriseId) {
		try{
			return entrepriseRepoistory.findById(entrepriseId).get();
		}catch(Exception e){
			l.error("Error : " + e.getMessage());
		}
		
		return null;
	}

}
