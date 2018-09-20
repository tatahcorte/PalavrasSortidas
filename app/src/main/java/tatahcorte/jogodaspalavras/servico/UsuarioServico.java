package tatahcorte.jogodaspalavras.servico;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import tatahcorte.jogodaspalavras.App;

public class UsuarioServico {

    private SharedPreferences sharedPreferences;
    private static UsuarioServico instance;

    public UsuarioServico(SharedPreferences sharedPreferences) {

        this.sharedPreferences = sharedPreferences;
    }
    // Metodo Estatico, que vai servir p/ toda a aplicacao
    public static UsuarioServico getInstance() {
        if (instance == null){
            instance = new UsuarioServico(PreferenceManager.getDefaultSharedPreferences(App.getInstance()));
        }
        return instance;
    }

    public String getUser(){
        return sharedPreferences.getString("USUARIO_LOGADO", null);
    }
    public void setUser(String user){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USUARIO_LOGADO", user);

        editor.apply();
    }


}
