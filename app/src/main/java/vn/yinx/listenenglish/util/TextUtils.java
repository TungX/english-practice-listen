package vn.yinx.listenenglish.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vn.yinx.listenenglish.entity.Sentence;

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

    public static String checksum(File fi) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        DigestInputStream dis = new DigestInputStream(new FileInputStream(fi), md);
        while (dis.read() != -1) ; //empty loop to clear the data
        md = dis.getMessageDigest();
        dis.close();
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    public static ArrayList<Sentence> readSentences(File fi) throws Exception {
        FileInputStream fis = new FileInputStream(fi);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;
        ArrayList<Sentence> sentences = new ArrayList<>();
        Pattern patternSize = Pattern.compile("(\\d+)");
        Pattern patternTime = Pattern.compile("(\\d+(\\.\\d+)?)");
        Pattern patternContent = Pattern.compile("\"(.*)\"$");

        while((line = br.readLine()) != null) {
            if(line.trim().startsWith("name") && line.contains("\"sentences\"")) {
                br.readLine();
                br.readLine();
                line = br.readLine();
                Matcher matcher = patternSize.matcher(line);
                if(!matcher.find()) {
                    break;
                }
                int size = Integer.parseInt(matcher.group(1));
                for(int i = 0; i < size; i++) {
                    Sentence sentence = new Sentence();
                    br.readLine();
                    line = br.readLine();
                    matcher = patternTime.matcher(line);
                    if(matcher.find()) {
                        double time = Double.parseDouble(matcher.group(0));
                        sentence.setStart((int) (time*1000));
                    }
                    line = br.readLine();
                    matcher = patternTime.matcher(line);
                    if(matcher.find()) {
                        double time = Double.parseDouble(matcher.group(0));
                        sentence.setEnd((int) (time*1000));
                    }
                    line = br.readLine();
                    matcher = patternContent.matcher(line.trim());
                    if(matcher.find()) {
                        sentence.setContent(matcher.group(1));
                        sentences.add(sentence);
                    }
                }
                break;
            }
        }
        fis.close();
        br.close();
        return sentences;
    }
}
