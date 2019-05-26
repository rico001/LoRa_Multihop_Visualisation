package de.htwberlin.lora_multihop_visualisation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import java.util.HashMap;
import java.util.Map;

import de.htwberlin.lora_multihop_implementation.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_visualisation.custom.NeighbourSetTableHead;
import de.htwberlin.lora_multihop_visualisation.custom.NeighbourSetTableRow;

public class NeighbourSetTableFragment extends Fragment {
    private TableLayout table;
    private Map<String, NeighbourSetTableRow> tableData;

    public NeighbourSetTableFragment() {
        tableData = new HashMap<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_set_table, container, false);
        NeighbourSetTableHead tableHead = new NeighbourSetTableHead(getContext());

        table = (TableLayout) view.findViewById(R.id.neighbour_set_table);

        table.addView(tableHead);

        //addRow("182381923", "FFF", "CDF", "50.000", "50.000", "Sending", "182381923");
        //addRow("182381924", "FFF", "CDF", "50.000", "50.000", "Sending", "182381923");
        //getRow("182381923").setAddressText("000");

        return view;
    }

    /**
     * Adds a row to the table
     *
     * @param id
     * @param address
     * @param dah
     * @param latitude
     * @param longitude
     * @param state
     * @param timestamp
     */
    public void addRow(String id, String address, String dah, String latitude, String longitude, String state, String timestamp) {
        NeighbourSetTableRow row = new NeighbourSetTableRow(getContext());

        row.setUidText(id);
        row.setAddressText(address);
        row.setDahText(dah);
        row.setLatitudeText(latitude);
        row.setLongitudeText(longitude);
        row.setStatusText(state);
        row.setTimestampText(timestamp);

        tableData.put(id, row);

        table.addView(row);
    }


    /**
     * Gets the row with the matching id
     *
     * @param id
     * @return
     */
    public NeighbourSetTableRow getRow(String id) {
        return tableData.get(id);
    }

    /**
     * Removes a row
     *
     * @param id
     */
    public void removeRow(String id) {
        if (tableData.containsKey(id)) {
            table.removeView(tableData.get(id));
        }
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
