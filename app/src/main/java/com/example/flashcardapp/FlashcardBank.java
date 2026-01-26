package com.example.flashcardapp;

import java.util.ArrayList;
import java.util.List;

public class FlashcardBank {

    public static List<Flashcard> getFlashcards() {
        List<Flashcard> flashcards = new ArrayList<>();

        // Easy Questions
        flashcards.add(new Flashcard("What is the capital of France?", "Paris", Flashcard.Difficulty.EASY));
        flashcards.add(new Flashcard("What is 2 + 2?", "4", Flashcard.Difficulty.EASY));
        flashcards.add(new Flashcard("What is the color of the sky on a clear day?", "Blue", Flashcard.Difficulty.EASY));
        flashcards.add(new Flashcard("How many days are in a week?", "7", Flashcard.Difficulty.EASY));
        flashcards.add(new Flashcard("What is the opposite of hot?", "Cold", Flashcard.Difficulty.EASY));
        flashcards.add(new Flashcard("What do bees make?", "Honey", Flashcard.Difficulty.EASY));
        flashcards.add(new Flashcard("What is the sound a cat makes?", "Meow", Flashcard.Difficulty.EASY));
        flashcards.add(new Flashcard("Which planet is known as the Red Planet?", "Mars", Flashcard.Difficulty.EASY));
        flashcards.add(new Flashcard("How many legs does a spider have?", "8", Flashcard.Difficulty.EASY));
        flashcards.add(new Flashcard("What is the fastest land animal?", "Cheetah", Flashcard.Difficulty.EASY));

        // Medium Questions
        flashcards.add(new Flashcard("Who wrote 'Hamlet'?", "William Shakespeare", Flashcard.Difficulty.MEDIUM));
        flashcards.add(new Flashcard("What is the largest ocean on Earth?", "Pacific Ocean", Flashcard.Difficulty.MEDIUM));
        flashcards.add(new Flashcard("What is the chemical symbol for gold?", "Au", Flashcard.Difficulty.MEDIUM));
        flashcards.add(new Flashcard("In which country are the Pyramids of Giza located?", "Egypt", Flashcard.Difficulty.MEDIUM));
        flashcards.add(new Flashcard("What is the currency of Japan?", "Yen", Flashcard.Difficulty.MEDIUM));
        flashcards.add(new Flashcard("Who painted the Mona Lisa?", "Leonardo da Vinci", Flashcard.Difficulty.MEDIUM));
        flashcards.add(new Flashcard("What is the main ingredient in guacamole?", "Avocado", Flashcard.Difficulty.MEDIUM));
        flashcards.add(new Flashcard("How many planets are in our solar system?", "8", Flashcard.Difficulty.MEDIUM));
        flashcards.add(new Flashcard("What is the hardest natural substance on Earth?", "Diamond", Flashcard.Difficulty.MEDIUM));
        flashcards.add(new Flashcard("Which is the only mammal capable of sustained flight?", "Bat", Flashcard.Difficulty.MEDIUM));

        // Hard Questions
        flashcards.add(new Flashcard("What is the powerhouse of the cell?", "Mitochondria", Flashcard.Difficulty.HARD));
        flashcards.add(new Flashcard("Who discovered penicillin?", "Alexander Fleming", Flashcard.Difficulty.HARD));
        flashcards.add(new Flashcard("What is the capital of Australia?", "Canberra", Flashcard.Difficulty.HARD));
        flashcards.add(new Flashcard("What is the speed of light?", "299,792 kilometers per second", Flashcard.Difficulty.HARD));
        flashcards.add(new Flashcard("Which country is the largest by land area?", "Russia", Flashcard.Difficulty.HARD));
        flashcards.add(new Flashcard("What is the main component of the sun?", "Hydrogen", Flashcard.Difficulty.HARD));
        flashcards.add(new Flashcard("Who developed the theory of relativity?", "Albert Einstein", Flashcard.Difficulty.HARD));
        flashcards.add(new Flashcard("What is the smallest country in the world?", "Vatican City", Flashcard.Difficulty.HARD));
        flashcards.add(new Flashcard("What is the most spoken language in the world?", "Mandarin Chinese", Flashcard.Difficulty.HARD));
        flashcards.add(new Flashcard("Who was the first person to step on the moon?", "Neil Armstrong", Flashcard.Difficulty.HARD));

        return flashcards;
    }
}
