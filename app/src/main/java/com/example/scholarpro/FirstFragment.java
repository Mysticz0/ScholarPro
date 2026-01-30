package com.example.scholarpro;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.scholarpro.databinding.FragmentFirstBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ApiService apiService;

    public void plotPoints(List<Double> cgpaOverTime, Double currentCGPA) {
        LineChart chart = binding.chart;
        chart.setNoDataText("");
        TextView currentCGPATextView;
        Button nextButton;

        if (cgpaOverTime == null || cgpaOverTime.isEmpty()) {
            chart.clear();
            chart.invalidate();

            currentCGPATextView = binding.currentCgpa;
            currentCGPATextView.setText(R.string.no_grades_entered);
            return;
        }

        List<Entry> entries = new ArrayList<>();
        int step = 0;
        for (Double p : cgpaOverTime) {
            entries.add(new Entry((float) step, p.floatValue()));
            step += 10;
        }
        if (entries.size() == 1) {
            entries.add(new Entry((float) step, cgpaOverTime.get(0).floatValue()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "");

        dataSet.setColor(Color.parseColor("#eb1c24"));
        dataSet.setLineWidth(3f);
        dataSet.setDrawCircles(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); // smooth curve

        dataSet.setDrawFilled(true);
        dataSet.setFillFormatter((dataSet1, dataProvider) -> dataProvider.getYChartMin());
        Drawable drawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{
                        Color.parseColor("#55ED4249"), // near line
                        Color.TRANSPARENT              // fade out
                }
        );
        dataSet.setFillDrawable(drawable);

        dataSet.setDrawValues(false);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);

        chart.getXAxis().setEnabled(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);

        chart.setViewPortOffsets(0, 0, 0, 0); // edge-to-edge
        chart.invalidate();

        nextButton = binding.buttonAddGrades;
        nextButton.bringToFront();
        currentCGPATextView = binding.currentCgpa;
        currentCGPATextView.bringToFront();
        currentCGPATextView.setText(String.format(Locale.US, "Current CGPA: %.2f", currentCGPA));
    }

    private void fetchAndPlotCGPA() {
        apiService.getCGPA().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, Object>> call, @NonNull Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Object> result = response.body();

                    // Extract cgpaOverTime as List<Double> with safe cast
                    Object cgpaObj = result.get("cgpaOverTime");
                    @SuppressWarnings("unchecked")
                    List<Double> cgpaOverTime = (cgpaObj instanceof List) ? (List<Double>) cgpaObj : new ArrayList<>();

                    Double currentCGPA = (Double) result.get("currentCGPA");

                    plotPoints(cgpaOverTime, currentCGPA);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, Object>> call, @NonNull Throwable t) {
                Log.e("API Error", "Failed to fetch CGPA", t);
                Toast.makeText(
                        requireContext(),
                        "Error loading data: " + t.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        apiService = RetrofitClient.getClient().create(ApiService.class);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Fetch data from API and plot
        view.post(this::fetchAndPlotCGPA);

        binding.buttonAddGrades.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );

        binding.buttonReset.setOnClickListener(v -> {
            // Reset data on the server
            apiService.reset().enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Refresh the chart
                        fetchAndPlotCGPA();
                        Toast.makeText(
                                requireContext(),
                                "Data reset!",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Log.e("API Error", "Failed to reset", t);
                    Toast.makeText(
                            requireContext(),
                            "Error resetting: " + t.getMessage(),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh data when returning to this fragment
        fetchAndPlotCGPA();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}