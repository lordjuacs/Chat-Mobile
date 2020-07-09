package utec.cs2b01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    public JSONArray users;
    private Context context;
    private int userFromId;
    public ContactsAdapter(JSONArray users, Context context, int userFromId){
        this.users = users;
        this.context = context;
        this.userFromId = userFromId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            final JSONObject user = users.getJSONObject(position);
            String name = user.getString("name") + " " + user.getString("fullname");
            final String username = user.getString("username");
            holder.firstLine.setText(name);
            holder.secondLine.setText("@" + username);
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        goToChatActivity(userFromId, user.getInt("id"), username);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return users.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView firstLine;
        TextView secondLine;
        RelativeLayout container;
        public ViewHolder(View itemView){
            super(itemView);
            firstLine = itemView.findViewById(R.id.element_view_first_line);
            secondLine = itemView.findViewById(R.id.element_view_second_line);
            container = itemView.findViewById(R.id.element_view_container);
        }
    }
    public void goToChatActivity(int userFromId, int userToId, String username){
        Intent intent = new Intent(this.context, ChatActivity.class);
        intent.putExtra("userFromId", userFromId);
        intent.putExtra("userToId", userToId);
        intent.putExtra("username", username);
        this.context.startActivity(intent);
    }
}