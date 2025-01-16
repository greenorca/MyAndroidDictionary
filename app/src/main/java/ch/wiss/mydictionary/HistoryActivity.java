package ch.wiss.mydictionary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Collections;
import java.util.List;

import ch.wiss.mydictionary.model.History;

public class HistoryActivity extends AppCompatActivity {

    List<DictionaryItem> historyItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView historyListView = findViewById(R.id.listView);
        historyItems = new History(this).getContent();
        // make sure to have newest entries an top
        Collections.reverse(historyItems);
        ArrayAdapter<DictionaryItem> adi = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, historyItems);
        historyListView.setAdapter(adi);
        historyListView.setOnItemClickListener((parent,view, pos, id) -> {
            DictionaryItem itemClicked = historyItems.get(pos);
            showItem(itemClicked);
        });
    }

    private void showItem(DictionaryItem item){
        //Toast.makeText()
        HistoryItemDialog dialog = new HistoryItemDialog(item);
        getSupportFragmentManager().beginTransaction()
                .add(dialog, "alert_dialog")
                .commitNow();
        //dialog.sh
    }

    public void backToMain(View v){
        this.finish();
    }
}