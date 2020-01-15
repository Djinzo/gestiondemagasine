package com.module;

import java.util.Date;

public class OnlinePayment extends Payment {
    private int Ncompte;
    private Date datePayment;
    private String nomP;

    public OnlinePayment(int id, double montant, Vente v,int Ncompte,Date datePayment,String nomP) {
        super(id, montant, v);
        this.Ncompte=Ncompte;
        this.datePayment=datePayment;
        this.nomP=nomP;
    }

    public String getNomP() {
        return nomP;
    }

    public void setNomP(String nomP) {
        this.nomP = nomP;
    }

    public int getNcompte() {
        return Ncompte;
    }

    public void setNcompte(int ncompte) {
        Ncompte = ncompte;
    }

    public Date getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(Date datePayment) {
        this.datePayment = datePayment;
    }
}
