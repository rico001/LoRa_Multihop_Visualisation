package de.htwberlin.lora_multihop_visualisation.custom;

import android.app.Activity;
import android.content.Context;
import android.widget.TableRow;
import android.widget.TextView;

import de.htwberlin.lora_multihop_visualisation.R;

public class NeighbourSetTableHead extends TableRow {
    TextView uid;
    TextView address;
    TextView dah;
    TextView latitude;
    TextView longitude;
    TextView status;
    TextView timestamp;

    public NeighbourSetTableHead(Context context) {
        super(context);
        ((Activity) context).getLayoutInflater().inflate(R.layout.neighbour_set_table_head, this);

        uid = (TextView) findViewById(R.id.row_head_uid);
        address = (TextView) findViewById(R.id.row_head_address);
        dah = (TextView) findViewById(R.id.row_head_dah);
        latitude = (TextView) findViewById(R.id.row_head_latitude);
        longitude = (TextView) findViewById(R.id.row_head_longitude);
        status = (TextView) findViewById(R.id.row_head_state);
        timestamp = (TextView) findViewById(R.id.row_head_timestamp);
    }
}
