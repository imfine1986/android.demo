package com.example.zhangshuibo.testdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by zhangshuibo on 14-10-4.
 */
public class CheatActivity extends Activity {

    public static final String IS_TRUE = "this is answer";
    public static final String ANSWER_SHOW = "answer show";

    private boolean answerIsTrue;

    private TextView answerTextView;
    private Button showAnswoer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        answerIsTrue = getIntent().getBooleanExtra(IS_TRUE,false);

        answerTextView = (TextView)findViewById(R.id.answerTextView);
        showAnswoer = (Button)findViewById(R.id.showAnswerButton);

        showAnswoer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answerIsTrue){
                    answerTextView.setText(R.string.true_button);
                }else{
                    answerTextView.setText(R.string.false_button);
                }

                setAnswerShownResult(true);
            }
        });
    }

    private void setAnswerShownResult(boolean b) {
        Intent intent = new Intent();
        intent.putExtra(ANSWER_SHOW,b);
        setResult(RESULT_OK,intent);
    }
}
