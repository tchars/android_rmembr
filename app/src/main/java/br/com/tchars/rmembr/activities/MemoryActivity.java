package br.com.tchars.rmembr.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.tchars.rmembr.R;
import br.com.tchars.rmembr.models.Memoria;
import br.com.tchars.rmembr.services.MemoriaService;
import br.com.tchars.rmembr.utils.SalvarMemoriaPasta;

public class MemoryActivity extends AppCompatActivity {

    private static final int CAMERA_CODE = 1888;
    private static final int GALERIA_CODE = 1889;

    ImageView imagemEscolhida;
    Button btnSalvar;
    Bitmap bmp;
    Uri uriImagemSelecionada;
    InputStream imageStream;
    SeekBar seekBar;

    EditText titulo;
    String nome;

    private MemoriaService _memoriaService;
    private static Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        getSupportActionBar().hide();

        _context = this.getApplicationContext();
        _memoriaService = new MemoriaService(_context);

        imagemEscolhida = findViewById(R.id.imagem_selecionada);
        btnSalvar = findViewById(R.id.btn_salvar);
        seekBar = findViewById(R.id.seekBar);

        titulo = findViewById(R.id.input_titulo_memoria);

        imagemEscolhida.setOnClickListener(v -> SelecionarImagem());
        btnSalvar.setOnClickListener(v -> SalvarMemoria());
    }

    private void SalvarMemoria() {

        nome = "memory_" + new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss.SSS").format(new Date()) + ".png";
        Integer valorSeekbar = seekBar.getProgress();

        //System.out.println("valorSeekbar " + valorSeekbar);

        try {
            new SalvarMemoriaPasta(getApplicationContext()).
                setFileName(nome).
                setQuality(valorSeekbar).
                save(bmp);

            SalvarNoBanco();

            Toast.makeText(getApplicationContext(), "Salvo", Toast.LENGTH_LONG).show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void SalvarNoBanco() {
        _memoriaService.CadastrarMemoria(nome, titulo.getText().toString());

        ArrayList<Memoria> memorias = _memoriaService.BuscarMemorias();
    }

    private void SelecionarImagem() {

        final CharSequence[] opcoes = { "Câmera", "Galeria", "Cancelar" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MemoryActivity.this);

        builder.setTitle("Adicionar imagem");

        builder.setItems(opcoes, (dialog, which) -> {
            if(opcoes[which].equals("Câmera")) {
                try {
                    if (ActivityCompat.checkSelfPermission(MemoryActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_CODE);

                    } else {
                        /* Exibe a tela para o usuário dar a permissão. */
                        ActivityCompat.requestPermissions(
                                MemoryActivity.this,
                                new String[] {
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                }, CAMERA_CODE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (opcoes[which].equals("Galeria")) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Selecione uma foto"), GALERIA_CODE);
            } else {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case CAMERA_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permissão aceita", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CODE) {
            Bundle bundle = data.getExtras();
            bmp = (Bitmap) bundle.get("data");
            //bmp = (Bitmap) bundle.get("br/com/tchars/rmembr/data");

            imagemEscolhida.setImageBitmap(bmp);

        } else if (requestCode == GALERIA_CODE) {
            uriImagemSelecionada = data.getData();
            imagemEscolhida.setImageURI(uriImagemSelecionada);

            try {
                imageStream = getContentResolver().openInputStream(uriImagemSelecionada);
                bmp = BitmapFactory.decodeStream(imageStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}