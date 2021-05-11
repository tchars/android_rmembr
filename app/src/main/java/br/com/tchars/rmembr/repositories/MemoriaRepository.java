package br.com.tchars.rmembr.repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;

import br.com.tchars.rmembr.data.DatabaseHelper;
import br.com.tchars.rmembr.models.Memoria;
import br.com.tchars.rmembr.repositories.interfaces.IMemoriaRepository;

public class MemoriaRepository implements IMemoriaRepository {

    private final SQLiteDatabase _db;

    public MemoriaRepository(Context context) {
        _db = DatabaseHelper.getInstance(context).getWritableDatabase();
    }

    @Override
    public boolean CriarMemoria(String urlImagem, String titulo) {
        try {
            System.out.println("urlImagem -> " + urlImagem + "titulo -> " + titulo);
            String insert = "INSERT INTO memorias(urlImagem, titulo) VALUES('" + urlImagem + "', " + "'" + titulo + "')";
            _db.execSQL(insert);
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public ArrayList<Memoria> BuscarTodasMemorias() {

        ArrayList<Memoria> memorias = new ArrayList<>();

        try {

            String consulta = "SELECT * FROM memorias";

            Cursor cursor = _db.rawQuery(consulta, null);

            int indiceId = cursor.getColumnIndex("id");
            int indiceUrlImagem = cursor.getColumnIndex("urlImagem");
            int indiceTitulo = cursor.getColumnIndex("titulo");

            boolean registros = cursor.moveToFirst();

            while (registros && (cursor.moveToNext())) {

                Memoria memoria = new Memoria();

                memoria.setId(cursor.getInt(indiceId));
                memoria.setUrlImagem(cursor.getString(indiceUrlImagem));
                memoria.setTitulo(cursor.getString(indiceTitulo));

                memorias.add(memoria);

                cursor.moveToNext();
            }

            cursor.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            return memorias;
        }
    }
}
