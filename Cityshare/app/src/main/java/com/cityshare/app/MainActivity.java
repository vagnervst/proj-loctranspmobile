package com.cityshare.app;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.cityshare.app.model.AnunciarActivity;
import com.cityshare.app.model.Anuncio;
import com.cityshare.app.model.HttpRequest;
import com.cityshare.app.model.Login;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SearchView searchView;
    Context context;
    ListView lv_anuncios;

    List<Anuncio> anuncios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        lv_anuncios = (ListView) findViewById( R.id.lv_anuncios );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AnunciarActivity.class);
                startActivity( intent );
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if( !Login.is_logado( context ) ) {

            for( int i = 0; i < navigationView.getMenu().size(); ++i ) {
                MenuItem item = navigationView.getMenu().getItem(i);

                if( item.getItemId() != R.id.nav_entrar ) item.setEnabled(false);
                if( item.getItemId() == R.id.nav_sair ) item.setVisible(false);
            }

        } else {

            for( int i = 0; i < navigationView.getMenu().size(); ++i ) {
                MenuItem item = navigationView.getMenu().getItem(i);

                if( item.getItemId() != R.id.nav_entrar ) item.setEnabled(true);
                if( item.getItemId() == R.id.nav_entrar ) item.setVisible(false);
            }

        }

        new BuscaAnuncios().execute();

        lv_anuncios.setOnItemClickListener( new ListaAnunciosAction() );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItemBusca = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItemBusca.getActionView();

        searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()) );
        searchView.setIconifiedByDefault(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_search:
                searchView.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput( InputMethodManager.SHOW_FORCED, inputMethodManager.HIDE_IMPLICIT_ONLY );
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if ( id == R.id.nav_entrar ) {
            Intent loginWin = new Intent( context, LoginActivity.class );
            startActivity( loginWin );
        } else if (id == R.id.nav_pedidos) {
            Intent pedidosWin = new Intent( context, PedidosActivity.class );
            startActivity( pedidosWin );
        } else if (id == R.id.nav_anuncios) {

        } else if (id == R.id.nav_solicitacoes) {

        } else if (id == R.id.nav_notificacoes) {

        } else if (id == R.id.nav_conta) {

        } else if (id == R.id.nav_sair) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void preencherListaAnuncios(){
        AnunciosAdapter adapter = new AnunciosAdapter(context, R.layout.list_view_item_anuncio, anuncios);

        lv_anuncios.setAdapter(adapter);
    }

    private class ListaAnunciosAction implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Anuncio item_selecionado = (Anuncio) lv_anuncios.getItemAtPosition(position);

            Intent detalhesAnuncioWin = new Intent(context, DetalhesAnuncio.class);
            detalhesAnuncioWin.putExtra("idAnuncio", item_selecionado.getId());

            startActivity( detalhesAnuncioWin );
        }
    }

    private class BuscaAnuncios extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(context, "Carregando", "Aguarde", true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json = HttpRequest.get( getString(R.string.serverAddr) + "apis/android/lista_anuncios.php");

            anuncios = Arrays.asList( new Gson().fromJson(json, Anuncio[].class) );

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            preencherListaAnuncios();

            progress.hide();
        }
    }

}
