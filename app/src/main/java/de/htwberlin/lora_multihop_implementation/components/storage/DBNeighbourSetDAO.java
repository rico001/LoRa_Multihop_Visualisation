package de.htwberlin.lora_multihop_implementation.components.storage;

import de.htwberlin.lora_multihop_implementation.components.model.NeighbourSet;

import java.util.List;

public class DBNeighbourSetDAO implements NeighbourSetDAO {

	//

	public DBNeighbourSetDAO() {
		//
	}

	@Override
	public NeighbourSet getNeighbourSetByUid(int uid) {
		return null;
	}

	@Override
	public List<NeighbourSet> getAllNeighbourSets() {
		return null;
	}

	@Override
	public int saveNeighbourSet(NeighbourSet song) {
		return 0;
	}

	@Override
	public boolean updateNeighbourSet(NeighbourSet song) {
		return false;
	}

	@Override
	public boolean deleteNeighbourSet(int uid) {
		return false;
	}
}
