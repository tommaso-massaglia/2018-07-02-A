package it.polito.tdp.extflightdelays.model;

public class SortableAirport implements Comparable<SortableAirport> {

	private Airport a;
	private double distanza;

	public Airport getA() {
		return a;
	}

	public void setA(Airport a) {
		this.a = a;
	}

	public double getDistanza() {
		return distanza;
	}

	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}

	public SortableAirport(Airport a, double distanza) {
		this.a = a;
		this.distanza = distanza;
	}

	@Override
	public int compareTo(SortableAirport o) {
		return (int) (o.getDistanza() - this.distanza);
	}

}
