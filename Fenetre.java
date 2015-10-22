// auteurs: Maud El-Hachem
// 2015
package drugware_v15;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
//2
public class Fenetre extends JFrame {
	private JMenuBar menuBar;
	private JMenu menuFic;
	private JMenu menuClients;
	private JMenu menuPresc;
	private JMenu menuMedic;

	private JMenuItem itemFic1;
	private JMenuItem itemFic2;
	private JMenuItem itemFic3;

	private JMenuItem itemClients1;
	private JMenuItem itemClients2;

	private JMenuItem itemPresc1;
	private JMenuItem itemPresc2;
	private JMenuItem itemPresc3;

	private JMenuItem itemMedic1;
	private JMenuItem itemMedic2;
	
	private JLabel label;

	private Pharmacie pharma;

	public Fenetre() {
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		menuBar = new JMenuBar();
		menuFic = new JMenu("Fichier");
		menuClients = new JMenu("Clients");
		menuPresc = new JMenu("Prescriptions");
		menuMedic = new JMenu("Médicaments");

		itemFic1 = new JMenuItem("Charger les fichiers");
		itemFic2 = new JMenuItem("Mettre à jour les fichiers");
		itemFic3 = new JMenuItem("Quitter");

		itemClients1 = new JMenuItem("Inscrire un nouveau client");
		itemClients2 = new JMenuItem("Afficher tous les clients");

		itemPresc1 = new JMenuItem("Afficher les prescriptions d'un client");
		itemPresc2 = new JMenuItem("Servir une prescription");
		itemPresc3 = new JMenuItem("Ajouter une nouvelle prescription à un client");

		itemMedic1 = new JMenuItem("Afficher tous les médicaments");
		itemMedic2 = new JMenuItem("Afficher si interaction");

		pharma = new Pharmacie();

	}

	public void initMenus() {

		// Menu fichier
		itemFic1.addActionListener(new BoutonFic1Listener());
		this.menuFic.add(itemFic1);
		itemFic2.addActionListener(new BoutonFic2Listener());
		this.menuFic.add(itemFic2);

		// Ajout d'un séparateur
		this.menuFic.addSeparator();
		// si quitter
		itemFic3.addActionListener(new BoutonFic3Listener());
		this.menuFic.add(itemFic3);

		// Menu Clients
		itemClients1.addActionListener(new BoutonClient1Listener());
		this.menuClients.add(itemClients1);
		itemClients2.addActionListener(new BoutonClient2Listener());
		this.menuClients.add(itemClients2);

		// Menu Prescriptions
		itemPresc1.addActionListener(new BoutonPresc1Listener());
		this.menuPresc.add(itemPresc1);
		itemPresc2.addActionListener(new BoutonPresc2Listener());
		this.menuPresc.add(itemPresc2);
		itemPresc3.addActionListener(new BoutonPresc3Listener());
		this.menuPresc.add(itemPresc3);

		// Menu Médicaments
		itemMedic1.addActionListener(new BoutonMedic1Listener());
		this.menuMedic.add(itemMedic1);
		itemMedic2.addActionListener(new BoutonMedic2Listener());
		this.menuMedic.add(itemMedic2);

		this.menuBar.add(menuFic);
		this.menuBar.add(menuClients);
		this.menuBar.add(menuPresc);
		this.menuBar.add(menuMedic);
		this.setJMenuBar(menuBar);
		
		label = new JLabel("Bienvenue dans le logiciel Drugware!");
		this.add(label);
		
		itemFic2.setEnabled(false);
		itemClients1.setEnabled(false);
		itemClients2.setEnabled(false);
		itemPresc1.setEnabled(false);
		itemPresc2.setEnabled(false);
		itemPresc3.setEnabled(false);
		itemMedic1.setEnabled(false);
		itemMedic2.setEnabled(false);
		this.setVisible(true);

	}

	public class BoutonFic1Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			pharma.lireClients();
			pharma.lirePrescriptions();
			pharma.lireMedicaments();
			itemFic2.setEnabled(true);
			itemClients1.setEnabled(true);
			itemClients2.setEnabled(true);
			itemPresc1.setEnabled(true);
			itemPresc2.setEnabled(true);
			itemPresc3.setEnabled(true);
			itemMedic1.setEnabled(true);
			itemMedic2.setEnabled(true);
		}
	}

	public class BoutonFic2Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			pharma.ecrireClients();
			pharma.ecrirePrescriptions();
		}
	}

	public class BoutonFic3Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}

	public class BoutonClient1Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String nom, prenom;
			String NAM = JOptionPane.showInputDialog(null,
					"Entre le numéro d'assurance maladie",
					"Numéro d'assurance maladie", JOptionPane.QUESTION_MESSAGE);
			if (NAM != null && NAM.length() > 0)
				if (pharma.siClientExiste(NAM))
					JOptionPane.showMessageDialog(null,
							"Ce numéro d'assurance maladie existe déjà",
							"Problème", JOptionPane.INFORMATION_MESSAGE);
				else {
					nom = JOptionPane.showInputDialog(null, "Entre le nom",
							"Nom", JOptionPane.QUESTION_MESSAGE);
					prenom = JOptionPane.showInputDialog(null,
							"Entre le prenom", "Prénom",
							JOptionPane.QUESTION_MESSAGE);
					if (nom != null && nom.length() > 0 && prenom != null
							&& prenom.length() > 0)
						pharma.ajouterClient(NAM, nom, prenom);
					Fenetre.this.label.setText("Client ajouté");
				}
		}
	}

	public class BoutonClient2Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String clients = "<html>";
			for (Iterator<Client> it = pharma.getLesClients().iterator(); it.hasNext();) {
				Client courant = it.next();
				clients += courant.afficherClient() + "<br>";
			}
			clients += "</html>";
			Fenetre.this.label.setText(clients);
		}
	}

	public class BoutonPresc1Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String NAM = JOptionPane.showInputDialog(null,
					"Entre le numéro d'assurance maladie",
					"Numéro d'assurance maladie", JOptionPane.QUESTION_MESSAGE);
			if (NAM != null && NAM.length() > 0) {
				List<Prescription> liste = pharma.getPrescriptionsClient(NAM);
				if (liste != null) {
					String prescriptions = "<html>";
					for (Iterator<Prescription> it = liste.iterator(); it
							.hasNext();) {
						Prescription courant = it.next();
						prescriptions += courant.afficherPrescription() + "<br>";
					}
					prescriptions += "</html>";
					Fenetre.this.label.setText(prescriptions);
					} else {
					Fenetre.this.label.setText("Aucune prescription trouvée pour ce client.");
				}

			}
		}
	}

	public class BoutonPresc2Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String NAM = JOptionPane.showInputDialog(null,
					"Entre le numéro d'assurance maladie",
					"Prescription", JOptionPane.QUESTION_MESSAGE);
			String medicament = JOptionPane.showInputDialog(null,
					"Entre le nom du médicament",
					"Prescription", JOptionPane.QUESTION_MESSAGE);
			String resultat;
			if ((resultat = pharma.servirPrescription(NAM, medicament)) != null)
				Fenetre.this.label.setText("<html>" + (resultat + "\n\nPrescription servie!").replace("\n", "<br>") + "</html>");
			else 
				Fenetre.this.label.setText("Il n'est pas possible de servir la prescription");
		}
	}
	
	public class BoutonPresc3Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String NAM = JOptionPane.showInputDialog(null, "Entre le numéro d'assurance maladie", "Prescription",
					JOptionPane.QUESTION_MESSAGE);
			String medicament = JOptionPane.showInputDialog(null, "Entre le nom du médicament", "Prescription",
					JOptionPane.QUESTION_MESSAGE);
			boolean ok = false;
			try {
				double dose = Double.parseDouble(JOptionPane.showInputDialog(null, "Entre la dose recommandée",
						"Prescription", JOptionPane.QUESTION_MESSAGE));
				int renouvellements = Integer
						.parseInt(JOptionPane.showInputDialog(null, "Entre le nombre de renouvellements utilisables",
								"Prescription", JOptionPane.QUESTION_MESSAGE));
				ok = pharma.ajouterPrescriptionClient(NAM, new Prescription(medicament, dose, renouvellements));
			} catch (NumberFormatException e) { }
			if (ok) {
				Fenetre.this.label.setText("Prescription ajoutée!");
			} else {
				Fenetre.this.label.setText("Il n'est pas possible d'ajouter la prescription");
			}
		}
	}

	public class BoutonMedic1Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String medicaments = "<html>";
			for (Iterator<Medicament> it = pharma.getLesMedicaments().iterator(); it.hasNext();) {
				Medicament courant = it.next();
				medicaments += courant.getNomMolecule() + " " + courant.getNomMarque() + "<br>";
			}
			medicaments += "</html>";
			Fenetre.this.label.setText(medicaments);
		}
	}

	public class BoutonMedic2Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String medicament1 = JOptionPane.showInputDialog(null,
					"Entre le nom de la molécule no 1",
					"Interactions", JOptionPane.QUESTION_MESSAGE);
			String medicament2 = JOptionPane.showInputDialog(null,
					"Entre le nom de la molécule no 2",
					"Interactions", JOptionPane.QUESTION_MESSAGE);
			if (pharma.trouverInteraction(medicament1, medicament2))
				Fenetre.this.label.setText("Interaction trouvée! Faites attention!");
			else 
				Fenetre.this.label.setText("Aucune interaction trouvée!");
		}
	}



}