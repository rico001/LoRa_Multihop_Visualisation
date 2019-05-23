package de.htwberlin.lora_multihop_implementation.components.storage;

import androidx.room.*;
import de.htwberlin.lora_multihop_implementation.components.model.NeighbourSet;

import java.util.List;

@Dao
public interface NeighbourSetDAO {

	@Query("SELECT * FROM neighbourset WHERE uid LIKE :uid LIMIT 1")
	NeighbourSet getNeighbourSetByUid(int uid);

	@Query("SELECT * FROM neighbourset")
	List<NeighbourSet> getAllNeighbourSets();

	@Insert
	int saveNeighbourSet(NeighbourSet song);

	@Update
	boolean updateNeighbourSet(NeighbourSet song);

	@Delete
	boolean deleteNeighbourSet(int uid);
}
