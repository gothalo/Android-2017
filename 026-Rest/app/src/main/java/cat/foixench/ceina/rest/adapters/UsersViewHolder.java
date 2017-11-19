package cat.foixench.ceina.rest.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cat.foixench.ceina.rest.R;

/**
 * Created by llorenc on 18/11/2017.
 */

public class UsersViewHolder extends RecyclerView.ViewHolder {
    TextView tvId, tvName, tvUserName, tvEmail;

    public UsersViewHolder (View itemView) {
        super (itemView);

        tvId = (TextView) itemView.findViewById(R.id.tvId);
        tvName = (TextView) itemView.findViewById(R.id.tvName);
        tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
        tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
    }
}
