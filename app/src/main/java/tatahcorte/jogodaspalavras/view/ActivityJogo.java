package tatahcorte.jogodaspalavras.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import tatahcorte.jogodaspalavras.R;
import tatahcorte.jogodaspalavras.utils.ActivityUtils;
import tatahcorte.jogodaspalavras.viewModel.PartidaViewModel;

public class ActivityJogo extends AppCompatActivity implements PartidaViewModel.ActivityJogoInterface {

    private Button btnResponder;
    private EditText editTextResposta;
    private TextView textViewDicas;
    private TextView textViewPalavraOculta;
    private TextView textViewNivel;
    private TextView textViewPontos;
    private ImageView coracao1;
    private ImageView coracao2;
    private ImageView coracao3;
    private ImageView coracao4;
    private ImageView coracao5;

    private PartidaViewModel partidaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);

        btnResponder = findViewById(R.id.btnResposta);
        editTextResposta = findViewById(R.id.editTextResposta);
        textViewDicas =  findViewById(R.id.textViewDicas);
        textViewPalavraOculta = findViewById(R.id.textViewPalavraOculta);
        textViewNivel = findViewById(R.id.textViewNivel);
        textViewPontos = findViewById(R.id.textViewPontos);
        coracao1 = findViewById(R.id.coracao1);
        coracao2 = findViewById(R.id.coracao2);
        coracao3 = findViewById(R.id.coracao3);
        coracao4 = findViewById(R.id.coracao4);
        coracao5 = findViewById(R.id.coracao5);

        partidaViewModel = ViewModelProviders.of(this).get(PartidaViewModel.class);
        partidaViewModel.atualizaDadosDaPartida(this);

        btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                partidaViewModel.onRespostaClick(
                    ActivityJogo.this
                    , editTextResposta.getText().toString()

                );
            }
        });

        editTextResposta.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    partidaViewModel.onRespostaClick(ActivityJogo.this, editTextResposta.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        partidaViewModel.onSair();
    }

    @Override
    public void setDica(String dica) {
        textViewDicas.setText(dica);
    }

    @Override
    public void setTentativa(String tentativa) {
        editTextResposta.setText(tentativa);
    }

    @Override
    public void setPalavraOculta(String palavraOculta) {
        textViewPalavraOculta.setText(palavraOculta);
    }

    @Override
    public void setCoracoes(int coracoes) {
        Drawable fullHeart = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_favorite_32dp, getTheme());
        Drawable emptyHeart = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_favorite_border_32dp, getTheme());
        coracao5.setImageDrawable(coracoes == 5 ? fullHeart : emptyHeart);
        coracao4.setImageDrawable(coracoes >= 4 ? fullHeart : emptyHeart);
        coracao3.setImageDrawable(coracoes >= 3 ? fullHeart : emptyHeart);
        coracao2.setImageDrawable(coracoes >= 2 ? fullHeart : emptyHeart);
        coracao1.setImageDrawable(coracoes >= 1 ? fullHeart : emptyHeart);
    }

    @Override
    public void setNivel(int nivel) {
        textViewNivel.setText(String.valueOf(nivel));
    }

    @Override
    public void mostrarMensagemDeFimDePartida(String sinonimoEscondido, long pontos, DialogInterface.OnClickListener callback) {
        ActivityUtils.mostrarDialogoFimDeJogo(
            this
            , R.string.fim_de_jogo
            , R.string.mensagem_fim_de_jogo
            , String.valueOf(pontos)
            , getString(R.string.palavra_oculta, sinonimoEscondido)
            , callback, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onBackPressed();
                }
            });
    }

    @Override
    public void mostrarMensagemParabens(String sinonimoEscondido, long pontos, DialogInterface.OnClickListener callback) {
        ActivityUtils.mostrarDialogoFimDeJogo(
            this
            , R.string.voce_acertou
            , R.string.mensagem_jogo_ganho
            , "+" + String.valueOf(pontos)
            , null
            , callback
            , null);
    }

    @Override
    public void setPontos(long pontos) {
        textViewPontos.setText(String.valueOf(pontos));
    }
}