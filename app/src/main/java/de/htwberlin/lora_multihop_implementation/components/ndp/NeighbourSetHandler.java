package de.htwberlin.lora_multihop_implementation.components.ndp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
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
