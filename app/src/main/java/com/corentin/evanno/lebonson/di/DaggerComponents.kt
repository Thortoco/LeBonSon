package com.corentin.evanno.lebonson.di

import android.content.Context

object DaggerComponents {

    @Volatile private var sComponent: ApplicationComponent? = null

    @Synchronized fun get(): ApplicationComponent {
        var component = sComponent
        if (component == null) {
            component = createDefaultComponent()
            sComponent = component
        }
        return component
    }


    /**
     * Returns the default dagger component for the app.
     * @param appContext The application context.
     * @return The default <tt>ApplicationComponent</tt>.
     */
    private fun createDefaultComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .build()
    }

}
