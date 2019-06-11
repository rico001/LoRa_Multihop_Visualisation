package de.htwberlin.lora_multihop_visualisation;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Functional tests for the main communication page.
 * https://developer.android.com/studio/test
 */
@RunWith(AndroidJUnit4.class)
public class LoraCommunicationActivityTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("de.htwberlin.lora_multihop_visualisation", appContext.getPackageName());
    }

    @Test
    public void showItselfOnMap() {
        // todo: do we show our client immediately or only after we connected to the LR module?
    }

    @Test
    public void connect() {
        // todo: what do we expect when "connect" is clicked?
        // probably detect a message (successful of failed one) in the "log" window
        // todo: validate the set address by calling a protocol method to read module address: "AT+ADDR? \r\n"
    }

    @Test
    public void reset() {
        // todo: check for the successfully sent reset command in the "log" window
        // points on the map should disappear once we reset the module
    }

    @Test
    public void pointClick() {
        // todo: what do we expect? point becomes active and changes color? do we open a marker window with its coordinates and id?
    }

    @Test
    public void sendCommand() {
        // todo: type a message and click "send", expect the message to appear in the "log" window
    }

    @Test
    public void discoverClick() {
        // todo: discovering should start and then after some messages exchange a set of points must appear
    }
}
