// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.codingchallenge;

import java.util.Collection;
import java.util.*;

final class MyJSON implements JSON {
  
  Map<String, Object> map = new HashMap<String, Object>();
  
  @Override
  //gets name from map
  public JSON getObject(String name) {
    if(map.containsKey(name)) {
      if(map.get(name) instanceof JSON) {
        return (JSON) map.get(name);
      }
    }
    return null;
  }

  @Override
  public JSON setObject(String name, JSON value) {
    if(map.containsKey(name) && map.get(name) instanceof JSON) {
      map.remove(name);
    }
    map.put(name, value);
    return this;
  }

  @Override
  public String getString(String name) {
    if(map.containsKey(name)) {
      if(map.get(name) instanceof String) {
        return (String) map.get(name);
      }
    }
    return null;
  }

  @Override
  public JSON setString(String name, String value) {
    if(map.containsKey(name) && map.get(name) instanceof String) {
      map.remove(name);
    }
    map.put(name, value);
    return this;
  }

  @Override
  public void getObjects(Collection<String> names) {
    Set allKeys = map.keySet();
    for(Object key: allKeys) {
      if(map.get(key) instanceof Object) {
        names.add((String)key);
      }
    }
  }

  @Override
  public void getStrings(Collection<String> names) {
    Set allKeys = map.keySet();
    Iterator it = allKeys.iterator();
    while(it.hasNext()) {
      names.add((String)it.next());
    }
  }

}
