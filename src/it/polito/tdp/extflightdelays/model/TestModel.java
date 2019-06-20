package it.polito.tdp.extflightdelays.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		model.creaGrafo(600);
		System.out.println(model.getVicini(20));
		System.out.println(model.getPercorsoMassimo(5000, 20));
	}

}
