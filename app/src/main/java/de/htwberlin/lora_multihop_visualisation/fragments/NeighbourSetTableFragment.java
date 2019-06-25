package de.htwberlin.lora_multihop_visualisation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import java.util.HashMap;

import de.htwberlin.lora_multihop_logic.NeighbourSetData;
import de.htwberlin.lora_multihop_logic.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_visualisation.LoRaApplication;
import de.htwberlin.lora_multihop_visualisation.R;
import de.htwberlin.lora_multihop_visualisation.custom.NeighbourSetTableHead;
import de.htwberlin.lora_multihop_visualisation.custom.NeighbourSetTableRow;


public class NeighbourSetTableFragment extends Fragment implements NeighbourSetData.INeighbourSetData {

    private static final String TAG = "NeighbourSetTableFragment";

    private TableLayout table;
    private HashMap<String, NeighbourSetTableRow> tableData;
    private ITableListener listener;

    public NeighbourSetTableFragment() {
        this.tableData = new HashMap<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_set_table, container, false);
        NeighbourSetTableHead tableHead = new NeighbourSetTableHead(getContext());

        this.table = (TableLayout) view.findViewById(R.id.neighbour_set_table);
        this.table.addView(tableHead);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        LoRaApplication.getDbRepo().getAllNeighbourSets(this);
    }

    /**
     * Adds a row to the table
     *
     * @param ns NeighbourSet data to be mapped
     */
    public void addRow(NeighbourSet ns) {
        if(getContext() != null)    {
            NeighbourSetTableRow row = new NeighbourSetTableRow(getContext());

            row.setUidText(Integer.toString(ns.getUid()));
            row.setAddressText(ns.getAddress());
            row.setDahText(ns.getDah());
            row.setLatitudeText(ns.getLatitude());
            row.setLongitudeText(ns.getLongitude());
            row.setStatusText(ns.getEnumLoraNodeState().toString());
            row.setTimestampText(Long.toString(ns.getTimestamp()));

            this.tableData.put(Integer.toString(ns.getUid()), row);

            this.table.addView(row);

            this.listener.onRowAdded(row);
        }
    }


    /**
     * Gets the row with the matching id
     *
     * @param id
     * @return
     */
    public NeighbourSetTableRow getRow(String id) {
        return this.tableData.get(id);
    }

    public void updateRow(NeighbourSet neighbourSet) {
        NeighbourSetTableRow row = getRow(Integer.toString(neighbourSet.getUid()));
        row.setAddressText(neighbourSet.getAddress());
        row.setDahText(neighbourSet.getDah());
        row.setLatitudeText(neighbourSet.getLatitude());
        row.setLongitudeText(neighbourSet.getLongitude());
        row.setStatusText(neighbourSet.getState());
        row.setTimestampText(Long.toString(neighbourSet.getTimestamp()));

        this.listener.onRowUpdated(row);
    }

    /**
     * Removes a row
     *
     * @param id
     */
    public void removeRow(String id) {
        if (this.tableData.containsKey(id)) {
            this.table.removeView(this.tableData.get(id));
            this.listener.onRowRemoved(id);
        }
    }

    public void removeAll() {
        this.tableData.clear();
        this.listener.onRemoveAll();
    }

    @Override
    public void onSaveNeighbourSet(NeighbourSet neighbourSet) {
        addRow(neighbourSet);
    }

    @Override
    public void onUpdateNeighbourSet(NeighbourSet neighbourSet) {
        updateRow(neighbourSet);
    }

    @Override
    public void onDeleteNeighbourSet(int uid) {
        removeRow(Integer.toString(uid));
    }

    @Override
    public void onClearTable() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
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
        void onRowUpdated(NeighbourSetTableRow row);
        void onRowRemoved(String id);
        void onRemoveAll();
    }
}
