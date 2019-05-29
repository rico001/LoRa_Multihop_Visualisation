package de.htwberlin.lora_multihop_visualisation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.htwberlin.lora_multihop_implementation.components.parser.MessageParser;
import de.htwberlin.lora_multihop_implementation.components.parser.ParserException;
import de.htwberlin.lora_multihop_implementation.enums.EFragments;

public class TerminalFragment extends Fragment {
    private final static int sendColor = Color.RED;
    private final static int readColor = Color.BLUE;
    private final static String AT_POSTFIX = "\r\n";

    private static final String TAG = "TerminalFragment";

    private ScrollView scrollView;
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
        scrollView = ((ScrollView) view.findViewById(R.id.terminal_scroll));
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
                ((MainActivity) getActivity()).setViewPager(EFragments.MAP_FRAGMENT.get());
            }
        });
        return view;
    }

    /**
     * Adds new messages into terminal, declared in the ITerminalFragmentListener interface
     *
     * @param color         of text
     * @param message
     * @param isSendMessage
     * @return
     */
    public synchronized boolean updateTerminalMessages(int color, String message, boolean isSendMessage) {

        boolean isNDPMessage = Boolean.FALSE;
        String prompt = "[" + getCurrentTime() + " - " + SingletonDevice.getBluetoothDevice().getName() + " ~]$ ";
        String sendPrompt = ">> ";
        TextView textView;

        try {
            textView = new TextView(getContext());
        } catch (NullPointerException e) {
            return false;
        }

        textView.setTextSize(12);

        //TODO: distinguish whether message is send by user or is a reply message. To supress the prompt for "real" terminal behaviour
        if (message.startsWith("AT,")) textView.setText(message);
        else if (message.startsWith("LR,")) {
            textView.setText(sendPrompt + message);
            isNDPMessage = Boolean.TRUE;
        } else textView.setText(prompt + message);

        terminalMessages.addView(textView);
        terminalInput.setText("");

        // Parsing
        if (isNDPMessage) {
            try {
                MessageParser.parseInput(message);
            } catch (ParserException e) {
                Log.e(TAG, "Error while parsing " + message);
            }
        }

        // Auto scroll down
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        return true;
    }

    private String getCurrentTime() {
        Date date = new Date();
        String strDateFormat = "HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
}
