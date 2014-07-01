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

  public void storeData(String key, String data, Boolean isPublic) {
    StorageHandler storageHandler;

    if (isPublic) {
      storageHandler = getPublicHandler();
    }
    else {
      storageHandler = getSecureHandler();
    }

    storageHandler.store(key, data);
  }

  private StorageHandler getPublicHandler() {
    return handlerMapping.get("public");
  }

  private StorageHandler getSecureHandler() {
    return handlerMapping.get("private");
  }

  public void saveSettings() {
    storeData("name", "private stuff", true);
    storeData("private.groups", "some more private stuff", true);
    storeData("address", "private address", true);

    storeData("profilephoto", "public info", false);
    storeData("homepageurl", "public info", false);
  }
}
