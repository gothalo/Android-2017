package cat.foixench.test.parcelable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import cat.foixench.test.parcelable.data.CustomerData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        Button btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random r = new Random();
                // get the id of the customer
                long id = (long) r.nextInt (100);
                // get the name of the customer from the names array
                String [] names = getResources().getStringArray(R.array.namesArray);
                String name = names[r.nextInt(10)];
                // get the city of the customer
                String [] cities = getResources().getStringArray(R.array.citiesArray);
                String city = cities[r.nextInt(10)];

                Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
                intent.putExtra(CustomerActivity.CUSTOMER_EXTRA, new CustomerData(id, name, city));

                startActivity(intent);

            }
        });
    }
}
