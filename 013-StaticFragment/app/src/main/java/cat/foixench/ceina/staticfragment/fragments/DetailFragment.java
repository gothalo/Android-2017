package cat.foixench.ceina.staticfragment.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import cat.foixench.ceina.staticfragment.R;

/**
 * Created by llorenc on 28/10/2017.
 */

public class DetailFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment_layout, container, false);
        return view;
    }

    public void setText (String header, String detail) {

        TextView tvHeader = (TextView) getView().findViewById(R.id.header);
        TextView tvContent = (TextView) getView().findViewById(R.id.content);

        tvHeader.setText(header);
        tvContent.setText(detail);


    }
}
