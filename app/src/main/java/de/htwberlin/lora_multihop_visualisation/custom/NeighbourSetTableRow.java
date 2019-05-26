package de.htwberlin.lora_multihop_visualisation.custom;

import android.app.Activity;
import android.content.Context;
import android.widget.TableRow;
import android.widget.TextView;

import de.htwberlin.lora_multihop_visualisation.R;

public class NeighbourSetTableRow extends TableRow {
    TextView uid;
    TextView address;
    TextView dah;
    TextView latitude;
    TextView longitude;
    TextView status;
    TextView timestamp;

    public NeighbourSetTableRow(Context context) {
        super(context);
        ((Activity) context).getLayoutInflater().inflate(R.layout.neighbour_set_table_row, this);

        uid = (TextView) findViewById(R.id.row_uid);
        address = (TextView) findViewById(R.id.row_address);
        dah = (TextView) findViewById(R.id.row_dah);
        latitude = (TextView) findViewById(R.id.row_latitude);
        longitude = (TextView) findViewById(R.id.row_longitude);
        status = (TextView) findViewById(R.id.row_state);
        timestamp = (TextView) findViewById(R.id.row_timestamp);
    }

    public void setUidText(String uid) {
        this.uid.setText(uid);
    }
    public void setAddressText(String address) {
        this.address.setText(address);
    }
    public void setDahText(String dah) {
        this.dah.setText(dah);
    }
    public void setLatitudeText(String latitude) {
        this.latitude.setText(latitude);
    }
    public void setLongitudeText(String longitude) {
        this.longitude.setText(longitude);
    }
    public void setStatusText(String status) {
        this.status.setText(status);
    }
    public void setTimestampText(String timestamp) {
        this.timestamp.setText(timestamp);
    }
}
