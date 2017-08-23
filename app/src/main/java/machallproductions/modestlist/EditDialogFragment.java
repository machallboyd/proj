package machallproductions.modestlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EditDialogFragment extends DialogFragment {

    private OnFragmentInteractionListener mListener;
    EditText editText;
    Button button;

    public EditDialogFragment() {
        // Required empty public constructor
    }

    public static EditDialogFragment newInstance(String editString, int position) {
        EditDialogFragment frag = new EditDialogFragment();
        Bundle args = new Bundle();
        args.putString("editString", editString);
        args.putInt("position", position);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_edit, container, true);
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = (EditText) view.findViewById(R.id.editText_field);
        editText.setText(getArguments().getString("editString"));
        editText.setSelection(editText.length());
        editText.requestFocus();
        button = (Button) view.findViewById(R.id.edit_button);
        button.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick (View view) {
                String text = editText.getText().toString();
                onButtonPressed(text, getArguments().getInt("position"));
            }

        }) ;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String text, int position) {
        if (mListener != null) {
            mListener.onFragmentInteraction(text, position);
        }
        dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String text, int position);
    }
}
