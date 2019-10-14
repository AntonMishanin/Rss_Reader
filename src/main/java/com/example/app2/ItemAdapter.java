package com.example.app2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<Item> news= new ArrayList<>();
    private Context context;
    private boolean[] descriptionVisible;

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(news.get(position).getTitle());
        holder.pubDate.setText(news.get(position).getPubDate());
        holder.site.setText(news.get(position).getSite());
        if (descriptionVisible[position]) {
            holder.description.setText(news.get(position).getDescription());
        } else {
            holder.description.setText("");
        }
        try {
            Picasso.with(context)
                    .load(news.get(position).getImage())
                   .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.imageView);
        } catch (Exception e) {
            Picasso.with(context)
                    .load(R.drawable.ic_launcher_background)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void setData(ArrayList<Item> items, Context context) {
        news.clear();
        news.addAll(items);
        descriptionVisible = new boolean[items.size()];
        this.context=context;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView site;
        TextView description;
        TextView pubDate;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            site = (TextView) itemView.findViewById(R.id.site);
            pubDate = (TextView) itemView.findViewById(R.id.pubDate);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            description = (TextView) itemView.findViewById(R.id.description);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        notifyItemChanged(pos);
                        descriptionVisible[pos]=!descriptionVisible[pos];
                        notifyItemChanged(pos);
                    }
                }
            });
        }
    }
}
