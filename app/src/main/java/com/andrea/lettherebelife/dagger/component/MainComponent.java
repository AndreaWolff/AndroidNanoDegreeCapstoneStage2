package com.andrea.lettherebelife.dagger.component;

import com.andrea.lettherebelife.dagger.module.MainModule;
import com.andrea.lettherebelife.dagger.scope.PerActivity;
import com.andrea.lettherebelife.features.main.ui.MainActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
