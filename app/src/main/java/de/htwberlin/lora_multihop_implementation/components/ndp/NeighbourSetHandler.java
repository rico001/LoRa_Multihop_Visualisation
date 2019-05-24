package de.htwberlin.lora_multihop_implementation.components.ndp;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;

import de.htwberlin.lora_multihop_implementation.components.storage.NeighbourSetDatabase;

public class NeighbourSetHandler extends AppCompatActivity {

	private NeighbourSetDatabase db;

	public NeighbourSetHandler()	{
		this.db = Room.databaseBuilder(getApplicationContext(), NeighbourSetDatabase.class, "neighbour-sets-db").build();
	}

	public NeighbourSetDatabase getDb() {
		return db;
	}

	public void setDb(NeighbourSetDatabase db) {
		this.db = db;
	}
}
