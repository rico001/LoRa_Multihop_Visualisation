package de.htwberlin.lora_multihop_visualisation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class NeighbourSetTableFragment extends Fragment {
    private TableLayout table;

    public NeighbourSetTableFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_set_table, container, false);

        table = (TableLayout) view.findViewById(R.id.neighbour_set_table);
        addRow();
        addRow();
        addRow();
        return view;
    }

    public void addRow() {
        TableRow row = new TableRow(getContext());

        TextView uid = new TextView(getContext());
        uid.setText("182381923");

        TextView address = new TextView(getContext());
        address.setText("FFF");

        TextView dah = new TextView(getContext());
        dah.setText("CDF");

        TextView latitude = new TextView(getContext());
        latitude.setText("50.000");

        TextView longitude = new TextView(getContext());
        longitude.setText("50.000");

        TextView state = new TextView(getContext());
        state.setText("Sending");

        TextView timestamp = new TextView(getContext());
        timestamp.setText("182381923");

        row.addView(uid);
        row.addView(address);
        row.addView(dah);
        row.addView(latitude);
        row.addView(longitude);
        row.addView(state);
        row.addView(timestamp);

        row.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);

        table.addView(row);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
