package com.dataBaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;


public class DataConnection {
	private static DataConnection connectionSingle=null;
	private Connection connection;
	private DataConnection(){
		try{
		String url = "jdbc:mysql://localhost:3306/boutique1119";
		connection = DriverManager.getConnection(url, "root", "");
		System.out.println(connection);
		//System.out.println("connection ok");
		} catch (Exception exp) {
			
			exp.printStackTrace();}

	}
	public Connection getConnection() {
		return connection;
	}

	public static DataConnection getDataConnection(){
		if(connectionSingle==null){
			connectionSingle=new DataConnection();
		}
		return connectionSingle;
	}
}
