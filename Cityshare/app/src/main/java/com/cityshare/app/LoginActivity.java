package com.cityshare.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Login;
import com.cityshare.app.model.Server;
import com.cityshare.app.model.Usuario;
import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText txt_email;
    EditText txt_senha;
    ProgressBar progress;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        btn_login = (Button) findViewById( R.id.btn_login );
        txt_email = (EditText) findViewById( R.id.txtEmail );
        txt_senha = (EditText) findViewById( R.id.txtSenha );
        progress = (ProgressBar) findViewById( R.id.pb_progress );
        progress.setVisibility( View.INVISIBLE );

        btn_login.setOnClickListener( new AcaoBotaoLogin() );
    }

    private class AcaoBotaoLogin implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            txt_email.setError(null);
            txt_senha.setError(null);

            if( txt_email.getText().toString().isEmpty() ) {
                txt_email.setError("Digite seu email");
                return;
            }

            if( txt_senha.getText().toString().isEmpty() ) {
                txt_senha.setError("Digite sua senha");
                return;
            }

            new VerificarAutenticacao().execute();
        }
    }

    private class VerificarAutenticacao extends AsyncTask<Void, Void, Void> {
        String email, senha;
        Usuario usuario_encontrado = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            email = txt_email.getText().toString().trim();
            senha = txt_senha.getText().toString().trim();

            progress.setVisibility( View.VISIBLE );
        }

        @Override
        protected Void doInBackground(Void... params) {
            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("email", email);
            parametros.put("senha", senha);

            String url = Server.servidor + "apis/android/login.php";
            String json = HttpRequest.post( url, parametros );

            Log.d( "DEBUG", json );
            usuario_encontrado = new Gson().fromJson(json, Usuario.class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.setVisibility( View.INVISIBLE );

            if( usuario_encontrado.getId() != 0 ) {
                Login.LoginUsuario( context, usuario_encontrado.getId() );

                Intent listagem_anuncios = new Intent( context, MainActivity.class );
                startActivity( listagem_anuncios );
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder( context );

                builder.setMessage("Email ou senha incorretos").setTitle("Houve um erro");

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

}
