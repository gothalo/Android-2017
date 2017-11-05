package cat.foixench.ceina.recyclerview.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cat.foixench.ceina.recyclerview.R;
import cat.foixench.ceina.recyclerview.ui.DividerItemDecoration;
import cat.foixench.ceina.recyclerview.ui.RecyclerTouchListener;
import cat.foixench.ceina.recyclerview.adapters.PeliculasAdapter;
import cat.foixench.ceina.recyclerview.data.Pelicula;

public class MainActivity extends AppCompatActivity {

    List<Pelicula> listaPeliculas;
    PeliculasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listaPeliculas = new ArrayList<Pelicula>();

        adapter = new PeliculasAdapter(listaPeliculas);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // asignamos una animación por defecto a las acciones de añadir, modificar, mover y eliminar
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapter);

        // cargamos los datos en el adapter
        prepareMovieData();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Pelicula pelicula = listaPeliculas.get(position);
                Toast.makeText(getApplicationContext(), pelicula.getTitulo() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void prepareMovieData() {
        Pelicula pelicula = new Pelicula("Mad Max: Fury Road", "Action & Adventure", "2015");
        listaPeliculas.add(pelicula);

        pelicula = new Pelicula("Inside Out", "Animation, Kids & Family", "2015");
        listaPeliculas.add(pelicula);

        pelicula = new Pelicula("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        listaPeliculas.add(pelicula);

        pelicula = new Pelicula("Shaun the Sheep", "Animation", "2015");
        listaPeliculas.add(pelicula);

        pelicula = new Pelicula("The Martian", "Science Fiction & Fantasy", "2015");
        listaPeliculas.add(pelicula);

        pelicula = new Pelicula("Mission: Impossible Rogue Nation", "Action", "2015");
        listaPeliculas.add(pelicula);

        pelicula = new Pelicula("Up", "Animation", "2009");
        listaPeliculas.add(pelicula);

        pelicula = new Pelicula("Star Trek", "Science Fiction", "2009");
        listaPeliculas.add(pelicula);

        pelicula = new Pelicula("The LEGO Pelicula", "Animation", "2014");
        listaPeliculas.add(pelicula);

        pelicula = new Pelicula("Iron Man", "Action & Adventure", "2008");
        listaPeliculas.add(pelicula);

        pelicula = new Pelicula("Aliens", "Science Fiction", "1986");
        listaPeliculas.add(pelicula);

        pelicula = new Pelicula("Chicken Run", "Animation", "2000");
        listaPeliculas.add(pelicula);

        pelicula = new Pelicula("Back to the Future", "Science Fiction", "1985");
        listaPeliculas.add(pelicula);

        pelicula = new Pelicula("Raiders of the Lost Ark", "Action & Adventure", "1981");
        listaPeliculas.add(pelicula);

        pelicula = new Pelicula("Goldfinger", "Action & Adventure", "1965");
        listaPeliculas.add(pelicula);

        pelicula = new Pelicula("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        listaPeliculas.add(pelicula);

        adapter.notifyDataSetChanged();

    }
}
