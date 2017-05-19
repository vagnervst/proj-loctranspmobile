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

import com.cityshare.app.model.HistoricoAlteracaoPedido;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vagne_000 on 18/05/2017.
 */

public class HistoricoPedidoAdapter extends ArrayAdapter<HistoricoAlteracaoPedido> {

    int resource;
    public HistoricoPedidoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull HistoricoAlteracaoPedido[] objects) {
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

        HistoricoAlteracaoPedido alteracao = getItem( position );

        TextView txt_data_ocorrencia = (TextView) v.findViewById(R.id.txt_data_ocorrencia);
        TextView txt_status_pedido = (TextView) v.findViewById(R.id.txt_status_pedido);

        Date data_ocorrencia = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());

            if( alteracao.getDataOcorrencia() != null ) {
                data_ocorrencia = formatter.parse( alteracao.getDataOcorrencia() );
                txt_data_ocorrencia.setText( formatter.format( data_ocorrencia ) );

                txt_status_pedido.setText( String.format(Locale.getDefault(), "%s", alteracao.getTituloStatus()) );
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return v;
    }
}
