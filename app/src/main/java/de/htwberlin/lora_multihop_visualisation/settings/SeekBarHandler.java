package de.htwberlin.lora_multihop_visualisation.settings;

import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SeekBarHandler {
    private SeekBar input;
    private TextView textView;
    private Map<Integer, String> valueLabelMap;
    private int min;
    private int max;
    private String unit = "";

    private Consumer<Integer> setter;

    public SeekBarHandler(SeekBar input, TextView textView, Map<Integer, String> valueLabelMap) {
        this.input = input;
        this.textView = textView;
        this.valueLabelMap = valueLabelMap;

        input.setOnSeekBarChangeListener(new SeekBarChangeHandler());
    }

    public SeekBarHandler(SeekBar input, TextView textView, int min, int max, String unit) {
        this.input = input;
        this.textView = textView;
        this.min = min;
        this.max = max;
        this.unit = unit;

        input.setOnSeekBarChangeListener(new SeekBarChangeHandler());
    }

    public void setValueProxy(Supplier<Integer> getter, Consumer<Integer> setter) {
        this.setter = setter;

        // Init with the current value.
        int value = getter.get();
        int progress = valueToProgress(value);
        input.setProgress(progress);
        syncLabel(value);
    }

    /**
     * Calculate the SeekBar progress for the given value.
     */
    private int valueToProgress(int value) {
        if (valueLabelMap != null) {
            // Find the progress step based on the value: index of the value is equals the progress step.
            // value = 6, valueLabelMap = {5: 'asd', 6: 'zzz', 7: 'foo'}, then the progress = 1.
            Integer[] values = valueLabelMap.keySet().toArray(new Integer[0]);
            for (int i = 0; i < values.length; i++) {
                if (values[i] == value) {
                    return i;
                }
            }
        }

        int step = (max - min) / input.getMax();
        return (value - min) / step;
    }

    /**
     * Calculate the value for the given SeekBar progress.
     */
    private int progressToValue(int progress) {
        if (valueLabelMap != null) {
            // Find the value based on the progress that cannot be bigger than the amount of elements in the map.
            // So progress=1 would match the second element in the map (with whatever key that would be the value of the selected option).
            return valueLabelMap.keySet().toArray(new Integer[0])[progress];
        }

        // No label-value map, so try to derive the value from the progress based on value step per change.
        int step = (max - min) / input.getMax();
        return min + (progress * step);
    }

    private void syncLabel(int value) {
        String label = valueLabelMap != null ? valueLabelMap.get(value) : value + unit;
        textView.setText(label);
    }

    public class SeekBarChangeHandler implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                int value = progressToValue(progress);
                syncLabel(value);
                setter.accept(value);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
