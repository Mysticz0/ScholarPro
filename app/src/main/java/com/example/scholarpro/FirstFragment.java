package com.example.scholarpro;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.example.scholarpro.databinding.FragmentFirstBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FirstFragment extends Fragment {

    private CalculatorViewModel viewModel;
    private FragmentFirstBinding binding;
    private TextView currentCGPA;

    public void plotPoints(View view) {
        LineChart chart = view.findViewById(R.id.chart);
        chart.setNoDataText("");
        GraphCalculator calculator = viewModel.calculator;

        List<Double> cgpaOverTime = calculator.getCgpaOverTime();
        if (cgpaOverTime.isEmpty()) {
            chart.clear();
            chart.invalidate();

            currentCGPA = view.findViewById(R.id.textView);
            currentCGPA.setText("No grades entered yet");
            return;
        }

        List<Entry> entries = new ArrayList<>();
        int step = 0;
        for (Double p : cgpaOverTime) {
            entries.add(new Entry((float) step,  p.floatValue()));
            step += 10;
        }
        if (entries.size() == 1){
            entries.add(new Entry((float) step, cgpaOverTime.get(0).floatValue()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "");

        dataSet.setColor(Color.parseColor("#eb1c24"));
        dataSet.setLineWidth(3f);
        dataSet.setDrawCircles(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); // smooth curve

        dataSet.setDrawFilled(true);
        dataSet.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return dataProvider.getYChartMin();
            }
        });
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
        chart.fitScreen();

        currentCGPA = view.findViewById(R.id.textView);
        currentCGPA.bringToFront();
        currentCGPA.setText(String.format(Locale.US ,"Current CGPA: %.2f", calculator.getCurrentCGPA()));
    }



    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);


        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(CalculatorViewModel.class);

        // Post the plotPoints action to the view's message queue. This ensures it runs
        // after the view has been measured and laid out on the screen.
        view.post(() -> plotPoints(view));
        binding.buttonFirst.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
