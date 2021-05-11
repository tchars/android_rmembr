package br.com.tchars.rmembr.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import br.com.tchars.rmembr.R;
import br.com.tchars.rmembr.models.Memoria;
import br.com.tchars.rmembr.services.MemoriaService;
import br.com.tchars.rmembr.utils.SalvarMemoriaPasta;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    Button button;

    private MemoriaService _memoriaService;
    private Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        _context = getApplicationContext();
        _memoriaService = new MemoriaService(_context);

        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> AdicionarMemoria());

        button = findViewById(R.id.button_buscar);
        button.setOnClickListener(v -> B());
    }

    private void AdicionarMemoria(){
        Intent intent = new Intent(getApplicationContext(), MemoryActivity.class);
        startActivity(intent);
    }

    private void B() {
        ArrayList<Memoria> memorias = _memoriaService.BuscarMemorias();

        for(Memoria memoria : memorias) {
            //System.out.println(memoria.getUrlImagem());
            Bitmap bitmap = new SalvarMemoriaPasta(_context).
                    setFileName(memoria.getUrlImagem()).
                    load();
        }
    }
}