package de.htwberlin.lora_multihop_visualisation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProtocolFragment extends Fragment {

    @BindView(R.id.buttonFetch) Button fetch;
    @BindView(R.id.buttonFetchReply) Button fetchReply;

    @BindView(R.id.buttonJoin) Button join;
    @BindView(R.id.buttonJoinReply) Button joinReply;

    @BindView(R.id.buttonMove) Button move;

    @BindView(R.id.buttonLeave) Button leave;

    @BindView(R.id.buttonPull) Button pull;
    @BindView(R.id.buttonPush) Button push;

    @BindView(R.id.buttonAck) Button ack;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_protocol, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.buttonFetch)
    public void fetchClicked(){
    }

    @OnClick(R.id.buttonFetchReply)
    public void FetchReplyClicked(){
    }

    @OnClick(R.id.buttonPull)
    public void pullClicked(){
    }

    @OnClick(R.id.buttonPush)
    public void pushClicked(){
    }

    @OnClick(R.id.buttonMove)
    public void moveClicked(){
    }

    @OnClick(R.id.buttonLeave)
    public void leaveClicked(){
    }

    @OnClick(R.id.buttonAck)
    public void ackClicked(){
    }

    @OnClick(R.id.buttonJoin)
    public void joinClicked(){
    }

    @OnClick(R.id.buttonJoinReply)
    public void joinReplyClicked(){
    }
}
