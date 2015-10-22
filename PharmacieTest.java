package drugware_v15;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class PharmacieTest {
	
	Pharmacie pharmacie;
	
	@Rule
	public TestName name = new TestName();
	
	@Before
	public void setUp(){
		System.out.println("Début du test - Pharmacie - " + name.getMethodName());
		pharmacie = new Pharmacie();
		pharmacie.ajouterClient("DANN12030405", "Danane", "Nawar");
	}
	
	@After
	public void tearDown() {
		System.out.println("Fin du test - Pharmacie - " + name.getMethodName());
	}

	@Test
	public void testPharmacie() {
		System.out.println("Vérifie que la pharmacie n'est pas nulle.");
		assertNotNull(pharmacie);
	}

	@Test
	public void testGetLesClients() {
		System.out.println("Vérifie si le programme retourne bien un client.");
		assertNotNull(pharmacie.getLesClients());
	}

	@Test
	public void testSetLesClients() {
		System.out.println("Vérifie si le programme arrive à modifier les clients de la pharmacie.");
		List<Client> lesClients = new ArrayList<>();
		lesClients.add(new Client("DANN12030405", "Danane", "Nawar"));
		pharmacie.setLesClients(lesClients);
		assertEquals(pharmacie.getLesClients(), lesClients);
	}

	@Test
	public void testGetLesMedicaments() {
		System.out.println("Vérifie si le programme retourne bien un médicament.");
		assertNotNull(pharmacie.getLesMedicaments());
	}

	@Test
	public void testSetLesMedicaments() {
		System.out.println("Vérifie si le programme arrive à modifier les médicaments de la pharmacie.");
		List<Medicament> lesMedicaments = new ArrayList<>();
		lesMedicaments.add(new Medicament());
		pharmacie.setLesMedicaments(lesMedicaments);
		assertEquals(pharmacie.getLesMedicaments(), lesMedicaments);
	}

	@Test
	public void testLireClients() {
		System.out.println("Simule une lecture de clients et vérifie si les données ont bien été ajoutées.");
		pharmacie.ajouterClient("ABCD12345678", "ABC", "DEF");
		pharmacie.ajouterClient("EFGH12345678", "GHI", "JKL");
		assertTrue(pharmacie.getLesClients().size() > 1);
	}

	@Test
	public void testLireMedicaments() {
		System.out.println("Simule une lecture de médicaments et vérifie si les données ont bien été ajoutées.");
		pharmacie.getLesMedicaments().add(new Medicament());
		pharmacie.getLesMedicaments().add(new Medicament());
		assertTrue(pharmacie.getLesMedicaments().size() > 1);
	}

	@Test
	public void testLirePrescriptions() {
		System.out.println("Simule une lecture de prescriptions et vérifie si les données ont bien été ajoutées.");
		pharmacie.getPrescriptionsClient("DANN12030405").add(new Prescription("mew", 50, 3));
		pharmacie.getPrescriptionsClient("DANN12030405").add(new Prescription("mewtwo", 50, 3));
		assertTrue(pharmacie.getPrescriptionsClient("DANN12030405").size() > 1);
	}

	@Test
	public void testSiClientExiste() {
		System.out.println("Vérifie si le programme arrive à vérifier l'existance d'un client.");
		assertTrue(pharmacie.siClientExiste("DANN12030405"));
	}

	@Test
	public void testAjouterClient() {
		System.out.println("Vérifie que le programme arrive à ajouter un client.");
		pharmacie.ajouterClient("BEAF01020304", "Beaulieu", "Frédéric");
		assertTrue(pharmacie.siClientExiste("BEAF01020304"));
	}

	@Test
	public void testGetPrescriptionsClient() {
		System.out.println("Vérifie si le programme retourne bien un client.");
		assertNotNull(pharmacie.getPrescriptionsClient("DANN12030405"));
	}

	@Test
	public void testServirPrescription() {
		System.out.println("Vérifie si le programme peut servir une prescription à un client lorsque les données sont entrées correctement.");
		Medicament med = new Medicament();
		med.setNomMarque("mewtwo");
		med.setDosesPossibles(new double[] {20, 10, 2});
		pharmacie.getLesMedicaments().add(med);
		pharmacie.getPrescriptionsClient("DANN12030405").add(new Prescription("mewtwo", 50, 3));
		assertNotNull(pharmacie.servirPrescription("DANN12030405", "mewtwo"));
	}

	@Test
	public void testTrouverInteraction() {
		System.out.println("Vérifie si le programme arrive à trouver une intéraction entre deux molécules. Vérifie également si il est capable de ne pas en trouver si tel est le cas.");
		List<Medicament> med = new ArrayList<>();
		Medicament med1 = new Medicament();
		med1.setNomMolecule("wouf");
		Medicament med2 = new Medicament();
		med2.setNomMolecule("miou");
		med1.setInteractions(new String[] {"miou"});
		med.add(med1);
		med.add(med2);
		pharmacie.setLesMedicaments(med);
		assertTrue(pharmacie.trouverInteraction("wouf", "miou"));
		assertTrue(pharmacie.trouverInteraction("miou", "wouf"));
		assertFalse(pharmacie.trouverInteraction("wouf", "wouf"));
	}
}
