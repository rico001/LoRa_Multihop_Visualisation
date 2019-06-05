package de.htwberlin.lora_multihop_visualisation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import java.util.HashMap;
import java.util.Map;

import de.htwberlin.lora_multihop_visualisation.R;
import de.htwberlin.lora_multihop_visualisation.custom.NeighbourSetTableHead;
import de.htwberlin.lora_multihop_visualisation.custom.NeighbourSetTableRow;

public class NeighbourSetTableFragment extends Fragment {
    private static final String TAG = "NeighbourSetTableFragme";

    private TableLayout table;
    private Map<String, NeighbourSetTableRow> tableData;
    private ITableListener listener;

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

        // Testing
        addRow("192831123", "000", "JFG", 52.463201, 13.507464, "Active", "182381912");
        addRow("182381923", "FFF", "CDF", 52.461776, 13.492454, "Sending", "182381923");
        addRow("182381924", "FFF", "CDF", 52.459102, 13.506970, "Sending", "182381923");
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
    public void addRow(String id, String address, String dah, Double latitude, Double longitude, String state, String timestamp) {
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

        this.listener.onRowAdded(row);
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
     * Gets the table data
     *
     * @return
     */
    public Map<String, NeighbourSetTableRow> getTableData() {
        return this.tableData;
    }

    /**
     * Removes a row
     *
     * @param id
     */
    public void removeRow(String id) {
        if (tableData.containsKey(id)) {
            table.removeView(tableData.get(id));
            this.listener.onRowRemoved(id);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ITableListener) {
            this.listener = (ITableListener) context;
        } else {
            throw new RuntimeException(context.toString() + " has to implement ITableListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    public interface ITableListener {
        void onRowAdded(NeighbourSetTableRow row);
        void onRowRemoved(String id);
    }
}
