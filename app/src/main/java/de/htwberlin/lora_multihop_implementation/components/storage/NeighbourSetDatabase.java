package de.htwberlin.lora_multihop_implementation.components.storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import de.htwberlin.lora_multihop_implementation.components.model.NeighbourSet;

@Database(entities = {NeighbourSet.class}, version = 1, exportSchema = false)
public abstract class NeighbourSetDatabase extends RoomDatabase {
	public abstract NeighbourSetDAO neighbourSetDao();
}
