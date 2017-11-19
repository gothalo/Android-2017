package cat.foixench.ceina.rest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cat.foixench.ceina.rest.R;
import cat.foixench.ceina.rest.data.UserData;

/**
 * Created by llorenc on 18/11/2017.
 */

public class UsersAdapter extends RecyclerView.Adapter <UsersViewHolder>{

    private List<UserData> usersList;
    private Context context;

    public UsersAdapter ()
    {
        usersList = new ArrayList<> ();
    }

    public UsersAdapter (List<UserData> usersList) {
        this.usersList = usersList;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.user_cardview, parent, false);
        return new UsersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, int position) {
        holder.tvId.setText(String.valueOf(usersList.get(position).getId()));
        holder.tvEmail.setText(usersList.get(position).geteMail());
        holder.tvName.setText(usersList.get(position).getName());
        holder.tvUserName.setText(usersList.get(position).getUserName());

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}
