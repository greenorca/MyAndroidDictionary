package ch.wiss.mydictionary.model;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import ch.wiss.mydictionary.DictionaryItem;

public class History {

    SharedPreferences prefs;
    final String separator = " :: ";

    public History(Activity context){
        prefs = context.getSharedPreferences("history_2",0);
    }

    public void addEntry(DictionaryItem item){
        SharedPreferences.Editor edi = prefs.edit();
        edi.putString(item.getEnglish(), item.getGerman() + separator + item.getSearchLanguage());
        edi.apply();
    }

    public List<DictionaryItem> getContent(){
        List<DictionaryItem> result = new ArrayList<>();
        prefs.getAll().keySet().forEach(
                key -> {
                    String[] vals = prefs.getString(key,separator).split(separator);

                    String german = vals[0];
                    String searchLanguage = vals[1];
                    result.add(new DictionaryItem(key, german, searchLanguage));
                }
                );
        return result;
    }

    public void removeItem(String key){
        prefs.edit().remove(key).apply();
    }


}
