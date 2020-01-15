package com.module;

public class Produit {
	
	@Override
	public String toString() {
		return  code + " " + disegniation ;
	}

	private int id;
	private String code;
	private String disegniation;
	private Double prix;
	private Categorie categorie;
	
	public Produit(String code, String disegniation, Double prix, Categorie categorie) {
		this.code = code;
		this.disegniation = disegniation;
		this.prix = prix;
		this.categorie = categorie;
	}
	public Produit(int id,String code, String disegniation, Double prix, Categorie categorie) {
		this.id=id;
		this.code = code;
		this.disegniation = disegniation;
		this.prix = prix;
		this.categorie = categorie;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDisegniation() {
		return disegniation;
	}

	public void setDisegniation(String disegniation) {
		this.disegniation = disegniation;
	}

	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	
	
	
	
}
