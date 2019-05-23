package de.htwberlin.lora_multihop_visualisation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.htwberlin.lora_multihop_implementation.enums.EFragments;
import de.htwberlin.lora_multihop_implementation.interfaces.ITerminalFragmentListener;

public class TerminalFragment extends Fragment implements ITerminalFragmentListener {
    private final static int sendColor = Color.RED;
    private final static int readColor = Color.BLUE;
    private final static String AT_POSTFIX = "\r\n";

    private static final String TAG = "TerminalFragment";

    private LinearLayout terminalMessages;
    private EditText terminalInput;
    private Button sendButton;
    private Button returnButton;

    public TerminalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terminal, container, false);
        terminalMessages = (LinearLayout) view.findViewById(R.id.terminal_messages);
        terminalInput = (EditText) view.findViewById(R.id.terminal_send_text);
        sendButton = (Button) view.findViewById(R.id.terminal_send_button);
        returnButton = (Button) view.findViewById(R.id.return_to_map);

        /**
         * Action for the send button
         */
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == sendButton.getId()) {
                    try {
                        ((MainActivity) getActivity()).btService.write((terminalInput.getText().toString() + AT_POSTFIX).getBytes());
                        updateTerminalMessages(sendColor, terminalInput.getText().toString(), true);
                    } catch (NullPointerException e) {
                        updateTerminalMessages(sendColor, "Wählen Sie ein Device in den Settings", true);
                    }
                } else {
                    Toast.makeText(getActivity(), sendButton.getText() + " AT-Routine", Toast.LENGTH_LONG).show();
                }
            }
        });

        /**
         * Button to return to the map
         */
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setViewPager(EFragments.MAP_FRAGMENT.get());
            }
        });

        return view;
    }

    /**
     * Adds new messages into terminal, declared in the ITerminalFragmentListener interface
     *
     * @param color of text
     * @param message
     * @param isSendMessage
     * @return
     */
    @Override
    public synchronized boolean updateTerminalMessages(int color, String message, boolean isSendMessage) {

        String symbols = "<< ";
        String time = getCurrentTime() + " ";

        if (isSendMessage) {
            symbols = ">> ";
        }

        TextView textView = new TextView(getActivity());
        textView.setText(time + symbols + message);
        textView.setTextColor(color);

        terminalMessages.addView(textView);

        //TODO auto. scrolling nach unten bei update
        return true;
    }

    private String getCurrentTime() {
        Date date = new Date();
        String strDateFormat = "hh:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
}
