package fr.esiea.mobile.attrackpark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.esiea.mobile.attrackpark.R;
import fr.esiea.mobile.attrackpark.domain.Park;
import fr.esiea.mobile.attrackpark.domain.Parks;

/**
 * Created by Christophe on 19/11/2015.
 */
public class ParkAdapter extends BaseAdapter implements Filterable{

    // Parameter needed to display and filter the list
    private Context context;
    private List<Park> originalData = null;
    private List<Park> filteredData = null;
    private ItemFilter mFilter;

    private Comparator<Park> compareParkByName;
    private Comparator<Park> compareParkByCountry;

    public ParkAdapter(Context context, List<Park> data) {

        // set comparator to sort by name
        compareParkByName = new Comparator<Park>() {
            @Override
            public int compare(Park p1, Park p2) {
                return p1.getName().compareTo(p2.getName());
            }
        };

        // set comparator to sort by country
        compareParkByCountry = new Comparator<Park>() {
            @Override
            public int compare(Park p1, Park p2) {
                return p1.getPays().compareTo(p2.getPays());
            }
        };

        this.context = context;
        this.originalData = data;
        this.filteredData = data;
        // sort filtered data (actual displayed data) by name
        Collections.sort(filteredData, compareParkByName);
        this.mFilter = new ItemFilter();
    }

    // Return the filtered list's size
    @Override
    public int getCount() {
        return filteredData.size();
    }

    // Return item at given position from filtered list
    @Override
    public Object getItem(int position) {
        return filteredData.get(position);
    }

    // Return item at given position's id from filtered list
    @Override
    public long getItemId(int position) {
        return filteredData.get(position).getId();
    }

    // Inflate the layout and apply correct value to every field
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View v = view;
        RowView holder;
        if (v == null) {
            // Inflate layout to display each list's item, in our case items are parks
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.pays_row, null);

            // Retrieve element from the layout and save them in holder
            holder = new RowView();
            holder.logo = (ImageView) v.findViewById(R.id.row_park_img);
            holder.name= (TextView) v.findViewById(R.id.row_park_name);
            holder.pays= (TextView) v.findViewById(R.id.row_park_pays);

            // Associate holder to view
            v.setTag(holder);
        } else {
            // If view already exists, retrieve holder
            holder = (RowView) v.getTag();
        }

        // Apply the correct data to the view
        Park park = (Park) getItem(position);
        Picasso.with(context).load(park.getImgUrl()).into(holder.logo);
        holder.name.setText(park.getName());
        holder.pays.setText(park.getPays());

        return v;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {

        // Filter the list depending on the given string
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // Put all the string to lowercase
            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            // To filter data, it will use the full data set
            final List<Park> list = originalData;

            // Create the list that will be used to save filtered data
            int count = list.size();
            final ArrayList<Park> nlist = new ArrayList<Park>();

            Park filterablePark ;

            // Compare given string to specific element of the park to filter list
            for (int i = 0; i < count; i++) {
                filterablePark = list.get(i);
                if (filterablePark.getName().toLowerCase().contains(filterString)) {
                    nlist.add(filterablePark);
                }
            }

            // Sort the list by name
            Collections.sort(nlist,compareParkByName);

            // Save the list to display it
            results.values = nlist;
            results.count = nlist.size();
            return results;
        }

        // Apply the result get from filter action
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // Display filtered data
            filteredData = (ArrayList<Park>) results.values;
            notifyDataSetChanged();
        }
    }

    private static class RowView{
        TextView name, pays;
        ImageView logo;
    }
}
