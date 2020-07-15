package utec.cs2b01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Log In");
    }



    public void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    public void goContactsActivity(String username, int id){
        Intent intent = new Intent(this, ContactsActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void onLoginClicked(View view){
        //TODO Implement Login
        //1. Get data from Layout
        EditText txtUsername = (EditText)findViewById(R.id.txtUsername);
        EditText txtPassword = (EditText)findViewById(R.id.txtPassword);
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        //2. Build JSON Message
        // {"username": "jramirez", "password": "1234"}
        Map<String, String> message = new HashMap<String, String>();
        message.put("username", username);
        message.put("password", password);

        JSONObject jsonMessage = new JSONObject(message);
        //Toast.makeText(this, jsonMessage.toString(), Toast.LENGTH_LONG).show();

        //3. Build Request Object --> volley
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:8080/authenticate",
                jsonMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO when OK Response
                        showMessage("Welcome!");
                        //showMessage(response.toString());
                        //Opening Contacts Activity<
                        try {
                            String username = response.getString("username");
                            int id = response.getInt("id");
                            goContactsActivity(username, id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO when Error Response
                        showMessage("Wrong username/password. Try again!");
                    }
                }
        );

        //4. Send Request Message to server
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
