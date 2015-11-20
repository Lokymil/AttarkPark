package fr.esiea.mobile.attrackpark;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import fr.esiea.mobile.attrackpark.adapter.ParkAdapter;
import fr.esiea.mobile.attrackpark.domain.Park;
import fr.esiea.mobile.attrackpark.domain.Parks;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    // Element from the layout, instanciated in onCreateView() method
    private EditText editText;
    private ListView listView;

    // Adapter to fill the ListView, instanciated in onCreatView() method
    private ParkAdapter arrayAdapter;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        // Retrieve element from the layout
            // Search field
        editText = (EditText) rootView.findViewById(R.id.editText_search_fragment);
            // ListView to display park's list
        listView = (ListView) rootView.findViewById(R.id.list_search_fragment);

        // Create adapter to fill the listView element
            // The park's list is to be displayed with a custom adapter
        arrayAdapter = new ParkAdapter(getActivity(), Parks.getInstance().getParks());
        listView.setAdapter(arrayAdapter);
        // Set the behavior when a list's item is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Park selected = Parks.getInstance().getParks().get(position);
                Log.d("List","Park selected " + selected.getName());
                // call the method from the interface, this method will be implemented in the activity using this fragment
                mListener.onParkSelected(selected.getId());
            }
        });

        // Set behavior on EditText change to filter the list
        editText.addTextChangedListener(new TextWatcher() {
            // Nothing to do before
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // Nothing to do during the change
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            // Behavior once the text has been changed
            @Override
            public void afterTextChanged(Editable s) {
                Log.d("EditText","editText content from searchFrag has changed to " + s.toString());
                // Apply filter on the list to obtain only the matching park name
                arrayAdapter.getFilter().filter(s.toString());
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
        public void onParkSelected(Long id);
    }

}
