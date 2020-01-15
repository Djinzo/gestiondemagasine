package com.dataBaseConnection;

import java.util.ArrayList;

public interface DAO<T> {
	
	
	public T create(T obj);
	public void delete(Long id);
	public void update(T obj);
	public T getOne(Long id);
	public ArrayList<T> getAll();
	
	
}
