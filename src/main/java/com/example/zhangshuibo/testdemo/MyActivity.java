package com.example.zhangshuibo.testdemo;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity {

    private static final String TAG = "MyActivity";
    private static final String KEY_INDEX = "index";

    private Button mTrueButton;
    private Button mFalseButton;

    private ImageButton mNextButton;
    private ImageButton mPrveButton;

    private Button cheatButton;

    private TextView mQuestionTextView;

    private boolean isCheater = false;


    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_one,true),
            new TrueFalse(R.string.question_two,true),
            new TrueFalse(R.string.question_three,false),
            new TrueFalse(R.string.question_four,true)
    };

    private int mCurrentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //日志
        Log.d(TAG,"onCreate(Bundle) called");

        setContentView(R.layout.activity_my);

        //text 显示
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });
        //保存的状态
        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }

        updateQuestion();

        //左右buttopn
        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mPrveButton = (ImageButton)findViewById(R.id.prev_button);
        //true false button
        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);

        cheatButton = (Button)findViewById(R.id.cheat_button);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCheater = false;

                mCurrentIndex = (mCurrentIndex+1)% mQuestionBank.length;
                updateQuestion();
            }
        });
        mPrveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex-1)% mQuestionBank.length;
                if (mCurrentIndex < 0)
                    mCurrentIndex = mQuestionBank.length-1;
                updateQuestion();
            }
        });

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyActivity.this,CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismTrueQuestion();
                intent.putExtra(CheatActivity.IS_TRUE,answerIsTrue);

                //startActivity(intent);
                startActivityForResult(intent,0);
            }
        });
    }

    //调用子类返回后的结果处理方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null){
            return;
        }
        isCheater = data.getBooleanExtra(CheatActivity.ANSWER_SHOW,false);
    }

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getmQuestion();

        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismTrueQuestion();

        int messageResId = 0;

//        if (userPressedTrue == answerIsTrue){
//            messageResId = R.string.correct_test;
//        }else {
//            messageResId = R.string.incorrect_toast;
//        }

        if(isCheater){
            messageResId = R.string.correct_test;
        }else {
            if (userPressedTrue == answerIsTrue){
                messageResId = R.string.correct_test;
            }else {
                messageResId = R.string.incorrect_toast;
            }

        }

        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSaveInstanceState");

        outState.putInt(KEY_INDEX,mCurrentIndex);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }
}
