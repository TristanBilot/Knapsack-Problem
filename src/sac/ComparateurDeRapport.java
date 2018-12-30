package sac;

import java.util.Comparator;

import objet.Objet;

/*  Ce comparateur est utilisé dans la classe SacADos pour permettre
	la comparaison et le tri des rapports de chaque objet qui compose 
	le sac à dos
*/
public class ComparateurDeRapport implements Comparator<Objet> {

		@Override
		public int compare(Objet o1, Objet o2) {
			if (o1.rapport() > o2.rapport()){
				return -1;
			}
			if(o1.rapport() < o2.rapport()){
				return 1;
			}
			return 0;
		}
	
}
