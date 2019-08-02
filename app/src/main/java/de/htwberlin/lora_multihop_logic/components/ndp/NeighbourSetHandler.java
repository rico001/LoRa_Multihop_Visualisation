package de.htwberlin.lora_multihop_logic.components.ndp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import de.htwberlin.lora_multihop_logic.components.storage.NeighbourSetDatabase;
import de.htwberlin.lora_multihop_visualisation.fragments.NeighbourSetTableFragment;

public class NeighbourSetHandler {

    private NeighbourSetDatabase db;
    private static NeighbourSetHandler instance;

    public NeighbourSetHandler(Context context) {
        this.db = Room.databaseBuilder(context, NeighbourSetDatabase.class, "neighbour-sets-db").build();
    }

    public NeighbourSetDatabase getDb() {
        return db;
    }

    public void setDb(NeighbourSetDatabase db) {
        this.db = db;
    }

    public static synchronized NeighbourSetHandler getInstance(Context context) {
        if (instance == null) instance = new NeighbourSetHandler(context);
        return instance;
    }
}
