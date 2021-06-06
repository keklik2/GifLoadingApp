package com.demo.appfortinkoff;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static FloatingActionButton floatingActionButtonShowPreviousGif;
    private static TextView textViewDescription;
    private static ImageView imageViewGif;

    private static ArrayList<GifToBeShown> shownGif = new ArrayList<>();
    private static Chapters currChapter = Chapters.CHAPTER_LATEST;
    private static int currPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButtonShowPreviousGif = findViewById(R.id.floatingActionButtonShowPreviousGif);
        textViewDescription = findViewById(R.id.textViewDescription);
        imageViewGif = findViewById(R.id.imageViewGif);

        showGif(getNewGif());
    }

    public void changeChapter(View view) {
        RadioButton checkedButton = (RadioButton) view;
        int id = checkedButton.getId();

        switch (id) {
            case (R.id.radioButtonHot):
                currChapter = Chapters.CHAPTER_HOT;
                break;
            case (R.id.radioButtonTop):
                currChapter = Chapters.CHAPTER_TOP;
                break;
            default:
                currChapter = Chapters.CHAPTER_LATEST;
                break;
        }

        shownGif.clear();
        currPos = -1;

        showGif(getNewGif());
    }

    public void getNextGif(View view) {
        if (currPos == shownGif.size() - 1) {
            showGif(getNewGif());
        } else {
            currPos++;
            showGif(shownGif.get(currPos));
        }
    }

    public void getPreviousGif(View view) {
        currPos--;
        showGif(shownGif.get(currPos));
    }

    private GifToBeShown getNewGif() {
        GifToBeShown gif = JSONLoad.getRandomGif(currChapter);

        shownGif.add(gif);
        currPos++;
        return gif;
    }

    private void showGif(GifToBeShown gif) {
        if (gif != null) {
            Glide.with(this)
                    .load(shownGif.get(currPos).getUrl())
                    .error(R.drawable.no_internet)
                    .placeholder(R.drawable.loading)
                    .centerCrop()
                    .into(imageViewGif);

            textViewDescription.setVisibility(View.VISIBLE);
            textViewDescription.setText(gif.getDescription());
        } else {
            shownGif.remove(currPos);
            currPos--;

            imageViewGif.setImageResource(R.drawable.error);
            textViewDescription.setVisibility(View.INVISIBLE);
        }

        if (currPos >= 1) {
            floatingActionButtonShowPreviousGif.setVisibility(View.VISIBLE);
        } else {
            floatingActionButtonShowPreviousGif.setVisibility(View.INVISIBLE);
        }
    }
}