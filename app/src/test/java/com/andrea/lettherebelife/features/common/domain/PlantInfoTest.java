package com.andrea.lettherebelife.features.common.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PlantInfoTest {

    @Test
    public void plantInfo_getters() {
        // Setup
        PlantInfo plantInfo = new PlantInfo(1,
                                            "Basil",
                                            "This is a common herb",
                                            "Optimal Sun",
                                            "Planting Considerations",
                                            "Growing From Seed",
                                            "Other Care",
                                            "Diseases",
                                            "Harvesting",
                                            "Storage");

        // Verify
        assertThat(plantInfo.getId(), is(1));
        assertThat(plantInfo.getName(), is("Basil"));
        assertThat(plantInfo.getDescription(), is("This is a common herb"));
        assertThat(plantInfo.getOptimalSun(), is("Optimal Sun"));
        assertThat(plantInfo.getPlantingConsiderations(), is("Planting Considerations"));
        assertThat(plantInfo.getGrowingFromSeed(), is("Growing From Seed"));
        assertThat(plantInfo.getOtherCare(), is("Other Care"));
        assertThat(plantInfo.getDiseases(), is("Diseases"));
        assertThat(plantInfo.getHarvesting(), is("Harvesting"));
        assertThat(plantInfo.getStorageUse(), is("Storage"));
    }

}