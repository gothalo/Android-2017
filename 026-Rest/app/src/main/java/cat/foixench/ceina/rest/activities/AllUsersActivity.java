package cat.foixench.ceina.rest.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import cat.foixench.ceina.rest.R;
import cat.foixench.ceina.rest.adapters.UsersAdapter;
import cat.foixench.ceina.rest.listeners.RecyclerTouchListener;
import cat.foixench.ceina.rest.network.GetAllUsersTask;

public class AllUsersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // revisar los permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET}, 1);
        }


        // configurar la recyclerView
        recyclerView =  (RecyclerView) findViewById(R.id.rvListContacts);

        // layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // asignamos el adapter
        UsersAdapter adapter = new UsersAdapter ();
        recyclerView.setAdapter(adapter);

        // definimos los eventos de selección del listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TextView id = (TextView) view.findViewById(R.id.tvId);

                Intent intent = new Intent(AllUsersActivity.this, UserDetailActivity.class);
                intent.putExtra(UserDetailActivity.EXTRA_USER_ID, Integer.parseInt(id.getText().toString()));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        // ocultamos o mostramos el floating action button cuando hacemos scroll en la lista
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0 ||dy<0 && fab.isShown())
                    fab.hide();
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // iniciamos el proceso de carga de la listview
        GetAllUsersTask tsk = new GetAllUsersTask(recyclerView);
        tsk.execute();
    }
}
