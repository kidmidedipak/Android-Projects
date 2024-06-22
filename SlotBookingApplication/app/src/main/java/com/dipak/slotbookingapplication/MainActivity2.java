package com.dipak.slotbookingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity {

    private TextView predefinedTextView;
    private EditText userEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        predefinedTextView = findViewById(R.id.pre_text);
        userEditText = findViewById(R.id.name);

        Calendar calendar=Calendar.getInstance();
        calendar.get(Calendar.MONTH);


        userEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Not used in this example
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Not used in this example
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String enteredText = editable.toString();
                String predefinedText = predefinedTextView.getText().toString();

                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(predefinedText);

                int correctColor = Color.GREEN;
                int incorrectColor = Color.RED;

                String[] predefinedWords = predefinedText.split("\\s+");
                String[] enteredWords = enteredText.split("\\s+");

                int minLength = Math.min(predefinedWords.length, enteredWords.length);

                for (int i = 0; i < minLength; i++) {
                    String predefinedWord = predefinedWords[i];
                    String enteredWord = enteredWords[i];

                    int textColor = (predefinedWord.startsWith(enteredWord) && predefinedWord.contains(enteredWord) && (predefinedWord.length()==enteredWord.length())) ? correctColor : incorrectColor;

                    // Set the color for the word
                    spannableStringBuilder.setSpan(
                            new ForegroundColorSpan(textColor),
                            spannableStringBuilder.toString().indexOf(predefinedWord),
                            spannableStringBuilder.toString().indexOf(predefinedWord) + predefinedWord.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    );
                }

                // Set the formatted text back to the TextView
                predefinedTextView.setText(spannableStringBuilder);
            }
        });










//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable1) {
//
//  //================================================================================================
////                String userString = editText.getText().toString();
////                if(userString.trim().length()==0)
////                {
////                    return;
////                }
////
////                if (userString.charAt(userString.length() - 1) == ' ') {
////                    int correctColor = Color.GRAY;
////                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
////
////                    SpannableString spannableChar = new SpannableString(userString);
////                    spannableChar.setSpan(new ForegroundColorSpan(correctColor), 0, userString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////                    spannableStringBuilder.append(spannableChar);
////
////                    editText.removeTextChangedListener(this); // Remove the TextWatcher temporarily
////                    editText.setText(spannableStringBuilder);
////                    editText.setSelection(spannableStringBuilder.length());
////                    editText.addTextChangedListener(this); // Reattach the TextWatcher
////
////                } else {
////                    String[] preArr = pre_text.getText().toString().trim().split(" ");
////                    String[] userArr = editText.getText().toString().trim().split(" ");
////
////                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
////                    for (int i = 0; i < userArr.length; i++) {
////
////                        String originalText = preArr[i]; // Replace this with your correct word
////                        String usertext = userArr[i]; // Replace this with your correct word
////
////                        if(usertext.length()>originalText.length())
////                        {
////                            return;
////                        }
////
////                        int correctColor = Color.GRAY;
////                        int blackCorrectColor = Color.BLACK;
////                        int incorrectColor = Color.RED;
////
////                        for (int j = 0; j < usertext.length(); j++) {
////                            char enteredChar = usertext.charAt(j);
////                            char originalChar = originalText.charAt(j);
////
////                            // Determine the color based on correctness
////                            int textColor = (enteredChar == originalChar) ? correctColor : incorrectColor;
////
////                            if (textColor == incorrectColor) {
////                                SpannableStringBuilder spannableStringBuilder1 = new SpannableStringBuilder();
////                                int incorrectIndex = i;
////                                //do incorrect
////                                for (int k = 0; k < preArr.length; k++) {
////                                    int preTextColor = (k != incorrectIndex) ? blackCorrectColor : incorrectColor;
////                                    SpannableString spannableChar = new SpannableString(preArr[k]);
////                                    spannableChar.setSpan(new ForegroundColorSpan(preTextColor), 0, preArr[k].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////                                    spannableStringBuilder1.append(spannableChar);
////                                    spannableStringBuilder1.append(" ");
////
////                                }
////                                pre_text.removeTextChangedListener(this); // Remove the TextWatcher temporarily
////                                pre_text.setText(spannableStringBuilder1);
////                                pre_text.setSelection(spannableStringBuilder1.length());
////                            } else {
////                                //do correct
////                                SpannableStringBuilder spannableStringBuilder1 = new SpannableStringBuilder();
////                                int correctIndex = i;
////                                //do incorrect
////                                for (int k = 0; k < preArr.length; k++) {
//////                                    int preTextColor = (k == correctIndex) ? blackCorrectColor : incorrectColor;
////                                    int preTextColor = blackCorrectColor;
////                                    SpannableString spannableChar = new SpannableString(preArr[k]);
////                                    spannableChar.setSpan(new ForegroundColorSpan(preTextColor), 0, preArr[k].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////                                    spannableStringBuilder1.append(spannableChar);
////                                    spannableStringBuilder1.append(" ");
////
////                                }
////                                pre_text.removeTextChangedListener(this); // Remove the TextWatcher temporarily
////                                pre_text.setText(spannableStringBuilder1);
////                                pre_text.setSelection(spannableStringBuilder1.length());
////
////                            }
////                            // Append the character with the specified color
////                            SpannableString spannableChar = new SpannableString(String.valueOf(enteredChar));
////                            spannableChar.setSpan(new ForegroundColorSpan(textColor), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////                            spannableStringBuilder.append(spannableChar);
////
////                        }
////                        // Set the formatted text back to the EditText
////                    }
////                        editText.removeTextChangedListener(this); // Remove the TextWatcher temporarily
////                        editText.setText(spannableStringBuilder);
////                        editText.setSelection(spannableStringBuilder.length());
////                        editText.addTextChangedListener(this); // Reattach the TextWatcher
////                }
//
//
//            }
//        });
    }
}