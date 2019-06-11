package de.htwberlin.lora_multihop_visualisation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import de.htwberlin.lora_multihop_logic.components.lora.LoRaConfig;
import de.htwberlin.lora_multihop_visualisation.settings.SeekBarHandler;
import de.htwberlin.lora_multihop_visualisation.settings.SwitchHandler;

/**
 * configuration for some AT CMDS (e.g. CONFIGURE_CMD).
 */
public class LoraSettingsActivity extends AppCompatActivity {
    LoRaConfig loRaConfig;

    Button button_saveSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        LoRaApplication app = (LoRaApplication) getApplicationContext();
        loRaConfig = app.getLoRaConfig();

        initSeekBars();
        initSwitches();

        // Save button.
        button_saveSettings = findViewById(R.id.button_saveSettings);
        button_saveSettings.setOnClickListener(v -> {
            if (app.persistConfig(loRaConfig)) {
                Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSeekBars() {
        new SeekBarHandler(
                findViewById(R.id.seekBar_frequency),
                findViewById(R.id.textView_frequency),
                410, 470, "MHz"
        ).setValueProxy(() -> loRaConfig.getFrequency(), (v) -> loRaConfig.setFrequency(v));

        new SeekBarHandler(
                findViewById(R.id.seekBar_transmitPower),
                findViewById(R.id.textView_transmitPower),
                5, 20, "dBm"
        ).setValueProxy(() -> loRaConfig.getPower(), (v) -> loRaConfig.setPower(v));

        new SeekBarHandler(
                findViewById(R.id.seekBar_bandwidth),
                findViewById(R.id.textView_bandwidth),
                LoRaConfig.bandwidthMap
        ).setValueProxy(() -> loRaConfig.getSignalBw(), (v) -> loRaConfig.setSignalBw(v));

        new SeekBarHandler(
                findViewById(R.id.seekBar_spreading),
                findViewById(R.id.textView_spreading),
                LoRaConfig.spreadingMap
        ).setValueProxy(() -> loRaConfig.getSpreadingFactor(), (v) -> loRaConfig.setSpreadingFactor(v));

        new SeekBarHandler(
                findViewById(R.id.seekBar_errorCoding),
                findViewById(R.id.textView_errorCoding),
                LoRaConfig.errorCodingMap
        ).setValueProxy(() -> loRaConfig.getErrorCoding(), (v) -> loRaConfig.setErrorCoding(v));

        new SeekBarHandler(
                findViewById(R.id.seekBar_rxPacketTimeout),
                findViewById(R.id.textView_rxPacketTimeout),
                1, 65535, "ms"
        ).setValueProxy(() -> loRaConfig.getRxPacketTimeout(), (v) -> loRaConfig.setRxPacketTimeout(v));
    }

    private void initSwitches() {
        new SwitchHandler(
                findViewById(R.id.switch_crc),
                findViewById(R.id.textView_CRC),
                loRaConfig.getCrc(),
                (v) -> loRaConfig.setCrc(v)
        );

        new SwitchHandler(
                findViewById(R.id.switch_implicitHeader),
                findViewById(R.id.textView_implicitHeader),
                loRaConfig.getImplicitHeaderOn(),
                (v) -> loRaConfig.setImplicitHeaderOn(v)
        ).setOnOffLabels("implicit", "explicit");

        new SwitchHandler(
                findViewById(R.id.switch_rxSingle),
                findViewById(R.id.textView_rxSingle),
                loRaConfig.getRxSingleOn(),
                (v) -> loRaConfig.setRxSingleOn(v)
        ).setOnOffLabels("single", "continue");

        new SwitchHandler(
                findViewById(R.id.switch_frequencyHop),
                findViewById(R.id.textView_frequencyHop),
                loRaConfig.getFrequencyHopOn(),
                (v) -> loRaConfig.setFrequencyHopOn(v)
        );
    }
}