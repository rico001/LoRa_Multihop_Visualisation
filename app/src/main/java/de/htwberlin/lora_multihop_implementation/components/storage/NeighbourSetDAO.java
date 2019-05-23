package de.htwberlin.lora_multihop_implementation.components.storage;

import de.htwberlin.lora_multihop_implementation.components.model.NeighbourSet;

import java.util.List;

public interface NeighbourSetDAO {

	NeighbourSet getNeighbourSetByUid(int uid);

	List<NeighbourSet> getAllNeighbourSets();

	int saveNeighbourSet(NeighbourSet song);

	boolean updateNeighbourSet(NeighbourSet song);

	boolean deleteNeighbourSet(int uid);
}
