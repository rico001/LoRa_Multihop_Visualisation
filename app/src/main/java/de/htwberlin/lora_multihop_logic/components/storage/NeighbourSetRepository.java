package de.htwberlin.lora_multihop_logic.components.storage;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import java.util.List;

import de.htwberlin.lora_multihop_logic.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_visualisation.fragments.NeighbourSetTableFragment;

public class NeighbourSetRepository {

    private String DB_NAME = "db_neighbourSet";

    private NeighbourSetDatabase db;

    public NeighbourSetRepository(Context context) {
        db = Room.inMemoryDatabaseBuilder(context, NeighbourSetDatabase.class).build();
    }

    public void getAllNeighbourSets(Fragment fragment)  {
        FillNeighbourSetTableWithDataTask task = new FillNeighbourSetTableWithDataTask(fragment) {
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

        public FillNeighbourSetTableWithDataTask(Fragment fragment)  {
            try {
                this.fragment = (NeighbourSetTableFragment) fragment;
            } catch (ClassCastException e)  {
                e.getStackTrace();
            }
        }

        protected void onPostExecute(List<NeighbourSet> neighbourSets) {
            super.onPostExecute(neighbourSets);
            neighbourSets.forEach(ns -> fragment.addRow(ns));
        }
    }
}
