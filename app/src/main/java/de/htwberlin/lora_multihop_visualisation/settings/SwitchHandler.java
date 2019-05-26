package de.htwberlin.lora_multihop_visualisation.settings;

import android.widget.Switch;
import android.widget.TextView;

import java.util.function.Consumer;

public class SwitchHandler {
    private String on = "an";
    private String off = "aus";

    public SwitchHandler(Switch input, TextView labelView, int value, Consumer<Integer> setter) {
        input.setChecked(value == 1);
        input.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setter.accept(isChecked ? 1 : 0);
            labelView.setText(isChecked ? on : off);
        });
    }

    public void setOnOffLabels(String onLabel, String offLabel) {
        on = onLabel;
        off = offLabel;
    }
}
