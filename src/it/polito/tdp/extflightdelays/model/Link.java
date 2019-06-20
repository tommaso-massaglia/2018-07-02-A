package it.polito.tdp.extflightdelays.model;

public class Link {

	private int origin_airport_id;
	private int destination_airport_id;
	private int distance;

	public Link(int origin_airport_id, int destination_airport_id, int distance) {
		this.origin_airport_id = origin_airport_id;
		this.destination_airport_id = destination_airport_id;
		this.distance = distance;
	}

	public int getOrigin_airport_id() {
		return origin_airport_id;
	}

	public int getDestination_airport_id() {
		return destination_airport_id;
	}

	public int getDistance() {
		return distance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + destination_airport_id;
		result = prime * result + origin_airport_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Link other = (Link) obj;
		if (destination_airport_id != other.destination_airport_id)
			return false;
		if (origin_airport_id != other.origin_airport_id)
			return false;
		return true;
	}

}
