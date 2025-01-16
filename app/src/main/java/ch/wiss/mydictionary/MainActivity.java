package ch.wiss.mydictionary;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    TextView helloWorld;

    List<String> dict;
    ListView resultList;

    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resultList = findViewById(R.id.resultList);
        input = findViewById(R.id.editTextInput);

        helloWorld = findViewById(R.id.txt_hello);

        Resources res = getResources();
        try (InputStream inputStream = res.openRawResource(R.raw.en_de_enwiktionary)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            dict = new LinkedList<>();
            while (br.ready()){
                String line = br.readLine();
                if (!line.startsWith("#")){
                    dict.add(line);
                }
            }
            helloWorld.setText("Dict entries: "+dict.size());
        } catch (Exception ex){
            Log.e("FAIL", "could not open resource file");
        }
    }

    private SortedMap<String, Integer> searchDict(String searchString, String languageName){

        String x = searchString.toLowerCase();
        SortedMap<String, Integer> matches = new TreeMap<>((x1,x2) -> {
            return x1.toLowerCase().indexOf(x) - x2.toLowerCase().indexOf(x);
        });
        dict.forEach(entry -> {
            int pos = entry.toLowerCase().indexOf(x);
            if (pos >= 0) {
                entry=entry.toLowerCase().replaceAll(x, "<b>"+x+"</b>");
                if (languageName.equals("en") && pos < entry.indexOf("::"))
                    matches.put(entry, pos);
                else if (languageName.equals("de") && pos > entry.indexOf("::"))
                    matches.put(entry, pos);
            }
        });
        Log.d("Main", "found "+matches+" for "+x+ " in "+languageName);
        /*matches.forEach((k,v) -> {
            Log.d("Main",v+"::"+k.substring(0,10));
        });*/

        return matches;
    }

    public void onTranslateClicked(View v){
        String searchLanguage = ((Button)v).getText().toString();
        String searchString = input.getText().toString();
        Log.d("Main", "Translate button clicked");
        if (searchString.trim().length()>1) {
            List<DictionaryItem> matches = new ArrayList<>();
            SortedMap<String, Integer> searchResults = searchDict(searchString, searchLanguage);
            if (searchResults!=null) {
                searchResults.keySet().forEach(
                        item -> matches.add(new DictionaryItem(item, searchResults.get(item)))
                );
            }
            ResultListAdapter listAdapter = new ResultListAdapter(this,
                    R.layout.my_list_item, matches, searchLanguage);
            resultList.setAdapter(listAdapter);
        }
        hideKeyboard();
    }

    public void switchToHistory(View v){
        Intent i = new Intent(this, HistoryActivity.class);
        startActivity(i);
    }

    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (input != null ) {
            inputMethodManager.hideSoftInputFromWindow(input.getWindowToken(), 0);
        }
    }

}