package com.module;

public class LignedeCommande {
	
	private int id;
	private Produit p;
	private int qte;
	private double total;
	private Vente vente;
	public LignedeCommande(int id, Produit p, int qte, Vente v) {
		
		this.id = id;
		this.p = p;
		this.qte = qte;
		this.total = p.getPrix()*qte;
		this.vente=v;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Produit getP() {
		return p;
	}
	public void setP(Produit p) {
		this.p = p;
	}
	public int getQte() {
		return qte;
	}
	public void setQte(int qte) {
		this.qte = qte;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Vente getVente() {
		return vente;
	}
	public void setVente(Vente vente) {
		this.vente = vente;
	}
	
	

}
