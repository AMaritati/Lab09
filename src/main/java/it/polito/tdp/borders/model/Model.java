package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private SimpleGraph<Country, DefaultEdge> graph;
	private Map<Integer, Country> idMap;
	private BordersDAO dao;
	
	public Model() {
		idMap = new HashMap<Integer, Country>();
		
	}
	
	public void creaGrafo (int anno) {
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		dao = new BordersDAO();
		this.dao.loadIdMapCountries(idMap); // devo caricare la idMap
		
		// crezione vertici
		Graphs.addAllVertices(this.graph, idMap.values());
		
		// creazione archi
		
		List<Border> confini = dao.getCountryPairs(idMap, anno);
		
		for(Border b : confini) {
			this.graph.addEdge(b.getC1(), b.getC2());
		}
	}
	
	
	public int nVertici() {
		return this.graph.vertexSet().size();
	}
	
	public int nArchi() {
		return this.graph.edgeSet().size();
	}
	
	/**
	 * Controllo che la stringa passata come parametro sia intero
	 * @param p
	 * @return
	 */
	public boolean controllaTxt (String p) {
		char c;
		boolean result = true;
		
		for(int i=0;i<p.length();i++){
            c = p.charAt(i);
            if(!((Character.isDigit(c)))){
                result = false;
                return result;
            }
        }
        
		return result;
	}

}
