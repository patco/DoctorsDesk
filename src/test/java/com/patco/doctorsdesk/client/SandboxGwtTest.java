package com.patco.doctorsdesk.client;

import com.google.gwt.junit.client.GWTTestCase;

public class SandboxGwtTest extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "com.patco.doctorsdesk.DoctorsDesk";
    }

    public void testSandbox() {
        assertTrue(true);
    }
}