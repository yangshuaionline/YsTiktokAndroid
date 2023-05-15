package com.yangshuai.library.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @author hcp
 * @created 17/2/4  09:40
 * @editor hcp
 * @edited 19/5/31
 * @type 缓存
 */

public class KVCache {
    private static Context context;
    private static SharedPreferences sp;
    public static Editor editor = null;

    private final static String SerializableFolder = "/KVCache/";

    public static void init(Context context) {
        KVCache.context = context;
        sp = context.getApplicationContext().getSharedPreferences("KVCache",
                Context.MODE_PRIVATE);
    }


    public static boolean putSerializable(String key, Serializable obj) {
        String path = context.getFilesDir().getPath() + SerializableFolder + key + ".obj";
        return Utils.writeObj(new File(path), obj);
    }

    public static <T> T getSerializable(String key) {
        String path = context.getFilesDir().getPath() + SerializableFolder + key + ".obj";
        return Utils.readObj(new File(path));
    }


    public static boolean removeSerializable(String key) {
        String path = context.getFilesDir().getPath() + SerializableFolder + key + ".obj";
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String val) {
        return sp.getString(key, val);
    }

    public static int getInt(String key) {
        return sp.getInt(key, 0);
    }

    public static int getInt(String key, int val) {
        return sp.getInt(key, val);
    }

    public static boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, Boolean val) {

        return sp.getBoolean(key, val);
    }


    public static long getLong(String key) {

        return sp.getLong(key, 0);
    }

    public static long getLong(String key, long val) {

        return sp.getLong(key, val);
    }

    public static float getFloat(String key) {

        return sp.getFloat(key, 0);
    }

    public static float getFloat(String key, float val) {

        return sp.getFloat(key, val);
    }


    public static Set<String> getStringSet(String key, Set<String> val) {
        return sp.getStringSet(key, val);
    }

    public static Map<String, ?> getAll() {
        return sp.getAll();
    }


    public SharedPreferences getSharedPreferences() {
        return KVCache.sp;
    }


    public static Editor putString(String key, String val) {
        checkEditor();
        editor.putString(key, val);
        return editor;
    }

    public static Editor putStringSet(String key, Set<String> val) {
        checkEditor();
        editor.putStringSet(key, val);
        return editor;
    }

    public static Editor putInt(String key, int val) {
        checkEditor();
        editor.putInt(key, val);
        return editor;
    }

    public static Editor putLong(String key, long val) {
        checkEditor();
        editor.putLong(key, val);
        return editor;
    }

    public static Editor putFloat(String key, float val) {
        checkEditor();
        editor.putFloat(key, val);
        return editor;
    }

    public static Editor putBoolean(String key, boolean val) {
        checkEditor();
        editor.putBoolean(key, val);
        return editor;
    }

    public static Editor remove(String key) {
        checkEditor();
        editor.remove(key);
        return editor;
    }


    private static void checkEditor() {
        if (editor == null) {
            editor = new KVCache.Editor(sp.edit());
        }
    }


    public final static class Editor {

        private SharedPreferences.Editor editor;

        public Editor(SharedPreferences.Editor editor) {
            this.editor = editor;

        }

        public Editor putString(String key, String val) {
            editor.putString(key, val);
            return this;
        }

        public Editor putStringSet(String key, Set<String> val) {
            editor.putStringSet(key, val);
            return this;
        }

        public Editor putInt(String key, int val) {
            editor.putInt(key, val);
            return this;
        }

        public Editor putLong(String key, long val) {
            editor.putLong(key, val);
            return this;
        }

        public Editor putFloat(String key, float val) {
            editor.putFloat(key, val);
            return this;
        }

        public Editor putBoolean(String key, boolean val) {
            editor.putBoolean(key, val);
            return this;
        }

        public Editor remove(String key) {
            editor.remove(key);
            return this;
        }

        public void commit() {
            editor.commit();
            KVCache.editor = null;
        }


    }

}
