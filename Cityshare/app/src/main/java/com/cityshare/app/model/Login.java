package com.cityshare.app.model;

import android.content.Context;

/**
 * Created by vagne_000 on 08/05/2017.
 */

public class Login {
    private static final int NAO_LOGADO = -1;
    private static boolean logado = false;
    private static final String nome_arquivo_preferences = "prefData";

    public static boolean is_logado() {
        return logado;
    }

    public static void LoginUsuario(Context context, int id) {
        context.getSharedPreferences( nome_arquivo_preferences, Context.MODE_PRIVATE ).edit().putInt( "idUsuario", id ).apply();
        logado = true;
    }

    public static void LogoutUsuario(Context context) {
        context.getSharedPreferences( nome_arquivo_preferences, Context.MODE_PRIVATE ).edit().remove("idUsuario").apply();
        logado = false;
    }

    public static int getId_usuario(Context context) {
        return context.getSharedPreferences( nome_arquivo_preferences, Context.MODE_PRIVATE ).getInt("idUsuario", -1);
    }
}
