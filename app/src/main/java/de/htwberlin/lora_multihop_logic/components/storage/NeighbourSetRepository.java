package de.htwberlin.lora_multihop_logic.components.storage;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import de.htwberlin.lora_multihop_logic.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_visualisation.LoRaApplication;
import de.htwberlin.lora_multihop_visualisation.fragments.MapFragment;
import de.htwberlin.lora_multihop_visualisation.fragments.NeighbourSetTableFragment;

public class NeighbourSetRepository {

    private String DB_NAME = "db_neighbourSet";

    private NeighbourSetDatabase db;

    public NeighbourSetRepository(Context context) {
        db = Room.inMemoryDatabaseBuilder(context, NeighbourSetDatabase.class).build();
    }

    public List<NeighbourSet> getAllNeighbourSets()  {
        return db.neighbourSetDao().getAllNeighbourSets();
    }

    public void getAllNeighbourSets(NeighbourSetTableFragment fragment)  {
        FillNeighbourSetTableWithDataTask task = new FillNeighbourSetTableWithDataTask(fragment) {
            @Override
            protected List<NeighbourSet> doInBackground(String... strings) {
                return db.neighbourSetDao().getAllNeighbourSets();
            }
        };

        task.execute();
    }

    public void getAllNeighbourSets(MapFragment fragment)  {
        FillMapWithDataTask task = new FillMapWithDataTask(fragment) {
            @Override
            protected List<NeighbourSet> doInBackground(String... strings) {
                return db.neighbourSetDao().getAllNeighbourSets();
            }
        };

        task.execute();
    }

    public void saveNeighbourSet(final NeighbourSet ns)  {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.neighbourSetDao().saveNeighbourSet(ns);
                return null;
            }
        }.execute();
    }

    public void clearTable()  {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.neighbourSetDao().clearTable();
                return null;
            }
        }.execute();
    }

    private abstract class FillNeighbourSetTableWithDataTask extends AsyncTask<String, Void, List<NeighbourSet>> {

        private NeighbourSetTableFragment fragment;

        public FillNeighbourSetTableWithDataTask(NeighbourSetTableFragment fragment)  {
            try {
                this.fragment = fragment;
            } catch (ClassCastException e)  {
                e.getStackTrace();
            }
        }

        protected void onPostExecute(List<NeighbourSet> neighbourSets) {
            super.onPostExecute(neighbourSets);
            neighbourSets.forEach(ns -> fragment.addRow(ns));
        }
    }

    private abstract class FillMapWithDataTask extends AsyncTask<String, Void, List<NeighbourSet>> {

        private MapFragment fragment;

        public FillMapWithDataTask(MapFragment fragment)  {
            this.fragment = fragment;
        }

        protected void onPostExecute(List<NeighbourSet> neighbourSets) {
            super.onPostExecute(neighbourSets);
            neighbourSets.forEach(ns -> fragment.addHostMarker(new LatLng(ns.getLatitude(), ns.getLongitude()), String.valueOf(ns.getUid()), LoRaApplication.RADIUS));
        }
    }
}
