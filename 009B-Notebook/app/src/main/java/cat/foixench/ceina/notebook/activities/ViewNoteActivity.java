package cat.foixench.ceina.notebook.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cat.foixench.ceina.notebook.R;
import cat.foixench.ceina.notebook.fragments.NoteDetailFragment;
import cat.foixench.ceina.notebook.util.AppConstants;

/**
 * Created by llorenc on 30/10/2017.
 */

public class ViewNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.note_detail_activity_layout);

        // buscamos si tenemos algun extra en esta activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long id = extras.getLong (AppConstants.EXTRA_NOTE_ID);
            if (id > 0) {
                NoteDetailFragment detailFragment = (NoteDetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);

                if (detailFragment != null) {
                    detailFragment.setNoteId(id);
                }
            }
        }

    }
}
