package br.com.tchars.rmembr.services.interfaces;

import java.util.ArrayList;

import br.com.tchars.rmembr.models.Memoria;

public interface IMemoriaService {
    boolean CadastrarMemoria(String urlImagem, String titulo);
    ArrayList<Memoria> BuscarMemorias();
}
