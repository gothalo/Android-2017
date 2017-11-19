package cat.foixench.ceina.rest.network;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cat.foixench.ceina.rest.adapters.UsersAdapter;
import cat.foixench.ceina.rest.data.UserData;

/**
 * Created by llorenc on 18/11/2017.
 */

public class GetAllUsersTask extends AsyncTask <String, String, String> {

    public static final String TAG = "GetAllUsersTask";
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    public static final String COMUNICATION_ERROR = "ERROR";

    RecyclerView rvUsers;

    public GetAllUsersTask(RecyclerView rvUsers)
    {
        this.rvUsers = rvUsers;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            URL serviceEndPoint = new URL("http://jsonplaceholder.typicode.com/users/");

            HttpURLConnection connection = (HttpURLConnection) serviceEndPoint.openConnection();

            // timeout de la peticion
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);

            // añadimos cabeceras a la conexión
            connection.setRequestProperty("Accept", "application/json");
            // connection.setRequestProperty("Authorization", "Basic lajskjflskjflsfjslfjslflsjfdls");

            // indicamos que tipo de petición vamos a realizar (GET, POST, ....)
            connection.setRequestMethod("GET");

            // se lanza la petición y se mira el resultado
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // recuperamos un stream con la respuesta del servidor.
                InputStream input = connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Se ha leido la respuesta de manera correcta. retornamos el json como string recibido
                return result.toString();

            }

            else {
                Log.e (TAG, "Error reading response from " + serviceEndPoint.toString() + " with code " + connection.getResponseCode());
            }

        } catch (MalformedURLException mue) {
            Log.e(TAG, "MalformedURLException", mue);

        } catch (IOException ioe) {
            Log.e(TAG, "IOException", ioe);
        }

        return COMUNICATION_ERROR;
    }

    @Override
    protected void onPostExecute(String data) {

        if (data.compareTo(COMUNICATION_ERROR) != 0) {
            List<UserData> usersList = new ArrayList<>();

            try {
                // convertimos el resultado de la llamada en una matriz json
                JSONArray jArray = new JSONArray(data);
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject item = jArray.getJSONObject(i);
                    UserData user = new UserData();

                    user.setId(item.getInt("id"));
                    user.setName(item.getString("name"));
                    user.setUserName(item.getString("username"));
                    user.seteMail(item.getString("email"));

                    usersList.add(user);

                }

                // refrescamos la lisview. para ello cambiaremos su adapter

                UsersAdapter adapter = new UsersAdapter(usersList);
                rvUsers.swapAdapter(adapter, false);

            } catch (JSONException je) {

            }
        }
    }
}
