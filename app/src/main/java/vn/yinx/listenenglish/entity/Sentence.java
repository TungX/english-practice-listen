package vn.yinx.listenenglish.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class Sentence {
    private int id;
    private String content;
    private int start;
    private int end;

    private boolean isActive = false;
    private boolean isRepeating = false;

    public Sentence() {

    }

    public Sentence(JSONObject data) throws Exception {
        content = data.getString("content");
        start = data.getInt("start");
        end = data.getInt("end");
    }

    public JSONObject toObject() throws Exception {
        JSONObject data = new JSONObject();
        data.put("content", this.content);
        data.put("start", this.start);
        data.put("end", this.end);
        return data;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public boolean isRepeating() {
        return isRepeating;
    }

    public void setRepeating(boolean repeating) {
        isRepeating = repeating;
    }
}
