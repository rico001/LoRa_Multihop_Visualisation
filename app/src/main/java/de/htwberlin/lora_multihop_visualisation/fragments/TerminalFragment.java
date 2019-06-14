package de.htwberlin.lora_multihop_visualisation.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.htwberlin.lora_multihop_logic.enums.EFragments;
import de.htwberlin.lora_multihop_visualisation.LastCommandsUI_Layout;
import de.htwberlin.lora_multihop_visualisation.MainActivity;
import de.htwberlin.lora_multihop_visualisation.R;
import de.htwberlin.lora_multihop_visualisation.SingletonDevice;

public class TerminalFragment extends Fragment {
    private final static int sendColor = Color.RED;
    private final static int readColor = Color.BLUE;
    private final static String AT_POSTFIX = "\r\n";

    private static final String TAG = "TerminalFragment";

    private ScrollView scrollView;
    private LinearLayout terminalMessages;
    private LastCommandsUI_Layout lastComandsUILayout = null;
    private EditText terminalInput;
    private Button sendButton;
    private Button returnButton;

    private ArrayList<String> messages = new ArrayList<>();

    public TerminalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            messages = savedInstanceState.getStringArrayList("messages");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terminal, container, false);
        scrollView = ((ScrollView) view.findViewById(R.id.terminal_scroll));
        terminalMessages = (LinearLayout) view.findViewById(R.id.terminal_messages);
        terminalInput = (EditText) view.findViewById(R.id.terminal_send_text);
        sendButton = (Button) view.findViewById(R.id.terminal_send_button);
        returnButton = (Button) view.findViewById(R.id.return_to_map);

        setUpTerminal();

        lastComandsUILayout = new LastCommandsUI_Layout(view.findViewById(R.id.linearLayout_lastCommands), getContext(), v -> {
            Button b = (Button) v;
            terminalInput.setText(b.getText());
        });

        /**
         * Action for the send button
         */
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lastComandsUILayout.addCMD(terminalInput.getText().toString());

                if (v.getId() == sendButton.getId()) {
                    try {
                        ((MainActivity) getActivity()).getSetupManager().getBtService().write((terminalInput.getText().toString() + AT_POSTFIX).getBytes());
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
        String finalMessage;
        String prompt = "";
        String sendPrompt = ">> ";
        TextView textView = new TextView(getContext());

        try {
            textView.setTextSize(12);
        } catch (NullPointerException e) {
            return false;
        }

        try {
            SingletonDevice.getBluetoothDevice();
            prompt = "[" + getCurrentTime() + " - " + SingletonDevice.getBluetoothDevice().getName() + " ~]$ ";
        } catch (NullPointerException e) {
            finalMessage = sendPrompt + "Please choose a bluetooth device!";
            messages.add(finalMessage);
            textView.setText(finalMessage);
            terminalMessages.addView(textView);
            return true;
        }

        //TODO: distinguish whether message is send by user or is a reply message. To supress the prompt for "real" terminal behaviour
        if (message.startsWith("AT,")) {
            finalMessage = message;
            textView.setText(message);
        } else if (message.startsWith("LR,")) {
            finalMessage = sendPrompt + message;
            textView.setText(finalMessage);
            isNDPMessage = Boolean.TRUE;
        } else {
            finalMessage = prompt + message;
            textView.setText(finalMessage);
        }

        // List with messages so
        // we can re-init the terminal in case
        // it gets destroyed
        messages.add(finalMessage);

        terminalMessages.addView(textView);
        terminalInput.setText("");

        // Auto scroll down
        scrollDown();

        return true;
    }

    /**
     * Sets up old messages in case the fragment was destroyed
     */
    private void setUpTerminal() {
        for (String message : messages) {
            TextView textView = new TextView(getContext());

            try {
                textView.setTextSize(12);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            textView.setText(message);
            terminalMessages.addView(textView);
        }

        scrollDown();
    }

    /**
     * Scrolls down
     */
    private void scrollDown() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("messages", messages);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private String getCurrentTime() {
        Date date = new Date();
        String strDateFormat = "HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
}
