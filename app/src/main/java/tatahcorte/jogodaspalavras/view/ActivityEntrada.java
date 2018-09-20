package tatahcorte.jogodaspalavras.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import tatahcorte.jogodaspalavras.R;
import tatahcorte.jogodaspalavras.servico.PontuacaoServico;
import tatahcorte.jogodaspalavras.servico.UsuarioServico;
import tatahcorte.jogodaspalavras.utils.ActivityUtils;

public class ActivityEntrada extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SING_IN = 1000;
    private static final String TAG = ActivityEntrada.class.getSimpleName();

    private ImageButton abrirHistorico;
    private ImageButton limparHistorico;
    private ImageButton botaoSair;
    private Button comecarPartida;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView nomeJogador;
    private SignInButton botaoLogin;

    private final PontuacaoServico pontuacaoServico = new PontuacaoServico();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_entrada);

        nomeJogador = findViewById(R.id.nomeJogador);
        comecarPartida = findViewById(R.id.btnStart);
        abrirHistorico = findViewById(R.id.btnViewScores);
        botaoSair = findViewById(R.id.btnSair);
        limparHistorico = findViewById(R.id.btnLimparHistorico);
        botaoLogin = findViewById(R.id.sign_in_button);
        botaoLogin.setOnClickListener(this);

        nomeJogador.setText(UsuarioServico.getInstance().getUser());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        updateUI(GoogleSignIn.getLastSignedInAccount(this));

        comecarPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioServico.getInstance().setUser(nomeJogador.getText().toString());
                Intent navegarParaPartida = new Intent(ActivityEntrada.this, ActivityJogo.class);
                startActivity(navegarParaPartida);
            }
        });

        abrirHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navegarParaPartida = new Intent(ActivityEntrada.this, ActivityHistorico.class);
                startActivity(navegarParaPartida);
            }
        });

        limparHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.mostrarDialogoConfirmacao(
                    ActivityEntrada.this
                    , R.string.cuidado
                    , R.string.apagar_dados_historico
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pontuacaoServico.limparTabela();
                    }
                }, null);
            }
        });

        botaoSair.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signId();
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SING_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            updateUI(account);
        }catch (ApiException e){
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void signId() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SING_IN);
    }


    private void updateUI(GoogleSignInAccount account) {
        if (account != null){
            nomeJogador.setText(account.getDisplayName());
            botaoLogin.setVisibility(View.INVISIBLE);
        } else {
            nomeJogador.setText("Sem login ativo");
        }
    }
}