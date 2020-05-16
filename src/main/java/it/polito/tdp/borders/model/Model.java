package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;



public class Model {

	private SimpleGraph<Country, DefaultEdge> graph;
	private Map<Integer, Country> idMap;
	private BordersDAO dao;
	
	public Model() {
		idMap = new HashMap<Integer, Country>();
		
	}
	
	/**
	 * Creazione del grafo
	 * @param anno valore compreso tra 1816 e 2016
	 */
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
	
	// PROVO AD UTILIZZARE LA VISITA IN AMPIEZZA
	/**
	 * Metodo visita in ampiezza
	 * @param source nazione punto di partenza
	 * @return lista di nazioni
	 */
	public List<Country> visitaAmpiezza (Country source){
		// BUONA DA UTILIZZARE PER LE NAZIONI VICINE
		List<Country> visita = new ArrayList<>();
		
		BreadthFirstIterator<Country, DefaultEdge> bfv = new BreadthFirstIterator<>(graph,source);
		while (bfv.hasNext()) {
		    visita.add(bfv.next());	
		}
		
		return visita;
	}
	
	public Map<Country,Country> alberoVisita (Country source){
		final Map<Country,Country> albero = new HashMap<>();
		albero.put(source,null);
		
		BreadthFirstIterator<Country, DefaultEdge> bfv = new BreadthFirstIterator<>(graph,source);
		bfv.addTraversalListener(new TraversalListener<Country, DefaultEdge>() {

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {		
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				DefaultEdge edge = e.getEdge();
				Country a = graph.getEdgeSource(edge);
				Country b = graph.getEdgeTarget(edge);
				
				if(albero.containsKey(a)) {
					albero.put(b, a);
				}
				else {
					albero.put(a, b);
				}
				
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {
				
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {				
			}
		});
		while (bfv.hasNext()) {
			bfv.next();
		}
		
		return albero;
	}
	
	public List<Country> trovaViciniAdiacenti(Country c){
		List<Country> vicini = Graphs.neighborListOf(this.graph, c);
		return vicini;
	}

	public Set<Country> trovaVicini(Country c){
		
		//BreadthFirstIterator<Country, DefaultEdge> it = new BreadthFirstIterator<>(this.grafo,c);
	
		ConnectivityInspector<Country,DefaultEdge> graf = new ConnectivityInspector<>(this.graph);
		return graf.connectedSetOf(c);
		//return it.getGraph().vertexSet();
		
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
	
	

	/**
	 * Nazioni effettivamente collegate
	 * @return intero
	 */
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
	 * Restituisce la collezione di nazioni del grafo
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
