package com.cityshare.app.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.cityshare.app.MainActivity;
import com.cityshare.app.PedidosActivity;
import com.cityshare.app.R;
import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Login;
import com.cityshare.app.model.Notificacao;
import com.cityshare.app.model.Server;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class NotificacoesService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {


                HashMap<String, String> parametros = new HashMap<String, String>();
                String url = Server.servidor + "apis/get_notificacoes_usuario.php";
                int id_usuario = Login.getId_usuario(getApplicationContext());

                parametros.put("where", "n.idUsuarioDestinatario = " + id_usuario + " AND n.visualizada = 0");

                String json = HttpRequest.post(url, parametros);

                Notificacao[] listaNotificacoes = new Gson().fromJson(json, Notificacao[].class);

                for( int i = 0; i < listaNotificacoes.length; ++i ) {
                    Notificacao notificacao = listaNotificacoes[i];

                    if( notificacao.getVisualizada() == 0 ) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext()).
                                setSmallIcon( R.mipmap.ic_launcher ).
                                setContentTitle( notificacao.getTipoNotificacao() );

                        int tipoNotificacao = notificacao.getIdTipoNotificacao();
                        if( tipoNotificacao == Notificacao.SOLICITACAO ) {
                            builder.setContentText( String.format(Locale.getDefault(), "Há uma nova solicitação de %s %s", notificacao.getNomeRemetente(), notificacao.getSobrenomeRemetente().charAt(0)) );
                        } else if( tipoNotificacao == Notificacao.ATUALIZACAO_PEDIDO ) {
                            builder.setContentText( "Há uma nova atualização de pedido" );
                        } else if( tipoNotificacao == Notificacao.AVALIACAO ) {
                            builder.setContentText( String.format(Locale.getDefault(), "%s avaliou você!", notificacao.getNomeRemetente() + notificacao.getSobrenomeRemetente().charAt(0)) );
                        }

                        Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());

                        stackBuilder.addParentStack( MainActivity.class );

                        stackBuilder.addNextIntent( intent );

                        Intent pedidosWin = new Intent( getApplicationContext(), PedidosActivity.class );
                        pedidosWin.putExtra("idNotificacao", notificacao.getId());

                        PendingIntent pendingIntent = PendingIntent.getActivity( getApplicationContext(), 0, pedidosWin, 0);

                        builder.setContentIntent( pendingIntent );

                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify( i, builder.build());
                    }
                }
            }
        }, 0, 5000);

        return super.onStartCommand(intent, flags, startId);
    }

    private class LerNotificacao extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        int idNotificacao;
        Boolean resultado;

        private LerNotificacao(int notificacao) {
            this.idNotificacao = notificacao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(getApplicationContext(), "Carregando", "Aguarde");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String url = Server.servidor + "apis/android/set_notificacao.php";

            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("idNotificacao", String.valueOf(this.idNotificacao));

            String json = HttpRequest.post(url, parametros);
            resultado = new Gson().fromJson(json, Boolean.class);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
        }
    }
}
