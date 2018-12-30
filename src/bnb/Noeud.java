package bnb;

public class Noeud {
	private int niveau;
	private float profit;
	private float borne;
	private float weight;

	public Noeud() {
		niveau = -1;
		profit = 0;
		weight = 0;
		borne = 0;
	}
	
	public void incrémenter() {
		this.niveau += 1;
	}

	public int getNiveau() {
		return niveau;
	}

	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	public float getProfit() {
		return profit;
	}

	public void setProfit(float profit) {
		this.profit = profit;
	}

	public float getBorne() {
		return borne;
	}

	public void setBorne(float borne) {
		this.borne = borne;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	
}
