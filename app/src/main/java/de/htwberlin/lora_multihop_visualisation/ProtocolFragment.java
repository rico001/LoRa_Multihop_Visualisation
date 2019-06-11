package de.htwberlin.lora_multihop_visualisation;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.htwberlin.lora_multihop_logic.components.messages.JoinMessage;
import de.htwberlin.lora_multihop_logic.interfaces.MessageConstants;

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
    @BindView(R.id.textViewStatus)
    TextView protocolStatus;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_protocol, container, false);
        ButterKnife.bind(this, view);
        initFragment();
        return view;
    }

    @OnClick(R.id.buttonSearch)
    public void searchButtonClicked() {
        String macAddress = SingletonDevice.getBluetoothDevice().getAddress();
        String modulAddress = macAddress.replace(":", "").substring(12 - 4);

        Log.i(TAG, "Modul address " + modulAddress);

        // Send JOIN
        protocolOutput.setText("Searching for Lora Modules ....");

        LatLng location = ((MainActivity) getActivity()).getMapFragment().getLocation();
        ((MainActivity) getActivity()).getSetupManager().getLoraHandler().getProcessor().getOutMessagesQueue().add(new JoinMessage(modulAddress, location.longitude, location.latitude));
    }

    @OnClick(R.id.buttonUpdate)
    public void updateButtonClicked() {

    }

    @OnClick(R.id.buttonInit)
    public void initButtonClicked() {
        String deviceAddress = SingletonDevice.getBluetoothDevice().getAddress().replace(":", "").substring(12 - 4);
        protocolOutput.setText("Initializing LoRA-Hop " + SingletonDevice.getBluetoothDevice().getName() + " for Neighbour Discovery.");
        protocolOutput.append("\nConfigure " + SingletonDevice.getBluetoothDevice().getName() + ".");

        protocolOutput.append("\nConfiguring source address [" + deviceAddress + "]");
        protocolOutput.append("\nConfiguring remote address [FFFF]");

        protocolOutput.append("\nSuccessfully finished initializing " + SingletonDevice.getBluetoothDevice().getName());
        buttonUpdate.setEnabled(Boolean.TRUE);
    }

    private void initFragment() {

        buttonInit.setEnabled(Boolean.FALSE);
        buttonUpdate.setEnabled(Boolean.FALSE);
        buttonSearch.setEnabled(Boolean.FALSE);

        try {
            SingletonDevice.getBluetoothDevice().getName();
            protocolStatus.setText("[CONNECTED]");
            buttonInit.setEnabled(Boolean.TRUE);
        } catch (NullPointerException e) {
            protocolStatus.setText("No active connection.\nPlease select an AT-module under Settings -> Bluetooth");
        }
    }

}
