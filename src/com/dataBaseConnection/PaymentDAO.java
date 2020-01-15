package com.dataBaseConnection;

import com.module.OnlinePayment;
import com.module.Payment;

import java.sql.*;
import java.util.ArrayList;

import com.module.chaque;

public class PaymentDAO implements DAO<Payment> {
	private DataConnection dc;
	private Connection cn;
	
	
	public PaymentDAO() {
		dc= DataConnection.getDataConnection();
		cn=dc.getConnection();
	}

	@Override
	public Payment create(Payment p) {

		try {
			if(p.getClass().getName()=="com.module.Payment"){
				PreparedStatement ps=cn.prepareStatement("insert into payment(montant,vente,type) values(?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setDouble(1,p.getMontant());
				ps.setInt(2, p.getVente().getId());
				ps.setString(3,"espace");
				ps.execute();
				ResultSet rs=ps.getGeneratedKeys();
				rs.next();
				p.setId(rs.getInt(1));
				return p;
			}
			if(p.getClass().getName()=="com.module.chaque"){
				chaque c=(chaque)p;
				PreparedStatement ps=cn.prepareStatement("insert into payment(montant,vente,num,date,np,type) values(?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setDouble(1,c.getMontant());
				ps.setInt(2, c.getVente().getId());
				ps.setInt(3,c.getnCheque());
				ps.setDate(4,new Date(c.getDateaf().getTime()));
				ps.setString(5,c.getNomP());
				ps.setString(6,"chaque");
				ps.execute();
				ResultSet rs=ps.getGeneratedKeys();
				rs.next();
				p.setId(rs.getInt(1));
				return c;
			}
			if(p.getClass().getName()=="com.module.OnlinePayment"){
				OnlinePayment c=(OnlinePayment) p;
				PreparedStatement ps=cn.prepareStatement("insert into payment(montant,vente,num,date,np,type) values(?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setDouble(1,c.getMontant());
				ps.setInt(2, c.getVente().getId());
				ps.setInt(3,c.getNcompte());
				ps.setDate(4,new Date(c.getDatePayment().getTime()));
				ps.setString(5,c.getNomP());
				ps.setString(6,"online");
				ps.execute();
				ResultSet rs=ps.getGeneratedKeys();
				rs.next();
				c.setId(rs.getInt(1));
				return c;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	@Override
	public void delete(Long id) {
		try {
			PreparedStatement ps=cn.prepareStatement("delete from payment where vente=?");
			ps.setLong(1, id);
			ps.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(Payment p) {
		try {
			if(p.getClass().getName().equals("com.module.payment")){
				PreparedStatement ps=cn.prepareStatement("update payment set montant =? where id= ?");
				ps.setDouble(1, p.getMontant());
				ps.setInt(2, p.getId());
				ps.execute();
			}
			if(p.getClass().getName().equals("com.module.chaque")){
				chaque c=(chaque)p;
				PreparedStatement ps=cn.prepareStatement("update payment set montant =?,num=?,date=?,np=? where id= ?");
				ps.setDouble(1, c.getMontant());
				ps.setInt(2,c.getnCheque());
				ps.setDate(3,new Date(c.getDateaf().getTime()));
				ps.setString(4,c.getNomP());
				ps.setInt(5, c.getId());
				ps.execute();
			}
			if(p.getClass().getName().equals("com.module.OnlinePayment")){
				OnlinePayment c=(OnlinePayment) p;
				PreparedStatement ps=cn.prepareStatement("update payment set montant =?,num=?,date=?,np=? where id= ?");
				ps.setDouble(1, c.getMontant());
				ps.setInt(2,c.getNcompte());
				ps.setDate(3,new Date(c.getDatePayment().getTime()));
				ps.setString(4,c.getNomP());
				ps.setInt(5, p.getId());
				ps.execute();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Payment getOne(Long id) {
		try {
			PreparedStatement ps=cn.prepareStatement("select * from payment where id= ?");
			ps.setLong(1, id);
			ResultSet rs=ps.executeQuery();
			rs.next();
			if(rs.getString(4).equals("espace")){
				return new Payment(rs.getInt(1), rs.getDouble(2), null);
			}
			if(rs.getString(4).equals("chaque")){
				return new chaque(rs.getInt(1), rs.getDouble(2), null,rs.getDate(6),rs.getInt(5),rs.getString(7));
			}
			if(rs.getString(4).equals("online")){
				return new OnlinePayment(rs.getInt(1), rs.getDouble(2), null,rs.getInt(5),rs.getDate(6),rs.getString(7));
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;
	}

	@Override
	public ArrayList<Payment> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
