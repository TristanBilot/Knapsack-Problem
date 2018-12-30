package objet;

public class Objet {

	private float poids, valeur;
	private String nom;
	private boolean present;
	
	public Objet() {
		this.present = false;
	}
	
	public Objet(String nom, float poids, float val) {
		if (poids <= 0 || val <= 0) 
			throw new RuntimeException("Les paramètres doivent être supérieurs à O.");
		
		this.nom = nom;
		this.poids = poids;
		this.valeur = val;
		this.present = false;
	}
	
	public float rapport() {
		return this.valeur / this.poids;
	}

	public boolean estPlusProfitableQue(Objet o) {
		return this.rapport() > o.rapport();
	}
	
	public boolean isPresent() {
		return present;
	}
	
	public void setPresent(boolean present) {
		this.present = present;
	}
	
	public float getPoids() {
		return poids;
	}

	public void setPoids(float poids) {
		this.poids = poids;
	}

	public float getValeur() {
		return valeur;
	}

	public void setValeur(float valeur) {
		this.valeur = valeur;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
	
}
