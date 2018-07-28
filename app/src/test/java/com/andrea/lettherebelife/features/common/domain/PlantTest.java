package com.andrea.lettherebelife.features.common.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PlantTest {

    @Test
    public void plant_getters() {
        // Setup
        Plant plant = new Plant("Basil", "Today", "This is a common herb.", "");

        // Verify
        assertThat(plant.getName(), is("Basil"));
        assertThat(plant.getSeedDate(), is("Today"));
        assertThat(plant.getDescription(), is("This is a common herb."));
        assertThat(plant.getPhotoUrl(), is(""));
    }

}