package vn.yinx.listenenglish.entity;

import org.json.JSONArray;

import java.util.ArrayList;

public class FileMusic extends EntityBase{
    private String _name;
    private String _audioPath;
    private String _lyric;
    private long _folderId;
    private ArrayList<Sentence> sentences;
    {
        this.tableName = "files";
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getAudioPath() {
        return _audioPath;
    }

    public void setAudioPath(String _audioPath) {
        this._audioPath = _audioPath;
    }

    public void setLyric(String _lyric){
        this._lyric = _lyric;
    }

    public ArrayList<Sentence> getSentences() throws Exception {
        if(sentences == null){
            sentences = new ArrayList<>();
            JSONArray jSentences = new JSONArray(_lyric);
            for (int i = 0; i < jSentences.length(); i++){
                Sentence sentence = new Sentence(jSentences.getJSONObject(i));
                sentence.setId(i);
                sentences.add(sentence);
            }
        }
        return sentences;
    }

    public void setSentences(ArrayList<Sentence> sentences) {
        this.sentences = sentences;
    }

    public long getFolderId() {
        return _folderId;
    }

    public void setFolderId(long _folderId) {
        this._folderId = _folderId;
    }
}
