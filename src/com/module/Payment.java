package com.module;

public class Payment {
	
	private int id;
	private double Montant;
	private Vente vente;
	
	
	public Payment(int id, double montant, Vente v) {
		this.id = id;
		Montant = montant;
		vente=v;
	}
	
	public Vente getVente(){
		return vente;
	}
	public void setVente(Vente v){
		this.vente=v;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public double getMontant() {
		return Montant;
	}


	public void setMontant(double montant) {
		Montant = montant;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", Montant=" + Montant + ", vente=" + vente + "]";
	}


	

}
