package com.example.flashcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditCardActivity extends AppCompatActivity {

    private EditText editQuestion, editAnswer;
    private Button saveCardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_card);

        editQuestion = findViewById(R.id.editQuestion);
        editAnswer = findViewById(R.id.editAnswer);
        saveCardBtn = findViewById(R.id.saveCardBtn);

        Intent intent = getIntent();
        String question = intent.getStringExtra("question");
        String answer = intent.getStringExtra("answer");
        editQuestion.setText(question);
        editAnswer.setText(answer);

        saveCardBtn.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("question", editQuestion.getText().toString());
            resultIntent.putExtra("answer", editAnswer.getText().toString());
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
