package com.balinasoft.test_task.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

public class EmailAdapter extends ArrayAdapter<String> {
    private ArrayList<String> items;
    private ArrayList<String> itemsAll;
    private ArrayList<String> suggestions;
    private int viewResourceId;

    public EmailAdapter(Context context, int viewResourceId, ArrayList<String> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<String>) items.clone();
        this.suggestions = new ArrayList<String>();
        this.viewResourceId = viewResourceId;
    }


    @NonNull
    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = vi.inflate(viewResourceId, null);
        String customer = items.get(position);
        TextView customerNameLabel = (TextView) v;
        customerNameLabel.setText(customer);
        return v;
    }


    @NonNull
    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    private Filter nameFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String lineBreaking = constraint.toString();
            if (lineBreaking.contains("@")) {
                String lineBreakingAndSearch = lineBreaking.substring(lineBreaking.indexOf("@") + 1);
                String symbolOfBreaking;
                try {
                    symbolOfBreaking = lineBreaking.substring(0, lineBreaking.indexOf("@") + 1);
                } catch (Exception ex) {
                    symbolOfBreaking = "";
                }
                suggestions.clear();
                for (String customer : itemsAll) {
                    if (customer.toLowerCase().startsWith(lineBreakingAndSearch.toLowerCase())) {
                        suggestions.add(symbolOfBreaking + customer);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<String> filteredList = (ArrayList<String>) results.values;
            if (results.count > 0) {
                clear();
                for (String c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };
}