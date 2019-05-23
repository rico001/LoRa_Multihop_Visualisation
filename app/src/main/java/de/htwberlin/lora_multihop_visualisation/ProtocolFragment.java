package de.htwberlin.lora_multihop_visualisation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ProtocolFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_protocol, container, false);

        initButtons(view);

        return view;
    }

    private void initButtons(View view){
        Button b1 = (Button) view.findViewById(R.id.button_msg1);
        Button b2 = (Button) view.findViewById(R.id.button_msg2);
        Button b3 = (Button) view.findViewById(R.id.button_msg3);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
            switch (button.getId()) {
                case R.id.button_msg1:
                    //deine Logik
                    Toast.makeText(getActivity(), button.getText(), Toast.LENGTH_LONG).show();
                    break;
                case R.id.button_msg2:
                    //deine Logik
                    Toast.makeText(getActivity(), button.getText(), Toast.LENGTH_LONG).show();
                    break;
                case R.id.button_msg3:
                    //deine Logik
                    Toast.makeText(getActivity(), button.getText(), Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(getActivity(), button.getText(), Toast.LENGTH_LONG).show();
            }
    }

}
