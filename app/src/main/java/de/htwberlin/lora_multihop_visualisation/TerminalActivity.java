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

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STATE_CONNECTING:
                    updateTerminalMessages(readColor, "Verbindung mit " + SingletonDevice.getBluetoothDevice().getName() + " wird aufgebaut", true);
                    break;
                case STATE_CONNECTED:
                    updateTerminalMessages(readColor, "Verbindung ist aufgebaut", true);
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    Log.d("blue", "send:     " + writeMessage);
                    updateTerminalMessages(readColor, writeMessage, false);
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    Log.d("blue", "read:     " + readMessage);
                    updateTerminalMessages(sendColor, readMessage, true);
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

            try {
                if (btService.isConnected()) {
                    String messageString = terminalInput.getText().toString();
                    byte[] messageByte = (messageString + AT_POSTFIX).getBytes();
                    btService.write(messageByte);
                }

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

}
