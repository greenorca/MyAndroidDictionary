package ch.wiss.mydictionary;

import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ch.wiss.mydictionary.model.History;

public class ResultListAdapter extends ArrayAdapter<DictionaryItem> {

    Activity context;
    List<DictionaryItem> items;
    String searchLanguage;

    public ResultListAdapter(Activity context, int resource, List<DictionaryItem> items, String searchLanguage){
        super(context, resource, items);
        this.context = context;
        this.items = items;
        this.searchLanguage = searchLanguage;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.my_list_item, null,true);

        TextView txtEnglish = rowView.findViewById(R.id.textViewEnglish);
        TextView txtGerman = rowView.findViewById(R.id.textViewGerman);

        DictionaryItem currentItem = items.get(position);
        txtEnglish.setText(Html.fromHtml(removeCurlyStuff(currentItem.getEnglish()),
                Html.FROM_HTML_MODE_COMPACT));
        txtGerman.setText(Html.fromHtml(removeCurlyStuff(currentItem.getGerman()),
                Html.FROM_HTML_MODE_COMPACT));

        Button button = rowView.findViewById(R.id.buttonBookmark);
        button.setOnClickListener((v)->{
            Log.d("Main", "adding stuff to history");
            new History(context).addEntry(
                    new DictionaryItem(txtEnglish.getText().toString(),
                            txtGerman.getText().toString(),
                            searchLanguage)
            );
            Toast.makeText(context, "History item added", Toast.LENGTH_LONG).show();
        });
        notifyDataSetChanged();
        return rowView;
    }

    private String removeCurlyStuff(String input){
        return input.replaceAll("\\{[^}]*\\}","");
    }

}
