package com.prokaysar.firebaefirestoredemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder>{
    List<DataModel> postList;
    Context context;

    public PostListAdapter(Context context,List<DataModel> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.header.setText(postList.get(position).getHeader());
        holder.postDesc.setText(postList.get(position).getDesc());
        final String post_id = postList.get(position).postId;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String header = postList.get(position).getHeader().toString();
               String desc = postList.get(position).getDesc().toString();

                Toast.makeText(context, ""+header, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView header;
        TextView postDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            header = mView.findViewById(R.id.textView2);
            postDesc = mView.findViewById(R.id.textView3);

        }
    }

}