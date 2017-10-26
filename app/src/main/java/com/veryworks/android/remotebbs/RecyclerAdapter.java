package com.veryworks.android.remotebbs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veryworks.android.remotebbs.model.Data;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {
    Data datas[];

    public RecyclerAdapter(Data datas[]){
        this.datas = datas;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Data data = datas[position];
        holder.textTitle.setText(data.getTitle());
        holder.textDate.setText(data.getDate());
    }

    @Override
    public int getItemCount() {
        return datas.length;
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView textTitle;
        TextView textDate;
        public Holder(View view){
            super(view);
            textTitle = view.findViewById(R.id.textTitle);
            textDate = view.findViewById(R.id.textDate);
        }
    }
}
