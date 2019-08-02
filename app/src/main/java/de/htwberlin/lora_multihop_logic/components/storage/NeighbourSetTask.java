package de.htwberlin.lora_multihop_logic.components.storage;

import android.os.AsyncTask;

import de.htwberlin.lora_multihop_logic.components.model.NeighbourSet;
import de.htwberlin.lora_multihop_visualisation.LoRaApplication;

public class NeighbourSetTask extends AsyncTask<NeighbourSet, Void, Void> {
    private static final String TAG = "NeighbourSetTask";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(NeighbourSet... neighbourSets) {
        NeighbourSetDatabase db = LoRaApplication.getDb();
        db.neighbourSetDao().clearTable();
        db.neighbourSetDao().saveNeighbourSet(neighbourSets[0]);
        return null;
    }
}
