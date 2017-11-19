package cat.foixench.ceina.rest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import cat.foixench.ceina.rest.R;
import cat.foixench.ceina.rest.data.UserData;
import cat.foixench.ceina.rest.network.VolleyManageUsers;

public class UserDetailActivity extends AppCompatActivity {

    private UserData userData;
    public static final String EXTRA_USER_ID ="EXTRA_USER_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                // crear un nuevo usuario
                VolleyManageUsers volleyManageUsers = VolleyManageUsers.getInstance(UserDetailActivity.this);
                volleyManageUsers.updateUser(userData, new VolleyManageUsers.GetUserCallback() {
                    @Override
                    public void onUserLoaded(UserData userData) {
                        Snackbar.make(view, "User Updated", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onError() {
                        Snackbar.make(view, "Error", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // recuperamos la información del usuario
        Intent intent = getIntent();

        int userId = intent.getIntExtra(EXTRA_USER_ID, 0);


        VolleyManageUsers volleyManageUsers = VolleyManageUsers.getInstance (this);
        volleyManageUsers.getUser(userId, new VolleyManageUsers.GetUserCallback() {
            @Override
            public void onUserLoaded(UserData userData) {
                UserDetailActivity.this.userData = userData;
                showUser (userData);
            }

            @Override
            public void onError() {

            }
        });

    }

    private void showUser (UserData userData) {
        TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvEmail = (TextView) findViewById(R.id.tvEmail);
        TextView tvPhone = (TextView) findViewById(R.id.tvPhone);
        TextView tvWebsite = (TextView) findViewById(R.id.tvWebsite);

        TextView tvCompanyName = (TextView) findViewById(R.id.tvCompanyName);
        TextView tvCompanyCatchPhrase = (TextView) findViewById(R.id.tvCompanyCatchPhrase);
        TextView tvCompanyBS = (TextView) findViewById(R.id.tvCompanyBS);

        TextView tvAddressCity = (TextView) findViewById(R.id.tvAddressCity);
        TextView tvAddressZipCode = (TextView) findViewById(R.id.tvAddressZipCode);
        TextView tvAddressSuite = (TextView) findViewById(R.id.tvAddressSuite);
        TextView tvAddressStreet = (TextView) findViewById(R.id.tvAddressStreet);

        tvUserName.setText(userData.getUserName());
        tvName.setText(userData.getName());
        tvEmail.setText(userData.geteMail());
        tvPhone.setText(userData.getPhone());
        tvWebsite.setText(userData.getWebsite());

        tvCompanyName.setText(userData.getCompany().getName());
        tvCompanyCatchPhrase.setText(userData.getCompany().getCatchPhrase());
        tvCompanyBS.setText(userData.getCompany().getBs());

        tvAddressCity.setText(userData.getAddress().getCity());
        tvAddressZipCode.setText(userData.getAddress().getZipCode());
        tvAddressSuite.setText(userData.getAddress().getSuite());
        tvAddressStreet.setText(userData.getAddress().getStreet());

    }

}
