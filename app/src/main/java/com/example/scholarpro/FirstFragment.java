package com.example.scholarpro;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.scholarpro.databinding.FragmentFirstBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jjoe64.graphview.series.DataPoint;


import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    public void plotPoints(View view) {
        LineChart chart = view.findViewById(R.id.chart);

        GraphCalculator calculator = new GraphCalculator();
        List<DataPoint> points = calculator.calculateParabola(-100, 100);

        List<Entry> entries = new ArrayList<>();
        for (DataPoint p : points) {
            entries.add(new Entry((float) p.getX(), (float) p.getY()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "");

        // 🔴 Line styling
        dataSet.setColor(Color.parseColor("#eb1c24"));
        dataSet.setLineWidth(3f);
        dataSet.setDrawCircles(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); // smooth curve

        // 🌈 Gradient fill (THIS is the magic)
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

        // Clean look
        dataSet.setDrawValues(false);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        // 🔥 Remove all axes, grids, borders
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);

        chart.getXAxis().setEnabled(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);

        chart.setViewPortOffsets(0, 0, 0, 0); // edge-to-edge
        chart.invalidate();
        chart.fitScreen();
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
