package utec.cs2b01;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
    JSONArray messages;
    private Context context;
    public int loggedUserId;

    public MessagesAdapter(JSONArray messages, Context context, int loggedUserId) {
        this.messages = messages;
        this.context = context;
        this.loggedUserId = loggedUserId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_view,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject message = messages.getJSONObject(position);
            String content = message.getString("content");
            String time = message.getString("sent_on");
            int whoSent = message.getInt("user_from_id");
            if(whoSent == loggedUserId) {
                //holder.friendLine.setBackgroundResource(0);
                holder.myLine.setText(content);
                holder.myTime.setText(time);
                holder.friendLine.setVisibility(View.INVISIBLE);
                holder.friendTime.setVisibility(View.INVISIBLE);

            }
            else{
                //holder.myLine.setBackgroundResource();
                holder.friendLine.setText(content);
                holder.friendTime.setText(time);
                holder.myLine.setVisibility(View.INVISIBLE);
                holder.myTime.setVisibility(View.INVISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return messages.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView friendLine;
        TextView myLine;
        TextView myTime;
        TextView friendTime;
        RelativeLayout container;

        public ViewHolder(View itemView){
            super(itemView);
            friendLine = itemView.findViewById(R.id.message_view_friend_line);
            myLine = itemView.findViewById(R.id.message_view_my_line);
            container = itemView.findViewById(R.id.message_view_container);
            myTime = itemView.findViewById(R.id.message_view_my_time);
            friendTime = itemView.findViewById(R.id.message_view_friend_time);

        }
    }
}
