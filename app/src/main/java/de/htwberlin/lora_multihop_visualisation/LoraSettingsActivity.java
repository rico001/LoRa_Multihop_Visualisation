package de.htwberlin.lora_multihop_visualisation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import de.htwberlin.lora_multihop_implementation.components.lora.LoRaConfig;
import de.htwberlin.lora_multihop_visualisation.settings.SeekBarHandler;

/**
 * configuration for some AT CMDS (e.g. CONFIGURE_CMD).
 */
public class LoraSettingsActivity extends AppCompatActivity {
    LoRaConfig loRaConfig;

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

        LoRaApplication app = (LoRaApplication) getApplicationContext();
        loRaConfig = app.getLoRaConfig();

        initTextViews();
        initSeekBars();
        initSwitches();
        syncTextViewsAndSwitches();

        // Save button.
        button_saveSettings = findViewById(R.id.button_saveSettings);
        button_saveSettings.setOnClickListener(v -> {
            if (app.persistConfig(loRaConfig)) {
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
                .setValueProxy(() -> loRaConfig.getFrequency(), (v) -> loRaConfig.setFrequency(v));

        seekBar_transmitPower = findViewById(R.id.seekBar_transmitPower);
        new SeekBarHandler(seekBar_transmitPower, textView_transmitPower, 5, 20, "dBm")
                .setValueProxy(() -> loRaConfig.getPower(), (v) -> loRaConfig.setPower(v));

        seekBar_bandwidth = findViewById(R.id.seekBar_bandwidth);
        new SeekBarHandler(seekBar_bandwidth, textView_bandwidth, LoRaConfig.bandwidthMap)
                .setValueProxy(() -> loRaConfig.getSignalBw(), (v) -> loRaConfig.setSignalBw(v));

        seekBar_spreading = findViewById(R.id.seekBar_spreading);
        new SeekBarHandler(seekBar_spreading, textView_spreading, LoRaConfig.spreadingMap)
                .setValueProxy(() -> loRaConfig.getSpreadingFactor(), (v) -> loRaConfig.setSpreadingFactor(v));

        seekBar_errorCoding = findViewById(R.id.seekBar_errorCoding);
        new SeekBarHandler(seekBar_errorCoding, textView_errorCoding, LoRaConfig.errorCodingMap)
                .setValueProxy(() -> loRaConfig.getErrorCoding(), (v) -> loRaConfig.setErrorCoding(v));
    }

    private void initSwitches() {
        switch_crc = findViewById(R.id.switch_crc);
    }

    private void syncTextViewsAndSwitches() {
        switch_crc.setChecked(loRaConfig.getCrc() == 1);
        switch_crc.setOnCheckedChangeListener((buttonView, isChecked) -> {
            this.loRaConfig.setCrc(isChecked ? 1 : 0);
            textView_CRC.setText(isChecked ? "1" : "0");
        });
    }
}