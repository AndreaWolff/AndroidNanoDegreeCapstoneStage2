package com.andrea.lettherebelife.dagger.component;

import com.andrea.lettherebelife.dagger.module.PlantDetailsModule;
import com.andrea.lettherebelife.dagger.scope.PerActivity;
import com.andrea.lettherebelife.features.details.ui.PlantDetailsActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = PlantDetailsModule.class)
public interface PlantDetailsComponent {
    void inject(PlantDetailsActivity activity);
}
