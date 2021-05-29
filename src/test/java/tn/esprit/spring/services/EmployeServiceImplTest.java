package tn.esprit.spring.services;

import java.text.ParseException;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.services.EmployeServiceImpl;;
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeServiceImplTest {
	@Autowired
	EmployeServiceImpl empS; 

		
		
		@Test
		public void testAddEmploye() throws ParseException {
			Employe e = new Employe("Test","Ben Test", "test@test.test",true,Role.ADMINISTRATEUR);
			Employe eAdded = empS.ajouterEmploye(e);
			Assert.assertEquals(e.getEmail(), eAdded.getEmail());
		}

		/*@Test 
		public void testShowEmploye()
		{
			List<Employe> listEmploye = empS.getAllEmployes();
			Assert.assertEquals(1, listEmploye.size());
			
		}*/
}
