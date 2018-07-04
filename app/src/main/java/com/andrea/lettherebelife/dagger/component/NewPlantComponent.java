package com.andrea.lettherebelife.dagger.component;

import com.andrea.lettherebelife.dagger.module.NewPlantModule;
import com.andrea.lettherebelife.dagger.scope.PerActivity;
import com.andrea.lettherebelife.features.newplant.ui.NewPlantFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = NewPlantModule.class)
public interface NewPlantComponent {
    void inject(NewPlantFragment fragment);
}
