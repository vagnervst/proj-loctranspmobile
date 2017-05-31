package com.cityshare.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cityshare.app.model.Server;

public class ConfiguracaoServidorActivity extends AppCompatActivity {

    EditText edtEnderecoServidor;
    Button btnSalvar;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao_servidor);

        context = this;

        edtEnderecoServidor = (EditText) findViewById(R.id.edt_endereco_servidor);
        btnSalvar = (Button) findViewById(R.id.btn_salvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Server.servidor = edtEnderecoServidor.getText().toString().trim();

                startActivity( new Intent(context, MainActivity.class) );
            }
        });
    }
}
