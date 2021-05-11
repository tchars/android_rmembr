package br.com.tchars.rmembr.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import br.com.tchars.rmembr.R;
import br.com.tchars.rmembr.adapters.MemoryAdapter;
import br.com.tchars.rmembr.models.Memoria;
import br.com.tchars.rmembr.services.MemoriaService;
import br.com.tchars.rmembr.utils.SalvarMemoriaPasta;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    private RecyclerView recyclerView;

    private List<Memoria> _memorias;

    private MemoriaService _memoriaService;
    private Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        _context = getApplicationContext();
        _memoriaService = new MemoriaService(_context);

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> AdicionarMemoria());

        recyclerView = findViewById(R.id.recyclerView);

        _memorias = _memoriaService.BuscarMemorias();

        MemoryAdapter memoryAdapter = new MemoryAdapter(_memorias);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(memoryAdapter);
    }

    private void AdicionarMemoria() {
        Intent intent = new Intent(getApplicationContext(), MemoryActivity.class);
        startActivity(intent);
    }
}