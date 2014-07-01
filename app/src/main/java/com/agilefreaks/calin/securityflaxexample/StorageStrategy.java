package com.agilefreaks.calin.securityflaxexample;

import android.util.Log;

import java.security.Security;
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
Expanding the bounds of values for our security level, increases complexity
 */

public class StorageStrategy {
  public static final int MAX_SECURITY = 1;
  public static final int NO_SECURITY = 0;
  public static final int REQUIRE_ENCRYPTION = 3;


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

  public void storeData(String key, String data, int security) {
    StorageHandler storageHandler;

    if (security < REQUIRE_ENCRYPTION) {
      storageHandler = getPublicHandler();
    } else {
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
    storeData("name", "private stuff", MAX_SECURITY);
    storeData("private.groups", "some more private stuff", MAX_SECURITY);
    storeData("address", "private address", MAX_SECURITY);

    storeData("profilephoto", "public info", NO_SECURITY);
    storeData("homepageurl", "public info", NO_SECURITY);
  }
}
