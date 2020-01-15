package com.dataBaseConnection;
import com.module.Payment;
import com.module.chaque;

import java.sql.*;
import java.util.ArrayList;

public class chaqueDAO implements DAO<chaque>{
    private DataConnection dc;
    private  Connection cn;

    public chaqueDAO(){
        dc=DataConnection.getDataConnection();
        cn=dc.getConnection();
    }

    @Override
    public chaque create(chaque p) {
        try {
            PreparedStatement ps=cn.prepareStatement("insert into payment(montant,vente,num,dateaff) values(?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setDouble(1,p.getMontant());
            ps.setInt(2, p.getVente().getId());
            ps.setInt(3,p.getnCheque());
            ps.setDate(4,new Date(p.getDateaf().getTime()));
            ps.execute();
            ResultSet rs=ps.getGeneratedKeys();
            rs.next();
            p.setId(rs.getInt(1));
            return p;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return p;
        }
    }

    @Override
    public void delete(Long id) {
        try {
            PreparedStatement ps=cn.prepareStatement("delete from payment where id=?");
            ps.setLong(1, id);
            ps.execute();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(chaque p) {

        try {
            PreparedStatement ps=cn.prepareStatement("update payment set montant =?,num=?,dateaff=? where id= ?");
            ps.setDouble(1, p.getMontant());
            ps.setInt(2,p.getnCheque());
            ps.setDate(3,new Date(p.getDateaf().getTime()));
            ps.setInt(4, p.getId());
            ps.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public chaque getOne(Long id) {
        try {
            PreparedStatement ps=cn.prepareStatement("select * from payment where id= ?");
            ps.setLong(1, id);
            ResultSet rs=ps.executeQuery();
            rs.next();
            return null;//new chaque(rs.getInt(1), rs.getDouble(2), null,rs.getDate(5),rs.getInt(4));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<chaque> getAll() {
        try {
            ArrayList<chaque> ls = new ArrayList<>();
            PreparedStatement ps=cn.prepareStatement("select * from payment ");
            ResultSet rs=ps.executeQuery();
           while(rs.next()){
               ls.add(null);//new chaque(rs.getInt(1), rs.getDouble(2), null,rs.getDate(5),rs.getInt(4)));
           }
           return ls;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
