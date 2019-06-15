package de.htwberlin.lora_multihop_logic;

import de.htwberlin.lora_multihop_logic.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_logic.components.ndp.NeighbourSetHandler;
import de.htwberlin.lora_multihop_logic.components.storage.NeighbourSetDatabase;

public class NeighbourSetData {
    private static NeighbourSetData instance;
    private NeighbourSetHandler nsh;
    private NeighbourSetDatabase db;
    private INeighbourSetData listener;

    private NeighbourSetData() {
        this.nsh = new NeighbourSetHandler();
        this.db = nsh.getDb();
    }

    public static NeighbourSetData getInstance() {
        if (instance == null) {
            instance = new NeighbourSetData();
        }

        return instance;
    }

    public void addListener(INeighbourSetData listener) {
        this.listener = listener;
    }

    public void saveNeighbourSet(NeighbourSet neighbourSet) {
        this.db.neighbourSetDao().saveNeighbourSet(neighbourSet);
        this.listener.onSaveNeighbourSet(neighbourSet);
    }

    public void updateNeighbourSet(NeighbourSet neighbourSet) {
        this.db.neighbourSetDao().updateNeighbourSet(neighbourSet);
        this.listener.onUpdateNeighbourSet(neighbourSet);
    }

    public void deleteNeighbourSet(NeighbourSet neighbourSet) {
        this.db.neighbourSetDao().deleteNeighbourSet(neighbourSet);
        this.listener.onDeleteNeighbourSet(neighbourSet.getUid());
    }

    public void deleteNeighbourSetByUid(int uid) {
        this.db.neighbourSetDao().deleteNeighbourSetByUid(uid);
        this.listener.onDeleteNeighbourSet(uid);
    }

    public void clearTable() {
        this.db.neighbourSetDao().clearTable();
        this.listener.onClearTable();
    }

    public interface INeighbourSetData {
        void onSaveNeighbourSet(NeighbourSet neighbourSet);
        void onUpdateNeighbourSet(NeighbourSet neighbourSet);
        void onDeleteNeighbourSet(int uid);
        void onClearTable();
    }
}
