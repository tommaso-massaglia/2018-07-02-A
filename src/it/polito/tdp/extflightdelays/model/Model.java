package it.polito.tdp.extflightdelays.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	Graph<Airport, DefaultWeightedEdge> grafo;
	Map<Integer, Airport> airportIdMap;

	public Graph<Airport, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public Collection<Airport> getAirports() {
		return airportIdMap.values();
	}

	public void creaGrafo(int miglia) {
		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.airportIdMap = new HashMap<>();

		for (Airport a : dao.loadAllAirports()) {
			this.airportIdMap.put(a.getId(), a);
		}

		Graphs.addAllVertices(grafo, this.airportIdMap.values());

		for (Link l : dao.loadAllLinks(miglia)) {
			if (!grafo.containsEdge(this.airportIdMap.get(l.getOrigin_airport_id()),
					this.airportIdMap.get(l.getDestination_airport_id()))) {
				grafo.addEdge(this.airportIdMap.get(l.getOrigin_airport_id()),
						this.airportIdMap.get(l.getDestination_airport_id()));
				grafo.setEdgeWeight(grafo.getEdge(this.airportIdMap.get(l.getOrigin_airport_id()),
						this.airportIdMap.get(l.getDestination_airport_id())), l.getDistance());
			}
		}

	}

	public String getVicini(int airport_id) {
		String result = "";
		List<SortableAirport> temp = new LinkedList<>();
		for (Airport a : Graphs.neighborListOf(grafo, airportIdMap.get(airport_id))) {
			temp.add(new SortableAirport(a, grafo.getEdgeWeight(grafo.getEdge(this.airportIdMap.get(airport_id), a))));
		}
		Collections.sort(temp);
		for (SortableAirport sa : temp) {
			result += sa.getA().getAirportName() + " " + sa.getDistanza() + "\n";
		}
		return result;
	}

	// PUNTO 2, ALGORITMO RICORSIVO
	// Obiettivo: Visitare il maggior numero di città con le miglia disponibili
	// Vincoli: Le miglia disponibili e non posso tornare in nessuna delle città
	// visitate
	// compresa quella di partenza

	private Set<Airport> parziale;
	private List<Airport> best;

	public String getPercorsoMassimo(double max_miglia, int airport_id) {
		this.parziale = new LinkedHashSet<Airport>();
		this.best = new LinkedList<Airport>();
		String result = "";
		int i = 1;
		this.parziale.add(this.airportIdMap.get(airport_id));

		this.recursive(parziale, this.airportIdMap.get(airport_id), max_miglia);

		for (Airport a : best) {
			result +="\n"+ i + ": " + a.getCity() +",  "+a.getAirportName()+"\n";
			i++;
		}
		return result;
	}

	private void recursive(Set<Airport> parziale, Airport attuale, double miglia_rimanenti) {

		if (miglia_rimanenti > 0 && parziale.size() > this.best.size()) {
			this.best = new LinkedList<>(parziale);
			System.out.print(".");
		}

		if (miglia_rimanenti > 0)
			for (Airport a : Graphs.neighborListOf(grafo, attuale)) {
				if (!parziale.contains(a)) {
					parziale.add(a);
					this.recursive(parziale, a, miglia_rimanenti - grafo.getEdgeWeight(grafo.getEdge(attuale, a)));
					parziale.remove(a);
				}
			}

	}

}
