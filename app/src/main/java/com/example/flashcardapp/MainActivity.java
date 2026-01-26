package com.example.flashcardapp;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_CARD_REQUEST = 1;
    private static final int EDIT_CARD_REQUEST = 2;

    private TextView flashcardQuestion, flashcardAnswer, progressIndicator, difficultyLabel;
    private FrameLayout flashcardContainer;
    private ImageButton prevBtn, nextBtn;
    private FloatingActionButton addCardBtn;
    private ImageView editCardBtn, deleteCardBtn;

    private List<Flashcard> allFlashcards;
    private List<Flashcard> currentFlashcards;
    private int currentCardIndex = 0;
    private boolean isShowingQuestion = true;
    private Flashcard.Difficulty currentDifficulty = Flashcard.Difficulty.EASY;

    private AnimatorSet frontAnim, backAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        flashcardQuestion = findViewById(R.id.flashcardQuestion);
        flashcardAnswer = findViewById(R.id.flashcardAnswer);
        flashcardContainer = findViewById(R.id.flashcardContainer);
        progressIndicator = findViewById(R.id.progressIndicator);
        difficultyLabel = findViewById(R.id.difficultyLabel);
        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);
        addCardBtn = findViewById(R.id.addCardBtn);
        editCardBtn = findViewById(R.id.editCardBtn);
        deleteCardBtn = findViewById(R.id.deleteCardBtn);

        // Load animations
        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        flashcardQuestion.setCameraDistance(8000 * scale);
        flashcardAnswer.setCameraDistance(8000 * scale);
        frontAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.front_animator);
        backAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.back_animator);

        // Load all flashcards from the bank
        allFlashcards = FlashcardBank.getFlashcards();
        loadFlashcardsForDifficulty(currentDifficulty);

        // Flip flashcard
        flashcardContainer.setOnClickListener(v -> flipCard());

        // Previous button
        prevBtn.setOnClickListener(v -> {
            if (currentCardIndex > 0) {
                currentCardIndex--;
                showCard(currentCardIndex);
            } else {
                Toast.makeText(MainActivity.this, "This is the first card.", Toast.LENGTH_SHORT).show();
            }
        });

        // Next button
        nextBtn.setOnClickListener(v -> {
            if (currentCardIndex < currentFlashcards.size() - 1) {
                currentCardIndex++;
                showCard(currentCardIndex);
            } else {
                // Move to the next difficulty level
                if (currentDifficulty == Flashcard.Difficulty.EASY) {
                    currentDifficulty = Flashcard.Difficulty.MEDIUM;
                    loadFlashcardsForDifficulty(currentDifficulty);
                } else if (currentDifficulty == Flashcard.Difficulty.MEDIUM) {
                    currentDifficulty = Flashcard.Difficulty.HARD;
                    loadFlashcardsForDifficulty(currentDifficulty);
                } else {
                    Toast.makeText(MainActivity.this, "You have completed all levels!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Add card button
        addCardBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditCardActivity.class);
            startActivityForResult(intent, ADD_CARD_REQUEST);
        });

        // Edit card button
        editCardBtn.setOnClickListener(v -> {
            if (!currentFlashcards.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, AddEditCardActivity.class);
                Flashcard currentCard = currentFlashcards.get(currentCardIndex);
                intent.putExtra("question", currentCard.getQuestion());
                intent.putExtra("answer", currentCard.getAnswer());
                startActivityForResult(intent, EDIT_CARD_REQUEST);
            }
        });

        // Delete card button
        deleteCardBtn.setOnClickListener(v -> {
            if (!currentFlashcards.isEmpty()) {
                currentFlashcards.remove(currentCardIndex);
                if (currentCardIndex >= currentFlashcards.size() && !currentFlashcards.isEmpty()) {
                    currentCardIndex = currentFlashcards.size() - 1;
                }
                if (!currentFlashcards.isEmpty()) {
                    showCard(currentCardIndex);
                } else {
                    // Handle empty list case
                    flashcardQuestion.setText("No cards available.");
                    flashcardAnswer.setText("");
                    progressIndicator.setText("Card 0 of 0");
                }
            }
        });
    }

    private void loadFlashcardsForDifficulty(Flashcard.Difficulty difficulty) {
        currentFlashcards = allFlashcards.stream()
                .filter(card -> card.getDifficulty() == difficulty)
                .collect(Collectors.toList());
        Collections.shuffle(currentFlashcards); // Randomize the order of questions
        currentCardIndex = 0;
        difficultyLabel.setText(difficulty.name());
        setDifficultyLabelColor();
        if (!currentFlashcards.isEmpty()) {
            showCard(currentCardIndex);
        } else {
            flashcardQuestion.setText("No cards available for this level.");
            flashcardAnswer.setText("");
            progressIndicator.setText("Card 0 of 0");
        }
    }

    private void setDifficultyLabelColor() {
        int colorResId;
        switch (currentDifficulty) {
            case EASY:
                colorResId = android.R.color.white;
                break;
            case MEDIUM:
                colorResId = R.color.medium_color;
                break;
            case HARD:
                colorResId = R.color.hard_color;
                break;
            default:
                colorResId = android.R.color.white;
                break;
        }
        difficultyLabel.setTextColor(ContextCompat.getColor(this, colorResId));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String question = data.getStringExtra("question");
            String answer = data.getStringExtra("answer");
            if (requestCode == ADD_CARD_REQUEST) {
                Flashcard newCard = new Flashcard(question, answer, currentDifficulty);
                allFlashcards.add(newCard);
                loadFlashcardsForDifficulty(currentDifficulty); // Reload to include the new card
                currentCardIndex = currentFlashcards.size() - 1;
            } else if (requestCode == EDIT_CARD_REQUEST) {
                Flashcard cardToEdit = currentFlashcards.get(currentCardIndex);
                cardToEdit.setQuestion(question);
                cardToEdit.setAnswer(answer);
            }
            showCard(currentCardIndex);
        }
    }

    private void showCard(int index) {
        if (index >= 0 && index < currentFlashcards.size()) {
            // Reset rotation before showing the new card
            flashcardQuestion.setRotationY(0);
            flashcardAnswer.setRotationY(0);

            Flashcard card = currentFlashcards.get(index);
            flashcardQuestion.setText(card.getQuestion());
            flashcardAnswer.setText(card.getAnswer());
            progressIndicator.setText("Card " + (index + 1) + " of " + currentFlashcards.size());

            // Set question color
            updateQuestionCardColor();

            // Set answer color
            GradientDrawable answerBg = (GradientDrawable) ((android.graphics.drawable.LayerDrawable) flashcardAnswer.getBackground()).getDrawable(1);
            answerBg.setColor(ContextCompat.getColor(this, R.color.answer_yellow));
            flashcardAnswer.setTextColor(ContextCompat.getColor(this, android.R.color.black));

            // Reset to show question
            flashcardQuestion.setVisibility(View.VISIBLE);
            flashcardAnswer.setVisibility(View.GONE);
            isShowingQuestion = true;
        }
    }

    private void flipCard() {
        if (isShowingQuestion) {
            frontAnim.setTarget(flashcardQuestion);
            backAnim.setTarget(flashcardAnswer);
            frontAnim.start();
            backAnim.start();
            isShowingQuestion = false;
            flashcardQuestion.setVisibility(View.GONE);
            flashcardAnswer.setVisibility(View.VISIBLE);
        } else {
            frontAnim.setTarget(flashcardAnswer);
            backAnim.setTarget(flashcardQuestion);
            frontAnim.start();
            backAnim.start();
            isShowingQuestion = true;
            flashcardAnswer.setVisibility(View.GONE);
            flashcardQuestion.setVisibility(View.VISIBLE);
        }
    }

    private void updateQuestionCardColor() {
        int questionColorResId;
        int questionTextColorResId;

        switch (currentDifficulty) {
            case EASY:
                questionColorResId = R.color.easy_color; // white
                questionTextColorResId = android.R.color.black;
                break;
            case MEDIUM:
                questionColorResId = R.color.medium_color; // orange
                questionTextColorResId = android.R.color.black;
                break;
            case HARD:
                questionColorResId = R.color.hard_color; // red
                questionTextColorResId = android.R.color.black;
                break;
            default:
                questionColorResId = android.R.color.black;
                questionTextColorResId = android.R.color.white;
                break;
        }

        int questionColor = ContextCompat.getColor(this, questionColorResId);
        int questionTextColor = ContextCompat.getColor(this, questionTextColorResId);

        GradientDrawable questionBg = (GradientDrawable) ((android.graphics.drawable.LayerDrawable) flashcardQuestion.getBackground()).getDrawable(1);
        questionBg.setColor(questionColor);
        flashcardQuestion.setTextColor(questionTextColor);
    }
}
