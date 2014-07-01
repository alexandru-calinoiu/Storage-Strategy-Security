package com.agilefreaks.calin.securityflaxexample;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/*
Things to consider:
- clarity with respect to security (we want to look at a abstraction and the security model should be very clear)
- catch flaws at compile time
- proper usage to be obvious
- separate and protect security state
 */

/*
Build abstractions that are hard to use insecurely
 */

/*
Add a enum
 */

/*
Avoid conditional logic
- easier to test
- semantics are clear
 */

public class StorageStrategy {
  private interface StorageHandler {
    public void store(String key, String data);
  }

  private class PublicStoreAdapter implements StorageHandler {

    @Override
    public void store(String key, String data) {
      Log.d("PublicStoreAdapter", "Store on sdcard");
    }
  }

  private class PrivateStoreAdapter implements StorageHandler {

    @Override
    public void store(String key, String data) {
      Log.d("PublicStoreAdapter", "Store on private folder");
    }
  }

  private Map<String, StorageHandler> handlerMapping = new HashMap<String, StorageHandler>();

  public StorageStrategy() {
    handlerMapping.put("private", new PrivateStoreAdapter());
    handlerMapping.put("public", new PublicStoreAdapter());
  }

  public void storeDataPublic(String key, String data) {
    getPublicHandler().store(key, data);
  }

  public void storeDataSecure(String key, String data) {
    getSecureHandler().store(key, data);
  }

  private StorageHandler getPublicHandler() {
    return handlerMapping.get("public");
  }

  private StorageHandler getSecureHandler() {
    return handlerMapping.get("private");
  }

  public void saveSettings() {
    storeDataSecure("name", "private stuff");
    storeDataSecure("private.groups", "some more private stuff");
    storeDataSecure("address", "private address");

    storeDataPublic("profilephoto", "public info");
    storeDataPublic("homepageurl", "public info");
  }
}
