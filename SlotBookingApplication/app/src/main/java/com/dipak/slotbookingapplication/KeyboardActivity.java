package com.dipak.slotbookingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.Transliterator;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class KeyboardActivity extends AppCompatActivity {

    private Transliterator transliterator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        EditText editText=findViewById(R.id.name);
        Button button=findViewById(R.id.btn);
        TextView textView=findViewById(R.id.result);

        // Create a Transliterator instance
        transliterator = Transliterator.getInstance("Latin; Devanagari; NFD; [:Nonspacing Mark:] Remove; NFC");

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String englishText = editText.getText().toString();
                String marathiText = transliterateToMarathi(englishText.charAt(englishText.length()-1)+"");
                englishText=englishText.substring(0,englishText.length()-1)+marathiText;
                textView.setText(englishText);
            }
        });
        // Example usage

    }

    private String transliterateToMarathi(String englishText) {
        // Use ICU4J Transliterator for transliteration

        return transliterator.transliterate(englishText);
    }
}