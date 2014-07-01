package com.agilefreaks.calin.securityflaxexample;

import android.util.Log;

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

/*
Prevent Secure pathways from being broken at runtime:
- the hash is not immutable
- easier to test
- and compile time

- tightly coupleled the code, we are still using the storage handler interface, we are being smart about where we apply the de-coupling
- modular in the right places
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

  public StorageStrategy() {
  }

  public void storeDataPublic(String key, String data) {
    getPublicHandler().store(key, data);
  }

  public void storeDataSecure(String key, String data) {
    getSecureHandler().store(key, data);
  }

  private StorageHandler getPublicHandler() {
    return new PublicStoreAdapter();
  }

  private StorageHandler getSecureHandler() {
    return new PrivateStoreAdapter();
  }

  public void saveSettings() {
    storeDataSecure("name", "private stuff");
    storeDataSecure("private.groups", "some more private stuff");
    storeDataSecure("address", "private address");

    storeDataPublic("profilephoto", "public info");
    storeDataPublic("homepageurl", "public info");
  }
}
