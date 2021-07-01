package com.example.wize;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class quiz extends AppCompatActivity {
String subject;
TextView ques, op1, op2, op3, op4, timer,quesno,load;
CountDownTimer time;
    int total = 0;
    LottieAnimationView anim;
    private FirebaseDatabase fDbase;
    private DatabaseReference dref;
    int correct = 0;
    String correct_ans;
    public int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ques=findViewById(R.id.question);
        op1=findViewById(R.id.option1);
        op2=findViewById(R.id.option2);
        op3=findViewById(R.id.option3);
        op4=findViewById(R.id.option4);
        load=findViewById(R.id.loading);
        timer=findViewById(R.id.time);
        anim=findViewById(R.id.animationloading);
        quesno=findViewById(R.id.questionno);
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        anim.setVisibility(View.GONE);
                        load.setVisibility(View.GONE);
                        ques.setVisibility(View.VISIBLE);
                        op1.setVisibility(View.VISIBLE);
                        op2.setVisibility(View.VISIBLE);
                        op3.setVisibility(View.VISIBLE);
                        op4.setVisibility(View.VISIBLE);
                        timer.setVisibility(View.VISIBLE);
                        quesno.setVisibility(View.VISIBLE);
                    }
                },2000);
        getquestion();
        checkans();

        time = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText("  " + String.format("%d : %d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                if ((TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) == 0) &&
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)) == 10) {
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                }
                counter++;
            }
            @Override
            public void onFinish() {
                timer.setText("Finished");
                Toast.makeText(quiz.this, "Time Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(quiz.this, result.class);
                intent.putExtra("Correct", correct);
                intent.putExtra("time", 60);
                startActivity(intent);
                finish();
            }
        }.start();
    }
    public void getquestion()
    {
        quesno.setText((total+1)+" / 5");
        Intent intent=getIntent();
        subject=intent.getStringExtra("topicname");
        fDbase = FirebaseDatabase.getInstance();
        final ArrayList<String> list = new ArrayList<String>();
        dref = fDbase.getReference().child(subject).child(Integer.toString(total));
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                QuestionModel Question = snapshot.getValue(QuestionModel.class);
                ques.setText(Question.getQuestion());
                correct_ans = Question.getCorrect();

                list.add(correct_ans);
                list.add(Question.getA());
                list.add(Question.getB());
                list.add(Question.getC());

                Collections.shuffle(list);

                op1.setText(list.get(0));
                op2.setText(list.get(1));
                op3.setText(list.get(2));
                op4.setText(list.get(3));
                list.clear();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        total++;
    }
    public void checkans()
    {
            op1.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    op1.setEnabled(false);
                    op2.setEnabled(false);
                    op3.setEnabled(false);
                    op4.setEnabled(false);
                    if (total < 5) {
                        if (op1.getText().toString().equals(correct_ans)) {
                            op1.setBackground(getDrawable(R.drawable.quiz_op_green));
                            correct++;
                        } else {
                            op1.setBackground(getDrawable(R.drawable.quiz_red));
                            if (op2.getText().toString().equals(correct_ans)) {
                                op2.setBackground(getDrawable(R.drawable.quiz_op_green));
                            } else if (op3.getText().toString().equals(correct_ans)) {
                                op3.setBackground(getDrawable(R.drawable.quiz_op_green));
                            } else {
                                op4.setBackground(getDrawable(R.drawable.quiz_op_green));
                            }
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void run() {
                                op1.setEnabled(true);
                                op2.setEnabled(true);
                                op3.setEnabled(true);
                                op4.setEnabled(true);
                                op1.setBackground(getDrawable(R.drawable.quiz_op));
                                op2.setBackground(getDrawable(R.drawable.quiz_op));
                                op3.setBackground(getDrawable(R.drawable.quiz_op));
                                op4.setBackground(getDrawable(R.drawable.quiz_op));
                                getquestion();
                            }
                        }, 1000);
                    }else if (total==5)
                    {
                        if (op1.getText().toString().equals(correct_ans)) {
                            op1.setBackground(getDrawable(R.drawable.quiz_op_green));
                            correct++;
                        } else {
                            op1.setBackground(getDrawable(R.drawable.quiz_red));
                            if (op2.getText().toString().equals(correct_ans)) {
                                op2.setBackground(getDrawable(R.drawable.quiz_op_green));
                            } else if (op3.getText().toString().equals(correct_ans)) {
                                op3.setBackground(getDrawable(R.drawable.quiz_op_green));
                            } else {
                                op4.setBackground(getDrawable(R.drawable.quiz_op_green));
                            }
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void run() {
                                time.cancel();
                                Intent intent = new Intent(quiz.this, result.class);
                                intent.putExtra("Correct", correct);
                                intent.putExtra("time", counter);
                                startActivity(intent);
                                finish();
                            }
                        }, 1000);
                    }
                }
            });
            op2.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    op1.setEnabled(false);
                    op2.setEnabled(false);
                    op3.setEnabled(false);
                    op4.setEnabled(false);
                    if (total < 5) {
                        if (op2.getText().toString().equals(correct_ans)) {
                            op2.setBackground(getDrawable(R.drawable.quiz_op_green));
                            correct++;
                        } else {
                            op2.setBackground(getDrawable(R.drawable.quiz_red));
                            if (op1.getText().toString().equals(correct_ans)) {
                                op1.setBackground(getDrawable(R.drawable.quiz_op_green));
                            } else if (op3.getText().toString().equals(correct_ans)) {
                                op3.setBackground(getDrawable(R.drawable.quiz_op_green));
                            } else {
                                op4.setBackground(getDrawable(R.drawable.quiz_op_green));
                            }
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void run() {

                                op1.setEnabled(true);
                                op2.setEnabled(true);
                                op3.setEnabled(true);
                                op4.setEnabled(true);
                                op1.setBackground(getDrawable(R.drawable.quiz_op));
                                op2.setBackground(getDrawable(R.drawable.quiz_op));
                                op3.setBackground(getDrawable(R.drawable.quiz_op));
                                op4.setBackground(getDrawable(R.drawable.quiz_op));
                                getquestion();
                            }
                        }, 1000);
                    }
                    else if (total==5)
                    {
                        if (op2.getText().toString().equals(correct_ans)) {
                            op2.setBackground(getDrawable(R.drawable.quiz_op_green));
                            correct++;
                        } else {
                            op2.setBackground(getDrawable(R.drawable.quiz_red));
                            if (op1.getText().toString().equals(correct_ans)) {
                                op1.setBackground(getDrawable(R.drawable.quiz_op_green));
                            } else if (op3.getText().toString().equals(correct_ans)) {
                                op3.setBackground(getDrawable(R.drawable.quiz_op_green));
                            } else {
                                op4.setBackground(getDrawable(R.drawable.quiz_op_green));
                            }
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void run() {
                                time.cancel();
                                Intent intent = new Intent(quiz.this, result.class);
                                intent.putExtra("Correct", correct);
                                intent.putExtra("time", counter);
                                startActivity(intent);
                                finish();
                            }
                        }, 1000);
                    }
                }
            });
            op3.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    op1.setEnabled(false);
                    op2.setEnabled(false);
                    op3.setEnabled(false);
                    op4.setEnabled(false);
                    if (total<5) {
                        if (op3.getText().toString().equals(correct_ans)) {
                            op3.setBackground(getDrawable(R.drawable.quiz_op_green));
                            correct++;
                        } else {
                            op3.setBackground(getDrawable(R.drawable.quiz_red));
                            if (op1.getText().toString().equals(correct_ans)) {
                                op1.setBackground(getDrawable(R.drawable.quiz_op_green));
                            } else if (op2.getText().toString().equals(correct_ans)) {
                                op2.setBackground(getDrawable(R.drawable.quiz_op_green));
                            } else {
                                op4.setBackground(getDrawable(R.drawable.quiz_op_green));
                            }
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void run() {

                                op1.setEnabled(true);
                                op2.setEnabled(true);
                                op3.setEnabled(true);
                                op4.setEnabled(true);
                                op1.setBackground(getDrawable(R.drawable.quiz_op));
                                op2.setBackground(getDrawable(R.drawable.quiz_op));
                                op3.setBackground(getDrawable(R.drawable.quiz_op));
                                op4.setBackground(getDrawable(R.drawable.quiz_op));
                                getquestion();
                            }
                        }, 1000);
                    } else if (total==5)
                    {
                        if (op3.getText().toString().equals(correct_ans)) {
                            op3.setBackground(getDrawable(R.drawable.quiz_op_green));
                            correct++;
                        } else {
                            op3.setBackground(getDrawable(R.drawable.quiz_red));
                            if (op1.getText().toString().equals(correct_ans)) {
                                op1.setBackground(getDrawable(R.drawable.quiz_op_green));
                            } else if (op2.getText().toString().equals(correct_ans)) {
                                op2.setBackground(getDrawable(R.drawable.quiz_op_green));
                            } else {
                                op4.setBackground(getDrawable(R.drawable.quiz_op_green));
                            }
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void run() {
                                time.cancel();
                                Intent intent = new Intent(quiz.this, result.class);
                                intent.putExtra("Correct", correct);
                                intent.putExtra("time", counter);
                                startActivity(intent);
                                finish();
                            }
                        }, 1000);
                    }
                }
            });
            op4.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    op1.setEnabled(false);
                    op2.setEnabled(false);
                    op3.setEnabled(false);
                    op4.setEnabled(false);
                    if (total<5) {

                        if (op4.getText().toString().equals(correct_ans)) {
                            op4.setBackground(getDrawable(R.drawable.quiz_op_green));
                            correct++;
                        } else {
                            op4.setBackground(getDrawable(R.drawable.quiz_red));
                            if (op1.getText().toString().equals(correct_ans)) {
                                op1.setBackground(getDrawable(R.drawable.quiz_op_green));
                            } else if (op3.getText().toString().equals(correct_ans)) {
                                op3.setBackground(getDrawable(R.drawable.quiz_op_green));
                            } else {
                                op2.setBackground(getDrawable(R.drawable.quiz_op_green));
                            }
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void run() {
                                op1.setEnabled(true);
                                op2.setEnabled(true);
                                op3.setEnabled(true);
                                op4.setEnabled(true);

                                op1.setBackground(getDrawable(R.drawable.quiz_op));
                                op2.setBackground(getDrawable(R.drawable.quiz_op));
                                op3.setBackground(getDrawable(R.drawable.quiz_op));
                                op4.setBackground(getDrawable(R.drawable.quiz_op));
                                getquestion();
                            }
                        }, 1000);
                    } else if (total==5)
                    {
                        if (op4.getText().toString().equals(correct_ans)) {
                            op4.setBackground(getDrawable(R.drawable.quiz_op_green));
                            correct++;
                        } else {
                            op4.setBackground(getDrawable(R.drawable.quiz_red));
                            if (op1.getText().toString().equals(correct_ans)) {
                                op1.setBackground(getDrawable(R.drawable.quiz_op_green));
                            } else if (op3.getText().toString().equals(correct_ans)) {
                                op3.setBackground(getDrawable(R.drawable.quiz_op_green));
                            } else {
                                op2.setBackground(getDrawable(R.drawable.quiz_op_green));
                            }
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void run() {
                                time.cancel();
                                Intent intent = new Intent(quiz.this, result.class);
                                intent.putExtra("Correct", correct);
                                intent.putExtra("time", counter);
                                startActivity(intent);
                                finish();
                            }
                        }, 1000);
                    }
                }
            });

    }
    @Override
    public void onBackPressed()
    {
        final AlertDialog.Builder builder=new AlertDialog.Builder(quiz.this);
        builder.setMessage("Are you sure? You want to END the Exam?");
        builder.setCancelable(true);
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                time.cancel();
                finish();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}