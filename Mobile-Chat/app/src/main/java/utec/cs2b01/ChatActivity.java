package utec.cs2b01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ChatActivity extends AppCompatActivity {
    private int userFromId;
    private int userToId;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        this.userFromId= getIntent().getExtras().getInt("userFromId");
        this.userToId= getIntent().getExtras().getInt("userToId");
        this.username= getIntent().getExtras().getString("username");
        setTitle("@" + username);
    }
}