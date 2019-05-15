package de.htwberlin.lora_multihop_visualisation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

/**
 * configuration for some AT CMDS (e.g. CONFIGURE_CMD).
 */
public class LoraSettingsActivity extends AppCompatActivity {

    private static String defaultMhzValue="433";

    //SeekBars
    SeekBar seekBar_transmitPower;
    SeekBar seekBar_frequency;
    SeekBar seekBar_bandwidth;

    //TextViews
    TextView textView_transmitPower;
    TextView textView_frequency;
    TextView textView_bandwidth;
    TextView textView_CRC;

    //Switches
    Switch switch_crc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        initTextViews();
        initSeekBars();
        initSwitches();
        syncTextViewsAndSeekBars();
        syncTextViewsAndSwitches();
        setDefaultOptions();                //later options will loadet
    }
    private void initTextViews(){
        textView_transmitPower = (TextView) findViewById(R.id.textView_transmitPower);
        textView_frequency = (TextView) findViewById(R.id.textView_frequency);
        textView_CRC = (TextView) findViewById(R.id.textView_CRC);
        textView_bandwidth = (TextView) findViewById(R.id.textView_bandwidth);
    }

    private void initSeekBars(){
        seekBar_frequency = (SeekBar) findViewById(R.id.seekBar_frequency);
        seekBar_transmitPower = (SeekBar) findViewById(R.id.seekBar_transmitPower);
        seekBar_bandwidth = (SeekBar) findViewById(R.id.seekBar_bandwidth);
    }

    private void initSwitches(){
        switch_crc = (Switch) findViewById(R.id.switch_crc);
    }

    private void syncTextViewsAndSeekBars() {
        seekBar_frequency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String unit = "mhz";
                
                switch(progress) {
                    case 0:
                        textView_frequency.setText(defaultMhzValue+unit);
                        break;
                    case 1:
                        textView_frequency.setText(progress+unit);
                        break;
                    case 2:
                        textView_frequency.setText(progress+unit);
                        break;
                    case 3:
                        textView_frequency.setText(progress+unit);
                        break;
                    default:
                        textView_frequency.setText(progress+unit);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        seekBar_bandwidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String unit = "dBm";
                
                switch(progress) {
                    case 0:
                        textView_bandwidth.setText(progress+unit);
                        break;
                    case 1:
                        textView_bandwidth.setText(progress+unit);
                        break;
                    case 2:
                        textView_bandwidth.setText(progress+unit);
                        break;
                    case 3:
                        textView_bandwidth.setText(progress+unit);
                        break;
                    default:
                        textView_bandwidth.setText(progress+unit);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar_transmitPower.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String unit = "dBm";

                switch(progress) {
                    case 0:
                        textView_transmitPower.setText(progress+unit);
                        break;
                    case 1:
                        textView_transmitPower.setText(progress+unit);
                        break;
                    case 2:
                        textView_transmitPower.setText(progress+unit);
                        break;
                    case 3:
                        textView_transmitPower.setText(progress+unit);
                        break;
                    default:
                        textView_transmitPower.setText(progress+unit);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void syncTextViewsAndSwitches(){
        switch_crc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    textView_CRC.setText("1");
                }else {
                    textView_CRC.setText("0");
                }
            }
        });
    }

    private void setDefaultOptions(){
        seekBar_transmitPower.setProgress(3);
        seekBar_frequency.setProgress(0);
        seekBar_bandwidth.setProgress(2);
        switch_crc.setChecked(true);
    }

}