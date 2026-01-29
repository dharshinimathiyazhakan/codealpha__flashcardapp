package com.example.flashcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView flashcardQuestion, flashcardAnswer, progressIndicator, difficultyLabel;
    private ImageButton prevButton, nextButton;
    private FloatingActionButton addCardBtn;
    private ImageView editCardBtn, deleteCardBtn;

    private List<Flashcard> flashcards;
    private int currentCardIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardQuestion = findViewById(R.id.flashcardQuestion);
        flashcardAnswer = findViewById(R.id.flashcardAnswer);
        addCardBtn = findViewById(R.id.addCardBtn);
        editCardBtn = findViewById(R.id.editCardBtn);
        deleteCardBtn = findViewById(R.id.deleteCardBtn);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        progressIndicator = findViewById(R.id.progressIndicator);
        difficultyLabel = findViewById(R.id.difficultyLabel);

        flashcards = FlashcardBank.getFlashcards();

        updateCard();

        flashcardQuestion.setOnClickListener(v -> {
            flashcardQuestion.setVisibility(View.GONE);
            flashcardAnswer.setVisibility(View.VISIBLE);
        });

        flashcardAnswer.setOnClickListener(v -> {
            flashcardAnswer.setVisibility(View.GONE);
            flashcardQuestion.setVisibility(View.VISIBLE);
        });

        addCardBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditCardActivity.class);
            startActivity(intent);
        });

        editCardBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditCardActivity.class);
            Flashcard currentCard = flashcards.get(currentCardIndex);
            intent.putExtra("question", currentCard.getQuestion());
            intent.putExtra("answer", currentCard.getAnswer());
            intent.putExtra("difficulty", currentCard.getDifficulty().name());
            startActivity(intent);
        });

        deleteCardBtn.setOnClickListener(v -> {
            flashcards.remove(currentCardIndex);
            if (currentCardIndex >= flashcards.size() && currentCardIndex > 0) {
                currentCardIndex--;
            }
            updateCard();
        });

        prevButton.setOnClickListener(v -> {
            if (currentCardIndex > 0) {
                currentCardIndex--;
                updateCard();
            }
        });

        nextButton.setOnClickListener(v -> {
            if (currentCardIndex < flashcards.size() - 1) {
                currentCardIndex++;
                updateCard();
            }
        });
    }

    private void updateCard() {
        if (flashcards.isEmpty()) {
            // Handle case where there are no flashcards
            flashcardQuestion.setText("No more cards!");
            flashcardAnswer.setText("");
            progressIndicator.setText("");
            difficultyLabel.setText("");
            return;
        }
        Flashcard currentCard = flashcards.get(currentCardIndex);
        flashcardQuestion.setText(currentCard.getQuestion());
        flashcardAnswer.setText(currentCard.getAnswer());
        flashcardQuestion.setVisibility(View.VISIBLE);
        flashcardAnswer.setVisibility(View.GONE);
        progressIndicator.setText("Card " + (currentCardIndex + 1) + " of " + flashcards.size());
        difficultyLabel.setText(currentCard.getDifficulty().name());
    }
}
