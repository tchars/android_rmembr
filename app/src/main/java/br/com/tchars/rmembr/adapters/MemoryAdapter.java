package br.com.tchars.rmembr.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.tchars.rmembr.R;
import br.com.tchars.rmembr.models.Memoria;
import br.com.tchars.rmembr.utils.SalvarMemoriaPasta;

public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.MyViewHolder> {

    private List<Memoria> _memorias;

    private Context _context;

    public MemoryAdapter(Context _context) {
        this._context = _context;
    }

    public MemoryAdapter(List<Memoria> _memorias, Context _context) {
        this._memorias = _memorias;
        this._context = _context;
    }

    public MemoryAdapter(List<Memoria> memorias) {
        this._memorias = memorias;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        _context = parent.getContext();

        View itemLista = LayoutInflater.from(_context)
                .inflate(R.layout.adapter_memory_list, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Memoria memoria = _memorias.get(position);

        Bitmap bitmap = new SalvarMemoriaPasta(_context)
                            .setFileName(memoria.getUrlImagem())
                            .load();

        holder.titulo.setText(memoria.getTitulo());
        holder.imagemCapa.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return _memorias.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titulo;
        ImageView imagemCapa;

        public MyViewHolder (View itemView) {
            super (itemView);
            titulo = itemView.findViewById(R.id.txt_tituloMemoria);
            imagemCapa = itemView.findViewById(R.id.imagemView);
        }
    }

}
