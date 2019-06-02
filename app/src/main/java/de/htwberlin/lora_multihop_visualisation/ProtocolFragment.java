package de.htwberlin.lora_multihop_visualisation;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.htwberlin.lora_multihop_implementation.components.lora.LoraCommandsExecutor;
import de.htwberlin.lora_multihop_implementation.components.lora.LoraHandler;
import de.htwberlin.lora_multihop_implementation.components.messages.JoinMessage;
import de.htwberlin.lora_multihop_implementation.interfaces.ILoraCommands;
import de.htwberlin.lora_multihop_implementation.interfaces.MessageConstants;

public class ProtocolFragment extends Fragment implements MessageConstants {

    private static final String TAG = "ProtocolFragment";

    @BindView(R.id.buttonSearch)
    Button buttonSearch;
    @BindView(R.id.buttonUpdate)
    Button buttonUpdate;
    @BindView(R.id.buttonInit)
    Button buttonInit;
    @BindView(R.id.textviewOutput)
    TextView protocolOutput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_protocol, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.buttonSearch)
    public void searchButtonClicked() {
        String macAddress = SingletonDevice.getBluetoothDevice().getAddress();
        String modulAddress = macAddress.replace(":", "").substring(12 - 4);

        Log.i(TAG, "Modul address " + modulAddress);

        //JoinMessage join = new JoinMessage(modulAddress, 52.5545, 43.000);


        // Send JOIN
        protocolOutput.setText("Searching for Lora Modules ....");

        //join.executeAtRoutine();
        //protocolOutput.setText("Sending " + join.toString() + " as Broadcast.");

        // Parse reply
        // processs
    }

    @OnClick(R.id.buttonUpdate)
    public void updateButtonClicked() {

    }

    @OnClick(R.id.buttonInit)
    public void initButtonClicked() {
        protocolOutput.setText("Preparing module " + SingletonDevice.getBluetoothDevice().getName() + " for neighbour discovery.");
        //TODO: AT+CFG
        //TODO: AT+ADDR=
        //TODO: AT+DEST=FFFF
    }

}
