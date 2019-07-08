package de.htwberlin.lora_multihop_logic;

import java.util.ArrayList;
import java.util.List;

import de.htwberlin.lora_multihop_logic.components.model.LocalHop;
import de.htwberlin.lora_multihop_logic.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_logic.components.storage.NeighbourSetRepository;
import de.htwberlin.lora_multihop_visualisation.LoRaApplication;

public class NeighbourSetDataHandler {

    private NeighbourSetRepository repository;
    private List<INeighbourSetData> listeners;

    public NeighbourSetDataHandler(INeighbourSetData... neighbourSetData) {
        this.repository = LoRaApplication.getDbRepo();

        this.listeners = new ArrayList<>();
        for(int i = 0; i < neighbourSetData.length; i++)   {
            this.listeners.add(neighbourSetData[i]);
        }
    }

    public List<NeighbourSet> getAllNeighbourSets() {
        return this.repository.getAllNeighbourSets();
    }

    public void saveNeighbourSet(NeighbourSet neighbourSet) {
        this.repository.saveNeighbourSet(neighbourSet);
        this.listeners.stream()
                .forEach(l -> l.onSaveNeighbourSet(neighbourSet));
    }

    public void clear() {
        this.repository.clearTable();
        this.listeners.stream().forEach(l -> l.onClearTable());
    }

    public interface INeighbourSetData {
        void onSaveNeighbourSet(NeighbourSet neighbourSet);
        void onUpdateNeighbourSet(NeighbourSet neighbourSet);
        void onDeleteNeighbourSet(int uid);
        void onClearTable();
    }
}
