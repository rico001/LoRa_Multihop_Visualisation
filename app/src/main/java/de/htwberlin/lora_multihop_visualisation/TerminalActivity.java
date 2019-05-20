package de.htwberlin.lora_multihop_visualisation;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.htwberlin.lora_multihop_implementation.interfaces.MessageConstants;

public class TerminalActivity extends AppCompatActivity implements MessageConstants {

    private BluetoothService btService = null;

    private final static String AT_POSTFIX = "\r\n";
    private final static int sendColor = Color.WHITE;
    private final static int readColor = Color.RED;

    private LinearLayout terminalMessages;
    private EditText terminalInput;

    private static final String TAG = "term";

    private final Handler mHandler = new Handler() {
        @Override
        public synchronized void handleMessage(Message msg) {
            switch (msg.what) {
                case STATE_CONNECTING:
                    updateTerminalMessages(readColor, "Verbindung mit " + SingletonDevice.getBluetoothDevice().getName() + " wird aufgebaut", true);
                    break;
                case STATE_CONNECTED:
                    updateTerminalMessages(readColor, "Verbindung ist aufgebaut", true);
                    break;
                case MESSAGE_READ:
                    String message = (String) msg.obj;
                    updateTerminalMessages(readColor, message, false);
                    break;
                case MESSAGE_ERROR:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal);
        terminalInput = (EditText) findViewById(R.id.terminal_send_text);
        terminalMessages = (LinearLayout) findViewById(R.id.terminal_messages);

        try {
            btService = new BluetoothService(this, mHandler, SingletonDevice.getBluetoothDevice());
            btService.connectWithBluetoothDevice();
        } catch (NullPointerException e) {
            updateTerminalMessages(sendColor, "Wählen Sie ein Device in den Settings", true);
        }
    }

    public void buttonHandling(View v) {
        Button b = (Button) v;

        if (v.getId() == R.id.terminal_send_button) {

            try {   btService.write((terminalInput.getText().toString()+AT_POSTFIX).getBytes());
                    updateTerminalMessages(sendColor, terminalInput.getText().toString(), true);
            } catch (NullPointerException e) {
                updateTerminalMessages(sendColor, "Wählen Sie ein Device in den Settings", true);
            }
        } else {
            Toast.makeText(this, b.getText() + " AT-Routine", Toast.LENGTH_LONG).show();
        }

    }

    private boolean updateTerminalMessages(int color, String message, boolean isSendMessage) {

        String symbols = "<< ";
        String time = getCurrentTime() + " ";

        if (isSendMessage) {
            symbols = ">> ";
        }

        TextView textView = new TextView(this);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(btService==null){
            return;
        }else{
            btService.diconnect();
        }
    }
}
