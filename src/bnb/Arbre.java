package bnb;

import java.util.ArrayList;

public class Arbre {

	private ArrayList<Noeud> noeuds;
	
	public Arbre() {
		this.noeuds = new ArrayList<>();
	}
	
	public Noeud getFirst() {
		return noeuds.get(0);
	}
	
	public Noeud getLast() {
		return noeuds.get(noeuds.size() - 1);
	}
	
	public void add(Noeud n) {
		noeuds.add(n);
	}
	
	public boolean isEmpty() {
		return noeuds.isEmpty();
	}
	
	public void supprimerLast() {
		noeuds.remove(getLast());
	}
	
}
