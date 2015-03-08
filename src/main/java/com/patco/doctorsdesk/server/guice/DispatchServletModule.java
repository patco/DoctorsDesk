package com.patco.doctorsdesk.server.guice;

import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.ServletModule;
import com.gwtplatform.dispatch.rpc.server.guice.DispatchServiceImpl;
import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

public class DispatchServletModule extends ServletModule {
    @Override
    public void configureServlets() {
        filter("/*").through(PersistFilter.class);
        serve("/" + ActionImpl.DEFAULT_SERVICE_NAME + "*").with(DispatchServiceImpl.class);
        
    }
}