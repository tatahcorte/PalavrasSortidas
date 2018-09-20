package tatahcorte.jogodaspalavras.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import tatahcorte.jogodaspalavras.R;

public class ActivityUtils {

    public static void mostrarDialogoConfirmacao(
            Context context
            , @StringRes int titulo
            , @StringRes int mensagem
            , DialogInterface.OnClickListener ok
            , DialogInterface.OnClickListener cancelar){
        new AlertDialog.Builder(context)
            .setTitle(titulo)
            .setMessage(mensagem)
            .setPositiveButton(R.string.ok, ok)
            .setNegativeButton(R.string.cancelar, cancelar)
            .show();
    }

    public static void mostrarDialogoFimDeJogo(
            Context context
            , @StringRes int titulo
            , @StringRes int mensagemPrimaria
            , String pontuacao
            , String mensagemSecundaria
            , DialogInterface.OnClickListener continuar
            , DialogInterface.OnClickListener voltar
    ){
        View view = LayoutInflater.from(context).inflate(R.layout.dialogo_fim_de_jogo, null);
        ((TextView)view.findViewById(R.id.textViewMensagemPrimaria)).setText(mensagemPrimaria);
        ((TextView)view.findViewById(R.id.textViewPontos)).setText(pontuacao);
        ((TextView)view.findViewById(R.id.textViewMensagemSecundaria)).setText(mensagemSecundaria);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(view)
            .setPositiveButton(R.string.continuar, continuar)
            .setCancelable(false);
        if(voltar != null){
            builder.setNegativeButton(R.string.voltar, voltar);
        }
        builder.show();
    }
}
