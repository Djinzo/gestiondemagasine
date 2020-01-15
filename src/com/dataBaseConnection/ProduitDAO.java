package com.dataBaseConnection;

import com.module.Categorie;
import com.module.Produit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProduitDAO implements DAO<Produit>{

	private DataConnection dc;
	private Connection cn;
	private CategorieDAO categorieDAO;
	
	public ProduitDAO() {
		this.dc = DataConnection.getDataConnection();
		this.cn = dc.getConnection();
		categorieDAO=new CategorieDAO();
	}

	@Override
	public Produit create(Produit p) {
		try {
			PreparedStatement ps=cn.prepareStatement("insert into produit(disnegiation,prix,categorie,code) values (?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);	
			ps.setString(1, p.getDisegniation());
			ps.setDouble(2, p.getPrix());
			ps.setInt(3, p.getCategorie().getId());
			ps.setString(4, p.getCode());
			ps.execute();
			ResultSet rs=ps.getGeneratedKeys();
			rs.next();
			p.setId(rs.getInt(1));
			return p;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public void delete(Long id) {
		try {
			PreparedStatement ps=cn.prepareStatement("delete from produit where id=?");
			ps.setLong(1, id);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
	}

	@Override
	public void update(Produit p) {
		try {
			PreparedStatement ps=cn.prepareStatement("update produit set disnegiation=?,prix=?,categorie=?,code=? where id=?");
			ps.setString(1, p.getDisegniation());
			ps.setDouble(2, p.getPrix());
			ps.setInt(3, p.getCategorie().getId());
			ps.setString(4, p.getCode());
			ps.setInt(5, p.getId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}

	@Override
	public Produit getOne(Long id) {
		try {
			
			PreparedStatement ps=cn.prepareStatement("select * from produit where id=?");
			ps.setLong(1, id);
			ResultSet rs=ps.executeQuery();
			Categorie c;
			
			if(rs.next()){
				System.out.println(rs.getLong(4));
				c=categorieDAO.getOne(rs.getLong(4));
				return new Produit(rs.getInt(4),rs.getString(1), rs.getString(2), rs.getDouble(3),c);
			}else return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;	
		}
	}
	
	

	@Override
	public ArrayList<Produit> getAll() {
		try {
			ArrayList<Produit> pl=new ArrayList<>();
			PreparedStatement ps=cn.prepareStatement("select * from produit");
			ResultSet rs=ps.executeQuery();
			Categorie c;
			Produit p;
			while (rs.next()){
				c=categorieDAO.getOne(rs.getLong(4));
				p=new Produit(rs.getInt(5),rs.getString(1), rs.getString(2), rs.getDouble(3),c);
				pl.add(p);
			}
			return pl;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			
		}
	}

}
