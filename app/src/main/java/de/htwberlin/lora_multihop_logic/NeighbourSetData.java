package de.htwberlin.lora_multihop_logic;

import android.content.Context;

import de.htwberlin.lora_multihop_logic.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_logic.components.storage.NeighbourSetDatabase;
import de.htwberlin.lora_multihop_logic.components.storage.NeighbourSetRepository;
import de.htwberlin.lora_multihop_visualisation.LoRaApplication;
import de.htwberlin.lora_multihop_visualisation.fragments.NeighbourSetTableFragment;

public class NeighbourSetData   {

    private NeighbourSetRepository repository;
    private INeighbourSetData listener;

    public NeighbourSetData(INeighbourSetData nstFragment) {
        this.repository = LoRaApplication.getDbRepo();
        this.listener = nstFragment;
    }

    public void addListener(INeighbourSetData listener) {
        this.listener = listener;
    }

    public void saveNeighbourSet(NeighbourSet neighbourSet) {
        this.repository.saveNeighbourSet(neighbourSet);
        this.listener.onSaveNeighbourSet(neighbourSet);
    }
/*
    public void updateNeighbourSet(NeighbourSet neighbourSet) {
        this.repository.updateNeighbourSet(neighbourSet);
        this.listener.onUpdateNeighbourSet(neighbourSet);
    }

    public void deleteNeighbourSet(NeighbourSet neighbourSet) {
        this.repository.deleteNeighbourSet(neighbourSet);
        this.listener.onDeleteNeighbourSet(neighbourSet.getUid());
    }

    public void deleteNeighbourSetByUid(int uid) {
        this.repository.deleteNeighbourSetByUid(uid);
        this.listener.onDeleteNeighbourSet(uid);
    }

    public void clearTable() {
        this.repository.clearTable();
        this.listener.onClearTable();
    }
*/
    public interface INeighbourSetData {
        void onSaveNeighbourSet(NeighbourSet neighbourSet);
        void onUpdateNeighbourSet(NeighbourSet neighbourSet);
        void onDeleteNeighbourSet(int uid);
        void onClearTable();
    }
}
