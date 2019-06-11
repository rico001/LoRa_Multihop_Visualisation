package de.htwberlin.lora_multihop_logic.components.storage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import de.htwberlin.lora_multihop_logic.components.model.NeighbourSet;

import java.util.List;

@Dao
public interface NeighbourSetDAO {

	@Query("SELECT * FROM neighbourset WHERE uid LIKE :uid LIMIT 1")
    NeighbourSet getNeighbourSetByUid(int uid);

	@Query("SELECT * FROM neighbourset")
	List<NeighbourSet> getAllNeighbourSets();

	@Insert
	void saveNeighbourSet(NeighbourSet neighbourSet);

	@Update
	void updateNeighbourSet(NeighbourSet neighbourSet);

	@Delete
	void deleteNeighbourSet(NeighbourSet neighbourSet);

    @Query("DELETE FROM neighbourset WHERE uid LIKE :uid")
    void deleteNeighbourSetByUid(int uid);

    @Query("DELETE FROM neighbourset")
    void clearTable();
}
