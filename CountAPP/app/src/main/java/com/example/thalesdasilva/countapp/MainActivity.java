package com.example.thalesdasilva.countapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.thalesdasilva.countapp.database.DataBase;
import com.example.thalesdasilva.countapp.dominio.RepositorioUsuario;
import com.example.thalesdasilva.countapp.fragments.Calculadora;
import com.example.thalesdasilva.countapp.fragments.Calendario;
import com.example.thalesdasilva.countapp.fragments.Conta;
import com.example.thalesdasilva.countapp.fragments.Grafico;
import com.example.thalesdasilva.countapp.fragments.Perfil;
import com.example.thalesdasilva.countapp.fragments.VisaoGeral;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        VisaoGeral.OnFragmentInteractionListener {

    //Atributos
    private FloatingActionButton fabPlus;
    private FloatingActionButton fabReceitas;
    private FloatingActionButton fabDespesas;

    private TextView txtReceitas;
    private TextView txtDespesas;

    private Animation FabOpen;
    private Animation FabClose;
    private Animation FabRClookwise;
    private Animation FabRanticlookwise;

    private Boolean isOpen = false;

    public String emailPerfil;

    private DataBase database;
    private SQLiteDatabase conn;

    public RepositorioUsuario repositorioUsuario;

    public TextView email;

    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            fabPlus = (FloatingActionButton) findViewById(R.id.fabPlus);
            fabReceitas = (FloatingActionButton) findViewById(R.id.fabReceitas);
            fabDespesas = (FloatingActionButton) findViewById(R.id.fabDespesas);

            txtReceitas = (TextView) findViewById(R.id.txtReceitas);
            txtDespesas = (TextView) findViewById(R.id.txtDespesas);

            FabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
            FabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

            FabRClookwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
            FabRanticlookwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

            final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabPlus);
            final FloatingActionButton fabR = (FloatingActionButton) findViewById(R.id.fabReceitas);
            final FloatingActionButton fabD = (FloatingActionButton) findViewById(R.id.fabDespesas);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (isOpen) {
                        fabReceitas.startAnimation(FabClose);
                        fabDespesas.startAnimation(FabClose);
                        fabPlus.startAnimation(FabRanticlookwise);
                        fabDespesas.setClickable(false);
                        fabReceitas.setClickable(false);
                        txtReceitas.setVisibility(View.INVISIBLE);
                        txtDespesas.setVisibility(View.INVISIBLE);
                        isOpen = false;
                    } else {
                        fabReceitas.startAnimation(FabOpen);
                        fabDespesas.startAnimation(FabOpen);
                        fabPlus.startAnimation(FabRClookwise);
                        fabDespesas.setClickable(true);
                        fabReceitas.setClickable(true);
                        txtReceitas.setVisibility(View.VISIBLE);
                        txtDespesas.setVisibility(View.VISIBLE);
                        isOpen = true;
                    }

                }
            });

            fabR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(getApplicationContext(), ActReceita.class);
                    startActivity(it);
                    fabPlus.startAnimation(FabRanticlookwise);
                    fabReceitas.startAnimation(FabClose);
                    fabDespesas.startAnimation(FabClose);
                    txtReceitas.setVisibility(View.INVISIBLE);
                    txtDespesas.setVisibility(View.INVISIBLE);
                    fabDespesas.setClickable(false);
                    fabReceitas.setClickable(false);
                    isOpen = false;
                }
            });

            fabD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(getApplicationContext(), ActDespesa.class);
                    startActivity(it);
                    fabPlus.startAnimation(FabRanticlookwise);
                    fabReceitas.startAnimation(FabClose);
                    fabDespesas.startAnimation(FabClose);
                    txtReceitas.setVisibility(View.INVISIBLE);
                    txtDespesas.setVisibility(View.INVISIBLE);
                    fabDespesas.setClickable(false);
                    fabReceitas.setClickable(false);
                    isOpen = false;
                }
            });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            View header = navigationView.getHeaderView(0);

            email = (TextView) header.findViewById(R.id.txtEmail);

            database = new DataBase(MainActivity.this);
            conn = database.getWritableDatabase();

            repositorioUsuario = new RepositorioUsuario(conn);

            //Ele começa na tela que você quer
            VisaoGeral vG = new VisaoGeral();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.main, vG, vG.getTag()).commit();

            super.onCreate(savedInstanceState);
            setContentView(R.layout.app_bar_main);

//            View headerView = navigationView.getHeaderView(0);
//            DataBase dataBase = new DataBase(MainActivity.this);
//            String email = dataBase.buscarPerfil(ActLogin.nome).get(1);
//            TextView txtEmail = (TextView) headerView.findViewById(R.id.txtEmail);
//            txtEmail.setText(email);

        } catch (Exception e) {
//            MessageBox.show(MainActivity.this, "Erro", "Erro: " + e.getMessage());
        }

    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        VisaoGeral vG = new VisaoGeral();
        Fragment fragment = fm.getFragments().get(0);
        if (!(fragment instanceof VisaoGeral)) {
            //Ele começa na tela que você quer
            fm.beginTransaction().replace(R.id.main, vG, vG.getTag()).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_ajuda) {
            Intent it = new Intent(MainActivity.this, ActAjuda.class);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.visaoGeral) {

            VisaoGeral vG = new VisaoGeral();//new Visaogeral() instancia também
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.main, vG, vG.getTag()).commit();

            manager.addOnBackStackChangedListener(null);

        } else if (id == R.id.perfil) {

//            FragmentManager fm = getSupportFragmentManager();
//            fm.beginTransaction().replace(R.id.main, new Perfil()).commit();

            Perfil perfil = new Perfil();
            Bundle bundle = getIntent().getExtras();
            perfil.setArguments(bundle);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.main, perfil, perfil.getTag()).commit();

            manager.addOnBackStackChangedListener(null);

        } else if (id == R.id.gerarGrafico) {

            Grafico grafico = new Grafico();//new Grafico() instancia também
            Bundle bundle = getIntent().getExtras();
            grafico.setArguments(bundle);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.main, grafico, grafico.getTag()).commit();

            manager.addOnBackStackChangedListener(null);

            //Toast.makeText(MainActivity.this, "Grafico Gráfico.", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.conta) {

            Conta conta = new Conta();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main, conta, conta.getTag()).commit();

            fragmentManager.addOnBackStackChangedListener(null);

        } else if (id == R.id.receita) {

            Intent it = new Intent(getApplicationContext(), ActReceita.class);
            startActivity(it);

        } else if (id == R.id.despesa) {

            Intent it = new Intent(getApplicationContext(), ActDespesa.class);
            startActivity(it);

        } else if (id == R.id.calendario) {

            Calendario calendario = new Calendario();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.main, calendario, calendario.getTag()).commit();

            manager.addOnBackStackChangedListener(null);

        } else if (id == R.id.calculadora) {

            Calculadora calculadora = new Calculadora();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.main, calculadora, calculadora.getTag()).commit();

            manager.addOnBackStackChangedListener(null);

        } else if (id == R.id.sair) {

            AlertDialog.Builder a = new AlertDialog.Builder(MainActivity.this);
            a.setTitle("Mensagem");
            a.setMessage("Deseja realmente sair?");
            a.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            a.setNegativeButton("NÃO", null);
            a.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<String> dados = database.buscarPerfil(ActLogin.nome);

        emailPerfil = String.valueOf(dados.get(1)).toString();

        email.setText(String.valueOf(emailPerfil));
    }

}//fim da classe
