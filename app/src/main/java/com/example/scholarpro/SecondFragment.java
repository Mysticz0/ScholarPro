package com.example.scholarpro;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.scholarpro.databinding.FragmentSecondBinding;

import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private ApiService apiService;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        apiService = RetrofitClient.getClient().create(ApiService.class);
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

            double credit = Double.parseDouble(creditText);

            GradeRequest request = new GradeRequest(letterGrade, credit);
            apiService.addGrade(request).enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<Map<String, Object>> call, @NonNull Response<Map<String, Object>> response) {
                    if (response.isSuccessful() && response.body() != null) {
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
                }

                @Override
                public void onFailure(@NonNull Call<Map<String, Object>> call, @NonNull Throwable t) {
                    Log.e("API Error", "Failed to add grade", t);
                    Toast.makeText(
                            requireContext(),
                            "Error: " + t.getMessage(),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        });
        getAverageButton.setOnClickListener(v -> {
            String creditText = editTextCreditsRemaining.getText().toString().trim();

            if (creditText.isEmpty()) {
                editTextCreditsRemaining.setError("Credit required");
                return;
            }

            double creditsRemaining = Double.parseDouble(creditText);
            ScholarshipRequest request = new ScholarshipRequest(creditsRemaining);
            apiService.checkScholarship(request).enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<Map<String, Object>> call, @NonNull Response<Map<String, Object>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Map<String, Object> result = response.body();
                        Double averageNeededValue = (Double) result.get("averageNeeded");
                        Boolean isPossible = (Boolean) result.get("isPossible");

                        if (isPossible != null && isPossible) {
                            averageNeeded.setText(String.format(Locale.US, "Average Needed: %.2f", averageNeededValue));
                            averageNeeded.setError(null);
                        } else {
                            averageNeeded.setText(R.string.scholarship_impossible);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Map<String, Object>> call, @NonNull Throwable t) {
                    Log.e("API Error", "Failed to check scholarship", t);
                    Toast.makeText(
                            requireContext(),
                            "Error: " + t.getMessage(),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}