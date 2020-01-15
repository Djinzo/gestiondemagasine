package com.ServerPayment;

import com.dataBaseConnection.DataConnection;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

public class ThrideServer extends Thread {
    private Socket socket;
    private DataConnection dc;
    private Connection cn;

    public ThrideServer(Socket socket) {
        this.socket=socket;
        dc=DataConnection.getDataConnection();
        cn=dc.getConnection();
        start();
    }
    public Double ChackeMony(long Ncompte) {

        try {
            PreparedStatement ps=cn.prepareStatement("select * from compte where Ncompte = ?");
            ps.setLong(1,Ncompte);
            ResultSet rs=ps.executeQuery();
            rs.next();
            return  rs.getDouble(2);

        } catch (SQLException e) {
            e.printStackTrace();
        }
       return null;
    }
    public Boolean updateMony(long Ncompte,Double Mony,Double Mysolde){
        try {
            PreparedStatement ps=cn.prepareStatement("update compte set Mony = ? where Ncompte = ?");
            ps.setLong(2,Ncompte);
            if(Mony<=Mysolde){
                ps.setDouble(1,Mysolde-Mony);
                ps.execute();
                return true;
            }else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    return false;
    }

    @Override
    public void run() {

        try {
            OutputStream out;
            PrintStream ps = new PrintStream(socket.getOutputStream());
            ps.println("Welcome to the Server Bank.....");
            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg=br.readLine();
            StringTokenizer st=new StringTokenizer(msg, "-");
            String tNcompte=st.nextToken();
            String tmony=st.nextToken();
            if(updateMony(Long.parseLong(tNcompte),Double.parseDouble(tmony),ChackeMony(Long.parseLong(tNcompte)))){
                ps.println("true");
            }else {
                ps.println("false");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
