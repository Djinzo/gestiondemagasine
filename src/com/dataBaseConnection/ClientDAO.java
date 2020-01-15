package com.dataBaseConnection;

import com.module.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDAO implements DAO<Client> {
	
	private DataConnection dc;
	private Connection cn;
	
	public ClientDAO(){
		dc= DataConnection.getDataConnection();
		cn=dc.getConnection();
	}

	@Override
	public Client create(Client c) {
		try {
			PreparedStatement ps=cn.prepareStatement("insert into client(cin,nom,prenom,tele,mail,address) values(?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, c.getCin());
			ps.setString(2,c.getNom());
			ps.setString(3, c.getPrenom());
			ps.setString(4, c.getTele());
			ps.setString(5, c.getMail());
			ps.setString(6, c.getAdress());
			ps.execute();
			ResultSet rs=ps.getGeneratedKeys();
			rs.next();
			c.setId(rs.getInt(1));
			return c;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void delete(Long id) {
		try {
			PreparedStatement ps=cn.prepareStatement("delete from client where id = ?");
			ps.setLong(1, id);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void update(Client c) {
		try {
			PreparedStatement ps=cn.prepareStatement("update client set cin=?,nom=?,prenom=?,tele=?,mail=?,address=? where id=?");
			ps.setString(1, c.getCin());
			ps.setString(2, c.getNom());
			ps.setString(3, c.getPrenom());
			ps.setString(4, c.getTele());
			ps.setString(5, c.getMail());
			ps.setString(6, c.getAdress());
			ps.setInt(7, c.getId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public Client getOne(Long id) {
		try {
			PreparedStatement ps=cn.prepareStatement("select * from client where id = ?" );
			ps.setLong(1, id);
		ResultSet rs=ps.executeQuery();
		rs.next();
		return new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<Client> getAll() {
		try{
			PreparedStatement ps=cn.prepareStatement("select * from client");
			ResultSet rs=ps.executeQuery();
			ArrayList<Client> cl=new ArrayList<>();
			while (rs.next()){
				cl.add(new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
			}
			return cl;
		}catch (SQLException e) {
		e.printStackTrace();
		return null;
		}
	}

}
