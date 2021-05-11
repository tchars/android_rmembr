package br.com.tchars.rmembr.services;

import android.content.Context;

import java.util.ArrayList;

import br.com.tchars.rmembr.models.Memoria;
import br.com.tchars.rmembr.repositories.MemoriaRepository;
import br.com.tchars.rmembr.services.interfaces.IMemoriaService;

public class MemoriaService implements IMemoriaService {

    private final MemoriaRepository _memoriaRepository;

    public MemoriaService(Context context) {
        this._memoriaRepository = new MemoriaRepository(context);
    }

    @Override
    public boolean CadastrarMemoria(String urlImagem, String titulo) {
        return _memoriaRepository.CriarMemoria(urlImagem, titulo);
    }

    @Override
    public ArrayList<Memoria> BuscarMemorias() {
        return _memoriaRepository.BuscarTodasMemorias();
    }
}
