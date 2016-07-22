package com.matteoveroni.views.question.model;

import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.ViewName;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.greenrobot.eventbus.Subscribe;

/**
 *
 * @author Matteo Veroni
 */
public class QuestionModel {

	private final List<String> stringhe = new ArrayList<>();

	public QuestionModel() {
		stringhe.add("ciao");
		stringhe.add("hellas verona");
		stringhe.add("casa");
		stringhe.add("sassaiola");
		stringhe.add("wao");
		stringhe.add("setter");
		stringhe.add("assassini");
		stringhe.add("caos");
		stringhe.add("magnaccione");
		stringhe.add("fao");
		stringhe.add("zao");
		stringhe.add("pterodaptilus");
		stringhe.add("albero");
		stringhe.add("miao");
		stringhe.add("topo");
		stringhe.add("john wayne");
		stringhe.add("tao");
		stringhe.add("erudita");
		stringhe.add("caos");
		stringhe.add("malos");
		stringhe.add("zuzzurellando");
		stringhe.add("attivismo");
	}

//	public void estraiTuttiGliElementiDellaLista() {
//		int i;
//		while (stringhe.size() > 0) {
//			i = estraiIndiceCasualePerListaStringhe();
//			String stringaEstratta = stringhe.remove(i);
//			System.out.println(stringaEstratta);
//		}
//	}
	
	public String estraiElementoDallaLista(){
		int i = estraiIndiceCasualePerListaStringhe();
		return stringhe.remove(i); 
	}

	private int estraiIndiceCasualePerListaStringhe() {
		Random random = new Random();
		return random.nextInt(stringhe.size());
	}
	
	private void disegnaElementoNellaLista(String stringa){
		
	}
}
