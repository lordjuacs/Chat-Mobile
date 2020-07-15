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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Sign Up");
    }

    public void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    public void goLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onSignupClicked(View view){
        //TODO Implement Login
        //1. Get data from Layout
        EditText txtUsername = (EditText)findViewById(R.id.txtUsername);
        EditText txtPassword = (EditText)findViewById(R.id.txtPassword);
        EditText txtName = (EditText)findViewById(R.id.txtName);
        EditText txtFullname = (EditText)findViewById(R.id.txtFullname);
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String name = txtUsername.getText().toString();
        String fullname = txtPassword.getText().toString();

        //2. Build JSON Message
        // {"username": "jramirez", "password": "1234"}
        Map<String, String> message = new HashMap<String, String>();
        message.put("username", username);
        message.put("password", password);
        message.put("name",  name);
        message.put("fullname", fullname);

        JSONObject jsonMessage = new JSONObject(message);
        //Toast.makeText(this, jsonMessage.toString(), Toast.LENGTH_LONG).show();

        //3. Build Request Object --> volley
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:8080/signup",
                jsonMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO when OK Response
                        showMessage("User created!");
                        goLoginActivity();
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
