package com.example.triviality;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {
	List<Question> quesList  = new ArrayList<>();


	//List<Question> questionList = new ArrayList<>();

	List<String> d = new ArrayList<>();
	int score=0;
	int qid=0;
	int qid_back=0;
	Question currentQ;
	TextView txtQuestion;
	RadioButton rda, rdb, rdc;
	Button butNext,button_pre;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);


		// using database
		DbHelper db=new DbHelper(this);
		quesList=db.getAllQuestions();


  //  or using arraylist

			/*Question	currentQ1 = new Question("1What is JP?","Jalur Pesawat", "Jack sParrow", "Jasa Programmer", "Jasa Programmer");
			Question currentQ2 = new Question("2What is JP?","Jalur Pesawat", "Jack sParrow", "Jasa Programmer", "Jasa Programmer");
			Question currentQ3 = new Question("3What is JP?","Jalur Pesawat", "Jack sParrow", "Jasa Programmer", "Jasa Programmer");
			Question currentQ4 = new Question("4What is JP?","Jalur Pesawat", "Jack sParrow", "Jasa Programmer", "Jasa Programmer");
			Question currentQ5 = new Question("5What is JP?","Jalur Pesawat", "Jack sParrow", "Jasa Programmer", "Jasa Programmer");

			quesList.add(currentQ1);
			quesList.add(currentQ2);
			quesList.add(currentQ3);
			quesList.add(currentQ4);
			quesList.add(currentQ5);*/





		currentQ=quesList.get(qid);
		txtQuestion=(TextView)findViewById(R.id.textView1);
		rda=(RadioButton)findViewById(R.id.radio0);
		rdb=(RadioButton)findViewById(R.id.radio1);
		rdc=(RadioButton)findViewById(R.id.radio2);
		butNext=(Button)findViewById(R.id.button1);
		button_pre=(Button)findViewById(R.id.button_pre);
		setQuestionView();

		butNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioGroup grp = (RadioGroup) findViewById(R.id.radioGroup1);
				RadioButton answer = (RadioButton) findViewById(grp.getCheckedRadioButtonId());
				Log.d("yourans", currentQ.getANSWER() + " " + answer.getText());
				if (currentQ.getANSWER().equals(answer.getText())) {
					score++;
					Log.d("score", "Your score" + score);
				}
				if (qid < quesList.size()) {
					currentQ = quesList.get(qid);
					setQuestionView();
				} else {
					Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
					Bundle b = new Bundle();
					b.putInt("score", score); //Your score
					intent.putExtras(b); //Put your score to your next Intent
					startActivity(intent);
					finish();
				}
			}
		});

		button_pre.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(qid > 0) {
					qid_back = qid - 1;


					if (qid_back < quesList.size()) {
						currentQ = quesList.get(qid_back);
						setQuestionView();
						qid = qid_back;
					}
				}
				else{
						Toast.makeText(QuizActivity.this,"This is First Question",Toast.LENGTH_LONG).show();
				}

			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_quiz, menu);
		return true;
	}
	private void setQuestionView()
	{
		txtQuestion.setText(currentQ.getQUESTION());
		rda.setText(currentQ.getOPTA());
		rdb.setText(currentQ.getOPTB());
		rdc.setText(currentQ.getOPTC());
		qid++;
	}
}
