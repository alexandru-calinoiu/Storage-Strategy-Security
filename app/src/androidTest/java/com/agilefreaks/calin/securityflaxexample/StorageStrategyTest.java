package com.agilefreaks.calin.securityflaxexample;

import junit.framework.TestCase;

public class StorageStrategyTest extends TestCase {
  private StorageStrategy subject;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    subject = new StorageStrategy();
  }

  public void testSaveSettings() throws Exception {
    subject.saveSettings();
  }
}