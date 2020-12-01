package vn.yinx.listenenglish.util;

import android.util.Log;

import java.security.MessageDigest;

public class TextUtils {
    public static String toAttribute(String field){
        StringBuffer sb = new StringBuffer();
        String[] elements = field.split("_");
        if(elements.length == 0){
            return "";
        }
        sb.append(elements[0]);
        for(int i = 1; i < elements.length; i++){
            String e = elements[i];
            sb.append(Character.toUpperCase(e.charAt(0))).append(e.substring(1));
        }
        return sb.toString();
    }

    public static String toField(String attribute){
        StringBuffer sb = new StringBuffer();
        if(attribute.startsWith("_")){
            attribute = attribute.substring(1);
        }
        for (char c: attribute.toCharArray()) {
            if(Character.isUpperCase(c)){
                c = Character.toLowerCase(c);
                if(sb.length() > 0){
                    sb.append("_");
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static String MD5(String text) throws Exception {
        Log.d("MD5", text);
        MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        byte[] array = md.digest(text.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }
}
