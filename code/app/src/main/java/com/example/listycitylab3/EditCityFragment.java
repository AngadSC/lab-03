package com.example.listycitylab3;
import static java.sql.Types.NULL;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
public class EditCityFragment extends DialogFragment{
    public interface EditCityDialogListener {
        void updateCity(int position, City updated);

    }

    private static final String ARG_CITY ="city";
    private static final String ARG_POSITION ="position";

    public static EditCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;


    }

    public static EditCityFragment newInstance(City city, int position) {
        EditCityFragment fragment = newInstance(city);
        Bundle args = fragment.getArguments();
        if (args==NULL) args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext())
                // reuse your add layout; IDs must match
                .inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        Bundle args = requireArguments();
        City city = (City) args.getSerializable(ARG_CITY);
        final int position = args.getInt(ARG_POSITION, -1);

        if (city != null) {
            editCityName.setText(city.getName());
            editProvinceName.setText(city.getProvince());
        }

        return new AlertDialog.Builder(requireContext())
                .setView(view)
                .setTitle("Edit city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (d, w) -> {
                    String newName = editCityName.getText().toString().trim();
                    String newProv = editProvinceName.getText().toString().trim();

                    if (getActivity() instanceof EditCityDialogListener) {
                        EditCityDialogListener host = (EditCityDialogListener) getActivity();

                        // If we have a valid index, update that row.
                        if (position >= 0) {
                            host.updateCity(position, new City(newName, newProv));
                        } else {
                            // Fallback: try to update by matching the first occurrence of the old values.
                            host.updateCity(-1, new City(newName, newProv));
                        }
                    }
                })
                .create();
    }


}
