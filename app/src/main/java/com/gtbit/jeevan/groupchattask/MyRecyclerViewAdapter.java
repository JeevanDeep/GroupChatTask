package com.gtbit.jeevan.groupchattask;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jeevan on 26/1/18.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<ChatMessage> list;
    private Context context;
    private LayoutInflater inflater;


    public MyRecyclerViewAdapter(ArrayList<ChatMessage> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.chat_list_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChatMessage currentMessage = list.get(position);
        holder.message.setText(currentMessage.getMessage());
        holder.username.setText(currentMessage.getUserName());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) holder.cardView.getLayoutParams();
        int dimen = (int) context.getResources().getDimension(R.dimen.my_margin);
        if (currentMessage.getUserName().equals(preferences.getString("username", ""))) {
            layoutParams.setMarginStart(dimen);
            holder.username.setTextColor(Color.BLUE);
            layoutParams.setMarginEnd(0);
        } else {
            layoutParams.setMarginEnd(dimen);
            layoutParams.setMarginStart(0);
            holder.username.setTextColor(Color.RED);
        }
        holder.cardView.requestLayout();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(ChatMessage item) {
        list.add(item);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView message, username;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.chatMessageTv);
            username = itemView.findViewById(R.id.usernameTv);
            cardView = itemView.findViewById(R.id.chat_cardView);
        }
    }
}
