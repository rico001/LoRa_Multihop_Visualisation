package de.htwberlin.lora_multihop_implementation;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.location.Location;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;

import de.htwberlin.lora_multihop_implementation.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_implementation.components.storage.NeighbourSetDAO;
import de.htwberlin.lora_multihop_implementation.components.storage.NeighbourSetDatabase;
import de.htwberlin.lora_multihop_implementation.enums.ELoraNodeState;

@RunWith(AndroidJUnit4.class)
public class NeighbourSetDatabaseTest {

    private NeighbourSetDAO userDao;
    private NeighbourSetDatabase db;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, NeighbourSetDatabase.class).build();
        userDao = db.neighbourSetDao();
    }

    @Test
    public void test() {

    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    private NeighbourSet createNeighbourSet()   {
        Location local = new Location("berlin");
        local.setLatitude(52.5200);
        local.setLongitude(13.4050);
        return new NeighbourSet(0000, "AAAA", "AAAB", local, ELoraNodeState.UNKNOWN, new Timestamp(System.currentTimeMillis()));
    }
}