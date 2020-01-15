package com.module;

import java.util.ArrayList;
import java.util.Date;

public class Vente {
	private int id;
	private Date date;
	private ArrayList<LignedeCommande> comandeliste;
	private Client client;
	private double totelVente;
	private ArrayList<Payment> payments;
	
	public ArrayList<Payment> getPayments() {
		return payments;
	}
	public void setPayments(ArrayList<Payment> payments) {
		this.payments = payments;
	}
	public Vente(int id, Date date, double totelVente, Client c ) {
		
		this.id = id;
		this.date = date;
		this.comandeliste =new ArrayList<>();
		payments=new ArrayList<>();
		this.totelVente = totelVente;
		client =c;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public void addComandeLine(LignedeCommande l){
		comandeliste.add(l);
		totelVente=calculerTotal();
	}
	public double calculerTotal() {
		double t=0;
		for(LignedeCommande l:comandeliste){
			t=+l.getTotal();
		}
		return t;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public ArrayList<LignedeCommande> getComandeliste() {
		return comandeliste;
	}
	public void setComandeliste(ArrayList<LignedeCommande> comandeliste) {
		this.comandeliste = comandeliste;
	}
	public double getTotelVente() {
		return totelVente;
	}
	public void setTotelVente(double totelVente) {
		this.totelVente = totelVente;
	}

	public void addPayment(Payment p){
		payments.add(p);
	}
	
	

}
