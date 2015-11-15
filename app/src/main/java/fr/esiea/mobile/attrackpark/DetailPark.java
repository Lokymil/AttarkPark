package fr.esiea.mobile.attrackpark;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.esiea.mobile.attrackpark.domain.Park;
import fr.esiea.mobile.attrackpark.domain.Parks;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailPark.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailPark#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailPark extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARK_ID = "idPark";

    // Element from the fragment's layout
    private TextView description;

    // Selected park's id
    private Long idPark;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param idPark Parameter 1.
     * @return A new instance of fragment DetailPark.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailPark newInstance(Long idPark) {
        DetailPark fragment = new DetailPark();
        Bundle args = new Bundle();
        args.putLong(ARG_PARK_ID, idPark);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailPark() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idPark = getArguments().getLong(ARG_PARK_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_park, container, false);

        // Instanciate the element from the layout
        description = (TextView) rootView.findViewById(R.id.description_detail);
        // Replace the informations with the informations of the selected park
        refresh(idPark);

        return rootView;
    }

    // Refresh the displayed informations with the selected park's informations
    public void refresh(Long idPark) {
        Park mPark = Parks.getInstance().getParkById(idPark);
        if (mPark == null) {
            mPark = Parks.getInstance().getParks().get(0);
        }
        description.setText(mPark.getDescription());
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
    }

}
