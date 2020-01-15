package com.dataBaseConnection;

import com.module.OnlinePayment;
import com.module.chaque;

import java.sql.*;
import java.util.ArrayList;

public class OnlinePaymentDAO  implements DAO<OnlinePayment> {

    private DataConnection dc;
    private Connection cn;

    public OnlinePaymentDAO(){
        dc=DataConnection.getDataConnection();
        cn=dc.getConnection();
    }


    @Override
    public OnlinePayment create(OnlinePayment p) {
        try {
            PreparedStatement ps=cn.prepareStatement("insert into payment(montant,vente,num,dateaff) values(?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setDouble(1,p.getMontant());
            ps.setInt(2, p.getVente().getId());
            ps.setInt(3,p.getNcompte());
            ps.setDate(4,new Date(p.getDatePayment().getTime()));
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
    public void update(OnlinePayment p) {
        try {
            PreparedStatement ps=cn.prepareStatement("update payment set montant =?,nchaque=?,dateaff=? where id= ?");
            ps.setDouble(1, p.getMontant());
            ps.setInt(2,p.getNcompte());
            ps.setDate(3,new Date(p.getDatePayment().getTime()));
            ps.setInt(4, p.getId());
            ps.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public OnlinePayment getOne(Long id) {
        try {
            PreparedStatement ps=cn.prepareStatement("select * from payment where id= ?");
            ps.setLong(1, id);
            ResultSet rs=ps.executeQuery();
            rs.next();
            return null;//new OnlinePayment(rs.getInt(1), rs.getDouble(2), null,rs.getInt(4),rs.getDate(5));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<OnlinePayment> getAll() {
        try {
            ArrayList<OnlinePayment> ls = new ArrayList<>();
            PreparedStatement ps=cn.prepareStatement("select * from payment ");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                ls.add(null);//new OnlinePayment(rs.getInt(1), rs.getDouble(2), null,rs.getInt(4),rs.getDate(5)));
            }
            return ls;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
