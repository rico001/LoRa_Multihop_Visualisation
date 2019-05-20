package de.htwberlin.lora_multihop_implementation;

import org.junit.Assert;
import org.junit.Test;

public class ConfiguratorTest {
    @Test
    public void testDefaultConfigPresented() {
        Configurator configurator = new Configurator();

        Assert.assertEquals("410000000,20,6,10,1,1,0,0,0,0,3000,8,4", configurator.toString());
    }

    @Test
    public void testToString() {
        Configurator configurator = new Configurator();

        configurator.setFrequency(430);
        Assert.assertEquals(430, configurator.getFrequency());
        Assert.assertEquals("430000000,20,6,10,1,1,0,0,0,0,3000,8,4", configurator.toString());

        configurator.setSignalBw(5);
        Assert.assertEquals(5, configurator.getSignalBw());
        Assert.assertEquals("430000000,20,5,10,1,1,0,0,0,0,3000,8,4", configurator.toString());

        configurator.setCrc(0);
        Assert.assertEquals(0, configurator.getCrc());
        Assert.assertEquals("430000000,20,5,10,1,0,0,0,0,0,3000,8,4", configurator.toString());
    }
}