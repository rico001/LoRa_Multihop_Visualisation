package de.htwberlin.lora_multihop_visualisation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import java.util.HashMap;

import de.htwberlin.lora_multihop_logic.interfaces.LoraCommandsConstants;

/**
 * Allows to save up to MAXSIZE favorite AT commands
 * You can delete cmd with on long click on cmd button
 */
public class LastCommandsUI_Layout implements LoraCommandsConstants{

    private static final int MAXSIZE=4;

    /**
     * contains favorite cmds
     */
    HashMap<String,String> lastCmds;

    /**
     * contains favorite cmds as buttons
     */
    LinearLayout lastComandsLayout;

    Context context;

    View.OnClickListener onClickListener;

    public LastCommandsUI_Layout(LinearLayout lastComandsLayout, Context context, View.OnClickListener onItemClick){

        lastCmds= new HashMap<>();

        this.lastComandsLayout=lastComandsLayout;
        this.context = context;
        this.onClickListener=onItemClick;

        addDefaultCommands();
    }

    public void addCMD(String newCMD){

        if(lastCmds.containsKey(newCMD) || newCMD.isEmpty() || lastCmds.size()>=MAXSIZE)
            return;

        lastCmds.put(newCMD,newCMD);

        update_LastCommandsUI_Layout(newCMD);
    }

    private void update_LastCommandsUI_Layout(String cmd){

            Button b = new Button(context);
            b.setOnClickListener(onClickListener);
            b.setText(cmd);

            b.setOnLongClickListener(view ->{
                lastCmds.remove(cmd);
                ((ViewGroup) view.getParent()).removeView(view);
                return true;
            });

            lastComandsLayout.addView(b);
    }

    /**
     * some default additions
     */
    private void addDefaultCommands(){
        addCMD(TEST_CMD_MSG);
        addCMD(GET_ADDRESS_CMD_MSG);
        addCMD(SET_ADDRESS_CMD_MSG);
    }
}
