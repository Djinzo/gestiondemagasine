package com.dataBaseConnection;

import com.module.*;

import java.sql.*;
import java.util.ArrayList;

public class VenteDAO implements DAO<Vente>{
	private DataConnection dc;
	private Connection cn;
	private LigneCommnadeDAO lcdao;
	private ProduitDAO pdao;
	private ClientDAO cdao;
	private PaymentDAO pydao;
	
	public VenteDAO() {
		dc= DataConnection.getDataConnection();
		cn=dc.getConnection();
		lcdao=new LigneCommnadeDAO();
		pdao=new ProduitDAO();
		cdao=new ClientDAO();
		pydao=new PaymentDAO();
	}

	@Override
	public Vente create(Vente v) {
		try{
			PreparedStatement ps=cn.prepareStatement("insert into vente(date,total,client) values (?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setDate(1, new Date(v.getDate().getTime()));
			ps.setDouble(2,v.getTotelVente());
			ps.setInt(3, v.getClient().getId());
			ps.execute();
			ResultSet rs=ps.getGeneratedKeys();
			rs.next();
			v.setId(rs.getInt(1));
			for(LignedeCommande c:v.getComandeliste()){
				lcdao.create(c);
			}
			return v;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public void delete(Long id) {
		try{
			PreparedStatement ps=cn.prepareStatement("delete from vente where id=? ");
			ps.setLong(1, id);
			ps.execute();
			lcdao.delete(id);
			pydao.delete(id);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(Vente v) {
		try {
			
			PreparedStatement ps=cn.prepareStatement("update vente set date = ? , total = ?, client=? where id =?");
			ps.setDate(1,new Date(v.getDate().getTime()));
			ps.setDouble(2,v.getTotelVente());
			ps.setInt(3, v.getClient().getId());
			ps.setInt(4, v.getId());
			ps.execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	public ArrayList<LignedeCommande> comandeVente(Vente v){
		try{
			PreparedStatement ps=cn.prepareStatement("select * from lignecommande where vente=?");
			ps.setInt(1, v.getId());
			ArrayList<LignedeCommande> ms=new ArrayList<>();
			ResultSet rs=ps.executeQuery();
			Produit p;
			while(rs.next()){
				p=pdao.getOne((long) rs.getInt(2));
				//System.out.println("///"+rs.getInt(1));
				ms.add(new LignedeCommande(rs.getInt(1),p, rs.getInt(3), v));
			}
				
			return ms;
		}catch (Exception e) {
		    e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Payment> ventePayament(Vente v){
		try {
			ArrayList<Payment> paymentListe=new ArrayList<>();
			PreparedStatement ps=cn.prepareStatement("select * from payment where vente = ?");
			ps.setInt(1, v.getId());
			ResultSet rs=ps.executeQuery();
			while (rs.next()){
				if(rs.getString(4).equals("espace")){
					paymentListe.add(new Payment(rs.getInt(1), rs.getDouble(2), v));
				}
				if(rs.getString(4).equals("chaque")){
					paymentListe.add(new chaque(rs.getInt(1), rs.getDouble(2), v,rs.getDate(6),rs.getInt(5),rs.getString(7)));
				}
				if(rs.getString(4).equals("online")){
					paymentListe.add(new OnlinePayment(rs.getInt(1), rs.getDouble(2), v,rs.getInt(5),rs.getDate(6),rs.getString(7)));
				}


			}
			return paymentListe;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Vente getOne(Long id) {
		try {
			PreparedStatement ps=cn.prepareStatement("select * from Vente where id = ?");
			ps.setLong(1, id);
			ResultSet rs=ps.executeQuery();
			rs.next();
			Date Gc=new Date(rs.getDate(2).getTime());
			Client c=cdao.getOne(rs.getLong(4));
			Vente v= new Vente(rs.getInt(1),Gc, rs.getDouble(3),c);
			v.setComandeliste(comandeVente(v));
			v.setPayments(ventePayament(v));
			return v;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public ArrayList<Vente> getAll() {
		try {
			ArrayList<Vente> ls=new ArrayList<>();
			PreparedStatement ps=cn.prepareStatement("select * from Vente");
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()){
				Date Gc=new Date(rs.getDate(2).getTime());
				Client c=cdao.getOne(rs.getLong(4));
				Vente v= new Vente(rs.getInt(1),Gc, rs.getDouble(3),c);
				v.setComandeliste(comandeVente(v));
				v.setPayments(ventePayament(v));
				ls.add(v);
			}
			return ls;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	

}
