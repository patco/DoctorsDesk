package com.patco.doctorsdesk.server.guice;

import com.google.inject.persist.jpa.JpaPersistModule;
import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

public class ServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
    	install(new JpaPersistModule("com.patco.doctorsdesk"));
    }
}