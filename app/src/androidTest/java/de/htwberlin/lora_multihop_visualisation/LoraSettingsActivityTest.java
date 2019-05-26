package de.htwberlin.lora_multihop_visualisation;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Functional tests for the settings page.
 * ...Change - a range input
 * ...Select - a dropdown input
 * ...Toggle - a checkbox input
 */
@RunWith(AndroidJUnit4.class)
public class LoraSettingsActivityTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("de.htwberlin.lora_multihop_visualisation", appContext.getPackageName());
    }

    @Test
    public void frequencyChange() {
        // todo: test that changing the frequency changes the generated config string
        // also validate the min-max range

        Context appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void powerChange() {
    }

    @Test
    public void modulationSelect() {
        // also validate the set of available options
    }

    @Test
    public void spreadSelect() {
        // also validate the set of available options
    }

    @Test
    public void errorCorrectionSelect() {
        // also validate the set of available options
    }


    @Test
    public void crcToggle() {
    }

    @Test
    public void implicitHeaderToggle() {
    }

    @Test
    public void singleReceptionToggle() {
    }

    @Test
    public void configurationStringReflectsChanges() {
    }

    @Test
    public void configureModule() {
        // todo: we expect the config string to be sent and what indicates the successful configuration?
        // expect "AT, OK\r\n"
    }
}
