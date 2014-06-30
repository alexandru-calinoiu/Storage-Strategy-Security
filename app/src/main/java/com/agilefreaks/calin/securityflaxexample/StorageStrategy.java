package com.agilefreaks.calin.securityflaxexample;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class StorageStrategy {
  private interface StorageHandler {
    public void store(String data);
  }

  private class PublicStoreAdapter implements StorageHandler {

    @Override
    public void store(String data) {
      Log.d("PublicStoreAdapter", "Store on sdcard");
    }
  }

  private class PrivateStoreAdapter implements StorageHandler {

    @Override
    public void store(String data) {
      Log.d("PublicStoreAdapter", "Store on private folder");
    }
  }

  private Map<String, StorageHandler> handlerMapping = new HashMap<String, StorageHandler>();

  public StorageStrategy() {
    handlerMapping.put("puser", new PrivateStoreAdapter());
    handlerMapping.put("group", new PublicStoreAdapter());
  }

  public void storeData(String key, String data) {
    int start = key.indexOf(".");
    String type = key.substring(start + 1, start + 6);

    handlerMapping.get(type).store(data);
  }

  public void saveSettings() {
    storeData("name.puser", "private stuff");
    storeData("private.groups.puser", "some more private stuff");
    storeData("address.puser", "private address");

    storeData("profilephoto.group", "public info");
    storeData("homepageurl.group", "public info");
  }
}
