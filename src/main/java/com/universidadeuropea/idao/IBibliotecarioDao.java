package com.universidadeuropea.idao;

import com.universidadeuropea.entities.Bibliotecario;

public interface IBibliotecarioDao extends IDao <Bibliotecario, Long> {
	
	boolean validarUsuario(String username,String password);

}
