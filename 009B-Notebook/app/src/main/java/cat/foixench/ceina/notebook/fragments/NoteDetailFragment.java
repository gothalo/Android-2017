package cat.foixench.ceina.notebook.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cat.foixench.ceina.notebook.R;
import cat.foixench.ceina.notebook.data.Note;
import cat.foixench.ceina.notebook.db.DataBaseHelper;
import cat.foixench.ceina.notebook.util.AppConstants;

/**
 * Created by llorenc on 30/10/2017.
 */

public class NoteDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_detail_fragment_layout, container, false);

    }

    public void setNoteId (long id) {
        // cargar la nota
        DataBaseHelper dbHelper = new DataBaseHelper(getActivity());

        Note note = dbHelper.getNote(id);

        if (note != null) {

            TextView tvHeader = (TextView) getView().findViewById(R.id.tvNoteTile);
            TextView tvContent = (TextView) getView().findViewById(R.id.tvNoteContent);
            TextView tvCreated = (TextView) getView().findViewById(R.id.tvNoteDateCreated);
            TextView tvUpdated = (TextView) getView().findViewById(R.id.tvNoteDateUpdated);
            TextView tvId = (TextView) getView().findViewById(R.id.tvNoteId);

            tvHeader.setText(note.getTitle());
            tvContent.setText(note.getContent());
            tvCreated.setText(AppConstants.getStringDate(note.getCreationDate()));
            tvUpdated.setText(AppConstants.getStringDate(note.getUpdateDate()));
            tvId.setText(Long.toString(note.getId()));

        }


    }
}
