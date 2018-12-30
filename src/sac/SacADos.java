package sac;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import bnb.Arbre;
import bnb.Noeud;
import objet.Objet;

public class SacADos {

	private float poidsMax, poidsFinalDuSac, valeurFinaleDuSac; 
	private ArrayList<Objet> objetsDispo; // liste de tous les objets
	private ArrayList<Objet> objets; // liste des objets réelement dans le sac
	private String chemin; // chemin du fichier txt
	
	public SacADos() {
		this.objetsDispo = new ArrayList<Objet>();
		this.objets = new ArrayList<Objet>();
		this.poidsMax = 0;
	}
	
	public SacADos(String chemin, float poidsMax) {
		this.chemin = chemin;
		this.objetsDispo = new ArrayList<Objet>();
		this.objets = new ArrayList<Objet>();
		this.poidsMax = poidsMax;
		
	}
	
	/**
	 * @brief Initialise la liste objetsDispo a partir du fichier
	 */
	public void setObjectsFromFile() {
		String tmpNom = "";
		float tmpVal, tmpPoids;
		
		// récupération de toutes les lignes du fichier
		try {
			for (String ligne : Files.readAllLines(Paths.get(chemin))) { 
				String ligneSplit[];
				ligneSplit = ligne.split(" ; ");
				tmpNom = ligneSplit[0];
				tmpVal = Float.parseFloat(ligneSplit[1]);
				tmpPoids = Float.parseFloat(ligneSplit[2]);
				objetsDispo.add(new Objet(tmpNom, tmpVal, tmpPoids));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Retourne une liste d'objets triés inversement par rapport à leur rapport Vi/Pi
	public ArrayList<Objet> getTriInverse() {
		ArrayList<Objet> reverse = objetsDispo;
		Collections.sort(reverse, new ComparateurDeRapport());
		return reverse;
	}
	
	// Tri inversement les objets par rapport à leur rapport Vi/Pi
	public void trierObjetsInverse() {
		Collections.sort(objetsDispo, new ComparateurDeRapport());
	}
	
	public void résolutionGloutonne() {
		float poidsDispo = poidsMax; // au début, le sac est vide
		trierObjetsInverse(); // on trie inversement les objets
		for (Objet o : objetsDispo) {
			// si il y a de la place
			if (poidsDispo >= o.getPoids()) { 
				// décrémetation du poids .de l'objet rentré
				poidsDispo -= o.getPoids(); 
				// ajout de l'objet au sac
				objets.add(o);
				o.setPresent(true);
				valeurFinaleDuSac += o.getValeur();
			}
		}
		this.poidsFinalDuSac = poidsMax - poidsDispo;
		System.out.println("\nContenu du sac après la méthode gloutonne : \n" + toString());
	}
	
	public float maxProfits(Noeud u) {
		
		float profit = u.getProfit();
		int j = u.getNiveau() + 1;
		float poidsTotal = u.getWeight();
		int n = objetsDispo.size();
		float W = poidsMax;
		
		if (u.getWeight() >= W)
			return 0;
		
		while((j < n) && (poidsTotal + objetsDispo.get(j).getPoids() <= W)) {
			poidsTotal += objetsDispo.get(j).getPoids();
			profit += objetsDispo.get(j).getValeur();
			j++;
		}
		if (j < n) 
			profit += (W - poidsTotal) * objetsDispo.get(j).rapport();
		
		return profit;
	}
	
	// complexité temporelle exponentielle 2^n
	
	public void résolutionPSE () {
		int n = objetsDispo.size();
		trierObjetsInverse();
		Arbre arbre = new Arbre(); // L'arbre
		Noeud u = new Noeud();
		Noeud v = new Noeud();
		u.setNiveau(-1);
		arbre.add(u);
		float profitMax = 0;
		while (!arbre.isEmpty()) {
			
			u = arbre.getLast();
			arbre.supprimerLast();
			
			if (u.getNiveau() == -1)
				v.setNiveau(0);
			if (u.getNiveau() == n - 1) 
				continue;
			
			v.setNiveau(u.getNiveau() + 1);
			v.setWeight(u.getWeight() + objetsDispo.get(v.getNiveau()).getPoids());
			v.setProfit(u.getProfit() + objetsDispo.get(v.getNiveau()).getValeur());
			
			if (v.getWeight() <= poidsMax && v.getProfit() > profitMax)
				profitMax = v.getProfit();

			v.setBorne(maxProfits(v));
			
			if (v.getBorne() > profitMax) {
				arbre.add(v);
				objets.add(objetsDispo.get(v.getNiveau()));
				valeurFinaleDuSac += objetsDispo.get(v.getNiveau()).getValeur();
				poidsFinalDuSac += objetsDispo.get(v.getNiveau()).getPoids();
			}
			
		}
		System.out.println("\nContenu du sac après la méthode PSE : \n" + toString());
	}
	
/*  prog dynamique -> néessite + de mémoire car on 
 	va stocker les valeurs afin de recalculer les données
	mais est aussi bien plus rapide en temps */
	
	public void résolutionDynamique() {
		int NB_OBJETS = objetsDispo.size();
		/* Les valeurs peuvent être décimales, or nous allons avoir besoin
		de la valeur et du poids en tant que coordonnées dans la matrice 
		donc on multiplie par 10 pour que ces 2 champs soient obligatoirement
		des entiers */
		for (Objet o : objetsDispo) {
			o.setPoids(o.getPoids()*10);
			o.setValeur(o.getValeur()*10);
		}
		poidsMax *= 10;
		
		// on utilise une matrice pour stocker la valeur max à chaque objet 
		float[][] matrice = new float[NB_OBJETS + 1][(int) (poidsMax + 1)];

		// initialisation de la première ligne 
		for (int i = 0; i <= poidsMax; i++)
		  matrice[0][i] = 0;

		// on itère sur tous les objets
		for (int i = 1; i <= NB_OBJETS; i++) {
		  // itération sur toutes les capacités possibles
			for (int j = 0; j <= poidsMax; j++) {
				if (objetsDispo.get(i - 1).getPoids() > j)
					matrice[i][j] = matrice[i-1][j];
		    else
		      // on maximise la valeur trouvée
		      matrice[i][j] = (int) Math.max(matrice[i-1][j], 
		    		  	matrice[i-1][(int) (j - objetsDispo.get(i-1).getPoids())] + objetsDispo.get(i-1).getValeur());
		  }
		}
		
		float res = matrice[NB_OBJETS][(int) poidsMax];
		int w = (int) poidsMax;
		ArrayList<Objet> résolution = new ArrayList<>();

		for (int i = NB_OBJETS; i > 0  &&  res > 0; i--) {
			// si l'objet est le bon
			if (res != matrice[i-1][w]) {
			  // ajout au sac
			    résolution.add(objetsDispo.get(i-1));
			    // on incrémente la valeur et le poids total du sac
			    this.valeurFinaleDuSac += objetsDispo.get(i-1).getValeur();
			    this.poidsFinalDuSac += objetsDispo.get(i-1).getPoids();
			    
			    res -= objetsDispo.get(i-1).getValeur();
			    w -= objetsDispo.get(i-1).getPoids();
			  }
		}
		for (Objet o : résolution) {
			o.setPoids(o.getPoids()/10);
			o.setValeur(o.getValeur()/10);
		}
		/* On divise par 10 pour annuler le x10 fait en début d'algo */
		poidsMax /= 10;
		valeurFinaleDuSac /= 10;
		poidsFinalDuSac /= 10;
		
		this.objets = résolution;
		System.out.println("\nContenu du sac après la méthode dynamique : \n" + toString());
	}
	
	
	public void afficherReverse() {
		ArrayList<Objet> temp = getTriInverse();
		for (Objet o : temp) {
			System.out.println(o.getNom() + " -> " + o.rapport() + "\n");
		}
	}
	
	public void afficherRapports() {
		System.out.println("---------------");
		for (Objet o : objetsDispo) {
			System.out.println(o.getNom() + " -> " + o.rapport() + "\n");
		}
	}
	
	public String toString() {
		String temp = "";
		for (Objet o : objets) {
			temp += ("  - " + o.getNom() + " : valeur -> " + o.getValeur() + " : poids -> " + o.getPoids() + "\n");
		}
		temp += "\n=> Nombre d'objets : " + objets.size();
		temp += "\n=> Poids du sac    : " + this.poidsFinalDuSac + " kg" + " sur " + poidsMax + " kg";
		temp += "\n=> Valeur du sac   : " + this.valeurFinaleDuSac;
		return temp;
	}
	
	
}
