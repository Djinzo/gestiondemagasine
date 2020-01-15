package com.module;

public class Client {
	
	private int id;
	private String cin;
	private String nom;
	private String prenom;
	private String tele;
	private String mail;
	private String adress;
	public Client(int id, String cin, String nom, String prenom, String tele, String mail, String adress) {
	
		this.id = id;
		this.cin = cin;
		this.nom = nom;
		this.prenom = prenom;
		this.tele = tele;
		this.mail = mail;
		this.adress = adress;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getTele() {
		return tele;
	}
	public void setTele(String tele) {
		this.tele = tele;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	@Override
	public String toString() {
		return  cin + " " + nom + " " + prenom ;
	}
	
	
	

}
