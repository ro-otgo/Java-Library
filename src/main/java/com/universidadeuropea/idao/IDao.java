package com.universidadeuropea.idao;

import java.util.List;

/**
 * Interfaz para interactuar con la base de datos.
 * 
 * Se realizan las operaciones tipicas CRUD.
 * 
 * @author Rodrigo
 *
 * @param <T> Objeto base.
 * @param <K> Tipo de la clave primaria del objeto base. 
 */
public interface IDao<T, K> {
	
	T save(T objeto) throws Exception;
	
	List<T> getAll() throws Exception;
	
	T getById(K id) throws Exception;
	
	boolean delete(T objeto) throws Exception;;
	
	boolean deleteById(K id) throws Exception;
	
	T update(T objeto) throws Exception;
	
}
