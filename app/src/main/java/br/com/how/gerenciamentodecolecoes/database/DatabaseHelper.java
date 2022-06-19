package br.com.how.gerenciamentodecolecoes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import br.com.how.gerenciamentodecolecoes.R;
import br.com.how.gerenciamentodecolecoes.categoria.Categoria;
import br.com.how.gerenciamentodecolecoes.colecao.Colecao;
import br.com.how.gerenciamentodecolecoes.itens_colecao.ItensColecao;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gerenciamento_colecao";
    private static final String TABLE_CATEGORIA = "categoria";
    private static final String TABLE_COLECAO = "colecao";
    private static final String TABLE_ITENS_COLECAO = "itens_colecao";

    private static final String CREATE_TABLE_CATEGORIA = "CREATE TABLE " + TABLE_CATEGORIA + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(255));";
    private static final String CREATE_TABLE_COLECAO = "CREATE TABLE " + TABLE_COLECAO + "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(255), " +
            "descricao TEXT, " +
            "data_inicio DATE, " +
            "completa TINYINT, " +
            "id_categoria TINYINT, " +
            "CONSTRAINT fk_colecao_categoria FOREIGN KEY (id_categoria) REFERENCES categoria (id))";
    private static final String CREATE_TABLE_ITENS_COLECAO = "CREATE TABLE " + TABLE_ITENS_COLECAO + "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(255), " +
            "descricao TEXT, " +
            "id_colecao INTEGER, " +
            "CONSTRAINT fk_intens_colecao_colecao FOREIGN KEY (id_colecao) REFERENCES colecao (id))";

    private static final String DROP_TABLE_CATEGORIA = "DROP TABLE IF EXISTS " + TABLE_CATEGORIA;
    private static final String DROP_TABLE_COLECAO = "DROP TABLE IF EXISTS " + TABLE_COLECAO;
    private static final String DROP_TABLE_ITENS_COLECAO = "DROP TABLE IF EXISTS " + TABLE_ITENS_COLECAO;

    private static final String INSERT_DEFAULT_CATEGORIAS = "INSERT INTO " + TABLE_CATEGORIA + " (nome) VALUES " +
            "('Quadrinhos')," +
            "('Carros')," +
            "('Moedas')," +
            "('Video Games');";

    public DatabaseHelper(Context context) { super(context, DATABASE_NAME, null, 1); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATEGORIA);
        db.execSQL(CREATE_TABLE_COLECAO);
        db.execSQL(CREATE_TABLE_ITENS_COLECAO);
        db.execSQL(INSERT_DEFAULT_CATEGORIAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_ITENS_COLECAO);
        db.execSQL(DROP_TABLE_COLECAO);
        db.execSQL(DROP_TABLE_CATEGORIA);
        onCreate(db);
    }

    /* Início CRUD Categoria */
    public long createCategoria (Categoria c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", c.getNome());
        long id = db.insert(TABLE_CATEGORIA, null, cv);
        db.close();
        return id;
    }

    public long updateCategoria (Categoria c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", c.getNome());
        long id = db.update(TABLE_CATEGORIA, cv, "_id = ?", new String[]{String.valueOf(c.getId())});
        db.close();
        return id;
    }

    public long deleteCategoria (Categoria c) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(TABLE_CATEGORIA, "_id = ?", new String[]{String.valueOf(c.getId())});
        db.close();
        return id;
    }

    public Categoria getByIdCategoria (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_CATEGORIA, columns, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);
        data.moveToFirst();
        Categoria c = new Categoria();
        c.setId(data.getInt(0));
        c.setNome(data.getString(1));
        data.close();
        db.close();
        return c;
    }

    public void getAllCategoria (Context context, ListView lv) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_CATEGORIA, columns, null, null, null, null, null);
        int[] to = {R.id.textViewIdListarCategoria, R.id.textViewNomeListCategoria};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context, R.layout.categoria_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }

    public void getAllNameCategoria (ArrayList<Integer> listCategoriaId, ArrayList<String> listCategoriaName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_CATEGORIA, columns, null, null, null,
                null, "nome");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listCategoriaId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome");
            listCategoriaName.add(data.getString(nameColumnIndex));
        }
        db.close();
    }

    /* Fim CRUD Categoria */

    /* Início CRUD Coleção */
    public long createColecao(Colecao c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", c.getNome());
        cv.put("descricao", c.getDescricao());
        cv.put("data_inicio", c.getData_inicio());
        cv.put("completa", c.getCompleta());
        cv.put("id_categoria", c.getId_categoria());
        long id = db.insert(TABLE_COLECAO, null, cv);
        db.close();
        return id;
    }

    public long updateColecao(Colecao c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", c.getNome());
        cv.put("descricao", c.getDescricao());
        cv.put("data_inicio", c.getData_inicio());
        cv.put("completa", c.getCompleta());
        cv.put("id_categoria", c.getId_categoria());
        long rows = db.update(TABLE_COLECAO, cv, "_id = ?", new String[]{String.valueOf(c.getId())});
        db.close();
        return rows;
    }

    public long deleteColecao(Colecao c) {
        SQLiteDatabase db = this.getWritableDatabase();
        long rows = db.delete(TABLE_COLECAO, "_id = ?", new String[]{String.valueOf(c.getId())});
        db.close();
        return rows;
    }

    public void getAllColecao (Context context, ListView lv) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_COLECAO, columns, null, null, null,
                null, "nome");
        int[] to = {R.id.textViewIdListarColecao, R.id.textViewNomeListarColecao};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.colecao_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }

    public void getAllNameColecao (ArrayList<Integer> listColecaoId, ArrayList<String> listColecaoName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_COLECAO, columns, null, null, null,
                null, "nome");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listColecaoId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome");
            listColecaoName.add(data.getString(nameColumnIndex));
        }
        db.close();
    }

    public Colecao getByIdColecao (int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] columns = {"_id", "nome", "descricao", "data_inicio", "completa", "id_categoria"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_COLECAO, columns, "_id = ?", args, null,
                null, null);
        data.moveToFirst();
        Colecao c = new Colecao();
        c.setId(data.getInt(0));
        c.setNome(data.getString(1));
        c.setDescricao(data.getString(2));
        c.setData_inicio(data.getString(3));
        c.setCompleta(this.convertToBoolean(data.getInt(4)));
        c.setId_categoria(data.getInt(5));
        data.close();
        db.close();
        return c;
    }
    /* Fim CRUD Coleção */

    /* Início CRUD Itens Colecao */
    public long createItensColecao(ItensColecao iC) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", iC.getNome());
        cv.put("descricao", iC.getDescricao());
        cv.put("id_colecao", iC.getId_colecao());
        long id = db.insert(TABLE_ITENS_COLECAO, null, cv);
        db.close();
        return id;
    }

    public long updateItensColecao(ItensColecao iC) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", iC.getNome());
        cv.put("descricao", iC.getDescricao());
        cv.put("id_colecao", iC.getId_colecao());
        long rows = db.update(TABLE_ITENS_COLECAO, cv, "_id = ?", new String[]{String.valueOf(iC.getId())});
        db.close();
        return rows;
    }

    public long deleteItensColecao(ItensColecao iC) {
        SQLiteDatabase db = this.getWritableDatabase();
        long rows = db.delete(TABLE_ITENS_COLECAO, "_id = ?", new String[]{String.valueOf(iC.getId())});
        db.close();
        return rows;
    }

    public void getAllItensColecao (Context context, ListView lv) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_ITENS_COLECAO, columns, null, null, null,
                null, "nome");
        int[] to = {R.id.textViewIdListarItensColecao, R.id.textViewNomeListarItensColecao};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.itens_colecao_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }

    public ItensColecao getByIdItensColecao (int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] columns = {"_id", "nome", "descricao", "id_colecao"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_ITENS_COLECAO, columns, "_id = ?", args, null,
                null, null);
        data.moveToFirst();
        ItensColecao iC = new ItensColecao();
        iC.setId(data.getInt(0));
        iC.setNome(data.getString(1));
        iC.setDescricao(data.getString(2));
        iC.setId_colecao(data.getInt(3));
        data.close();
        db.close();
        return iC;
    }
    /* Fim CRUD Itens Coleção */

    private boolean convertToBoolean(int number) {
        boolean boolValue;

        // Check if it's greater than equal to 1
        if (number >= 1) {
            boolValue = true;
        }
        else {
            boolValue = false;
        }

        return boolValue;
    }
}
