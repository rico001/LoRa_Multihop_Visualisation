package de.htwberlin.lora_multihop_visualisation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import de.htwberlin.lora_multihop_implementation.Configurator;
import de.htwberlin.lora_multihop_visualisation.settings.SeekBarHandler;

public class LoraSettingsActivity extends AppCompatActivity {

    private static String defaultMhzValue = "433";

    Configurator configurator;

    //SeekBars
    SeekBar seekBar_transmitPower;
    SeekBar seekBar_frequency;
    SeekBar seekBar_bandwidth;
    SeekBar seekBar_spreading;
    SeekBar seekBar_errorCoding;

    //TextViews
    TextView textView_transmitPower;
    TextView textView_frequency;
    TextView textView_bandwidth;
    TextView textView_spreading;
    TextView textView_errorCoding;
    TextView textView_CRC;

    //Switches
    Switch switch_crc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        // todo: we need to persist changes
        configurator = new Configurator();

        initTextViews();
        initSeekBars();
        initSwitches();
        syncTextViewsAndSwitches();
        setDefaultOptions();  //later options will loadet
    }

    private void initTextViews() {
        textView_transmitPower = findViewById(R.id.textView_transmitPower);
        textView_frequency = findViewById(R.id.textView_frequency);
        textView_bandwidth = findViewById(R.id.textView_bandwidth);
        textView_spreading = findViewById(R.id.textView_spreading);
        textView_errorCoding = findViewById(R.id.textView_errorCoding);

        textView_CRC = findViewById(R.id.textView_CRC);
    }

    private void initSeekBars() {
        seekBar_frequency = findViewById(R.id.seekBar_frequency);
        new SeekBarHandler(seekBar_frequency, textView_frequency, 410, 470, "MHz")
                .setValueProxy(() -> configurator.getFrequency(), (v) -> configurator.setFrequency(v));

        seekBar_transmitPower = findViewById(R.id.seekBar_transmitPower);
        new SeekBarHandler(seekBar_transmitPower, textView_transmitPower, 5, 20, "dBm")
                .setValueProxy(() -> configurator.getPower(), (v) -> configurator.setPower(v));

        seekBar_bandwidth = findViewById(R.id.seekBar_bandwidth);
        new SeekBarHandler(seekBar_bandwidth, textView_bandwidth, Configurator.bandwidthMap)
                .setValueProxy(() -> configurator.getSignalBw(), (v) -> configurator.setSignalBw(v));

        seekBar_spreading = findViewById(R.id.seekBar_spreading);
        new SeekBarHandler(seekBar_spreading, textView_spreading, Configurator.spreadingMap)
                .setValueProxy(() -> configurator.getSpreadingFactor(), (v) -> configurator.setSpreadingFactor(v));

        seekBar_errorCoding = findViewById(R.id.seekBar_errorCoding);
        new SeekBarHandler(seekBar_errorCoding, textView_errorCoding, Configurator.errorCodingMap)
                .setValueProxy(() -> configurator.getErrorCoding(), (v) -> configurator.setErrorCoding(v));
    }

    private void initSwitches() {
        switch_crc = findViewById(R.id.switch_crc);
    }

    private void syncTextViewsAndSwitches() {
        switch_crc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                textView_CRC.setText(isChecked ? "1" : "0");
            }
        });
    }

    private void setDefaultOptions() {
        seekBar_transmitPower.setProgress(3);
        seekBar_frequency.setProgress(0);
        seekBar_bandwidth.setProgress(2);
        switch_crc.setChecked(true);
    }

}