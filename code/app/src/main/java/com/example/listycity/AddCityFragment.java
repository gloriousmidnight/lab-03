package com.example.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);
    }

    private AddCityDialogListener listener;

    private City cityToEdit = null;

    //the following function is from the lab 3 description (Hint #3)
    public static AddCityFragment newInstance(City city) {
        Bundle args = new Bundle();

        args.putSerializable("city", city);

        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + "must implement AddCityDialogListener");
        }
    }

    // The following was created with the assistance of OpenAI's ChatGPT to help
    //me (Cassie Burke) with specifics of Java syntax and how bundles/fragments/serializable
    //work, mainly for clarification about how to format what I wanted to do into
    //Java. In this function, particularly how bundles and their arguments work so
    //the selected city data could be used in the fragment (january 21st, 2025)
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            cityToEdit = (City) args.getSerializable("city");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //for the following four lines, see above for the previous comment about using
        //ChatGPT to assist with bundles/serializable/args
        Bundle args = getArguments();
        if (args != null) {
            cityToEdit = (City) args.getSerializable("city");
        }

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        if (cityToEdit != null) {
            editCityName.setText(cityToEdit.getName());
            editProvinceName.setText(cityToEdit.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String title = (cityToEdit == null) ? "Add a City" : "Edit City";
        String positiveButtonText = (cityToEdit == null) ? "Add" : "Save";

        return builder
                .setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(positiveButtonText, (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();

                    if (cityToEdit == null) {
                        //add mode
                        listener.addCity(new City(cityName, provinceName));
                    } else {
                        //edit mode
                        cityToEdit.setName(cityName);
                        cityToEdit.setProvince(provinceName);

                        listener.addCity(cityToEdit);
                    }
                })
                .create();
    }
}
