package com.dataBaseConnection;

import com.module.Categorie;
import com.module.Produit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategorieDAO implements DAO<Categorie> {
	private DataConnection dc;
	private Connection cn;

	
	

	public CategorieDAO() {
		this.dc = DataConnection.getDataConnection();
		this.cn = dc.getConnection();
		
	}

	@Override
	public Categorie create(Categorie c) {
		try {
			PreparedStatement ps=cn.prepareStatement("insert into categorie(nom) values (?)",PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, c.getNom());
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
			PreparedStatement ps=cn.prepareStatement("delete from categorie where id=?");
			ps.setLong(1, id);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(Categorie c) {
		try {
			PreparedStatement ps=cn.prepareStatement("update categorie set nom=? where id=?");
			ps.setString(1, c.getNom());
			ps.setInt(2, c.getId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Categorie getOne(Long id) {
		try {
			PreparedStatement ps=cn.prepareStatement("select * from categorie where id=?");
			ps.setLong(1, id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				return new Categorie(rs.getInt(1), rs.getString(2));
			}else return null;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public ArrayList<Produit> getCategorieProduits(Categorie c){
		try {
			ArrayList<Produit> produitListe=new ArrayList<>();
			PreparedStatement ps=cn.prepareStatement("select * from produit where categorie = ? ");
			ps.setInt(1, c.getId());
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				produitListe.add(new Produit(rs.getInt(4),rs.getString(1), rs.getString(2),rs.getDouble(3),c));
			}
			return produitListe;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<Categorie> getAll() {
		try {
			ArrayList<Categorie> catListe=new ArrayList<>();
			PreparedStatement ps=cn.prepareStatement("select * from categorie ");
			ResultSet rs=ps.executeQuery();
			Categorie c;
			while(rs.next()){
				c=new Categorie(rs.getInt(1), rs.getString(2));
				c.setProduitListe(getCategorieProduits(c));
				catListe.add(c);
			}
			return catListe;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
