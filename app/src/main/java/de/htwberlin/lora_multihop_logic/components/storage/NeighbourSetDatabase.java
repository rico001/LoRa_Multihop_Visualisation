package de.htwberlin.lora_multihop_logic.components.storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import de.htwberlin.lora_multihop_logic.components.model.NeighbourSet;

@Database(entities = {NeighbourSet.class}, version = 1, exportSchema = false)
public abstract class NeighbourSetDatabase extends RoomDatabase {

	private static NeighbourSetDatabase instance;

	public abstract NeighbourSetDAO neighbourSetDao();

	public static NeighbourSetDatabase getInMemoryDatabase(Context context) {
		if (instance == null) {
			instance = Room.inMemoryDatabaseBuilder(LoRaApplication.getAppContext(), NeighbourSetDatabase.class).allowMainThreadQueries().build();
		}
		return instance;
	}

	public static void destroyInstance() {
		instance = null;
	}
}
