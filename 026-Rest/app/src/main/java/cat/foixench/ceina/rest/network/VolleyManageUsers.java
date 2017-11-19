package cat.foixench.ceina.rest.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import cat.foixench.ceina.rest.data.UserData;


/**
 * Created by llorenc on 19/11/2017.
 */

public class VolleyManageUsers {

    private static VolleyManageUsers INSTANCE = null;

    private Context context;

    // para evitar sobrecargar la memoria usaremos un Singleton para esta clase
    private VolleyManageUsers(Context context) {
        this.context = context;
    }

    public static VolleyManageUsers getInstance (Context context) {
        if (INSTANCE == null) {
            INSTANCE = new VolleyManageUsers(context);
        }
        return INSTANCE;
    }

    public void getUser (final int userId, final GetUserCallback callback) {

        // url a recuperar
        String userUri = "http://jsonplaceholder.typicode.com/users/" + userId + "/";

        // declarar objetos necesarios
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                userUri,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response OK. se ejecuta en el hilo principal
                        try {

                            UserData user = new UserData();

                            user.seteMail(response.getString("email"));
                            user.setUserName(response.getString("username"));
                            user.setName(response.getString("name"));
                            user.setId(response.getInt("id"));
                            user.setPhone(response.getString("phone"));
                            user.setWebsite(response.getString("website"));

                            // leer los objetos anidados
                            JSONObject company = response.getJSONObject("company");
                            UserData.CompanyData userCompany = user.new CompanyData();
                            userCompany.setName(company.getString("name"));
                            userCompany.setCatchPhrase(company.getString("catchPhrase"));
                            userCompany.setBs(company.getString("bs"));
                            user.setCompany(userCompany);

                            JSONObject address = response.getJSONObject("address");
                            UserData.AddresData userAddress = user.new AddresData();
                            userAddress.setStreet(address.getString("street"));
                            userAddress.setSuite(address.getString("suite"));
                            userAddress.setCity(address.getString("city"));
                            userAddress.setZipCode(address.getString("zipcode"));
                            user.setAddress(userAddress);


                            callback.onUserLoaded(user);

                        } catch (JSONException jse) {
                            Log.e(GetAllUsersTask.TAG, "Error reading user " + userId, jse);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(GetAllUsersTask.TAG, "Error loading user " + userId, error.getCause());
                        callback.onError();
                    }
                });
        // lanzar la peticion en la cola de peticiones
        requestQueue.add(request);
    }

    public void updateUser (UserData userData, final GetUserCallback callback) {

        // url a
        String userUri = "http://jsonplaceholder.typicode.com/users/" + userData.getId() + "/";

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // create User Data
        JSONObject data = new JSONObject();

        try {
            data.put("id", userData.getId());
            data.put("username", userData.getUserName());
            data.put("name", userData.getName());
            data.put("phone", userData.getPhone());
            data.put("website", userData.getWebsite());

            JSONObject company = new JSONObject();

            company.put("name", userData.getCompany().getName());
            company.put("catchPhrase", userData.getCompany().getCatchPhrase());
            company.put("bs", userData.getCompany().getBs());

            data.put("company", company);

            JSONObject address = new JSONObject();

            address.put("street", userData.getAddress().getStreet());
            address.put("suite", userData.getAddress().getSuite());
            address.put("city", userData.getAddress().getCity());
            address.put("zipcode", userData.getAddress().getZipCode());

            data.put("address", address);

        } catch (JSONException jse) {

        }

        // crear la petición JSON
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                userUri,
                data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onUserLoaded(null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(GetAllUsersTask.TAG, error.getMessage(), error.fillInStackTrace());
                        callback.onError();
                    }
                }
        );

        requestQueue.add(request);


    }


    public interface GetUserCallback {
        public void onUserLoaded (UserData userData);
        public void onError ();

    }

}
