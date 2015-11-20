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
import java.util.List;

import fr.esiea.mobile.attrackpark.R;
import fr.esiea.mobile.attrackpark.domain.Park;
import fr.esiea.mobile.attrackpark.domain.Parks;

/**
 * Created by Christophe on 19/11/2015.
 */
public class ParkAdapter extends BaseAdapter implements Filterable{

    private Context context;
    private List<Park> originalData = null;
    private List<Park> filteredData = null;
    private ItemFilter mFilter;


    public ParkAdapter(Context context, List<Park> data) {
        this.context = context;
        this.originalData = data;
        this.filteredData = data;
        this.mFilter = new ItemFilter();
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return filteredData.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View v = view;
        RowView holder;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.pays_row, null);

            holder = new RowView();
            holder.logo = (ImageView) v.findViewById(R.id.row_park_img);
            holder.name= (TextView) v.findViewById(R.id.row_park_name);
            holder.pays= (TextView) v.findViewById(R.id.row_park_pays);

            v.setTag(holder);
        } else {
            holder = (RowView) v.getTag();
        }

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

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Park> list = originalData;

            int count = list.size();
            final ArrayList<Park> nlist = new ArrayList<Park>();

            Park filterablePark ;

            for (int i = 0; i < count; i++) {
                filterablePark = list.get(i);
                if (filterablePark.getName().toLowerCase().contains(filterString)) {
                    nlist.add(filterablePark);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<Park>) results.values;
            notifyDataSetChanged();
        }
    }

    private static class RowView{
        TextView name, pays;
        ImageView logo;
    }
}
