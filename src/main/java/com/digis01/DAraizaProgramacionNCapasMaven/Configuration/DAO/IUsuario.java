/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO;

import com.digis01.DAraizaProgramacionNCapasMaven.ML.Result;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Usuario;
import java.util.List;

/**
 *
 * @author digis
 */
public interface IUsuario {
    Result GetAll();
    Result GetById(int identificador);
    Result Add(Usuario Usuario);
    Result Delete(int identificadorUsuario); 
    Result Update(Usuario Usuario);
        Result UpdateImagen(Usuario Usuario);
    Result Search (Usuario Usuario);
    Result AddAll (List<Usuario> usuarios);
    Result UpdateStatus (int identUsuario, int status);
}
