package de.htwberlin.lora_multihop_visualisation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import de.htwberlin.lora_multihop_implementation.Configurator;
import de.htwberlin.lora_multihop_visualisation.settings.SeekBarHandler;

public class LoraSettingsActivity extends AppCompatActivity {
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

    Button button_saveSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);

        if (configurator == null) {
            if (prefs.getString("config", null) != null) {
                configurator = new Gson().fromJson(prefs.getString("config", null), Configurator.class);
            } else {
                configurator = new Configurator();
            }
        }

        initTextViews();
        initSeekBars();
        initSwitches();
        syncTextViewsAndSwitches();

        button_saveSettings = findViewById(R.id.button_saveSettings);

        button_saveSettings.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("config", new Gson().toJson(configurator));
            if (editor.commit()) {
                Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
            }
        });
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
}