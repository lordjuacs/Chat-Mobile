package utec.cs2b01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.JsonToken;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    int userFromId;
    int userToId;
    String usernameTo;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        this.userFromId= this.getIntent().getExtras().getInt("userFromId");
        this.userToId= this.getIntent().getExtras().getInt("userToId");
        this.usernameTo= this.getIntent().getExtras().getString("usernameTo");
        this.mRecyclerView = findViewById(R.id.messages_recycler_view);
        setTitle("@" + usernameTo);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getMessages();
        runTimer(1);
    }
    public Activity getActivity(){
        return this;
    }

    public void getMessages(){
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "http://10.0.2.2:8080/messages/" + userFromId + "/" + userToId,
                new JSONArray(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //TODO
                        mAdapter = new MessagesAdapter(response, getActivity(), userFromId);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO
                        error.printStackTrace();
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    private void runTimer(int valid) {
        final Handler handler = new Handler();
        if(valid == 1) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getMessages();
                    handler.postDelayed(this, 3000);
                }
            });
        }
        else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    handler.removeCallbacks(this);
                }
            });
        }
    }
    public void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void goMainActivity(){
        //TODO Implement Login
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onLogoutClicked(View view){
        //TODO Implement Login
        //3. Build Request Object --> volley
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://10.0.2.2:8080/logout",
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO when OK Response
                        showMessage("Logged Out!");
                        runTimer(0);
                        goMainActivity();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO when Error Response
                        showMessage("Failed!");
                    }
                }
        );

        //4. Send Request Message to server
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    public void onSendClicked(View view){
        //TODO Implement Login
        //1. Get data from Layout
        EditText txtContent = (EditText)findViewById(R.id.content);
        String content = txtContent.getText().toString();


        //2. Build JSON Message
        Map<String, String> message = new HashMap<String, String>();
        message.put("user_from_id", Integer.toString(userFromId));
        message.put("user_to_id", Integer.toString(userToId));
        message.put("content", content);


        JSONObject jsonMessage = new JSONObject(message);
        //Toast.makeText(this, jsonMessage.toString(), Toast.LENGTH_LONG).show();

        //3. Build Request Object --> volley
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:8080/new_message",
                jsonMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO when OK Response
                        showMessage("Sent!");
                        getMessages();
                        //showMessage(response.toString());
                        //Opening Contacts Activity<
                       /* try {
                            String username = response.getString("username");
                            int id = response.getInt("id");
                            //goContactsActivity(username, id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
*/
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO when Error Response
                        showMessage("Failed!");
                    }
                }
        );

        //4. Send Request Message to server
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}