// auteurs: Maud El-Hachem
// 2015
package drugware_v15;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Pharmacie {
	private List<Client> lesClients;
	private List<Medicament> lesMedicaments;

	public Pharmacie() {
		this.lesMedicaments = new ArrayList<>();
		this.lesClients = new ArrayList<>();
	}

	/**
	 * @return the lesClients
	 */
	public List<Client> getLesClients() {
		return lesClients;
	}

	/**
	 * @param lesClients
	 *            the lesClients to set
	 */
	public void setLesClients(List<Client> lesClients) {
		this.lesClients = lesClients;
	}

	/**
	 * @return the lesMedicaments
	 */
	public List<Medicament> getLesMedicaments() {
		return lesMedicaments;
	}
	
	public Medicament obtenirMedicament(String nom) {
		for (Iterator<Medicament> it = lesMedicaments.iterator(); it.hasNext();) {
			Medicament itMedicament = it.next();
			if (itMedicament.getNomMarque().equals(nom)) {
				return itMedicament;
			}
		}
		return null;
	}

	/**
	 * @param lesMedicaments
	 *            the lesMedicaments to set
	 */
	public void setLesMedicaments(List<Medicament> lesMedicaments) {
		this.lesMedicaments = lesMedicaments;
	}

	public void lireClients() {
		Fichiers fichier = new Fichiers();
		fichier.lireClients(lesClients);
	}

	public void lireMedicaments() {
		Fichiers fichier = new Fichiers();
		fichier.lireMedicaments(lesMedicaments);
	}

	public void lirePrescriptions() {
		Fichiers fichier = new Fichiers();
		fichier.lirePrescriptions(lesClients);
	}

	public boolean siClientExiste(String NAM) {
		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client itClient = it.next();
			if (itClient.getNAM().equals(NAM)) {
				return true;
			}
		}
		return false;
	}

	public void ajouterClient(String NAM, String nom, String prenom) {
		this.lesClients.add(new Client(NAM, nom, prenom));
	}
	
	public boolean ajouterPrescriptionClient(String NAM, Prescription prescription) {
		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client itClient = it.next();
			if (itClient.getNAM().equals(NAM)) {
				for (Prescription p : itClient.getPrescriptions()) {
					if (p.getMedicamentAPrendre().equals(prescription.getMedicamentAPrendre())) {
						itClient.getPrescriptions().remove(p);
						break;
					}
				}
				itClient.getPrescriptions().add(prescription);
				return true;
			}
		}
		return false;
	}

	public List<Prescription> getPrescriptionsClient(String NAM) {
		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client itClient = it.next();
			if (itClient.getNAM().equals(NAM)) {
				return itClient.getPrescriptions();
			}
		}
		return null;
	}

	public String servirPrescription(String NAM, String medicament) {
		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client itClient = it.next();
			if (itClient.getNAM().equals(NAM)) {
				for (Iterator<Prescription> it2 = itClient.getPrescriptions()
						.iterator(); it2.hasNext();) {
					Prescription courante = it2.next();
					if (courante.getMedicamentAPrendre().equalsIgnoreCase(
							medicament))
						if (courante.getRenouvellements() >= 1) {
							courante.setRenouvellements(courante
									.getRenouvellements() - 1);
							Medicament med = obtenirMedicament(courante.getMedicamentAPrendre());
							double dose = courante.getDose();
							double[] dosesPossibles = med.getDosesPossibles();
							String suggestion = "";
							int[] nombreAPrendre = new int[dosesPossibles.length];
							for (int i = dosesPossibles.length - 1; i > 0; i--) {
								nombreAPrendre[i] = 0;
								System.out.println("Dose " + dose + "   possible " + dosesPossibles[i]);
								while (dose > dosesPossibles[i]) {
									dose -= dosesPossibles[i];
									nombreAPrendre[i] += 1;
								}
								if (nombreAPrendre[i] > 0) {
									suggestion += "\nComprimés de " + dosesPossibles[i] + " " + med.getUnite() + " : " + nombreAPrendre[i];
								}
							}
							return "Suggestion pour les doses : \n" + suggestion;
						}
				}
			}
		}
		return null;
	}

	public boolean trouverInteraction(String medicament1, String medicament2) {
		for (Iterator<Medicament> it = lesMedicaments.iterator(); it.hasNext();) {
			Medicament courant = it.next();
			if (courant.getNomMolecule().equalsIgnoreCase(medicament1))
				for (int i = 0; i < courant.getInteractions().length; i++)
					if (courant.getInteractions()[i]
							.equalsIgnoreCase(medicament2))
						return true;
			if (courant.getNomMolecule().equalsIgnoreCase(medicament2))
				for (int i = 0; i < courant.getInteractions().length; i++)
					if (courant.getInteractions()[i]
							.equalsIgnoreCase(medicament1))
						return true;
		}
		return false;
	}

	public void ecrireClients() {
		Fichiers fichier = new Fichiers();
		fichier.ecrireClients(lesClients);
	}

	public void ecrirePrescriptions() {
		Fichiers fichier = new Fichiers();
		fichier.ecrirePrescriptions(lesClients);
	}
}
