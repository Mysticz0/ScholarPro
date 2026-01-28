package com.example.scholarpro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.example.scholarpro.databinding.FragmentSecondBinding;

import java.util.Locale;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonViewChart.setOnClickListener(v ->
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment)
        );

        Button submitButton = binding.buttonSubmitGrade;
        Button getAverageButton = binding.buttonGetAverage;
        EditText letterGradeEditText = binding.editTextLetterGrade;
        EditText editTextCredit = binding.editTextCredit;
        EditText editTextCreditsRemaining = binding.editTextCreditsRemaining;
        TextView averageNeeded = binding.averageNeeded;

        CalculatorViewModel viewModel = new ViewModelProvider(requireActivity()).get(CalculatorViewModel.class);
        GraphCalculator calculator = viewModel.calculator;

        submitButton.setOnClickListener(v -> {
            String letterGrade = letterGradeEditText.getText().toString().trim();
            String creditText = editTextCredit.getText().toString().trim();

            if (letterGrade.isEmpty()) {
                letterGradeEditText.setError("Letter grade required");
                if (creditText.isEmpty()) {
                    editTextCredit.setError("Credit required");
                }
                return;
            }
            if (creditText.isEmpty()) {
                editTextCredit.setError("Credit required");
                return;
            }

            Double credit = Double.parseDouble(creditText);

            GradeKey newGrade = new GradeKey(letterGrade, credit);

            if (calculator.gradeMap.containsKey(newGrade)){
                calculator.addGrade(letterGrade, credit);
                calculator.calculateCGPA();

                Toast.makeText(
                        requireContext(),
                        "Grade Saved!",
                        Toast.LENGTH_SHORT
                ).show();

                letterGradeEditText.setText("");
                editTextCredit.setText("");
            } else {
                Toast.makeText(
                        requireContext(),
                        "Invalid Grade",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        getAverageButton.setOnClickListener(v -> {
            String creditText = editTextCreditsRemaining.getText().toString().trim();

            if (creditText.isEmpty()) {
                editTextCreditsRemaining.setError("Credit required");
                return;
            }

            Double creditsRemaining = Double.parseDouble(creditText);
            if (calculator.cgpaOverTime.isEmpty()){
                averageNeeded.setText("Average Needed: 10.0");
            } else if (calculator.isAveragePossible(creditsRemaining)){
                averageNeeded.setText(String.format(Locale.US ,"Average Needed: %.2f", calculator.getAverageNeeded(creditsRemaining)));
            }
            else {
                averageNeeded.setError("Keeping Scholarship is impossible");
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}