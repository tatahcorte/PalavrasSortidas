package tatahcorte.jogodaspalavras.contract;

import android.content.ContentValues;

import tatahcorte.jogodaspalavras.entidade.Sinonimo;
import tatahcorte.jogodaspalavras.utils.CursorWrapper;

public class SinonimoContract implements Contract<Sinonimo> {

    private static final String TABLE_NAME = "TBL_SINONIMOS";
    private static final String ID = "ID";
    private static final String SINONIMOS = "SINONIMOS";

    public static final String DDL =
        "CREATE TABLE " + TABLE_NAME + "(" +
        " " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        " " + SINONIMOS + " TEXT " +
        ");";

    @Override
    public Sinonimo deserialize(CursorWrapper cursor) {
        Sinonimo sinonimo = new Sinonimo();
        sinonimo.setId(cursor.getInt(ID, 0));
        sinonimo.setSinonimos(cursor.getString(SINONIMOS, ""));
        return sinonimo;
    }

    @Override
    public ContentValues serialize(Sinonimo entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SINONIMOS, entity.getId());
        return contentValues;
    }

    @Override
    public String[] getColumns() {
        return new String[]{ ID, SINONIMOS };
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
