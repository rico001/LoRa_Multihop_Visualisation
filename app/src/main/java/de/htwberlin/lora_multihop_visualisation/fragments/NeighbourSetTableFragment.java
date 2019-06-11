package de.htwberlin.lora_multihop_visualisation.fragments;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import java.util.HashMap;
import java.util.Map;

import de.htwberlin.lora_multihop_implementation.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_implementation.enums.ELoraNodeState;
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
        Location mapPoint = new Location("NodeOne");
        mapPoint.setLatitude(52.463201);
        mapPoint.setLongitude(13.507464);

        NeighbourSet nsOne = new NeighbourSet(0, "AAAA", "BBBB", mapPoint, ELoraNodeState.UP, System.currentTimeMillis())

        addRow(nsOne);
        getRow(Integer.toString(nsOne.getUid())).setAddressText("AAAB");

        return view;
    }

    /**
     * Adds a row to the table
     *
     * @param ns NeighbourSet data to be mapped
     */
    public void addRow(NeighbourSet ns) {
        NeighbourSetTableRow row = new NeighbourSetTableRow(getContext());

        row.setUidText(Integer.toString(ns.getUid()));
        row.setAddressText(ns.getAddress());
        row.setDahText(ns.getDah());
        row.setLatitudeText(ns.getLatitude());
        row.setLongitudeText(ns.getLongitude());
        row.setStatusText(ns.getEnumLoraNodeState().toString());
        row.setTimestampText(Long.toString(ns.getTimestamp()));

        tableData.put(Integer.toString(ns.getUid()), row);

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
