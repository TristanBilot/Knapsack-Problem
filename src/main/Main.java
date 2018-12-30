package main;

import sac.SacADos;

public class Main {

	public static void main(String[] args) {
		//String chemin = "/Users/tristan/objets.txt";
		
		// args[0] => chemin
		// args[1] => poidsMax
		// args[2] => méthode de résolution
		
		String chemin = args[0];
		float poidsMax = Float.parseFloat(args[1]);
		
		SacADos sac = new SacADos(chemin, poidsMax);
		sac.setObjectsFromFile();
		
		
		if (args[2].equals("pse")) {
			sac.résolutionPSE();
		}
		if (args[2].equals("dynamique")) {
			sac.résolutionDynamique();
		}
		if (args[2].equals("glouton")) {
			sac.résolutionGloutonne();
		}
		
	}
}
