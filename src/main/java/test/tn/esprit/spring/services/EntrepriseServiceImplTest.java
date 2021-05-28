package test.tn.esprit.spring.services;

import java.text.ParseException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.EntrepriseServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseServiceImplTest {
	
	@Autowired
	EntrepriseServiceImpl es;
	
	
	@Test
	public void testAddEntreprise() throws ParseException {
		Entreprise e = new Entreprise("Dream Tek", "Borj Cedria");
		Entreprise eAdded = es.ajouterEntreprise(e);
		Assert.assertEquals(e.getName(), eAdded.getName());
		
	}

}
