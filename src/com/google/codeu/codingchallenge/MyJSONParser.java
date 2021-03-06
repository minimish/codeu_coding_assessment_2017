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

import java.io.IOException;
import java.util.*;

final class MyJSONParser implements JSONParser {


  @Override
  public JSON parse(String in) throws IOException {
    if(!okString(in) || !okObj(in)) {
      throw new IOException();
    }
    JSON js = new MyJSON();
      //set up string to work with
    int startPos = in.indexOf('{');
    int endPos = in.length() - 1;
    String newStr = in.substring(startPos++, endPos).trim();

    if(newStr.length() == 0) {
      return js;
    }
    ArrayList<String> getKeyVal = getKeyVal(newStr);
    String key = getKeyVal.get(0);
    String val = getKeyVal.get(1);
    iterateObjects(val, js, key, newStr, in);
    return js;
  }
  
  //helper method to manipulate objects
  private void iterateObjects(String value, JSON js, String key, String newStr, String in) {
    ArrayList<String> res = new ArrayList<String>();
    if(okObj(value)) {
      js.setObject(key, parse(value));
    }
    else {
      //save all objects
      String[] objects = newStr.split(",");
      for(String s: objects) {
          if(!s.contains(":")) {
            throw new IOException(); 
          }
          //get rid of whitespaces
          s = s.replaceAll("\\s","");
          int colonPos = s.indexOf(':');

          //getName and value
          String sName = s.substring(0, colonPos);
          String sVal = s.substring(colonPos+1);
          js.setString(sName, sVal);
      }

  }
}

//helper method to determine if char is an escape character
//where would I check this?
private boolean escapeChar(String s) {
  for(int i = 0; i < s.length(); i++) {
    Character c = s.charAt(i);
    if(c == '\\') {
     char nextChar = c++;
     if(nextChar != 'n' || nextChar != 't') {
       return false;
     }
   }
 }
 return true;
}

  //helper method to get key and value from strings
private ArrayList<String> getKeyVal(String newStr) {
  ArrayList<String> res = new ArrayList<String>();

    int colonPos = newStr.indexOf(':');
    String key = newStr.substring(0, colonPos);

    key = key.substring(1, key.length() - 1);
    String val = newStr.substring(colonPos++);
    res.add(key);
    res.add(val);

  return res; 
}


  //helper method to determine if a string is valid
private boolean okString(String s) {
    //remove whitespace
  s = s.replaceAll("\\s","");

  Character firstChar = s.charAt(0);
  Character lastChar = s.charAt(s.length() - 1);

  if((firstChar != '\\') && (lastChar != '\\')) {
    return false;
  }
  else if(s.length() < 2) {
    return false;
  }

    //must be a valid string
  return true;
}

  //helper method to determine if a string is valid
private boolean okObj(String s) {
    //remove whitespace
  s = s.replaceAll("\\s","");
  int colonPos = s.indexOf(':');

  Character firstChar = s.charAt(0);
  Character lastChar = s.charAt(s.length() - 1);

  if((firstChar != '{') && (lastChar != '}')) {
    return false;
  }
  else if(s.length() < 2) {
    return false;
  }

    //must be valid
  return true;
}       

}
