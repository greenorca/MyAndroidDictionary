package ch.wiss.mydictionary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class HistoryItemDialog extends DialogFragment {

    DictionaryItem item;
    public HistoryItemDialog(DictionaryItem dictItem){
        super();
        this.item = dictItem;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set title and message for the dialog.
        builder.setTitle("Thats the solution")
                .setMessage(
                        item.getSearchLanguage().equals("en")?item.getGerman():item.getEnglish()
                )
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    //Toast.makeText(getActivity(), "closing", Toast.LENGTH_SHORT).show();
                })
                ;

        return builder.create();
    }
}
