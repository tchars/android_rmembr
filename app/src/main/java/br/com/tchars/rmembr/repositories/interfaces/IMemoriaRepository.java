package br.com.tchars.rmembr.repositories.interfaces;

import java.util.ArrayList;

import br.com.tchars.rmembr.models.Memoria;

public interface IMemoriaRepository {
    boolean CriarMemoria(String urlImagem, String titulo);
    ArrayList<Memoria> BuscarTodasMemorias();
}
