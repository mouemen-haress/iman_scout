package com.example.scoutchallenge.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JsonHelper {

    public static JSONObject parse(String data) {
        try {
            if (data == null || data.isEmpty() || data.equalsIgnoreCase("null")) return null;
            JSONObject obj = new JSONObject(data);
            if (obj.has("retcode")) {
                int retcode = obj.getInt("retcode");
                if (retcode == -2 || retcode == -3) {
                    //SESSION EXPIRED

                }
            }
            return obj;
        } catch (JSONException e) {

        }
        return null;
    }

    public static JSONArray parseArray(String data) {
        try {
            if (data == null) return null;
            JSONArray obj = new JSONArray(data);
            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static JSONArray getJSONArray(JSONArray obj, int index) {
        if (obj != null && obj.length() > index) {
            try {
                return obj.getJSONArray(index);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static JSONObject getJSONObject(JSONArray obj, int index) {
        if (obj != null && obj.length() > index) {
            try {
                return obj.getJSONObject(index);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static JSONArray getJSONArray(JSONObject obj, String name) {
        if (obj != null && obj.has(name)) {
            try {
                return obj.getJSONArray(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static JSONObject getJSONObject(JSONObject obj, String name) {
        if (obj != null && obj.has(name)) {
            try {
                return obj.getJSONObject(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static String getString(JSONObject obj, String name) {
        if (obj != null && obj.has(name)) {
            try {
                return obj.getString(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int getInt(JSONObject obj, String name) {
        if (obj != null && obj.has(name)) {
            try {
                return obj.getInt(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static void put(JSONObject obj, String key, String value) {
        try {
            if (obj != null) obj.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void put(JSONObject obj, String key, Object value) {
        try {
            if (obj != null) obj.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void put(JSONObject obj, String key, int value) {
        try {
            if (obj != null) obj.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void put(JSONObject obj, String key, JSONObject value) {
        try {
            if (obj != null) obj.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void put(JSONArray arr, JSONObject obj) {
        if (arr != null) {
            arr.put(obj);
        }
    }

    /*
        this method, convert json arr to string compatible with HW backend, since "name"
        should be before "param" and when using toString the property param is shown
        before "name"
     */
    public static String batchToString(JSONArray arr) {
        String result = "{\"requestList\":[";
        try {
            JSONObject item;
            String itemStr;
            for (int i = 0; i < arr.length(); i++) {
                item = arr.getJSONObject(i);
                itemStr = "{\"name\":\"" + item.getString("name") + "\",\"param\":" + item.getJSONObject("param").toString() + "}";
                result += itemStr;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        result += "]}";
        return result;
    }

    public static JSONArray removeObject(JSONArray array, int position) {
        JSONArray newArray = new JSONArray();
        try {
            for (int i = 0; i < array.length(); i++) {
                if (i != position)
                    newArray.put(array.getJSONObject(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newArray;
    }

    public static JSONArray removeObjectWithId(JSONArray array, String id) {
        JSONArray newArray = new JSONArray();
        try {
            for (int i = 0; i < array.length(); i++) {
                String currentId = array.getJSONObject(i).getString("id");
                if (!currentId.equals(id))
                    newArray.put(array.getJSONObject(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newArray;
    }


    public static String stringify(String value) {
        if (value.charAt(0) == '[' || value.charAt(0) == '{') {
            return value;
        }
        if (!isInteger(value)) {
            return '"' + value + '"';
        }

        return value;
    }

    public static Object parseString(String value) {
        if (value == null || value.isEmpty())
            return null;
        if (value.charAt(0) == '[') {
            try {
                return new JSONArray(value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (value.charAt(0) == '{') {
            try {
                return new JSONObject(value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"') {
            String newValue = value.replaceAll("\"", "");
            return newValue;
        }

        if (isInteger(value)) {
            return Integer.valueOf(value);
        }
        return null;
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isLong(String str) {
        try {
            Long.parseLong(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static JSONArray cloneArray(JSONArray array) {
        JSONArray arr = new JSONArray();
        if (array == null) {
            return arr;
        }
        JSONObject obj;
        int len = array.length();
        for (int i = 0; i < len; i++) {
            obj = array.optJSONObject(i);
            if (obj != null) {
                String temp = obj.toString();
                obj = parse(temp);
            }
            arr.put(obj);
        }
        return arr;
    }

    public static JSONArray sort(JSONArray array) {
        if (array != null) {
            List asList = new ArrayList(array.length());
            for (int i = 0; i < array.length(); i++) {
                asList.add(array.opt(i));
            }
            Collections.reverse(asList);
            JSONArray res = new JSONArray();
            for (Object o : asList) {
                res.put(o);
            }
            return res;
        }
        return new JSONArray();
    }

}
