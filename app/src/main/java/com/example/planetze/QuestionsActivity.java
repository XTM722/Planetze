package com.example.planetze;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.planetze.models.Question;
import com.example.planetze.models.QuestionSet;
import com.example.planetze.models.User;
import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {
    private User user;
    private int currentQuestionIndex;

    private RadioGroup radioGroup;
    private List<RadioButton> ratioButtons;


    private TextView questionTopicTextView;

    private Button questionNextOrSubmitButton;
    private QuestionSet questionSet;

    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        user = (User) getIntent().getSerializableExtra("user");
        currentQuestionIndex = 0;
        model = Model.getInstance();
        boolean addToFireBase = false;
        if (addToFireBase) {
            questionSet = getQuestionSet();
            model.postQuestionSet(questionSet, (Boolean success) -> {});
            updateCurrentQuestion();
        } else {
            model.getQuestionSet("qs1", (QuestionSet questionSet) -> {
                this.questionSet = questionSet;
                updateCurrentQuestion();
            });
        }



        questionTopicTextView = findViewById(R.id.questionTopicTextView);
        radioGroup = findViewById(R.id.radioGroup);

        // get a list of Radio Buttons from the radioGroup
        ratioButtons = new ArrayList<>();
        for (int i = 0; i < radioGroup.getChildCount(); i++)
            ratioButtons.add((RadioButton) radioGroup.getChildAt(i));


        questionNextOrSubmitButton = findViewById(R.id.questionNextOrSubmitButton);
        questionNextOrSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Question question = questionSet.questions.get(currentQuestionIndex);

                int selectedID = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedButton = findViewById(selectedID);
                String selectedOption = (String) selectedButton.getText();
                user.answers.put(question.questionID, selectedOption);

                if (questionNextOrSubmitButton.getText().equals("Submit")) {
                    model.postUser(user, (Boolean created) -> {
                        if (!created) {
                            Toast.makeText(QuestionsActivity.this, "Failed to Submit Questions!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Toast.makeText(QuestionsActivity.this, "Questions has been submitted successfully!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(QuestionsActivity.this, DashboardActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);

                    });
                }

                else {

                    if (currentQuestionIndex == 0 && selectedOption.equals("No")) {
                        currentQuestionIndex = 3;
                    }
                    else if( currentQuestionIndex ==3 && selectedOption.equals("Never")) {
                        currentQuestionIndex +=2;

                    }
                    else if( currentQuestionIndex ==5 && selectedOption.equals("None")) {
                        currentQuestionIndex +=2;

                    }
                    else if( currentQuestionIndex == 7 && !selectedOption.equals("Meat-based (eat all types of animal products)")) {
                        currentQuestionIndex +=5;

                    }
                    else {
                        currentQuestionIndex++;
                        updateCurrentQuestion();
                    }

                    updateCurrentQuestion();

                }

            }
        });


    }

    public QuestionSet getQuestionSet() {
        //  FireBase or hardcode;
        return new QuestionSet("qs1", new Question[]{
                new Question("q1", "Do you own or regularly use a car?", new String[]{"Yes", "No"}),
                new Question("q2", "What type of car do you drive?", new String[]{"Gasoline", "Diesel", "Hybrid", "Electric", "I don’t know"}),
                new Question("q3", "How many kilometers/miles do you drive per year?", new String[]{"Up to 5,000 km (3,000 miles)", "5,000–10,000 km (3,000–6,000 miles)","10,000–15,000 km (6,000–9,000 miles)","15,000–20,000 km (9,000–12,000 miles)" ,
                        "20,000–25,000 km (12,000–15,000 miles)","More than 25,000 km (15,000 miles)"}),
                new Question("q4", "How often do you use public transportation (bus, train,\n" +
                        "subway)", new String[]{"Never" , "Occasionally (1-2 times/week)" , "Frequently (3-4 times/week)" , "Always (5+ times/week)"}),
<<<<<<< HEAD
                new Question("q5", ": How much time do you spend on public transport\n" +
                        "per week  (bus, train," +
                        "subway)", new String[]{"Under 1 hour" , "1-3 hours" , "3-5 hours","5-10 hours" , "More than 10 hours"}),
=======
                new Question("q5", "How many short-haul flights (less than 1,500 km / 932 miles) have you\n" +
                        "taken in the past year?", new String[]{"Under 1 hour" , "1-3 hours" , "3-5 hours","5-10 hours" , "More than 10 hours"}),
>>>>>>> 9c8eca24cf9cf68c31d8046f5e8320f56903d29a
                new Question("q6", ". How many short-haul flights (less than 1,500 km / 932 miles) have you\n" +
                        "taken in the past year?", new String[]{"None" , "1-2 flights", "3-5 flights", "6-10 flights", "More than 10 flights"}),
                new Question("q7", "How many long-haul flights (more than 1,500 km / 932 miles) have you\n" +
                        "taken in the past year?", new String[]{"None" , "1-2 flights", "3-5 flights", "6-10 flights", "More than 10 flights"}),
                new Question("q8", "What best describes your diet?", new String[]{"Vegetarian", "Vegan", "Pescatarian (fish/seafood)", "Meat-based (eat all types of animal product"}),
                new Question("q9_a", "How often do you eat the following animal-based products?/n Beef:", new String[]{"Daily",
                        "Frequently (3-5 times/week)",
                        "Occasionally (1-2 times/week)",
                        "Never"}),
                new Question("q9_b", "How often do you eat the following animal-based products?/n Pork:", new String[]{"Daily",
                        "Frequently (3-5 times/week)",
                        "Occasionally (1-2 times/week)",
                        "Never"}),
                new Question("q9_c", "How often do you eat the following animal-based products?/n Chicken", new String[]{"Daily",
                        "Frequently (3-5 times/week)",
                        "Occasionally (1-2 times/week)",
                        "Never"}),
                new Question("q9_d", "How often do you eat the following animal-based products?/n Fish/Seafood:", new String[]{"Daily",
                        "Frequently (3-5 times/week)",
                        "Occasionally (1-2 times/week)",
                        "Never"}),
                new Question("q10", "How often do you waste food or throw away uneaten leftovers?", new String[]{"Never","Rarely", "Occasionally", "Frequently"}),
                new Question("q11", "What type of home do you live in?", new String[]{"Detached house",
                        "Semi-detached house",
                        "Townhouse",
                        "Condo/Apartment",
                        "Other "}),
                new Question("q12", "How many people live in your household?", new String[]{"1",
                        "2",
                        "3-4" ,
                        "5 or more"}),
                new Question("q13", " What is the size of your home?", new String[]{"Under 1000 sq. ft.",
                        "1000-2000 sq. ft.",
                        "Over 2000 sq. ft"}),
                new Question("q14", "What type of energy do you use to heat your home?", new String[]{"Natural Gas",
                        "Electricity",
                        "Oil" ,
                        "Propane",
                        "Wood",
                        "Other"}),
                new Question("q15", "What is your average monthly electricity bill?", new String[]{"Under $50" ,
                        "$50-$100",
                        "$100-$150",
                        "$150-$200",
                        "Over $200"}),
                new Question("q16", "What type of energy do you use to heat water in your home?", new String[]{"Natural Gas",
                        "Electricity",
                        "Oil",
                        "Propane",
                        "Solar",
                        "Other"}),
                new Question("q17", "Do you use any renewable energy sources for electricity or heating (e.g., solar,wind)?", new String[]{"Yes, primarily (more than 50% of energy use)",
                        "Yes, partially (less than 50% of energy use)",
                        "No"}),
                new Question("q18", "How often do you buy new clothes?", new String[]{"Monthly",
                        "Quarterly",
                        "Annually",
                        "Rarely"}),
                new Question("q19", "Do you buy second-hand or eco-friendly products?", new String[]{"Yes, regularly",
                        "Yes, occasionally",
                        "No"}),
                new Question("q20", "How many electronic devices (phones, laptops, etc.) have you purchased in the\n" +
                        "past year?", new String[]{"None",
                        "1",
                        "2",
<<<<<<< HEAD
                        "3 ",
                        "4 or more"}),
=======
                        "3 or more"}),
>>>>>>> 9c8eca24cf9cf68c31d8046f5e8320f56903d29a
                new Question("q21", "How often do you recycle?", new String[]{"Never",
                        "Occasionally",
                        "Frequently",
                        "Always"})
        });
    }


    private void updateCurrentQuestion() {
        radioGroup.clearCheck();
        questionNextOrSubmitButton.setText((currentQuestionIndex == questionSet.questions.size() - 1) ? "Submit" : "Next");
        Question question = questionSet.questions.get(currentQuestionIndex);
        questionTopicTextView.setText(question.topic);
        for (int i = 0; i < ratioButtons.size(); i++) {
            RadioButton radioButton = ratioButtons.get(i);
            if (i < question.options.size()) {
                radioButton.setVisibility(View.VISIBLE);
                radioButton.setText(question.options.get(i));
            } else {
                radioButton.setVisibility(View.GONE);
            }
        }
    }
}