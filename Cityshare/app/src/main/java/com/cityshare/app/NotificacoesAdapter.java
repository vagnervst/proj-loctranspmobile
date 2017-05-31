package com.cityshare.app;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cityshare.app.model.Notificacao;

import java.util.List;

/**
 * Created by vagne_000 on 31/05/2017.
 */

public class NotificacoesAdapter extends ArrayAdapter<Notificacao> {

    private int resource;
    public NotificacoesAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Notificacao> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if( v == null ) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(this.resource, null);
        }

        Notificacao notificacao = getItem(position);

        TextView txt_titulo_notificacao = (TextView) v.findViewById(R.id.txt_titulo_notificacao);
        TextView txt_descricao_notificacao = (TextView) v.findViewById(R.id.txt_descricao_notificacao);
        TextView txt_tempo_notificacao = (TextView) v.findViewById(R.id.txt_tempo_notificacao);

        txt_titulo_notificacao.setText( notificacao.getTipoNotificacao() );
        txt_descricao_notificacao.setText( notificacao.getMensagem() );

        return v;
    }
}
