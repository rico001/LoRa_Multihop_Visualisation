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
        db = Room.databaseBuilder(context, NeighbourSetDatabase.class, DB_NAME).build();
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

    public void getAllNeighbourSets(Fragment fragment)  {
        GetDataTask task = new GetDataTask(fragment) {
            @Override
            protected List<NeighbourSet> doInBackground(String... strings) {
                return db.neighbourSetDao().getAllNeighbourSets();
            }
        };

        task.execute();
    }

    private abstract class GetDataTask extends AsyncTask<String, Void, List<NeighbourSet>> {

        private NeighbourSetTableFragment fragment;

        public GetDataTask(Fragment fragment)  {
            if (fragment.getClass() == NeighbourSetTableFragment.class) {
                this.fragment = (NeighbourSetTableFragment) fragment;
            }
        }

        protected void onPostExecute(List<NeighbourSet> neighbourSets) {
            super.onPostExecute(neighbourSets);
            neighbourSets.forEach(ns -> fragment.addRow(ns));
        }
    }
}
