package com.module;

import java.util.Date;

public class chaque extends Payment {
	

	private Date dateaf;
	private int nCheque;
	private String nomP;
	
	public chaque(int id, double montant, Vente v, Date d, int nc,String nomP) {
		super(id, montant,v);
		dateaf=d;
		nCheque=nc;
		this.nomP=nomP;
	}

	public String getNomP() {
		return nomP;
	}

	public void setNomP(String nomP) {
		this.nomP = nomP;
	}

	public Date getDateaf() {
		return dateaf;
	}

	public void setDateaf(Date dateaf) {
		this.dateaf = dateaf;
	}

	public int getnCheque() {
		return nCheque;
	}

	public void setnCheque(int nCheque) {
		this.nCheque = nCheque;
	}

	@Override
	public String toString() {
		return "chaque [dateaf=" + dateaf + ", nCheque=" + nCheque + "]";
	}
	
}
