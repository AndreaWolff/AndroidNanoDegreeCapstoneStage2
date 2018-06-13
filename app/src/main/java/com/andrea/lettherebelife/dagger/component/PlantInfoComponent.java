package com.andrea.lettherebelife.dagger.component;

import com.andrea.lettherebelife.dagger.module.PlantInfoModule;
import com.andrea.lettherebelife.dagger.scope.PerActivity;
import com.andrea.lettherebelife.features.plantinfo.ui.PlantInfoActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = PlantInfoModule.class)
public interface PlantInfoComponent {
    void inject(PlantInfoActivity activity);
}
