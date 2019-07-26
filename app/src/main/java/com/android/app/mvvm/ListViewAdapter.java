package com.android.app.mvvm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.mvvm.databinding.ItemBinding;
import com.android.app.mvvm.data.Item;

import java.util.ArrayList;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.VH> {

    private final ArrayList<Item> items = new ArrayList<>();

    private final ObservableField<Boolean> imagesVisible;

    public ArrayList<Item> getItems() {
        return items;
    }

    ListViewAdapter(ObservableField<Boolean> imagesVisible) {
        this.imagesVisible = imagesVisible;
    }

    void add(Item item) {
        items.add(item);
        this.notifyItemInserted(items.size() - 1);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBinding binding = ItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class VH extends RecyclerView.ViewHolder{

        public VH(@NonNull View itemView) {
            super(itemView);
        }

        ItemBinding binding;
        public VH (ItemBinding binding) {
            this(binding.getRoot());
            this.binding = binding;
        }

        void bind(@NonNull Item item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }
}
