package it.polito.tdp.borders.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
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
	
	/**
	 * Metodo per studiare il grado di ogni nazione,
	 * mi indica il numero di confini
	 * @return
	 */
	public String degreesVertexes() {
		String elenco = "";
		for (Country c : this.graph.vertexSet()) {
			elenco+= c.getNome()+" -- "+this.graph.degreeOf(c)+" confini\n";
		}
		return elenco;
	}
	

	public int componentiConnessi() {
		
		ConnectivityInspector<Country,DefaultEdge> graf = new ConnectivityInspector<>(this.graph);
		return graf.connectedSets().size();
	}
	
	public int nVertici() {
		return this.graph.vertexSet().size();
	}
	
	public int nArchi() {
		return this.graph.edgeSet().size();
	}
	
	/**
	 * Restituisce la collezione di nazioni
	 * @return
	 */
	public Collection<Country> getCountries(){
		return this.graph.vertexSet();
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
