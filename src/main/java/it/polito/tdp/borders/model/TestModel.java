package it.polito.tdp.borders.model;

import java.util.List;



public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		
		System.out.println("Creo il grafo relativo al 2000");
		model.creaGrafo(2000);
		
        System.out.format("Trovate %d nazioni\n", model.nVertici());
        System.out.format("%d\n", model.getCountries().size());
        
		System.out.format("Numero archi: %d\n", model.nArchi());
		
		System.out.format("Numero componenti connesse: %d\n", model.componentiConnessi());
		
		System.out.println(model.degreesVertexes());
		System.out.println("\n\n\n\n");
		List<Country> visita1 = model.visitaAmpiezza(new Country("LAO",812,"Laos"));
		System.out.println(visita1);
		
		
		
//		Map<Country, Integer> stats = model.getCountryCounts();
//		for (Country country : stats.keySet())
//			System.out.format("%s %d\n", country, stats.get(country));		
		
	}

}
/*
 

 * */

