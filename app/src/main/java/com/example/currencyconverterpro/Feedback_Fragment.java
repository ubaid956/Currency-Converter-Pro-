package com.example.currencyconverterpro;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Feedback_Fragment extends Fragment {

    private RadioGroup radioGroup;
    private Button button;


    public Feedback_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback_, container, false);

        button = view.findViewById(R.id.button);
        radioGroup = view.findViewById(R.id.radio_group);
       setButtonEnabled(false);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setButtonEnabled(checkedId != -1);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkButton(view);
            }
        });

        return view;
    }

    private void setButtonEnabled(boolean isEnabled) {
        button.setEnabled(isEnabled);

        button.setAlpha(isEnabled ? 1.0f : 0.5f); // Set alpha to 1.0 when enabled and 0.5 when disabled
    }

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        if (radioId != -1) {
            RadioButton radioButton = v.findViewById(radioId);
            Toast.makeText(getContext(), "Thanks For you FeedBack!", Toast.LENGTH_SHORT).show();
            MainActivity activity = (MainActivity) getActivity();
            if (activity != null) {
                activity.loadFragment(new Main_Fragment());
            }
        }
    }

}
