package cat.foixench.ceina.istambul.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cat.foixench.ceina.istambul.R;

/**
 * Created by llorenc on 02/11/2017.
 */

public class ContentFragment extends Fragment {

    private int contentCode;


    public static ContentFragment newInstance () {
        return new ContentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.changeContent(this.contentCode);

    }

    public void setContentCode (int contentCode) {
        this.contentCode = contentCode;
    }

    public void changeContent(int contentCode) {

        TextView textView = getView().findViewById(R.id.txtFragment);
        ImageView imageView = getView().findViewById(R.id.imgFragment);

        Log.d(MainActivity.APP_TAG, "content code" + contentCode);

        switch (contentCode) {
            case 0:
                textView.setText(R.string.hayasofia);
                imageView.setImageResource(R.drawable.hayasofia);
                break;
            case 1:
                textView.setText(R.string.ortakoy);
                imageView.setImageResource(R.drawable.ortakoy);
                break;
            case 2 :
                textView.setText(R.string.suleymaniye);
                imageView.setImageResource(R.drawable.suleymaniye);
                break;
            case 3 :
                textView.setText(R.string.sultanahmet);
                imageView.setImageResource(R.drawable.sultanahmet);
                break;
        }
    }
}
