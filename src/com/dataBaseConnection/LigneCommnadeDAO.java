package com.dataBaseConnection;

import com.module.LignedeCommande;
import com.module.Produit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LigneCommnadeDAO implements DAO<LignedeCommande> {
	
	private DataConnection dc;
	private Connection cn;
	private ProduitDAO pdao;
	
	
	public LigneCommnadeDAO() {
		dc= DataConnection.getDataConnection();
		cn=dc.getConnection();
		pdao=new ProduitDAO();
	}

	@Override
	public LignedeCommande create(LignedeCommande l) {
		try {
			PreparedStatement ps=cn.prepareStatement("insert into lignecommande(produit,qte,total,vente) values (?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, l.getP().getId());
			ps.setInt(2, l.getQte());
			ps.setDouble(3,l.getTotal());
			ps.setInt(4, l.getVente().getId());
			ps.execute();
			ResultSet rs=ps.getGeneratedKeys();
			rs.next();
			l.setId(rs.getInt(1));
			return l;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public void delete(Long id) {
		try{
			PreparedStatement ps=cn.prepareStatement("delete from lignecommande where vente =?");
			ps.setLong(1, id);
			ps.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(LignedeCommande l) {
		try {
			PreparedStatement ps=cn.prepareStatement("update  lignecommande set produit=?,qte=?,total=?,vente=? where id = ?");
			ps.setInt(1, l.getP().getId());
			ps.setInt(2, l.getQte());
			ps.setDouble(3,l.getTotal());
			ps.setInt(4, l.getVente().getId());
			ps.setInt(5, l.getId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public LignedeCommande getOne(Long id) {
		try{
			PreparedStatement ps=cn.prepareStatement("select * from lignecommande where id=?");
			ps.setLong(1, id);
			ResultSet rs=ps.executeQuery();
			rs.next();
			Produit p=pdao.getOne((long) rs.getInt(2));
			return new LignedeCommande(rs.getInt(1),p, rs.getInt(3), null);
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public ArrayList<LignedeCommande> getAll() {
		try{
			ArrayList<LignedeCommande> ls=new ArrayList<>();
			PreparedStatement ps=cn.prepareStatement("select * from lignecommande");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				Produit p=pdao.getOne((long) rs.getInt(2));
				ls.add(new LignedeCommande(rs.getInt(1),p, rs.getInt(3), null));	
			}
			return ls;
			
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

}
