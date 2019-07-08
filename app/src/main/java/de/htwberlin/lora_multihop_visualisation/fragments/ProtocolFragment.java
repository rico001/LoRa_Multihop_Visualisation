package de.htwberlin.lora_multihop_visualisation.fragments;

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
import de.htwberlin.lora_multihop_logic.components.model.LocalHop;
import de.htwberlin.lora_multihop_logic.interfaces.LoraCommandsConstants;
import de.htwberlin.lora_multihop_logic.interfaces.MessageConstants;
import de.htwberlin.lora_multihop_visualisation.MainActivity;
import de.htwberlin.lora_multihop_visualisation.R;
import de.htwberlin.lora_multihop_visualisation.SingletonDevice;

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
        // buttonSearch.setEnabled(false);
        String macAddress = SingletonDevice.getBluetoothDevice().getAddress();
        String modulAddress = macAddress.replace(":", "").substring(12 - 4);
        LatLng location = ((MainActivity) getActivity()).getMapFragment().getLocation();
        JoinMessage joinMessage = new JoinMessage("1", location.longitude, location.latitude);

        protocolOutput.append("\nSearching for Lora Modules ....");
        protocolOutput.append("\nSending JOIN Message (" + joinMessage.toString() + ") to \"" + joinMessage.getRemoteAddress() + "\"");
        ((MainActivity) getActivity()).getSetupManager().getLoraHandler().getProcessor().getOutMessagesQueue().add(joinMessage);

    }

    @OnClick(R.id.buttonUpdate)
    public void updateButtonClicked() {

    }

    @OnClick(R.id.buttonInit)
    public void initButtonClicked() {
        buttonUpdate.setEnabled(false);

        protocolOutput.setText("Initializing \"" + SingletonDevice.getBluetoothDevice().getName() + "\" for Neighbour Discovery.");
        String deviceAddress = SingletonDevice.getBluetoothDevice().getAddress().replace(":", "").substring(12 - 4);

        protocolOutput.append("\nConfiguring source address: \"" + deviceAddress + "\"");
        ((MainActivity) getActivity()).getSetupManager().getLoraHandler().getBtService().write(((LoraCommandsConstants.SET_ADDRESS_CMD_MSG).replace("XX", deviceAddress)).getBytes());
        LocalHop localHop = LocalHop.getInstance();
        localHop.setAddress(deviceAddress);

        protocolOutput.append("\nConfiguring remote address: \"FFFF\"");
        buttonUpdate.setVisibility(View.VISIBLE);
        buttonSearch.setVisibility(View.VISIBLE);

    }

    private void initFragment() {

        buttonInit.setVisibility(View.INVISIBLE);
        buttonUpdate.setVisibility(View.INVISIBLE);
        buttonSearch.setVisibility(View.INVISIBLE);

        try {
            SingletonDevice.getBluetoothDevice().getName();
            protocolStatus.setText("[CONNECTED]");
            buttonInit.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            protocolStatus.setText("No active connection.\nPlease select an AT-module under Settings -> Bluetooth");
        }
    }

}
