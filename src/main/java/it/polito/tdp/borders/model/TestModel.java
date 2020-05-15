package it.polito.tdp.borders.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		
		System.out.println("Creo il grafo relativo al 2000");
		model.creaGrafo(1900);
		
        System.out.format("Trovate %d nazioni\n", model.nVertici());
        System.out.format("%d\n", model.getCountries().size());
        
		System.out.format("Numero archi: %d\n", model.nArchi());
		
		System.out.format("Numero componenti connesse: %d\n", model.componentiConnessi());
		
		System.out.println(model.degreesVertexes());
		
//		Map<Country, Integer> stats = model.getCountryCounts();
//		for (Country country : stats.keySet())
//			System.out.format("%s %d\n", country, stats.get(country));		
		
	}

}
