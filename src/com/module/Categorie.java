package com.module;

import java.util.ArrayList;

public class Categorie {
	
	private int id;
	private String nom;
	private ArrayList<Produit> produitListe;
	public Categorie(int id, String nom) {
		
		this.id = id;
		this.nom = nom;
		this.produitListe = new ArrayList<>();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public ArrayList<Produit> getProduitListe() {
		return produitListe;
	}
	public void setProduitListe(ArrayList<Produit> produitListe) {
		this.produitListe = produitListe;
	}
	@Override
	public String toString() {
		return nom;
	}
	
	

}
