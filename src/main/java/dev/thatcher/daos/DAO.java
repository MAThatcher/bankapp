package dev.thatcher.daos;

import java.util.List;

public interface DAO<T> {

	// CREATE
	T create(T t);

	// READ
	T getById(int id);

	// UPDATE
	T update(T t);

	// Delete
	boolean delete(int id);
	
	//READ ALL
	List<T> getAll();
	
}