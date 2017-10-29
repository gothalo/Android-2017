package cat.foixench.test.parcelable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cat.foixench.test.parcelable.data.CustomerData;

public class CustomerActivity extends AppCompatActivity {

    public static final String CUSTOMER_EXTRA = "customer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_activity_layout);

        // recuperar los datos del cliente del Intent
        Intent intent = getIntent();

        CustomerData customer = intent.getParcelableExtra(CUSTOMER_EXTRA);

        TextView tvId = (TextView) findViewById(R.id.contentId);
        TextView tvName = (TextView) findViewById(R.id.contentName);
        TextView tvAddress = (TextView) findViewById(R.id.contentAddress);

        if (customer != null) {
            tvId.setText(Long.toString(customer.getId()));
            tvName.setText(customer.getCustomerName());
            tvAddress.setText(customer.getAddress());
        }


    }
}
