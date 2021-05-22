package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	
	
	private static final Logger l = LogManager.getLogger(EmployeServiceImpl.class);

	public int ajouterEmploye(Employe employe) {
		try {
			employeRepository.save(employe);
			l.info("employe added successfuly");
			return employe.getId();
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}
		
		return 0;
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		try {
			Employe employe = employeRepository.findById(employeId).get();
			employe.setEmail(email);
			employeRepository.save(employe);
			l.info("employe updated successfuly");
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}

	}

	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		try {
			Departement depManagedEntity = deptRepoistory.findById(depId).get();
			Employe employeManagedEntity = employeRepository.findById(employeId).get();

			if(depManagedEntity.getEmployes() == null){

				List<Employe> employes = new ArrayList<>();
				employes.add(employeManagedEntity);
				depManagedEntity.setEmployes(employes);
			}else{

				depManagedEntity.getEmployes().add(employeManagedEntity);

			}
			l.info("Affecter Employe to Departement successfuly");
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}

	}
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		try {
			Departement dep = deptRepoistory.findById(depId).get();

			int employeNb = dep.getEmployes().size();
			for(int index = 0; index < employeNb; index++){
				if(dep.getEmployes().get(index).getId() == employeId){
					dep.getEmployes().remove(index);
					break;//a revoir
				}
			}
			l.info("disaffecting Employe from Departement successfuly");
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}
		
	}

	public int ajouterContrat(Contrat contrat) {
		try {
			contratRepoistory.save(contrat);
			l.info("Contrat added successfuly");

			return contrat.getReference();
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}
		
		return 0;
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		try {

			Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
			Employe employeManagedEntity = employeRepository.findById(employeId).get();

			contratManagedEntity.setEmploye(employeManagedEntity);
			contratRepoistory.save(contratManagedEntity);
			l.info("Affecter Contrat to Employe successfuly");
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}
		
	}

	public String getEmployePrenomById(int employeId) {
		try {
			Employe employeManagedEntity = employeRepository.findById(employeId).get();
			l.info("get Employe Prenom by Id:"+employeId);
			
			return employeManagedEntity.getPrenom();
			
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}
		return "";
	}
	public void deleteEmployeById(int employeId)
	{
		try {
			Employe employe = employeRepository.findById(employeId).get();

			//Desaffecter l'employe de tous les departements
			//c'est le bout master qui permet de mettre a jour
			//la table d'association
			for(Departement dep : employe.getDepartements()){
				dep.getEmployes().remove(employe);
			}

			employeRepository.delete(employe);
			l.info("Employe with id:"+employeId+"deleted successfuly");
						
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}
	}

	public void deleteContratById(int contratId) {
		
		try {
			Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
			contratRepoistory.delete(contratManagedEntity);
			l.info("Contrat with id:"+contratId+"deleted successfuly");
						
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}

	}

	public int getNombreEmployeJPQL() {
		try {
			int countemp = employeRepository.countemp();
			l.info("countemp : "+countemp);
			
			return countemp;
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}
		return 0;
	}
	
	public List<String> getAllEmployeNamesJPQL() {
		
		try {
			l.info("getAllEmployeNamesJPQL ");
			return employeRepository.employeNames();
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}

		return null;
	}
	
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		try {
			l.info("getAllEmployeByEntreprise ");
			return employeRepository.getAllEmployeByEntreprisec(entreprise);
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}

		return null;
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		try {
			l.info("mettreAjourEmailByEmployeIdJPQL ");
			employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}

	}
	public void deleteAllContratJPQL() {
		try {
			l.info("deleteAllContratJPQL ");
	         employeRepository.deleteAllContratJPQL();
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}
	}
	
	public float getSalaireByEmployeIdJPQL(int employeId) {
		try {
			l.info("getSalaireByEmployeIdJPQL ");
			return employeRepository.getSalaireByEmployeIdJPQL(employeId);
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}
		
		return 0;
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		try {
			l.info("getSalaireByEmployeIdJPQL ");
			return employeRepository.getSalaireMoyenByDepartementId(departementId);
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}
		
		return null;
	}
	
	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		try {
			l.info("getTimesheetsByMissionAndDate ");
			return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}
		
		return null;
	}

	public List<Employe> getAllEmployes() {
		try {
			l.info("getAllEmployes ");
			return (List<Employe>) employeRepository.findAll();
		} catch(Exception e) {
			l.error("Error : " + e.getMessage());
		}
		
		return null;
	}

}
