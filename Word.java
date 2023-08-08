
package loginsignup;

import javafx.scene.control.TableColumn;


public class Word {
    private Integer id;
    private String word;
    private String type;
    private String traduction;
    private String example;

    public Word(Integer id, String word, String type, String traduction, String example) {
        this.id = id;
        this.word = word;
        this.type = type;
        this.traduction = traduction;
        this.example = example;
    }

    Word() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Word(TableColumn<Word, Integer> id, TableColumn<Word, String> word, TableColumn<Word, String> type, TableColumn<Word, String> example) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Word(String id, String word, String type, String example) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Word(String id, String word, String type, String traduction, String example) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Word(Word get, Word get0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    public Integer getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getType() {
        return type;
    }

    public String getTraduction() {
        return traduction;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTraduction(String traduction) {
        this.traduction = traduction;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWord(String word) {
        this.word = word;
    }

    void getWord(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    
}
