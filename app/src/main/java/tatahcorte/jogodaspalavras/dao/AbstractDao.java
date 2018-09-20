package tatahcorte.jogodaspalavras.dao;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import tatahcorte.jogodaspalavras.contract.Contract;
import tatahcorte.jogodaspalavras.database.DatabaseHelper;
import tatahcorte.jogodaspalavras.utils.CursorWrapper;

public abstract class AbstractDao<T> {
    @NonNull
    final Contract<T> contract;
    @NonNull
    final DatabaseHelper databaseHelper;

    AbstractDao(@NonNull Contract<T> contract, @NonNull DatabaseHelper databaseHelper) {
        this.contract = contract;
        this.databaseHelper = databaseHelper;
    }

    public List<T> query() {
        return query(null);
    }

    public List<T> query(String selection, String[] selectionArgs, String orderBy) {
        List<T> result = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        CursorWrapper cursor = CursorWrapper.wrap(db.query(contract.getTableName(),
                contract.getColumns(),
                selection,
                selectionArgs,
                null,
                null,
                orderBy));
        if(cursor.moveToFirst()){
            do {
                result.add(contract.deserialize(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public List<T> query(String selection, String... selectionArgs) {
        return query(selection, selectionArgs, null);
    }

    public T findOneById(long id) {
        List<T> result = query("ID = ?", String.valueOf(id));
        return result.size() > 0 ? result.get(0) : null;
    }

    public T findOne(String selection, String... selectionArgs) {
        List<T> result = query(selection, selectionArgs);
        return result.size() > 0 ? result.get(0) : null;
    }

    public T findOne(String selection, String[] selectionArgs, String orderBy) {
        List<T> result = query(selection, selectionArgs, orderBy);
        return result.size() > 0 ? result.get(0) : null;
    }

    public T insert(T entity) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long rowid = db.insert(contract.getTableName(), null, contract.serialize(entity));
        return findOne("rowid = ?", String.valueOf(rowid));
    }

    public Integer delete(long id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        return db.delete(contract.getTableName(), "ID = ?", new String[]{String.valueOf(id)});
    }

    public Integer update(T entity, int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        return db.update(contract.getTableName(), contract.serialize(entity), "ID = ?", new String[]{ String.valueOf(id) });
    }

    public void clear(){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(contract.getTableName(), null, null);
    }
}