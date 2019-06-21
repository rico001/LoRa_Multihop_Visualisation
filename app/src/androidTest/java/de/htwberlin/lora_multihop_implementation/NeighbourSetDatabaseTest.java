package de.htwberlin.lora_multihop_implementation;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.location.Location;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import de.htwberlin.lora_multihop_logic.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_logic.components.storage.NeighbourSetDAO;
import de.htwberlin.lora_multihop_logic.components.storage.NeighbourSetDatabase;
import de.htwberlin.lora_multihop_logic.enums.ELoraNodeState;

@RunWith(AndroidJUnit4.class)
public class NeighbourSetDatabaseTest {

    private NeighbourSetDAO neighbourSetDAO;
    private NeighbourSetDatabase db;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, NeighbourSetDatabase.class).build();
        neighbourSetDAO = db.neighbourSetDao();
    }

    @Test
    public void testSaveObjRemoveByObjSuccess() {
        NeighbourSet ns = createNeighbourSetUid0000();

        neighbourSetDAO.saveNeighbourSet(ns);
        List<NeighbourSet> neighbourSets = neighbourSetDAO.getAllNeighbourSets();
        Assert.assertEquals(1, neighbourSets.size());

        neighbourSetDAO.deleteNeighbourSet(ns);
        neighbourSets = neighbourSetDAO.getAllNeighbourSets();
        Assert.assertEquals(0, neighbourSets.size());

        neighbourSetDAO.clearTable();
    }

    @Test
    public void testSaveObjRemoveByObjFail() {
        NeighbourSet ns = createNeighbourSetUid0000();

        neighbourSetDAO.saveNeighbourSet(ns);
        List<NeighbourSet> neighbourSets = neighbourSetDAO.getAllNeighbourSets();
        Assert.assertEquals(1, neighbourSets.size());

        ns = createNeighbourSetUid0001();
        neighbourSetDAO.deleteNeighbourSet(ns);
        neighbourSets = neighbourSetDAO.getAllNeighbourSets();
        Assert.assertEquals(1, neighbourSets.size());

        neighbourSetDAO.clearTable();
    }

    @Test
    public void testSaveObjRemoveByUidSuccess() {
        NeighbourSet ns = createNeighbourSetUid0000();

        neighbourSetDAO.saveNeighbourSet(ns);
        List<NeighbourSet> neighbourSets = neighbourSetDAO.getAllNeighbourSets();
        Assert.assertEquals(1, neighbourSets.size());

        neighbourSetDAO.deleteNeighbourSetByUid(0000);
        neighbourSets = neighbourSetDAO.getAllNeighbourSets();
        Assert.assertEquals(0, neighbourSets.size());

        neighbourSetDAO.clearTable();
    }

    @Test
    public void testSaveObjRemoveObjByUidFail() {
        NeighbourSet ns = createNeighbourSetUid0000();

        neighbourSetDAO.saveNeighbourSet(ns);
        List<NeighbourSet> neighbourSets = neighbourSetDAO.getAllNeighbourSets();
        Assert.assertEquals(1, neighbourSets.size());

        neighbourSetDAO.deleteNeighbourSetByUid(0001);
        neighbourSets = neighbourSetDAO.getAllNeighbourSets();
        Assert.assertEquals(1, neighbourSets.size());

        neighbourSetDAO.clearTable();
    }

    @Test
    public void testUpdateObjSuccess() {
        NeighbourSet ns = createNeighbourSetUid0000();
        neighbourSetDAO.saveNeighbourSet(ns);

        Assert.assertEquals("AAAB", ns.getDah());

        ns.setDah("AAAX");
        neighbourSetDAO.updateNeighbourSet(ns);
        ns = neighbourSetDAO.getNeighbourSetByUid(0000);

        Assert.assertEquals("AAAX", ns.getDah());
    }

    @Test
    public void testSaveTwoObjAndClearTableSuccess() {
        NeighbourSet nsOne = createNeighbourSetUid0000();
        NeighbourSet nsTwo = createNeighbourSetUid0001();

        neighbourSetDAO.saveNeighbourSet(nsOne);
        neighbourSetDAO.saveNeighbourSet(nsTwo);

        Assert.assertEquals(2, neighbourSetDAO.getAllNeighbourSets().size());

        neighbourSetDAO.clearTable();

        Assert.assertEquals(0, neighbourSetDAO.getAllNeighbourSets().size());
    }

    @Test
    public void testGetNsByUidSuccess() {
        NeighbourSet nsForSave = createNeighbourSetUid0001();
        neighbourSetDAO.saveNeighbourSet(nsForSave);

        NeighbourSet nsFromDb = neighbourSetDAO.getNeighbourSetByUid(1);
        Assert.assertEquals(nsForSave.getUid(), nsFromDb.getUid());
    }

    @Test
    public void testGetNsByUidFail() {
        NeighbourSet nsForSave = createNeighbourSetUid0001();
        neighbourSetDAO.saveNeighbourSet(nsForSave);

        NeighbourSet nsFromDb = neighbourSetDAO.getNeighbourSetByUid(2);
        Assert.assertNull(nsFromDb);
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    private NeighbourSet createNeighbourSetUid0000() {
        Location local = new Location("berlin");
        local.setLatitude(52.5200);
        local.setLongitude(13.4050);
        return new NeighbourSet(0, "AAAA", "AAAB", local, ELoraNodeState.UNKNOWN, System.currentTimeMillis());
    }

    private NeighbourSet createNeighbourSetUid0001() {
        Location local = new Location("berlin");
        local.setLatitude(52.5200);
        local.setLongitude(13.4050);
        return new NeighbourSet(1, "AAAB", "AAAC", local, ELoraNodeState.UNKNOWN, System.currentTimeMillis());
    }
}