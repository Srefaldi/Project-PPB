package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.quiz.Models.QuestionModel;
import com.example.quiz.databinding.ActivityQuestionBinding;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    ActivityQuestionBinding binding;
    ArrayList<QuestionModel> list = new ArrayList<>();
    private int count = 0;
    private int score = 0;
    private int position = 0;
    CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        reserTime();
        timer.start();

        String setNmae = getIntent().getStringExtra("set");
        if(setNmae.equals("Quiz-1")){

            setOne();
        } else if (setNmae.equals("Quiz-2")) {
            setTwo();
        }
        for(int i =0; i<4;i++){
            binding.optionCont.getChildAt(i).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    checkAnswer((Button) view);
                }

                private void checkAnswer(Button selectedOption) {

                    if(timer !=null){
                        timer.cancel();
                    }

                    binding.btnNext.setEnabled(true);
                    binding.btnNext.setAlpha(1);
                    if(selectedOption.getText().toString().equals(list.get(position).getCorrectAnswer())){
                        score++;
                        selectedOption.setBackgroundResource(R.drawable.right_answ);
                    }
                    else{
                        selectedOption.setBackgroundResource((R.drawable.wrong_answ));
                        Button correctOption = (Button) binding.optionCont.findViewWithTag(list.get(position).getCorrectAnswer());
                        correctOption.setBackgroundResource(R.drawable.right_answ);
                    }
                }
            });
        }

        playAnimation(binding.question, 0,list.get(position).getQuetion());


        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (timer !=null){
                    timer.cancel();
                }

                timer.start();

                binding.btnNext.setEnabled(false);
                binding.btnNext.setAlpha((float)0.3);
                enableOption(true);

                position ++;
                if(position==list.size()){

                    Intent intent = new Intent(QuestionActivity.this,ScoreActivity.class);
                    intent.putExtra("Score",score);
                    intent.putExtra("total",list.size());
                    startActivity(intent);
                    finish();
                    return;
                }

                count=0;
                playAnimation(binding.question,0,list.get(position).getQuetion());
            }

        });
    }

    private void reserTime() {
        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long l) {
                binding.timer.setText(String.valueOf((l/1000)));
            }

            @Override
            public void onFinish() {

                Dialog dialog =new Dialog(QuestionActivity.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(QuestionActivity.this,SetsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.show();

            }
        };
    }

    private void playAnimation(View view, int value, String data) {

        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animator) {

                        if(value ==0 && count <4){
                            String option = "";
                            if(count ==0){
                                option = list.get(position).getOptionA();
                            }else if(count ==1){
                                option = list.get(position).getOptionB();
                            }
                            else if(count ==2){
                                option = list.get(position).getOptionC();
                            }
                            else if(count ==3){
                                option = list.get(position).getOptionD();
                            }
                            playAnimation(binding.optionCont.getChildAt(count), 0,option);
                            count ++;
                        }

                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animator) {


                        if(value==0){

                            try {
                                ((TextView) view).setText(data);
                                binding.totalquestion.setText(position+1+"/"+list.size());
                            } catch (Exception e){
                                ((Button)view).setText(data);
                            }
                            view.setTag(data);
                            playAnimation(view, 1,data);

                        }
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {

                    }
                });
    }

    private void enableOption(boolean enable) {

        for(int i=0;i<4;i++) {

            binding.optionCont.getChildAt(i).setEnabled(enable);
            if(enable){
                binding.optionCont.getChildAt(i).setBackgroundResource(R.drawable.button_opt);
            }

        }


        }


    private void setTwo() {

        list.add(new QuestionModel("Apa itu Mikrotik?","Barang","Alat",
                "Alat","Alat","Barang"));

        list.add(new QuestionModel("Mikrotik Ditemukan Pada Tahun?","2023","2022",
                "2021","2020","2020"));

        list.add(new QuestionModel("Siapa Penemu Mikrotik?","Upin","Ipin",
                "Kak Ros","Opah","Opah"));

        list.add(new QuestionModel("Jaringan Mikrotik Adalah, Kecuali?","Jaringan Wifi","Jaringanku",
                "Jaringanmu","Jaring Jaring","Jaringan Wifi"));
    }

    private void setOne() {
        list.add(new QuestionModel("Apa itu Mikrotik?","Barang","Alat",
                "Alat","Alat","Barang"));

        list.add(new QuestionModel("Mikrotik Ditemukan Pada Tahun?","2023","2022",
                "2021","2020","2020"));

        list.add(new QuestionModel("Siapa Penemu Mikrotik?","Upin","Ipin",
                "Kak Ros","Opah","Opah"));

        list.add(new QuestionModel("Jaringan Mikrotik Adalah, Kecuali?","Jaringan Wifi","Jaringanku",
                "Jaringanmu","Jaring Jaring","Jaringan Wifi"));
    }
}