package tatahcorte.jogodaspalavras.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import tatahcorte.jogodaspalavras.App;
import tatahcorte.jogodaspalavras.BuildConfig;
import tatahcorte.jogodaspalavras.R;
import tatahcorte.jogodaspalavras.contract.PontuacaoContract;
import tatahcorte.jogodaspalavras.contract.SinonimoContract;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static DatabaseHelper instance;
    private final Context context;

    private DatabaseHelper(Context context, String name) {
        super(context, name, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SinonimoContract.DDL);
        db.execSQL(PontuacaoContract.DDL);
        try {
            Log.d(TAG, "inserted " + insertFromFile(db, context, R.raw.insert_inicial) + " rows from insert_inicial");
        } catch (IOException e) {
            Log.e(TAG, "nao pode executar insert inicial ", e);
        }
    }

    public int insertFromFile(SQLiteDatabase db, Context context, int resourceId) throws IOException {
        // Reseting Counter
        int result = 0;

        // Open the resource
        InputStream insertsStream = context.getResources().openRawResource(resourceId);
        BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));
        db.beginTransaction();
        String insertStmt;
        // Iterate through lines (assuming each insert has its own line and theres no other stuff)
        while ((insertStmt = insertReader.readLine()) != null) {
            db.execSQL(insertStmt);
            result++;
        }
        insertReader.close();
        db.setTransactionSuccessful();
        db.endTransaction();

        // returning number of inserted rows
        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static DatabaseHelper getInstance() {
        if(instance == null){
            instance = new DatabaseHelper(App.getInstance(), BuildConfig.DB_NAME);
        }
        return instance;
    }
}
