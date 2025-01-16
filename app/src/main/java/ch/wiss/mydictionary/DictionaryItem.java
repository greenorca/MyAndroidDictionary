package ch.wiss.mydictionary;

import androidx.annotation.NonNull;

public class DictionaryItem {

    public String getEnglish() {
        return english;
    }

    public String getGerman() {
        return german;
    }

    public int getPositionFound() {
        return positionFound;
    }

    private String english;
    private String german;

    private int positionFound;

    private String searchLanguage;

    public String getSearchLanguage(){
        return searchLanguage;
    }

    public DictionaryItem(String item, int pos){
        String[] translations = item.split("::");
        english = translations[0];
        if (translations.length==2){
            german = translations[1];
        } else {
            german = "parsing error";
        }
        positionFound = pos;
    }

    public DictionaryItem(String english, String german, String searchLanguage){
        this.english = english;
        this.german = german;
        this.searchLanguage = searchLanguage;
        positionFound = -1;
    }

    @NonNull
    public String toString(){
        return searchLanguage.equals("en")? english : german;
    }

}
