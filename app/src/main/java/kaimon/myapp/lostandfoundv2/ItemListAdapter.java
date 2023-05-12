package kaimon.myapp.lostandfoundv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class ItemListAdapter extends ListAdapter<Item, ItemListAdapter.MyViewHolder> {

    Context context;
    ItemViewModel itemViewModel;

    public ItemListAdapter(@NonNull DiffUtil.ItemCallback<Item> diffCallback, Context context, ItemViewModel itemViewModel) {
        super(diffCallback);
        this.context = context;
        this.itemViewModel = itemViewModel;
    }


    @NonNull
    @Override
    public ItemListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.MyViewHolder holder, int position) {
        Item current = getItem(position);
        holder.name.setText(current.getName());
        holder.itemName.setText(current.getItemName());
        holder.location.setText(current.getLocation());
        holder.date.setText(current.getDate());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemViewModel.delete(current);
            }
        });

    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, itemName, location, date, lostOrFound;
        Button delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textView);
            itemName = itemView.findViewById(R.id.textView2);
            location = itemView.findViewById(R.id.textView3);
            date = itemView.findViewById(R.id.textView4);
            lostOrFound = itemView.findViewById(R.id.textView5);

            delete = itemView.findViewById(R.id.delete);
        }
    }

    public static class ItemDiff extends  DiffUtil.ItemCallback<Item>{

        @Override
        public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}