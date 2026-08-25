package com.example.cndc_lpar_rar_finalexer3;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JewelryAdapter extends RecyclerView.Adapter<JewelryAdapter.JewelryViewHolder> {

    private Context context;
    private Cursor cursor;

    public JewelryAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    public class JewelryViewHolder extends RecyclerView.ViewHolder {
        public TextView idText;
        public TextView nameText;
        public TextView valueText;

        public JewelryViewHolder(@NonNull View itemView) {
            super(itemView);
            idText = itemView.findViewById(R.id.textViewIdValue);
            nameText = itemView.findViewById(R.id.textViewName);
            valueText = itemView.findViewById(R.id.textViewValue);
        }
    }

    @NonNull
    @Override
    public JewelryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_jewelry, parent, false);
        return new JewelryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JewelryViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("jname"));
        double value = cursor.getDouble(cursor.getColumnIndexOrThrow("value"));

        holder.idText.setText(String.valueOf(id));
        holder.nameText.setText(name);
        holder.valueText.setText("₱" + String.format("%.2f", value));


    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}