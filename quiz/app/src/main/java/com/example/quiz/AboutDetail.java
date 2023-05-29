package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quiz.databinding.ActivityAboutDetailBinding;
import com.example.quiz.databinding.ActivityDetailBinding;

public class AboutDetail extends AppCompatActivity {
    ActivityAboutDetailBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAboutDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        if (intent != null){
            String name = intent.getStringExtra("name");
            String time = intent.getStringExtra("time");
            int ingredients = intent.getIntExtra("ingredients", R.string.maggiIngredients);
            int desc = intent.getIntExtra("desc", R.string.maggieDesc);
            int image = intent.getIntExtra("image", R.drawable.magi);
            binding.detailName.setText(name);
            binding.detailTime.setText(time);
            binding.detailDesc.setText(String.valueOf(desc));
            binding.detailIngredients.setText(String.valueOf(ingredients));
            binding.detailImage.setImageResource(image);
        }
    }
}