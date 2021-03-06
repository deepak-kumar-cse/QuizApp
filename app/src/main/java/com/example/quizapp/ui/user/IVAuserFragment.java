package com.example.quizapp.ui.user;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.R;
import com.example.quizapp.backend.Question.IVA;
import com.example.quizapp.backend.Question.Question;

import java.util.ArrayList;
import java.util.Locale;

public class IVAuserFragment extends Fragment {
    TextView text_question;
    EditText text_user_ans;
    Button text_actual_ans_btn;
    Question current_question;
    int index = 0;
    ArrayList<String> answer_given;
    int total_question, question_unsolved, question_solved, question_wrong, question_correct;



    public IVAuserFragment(Question question) {
        // Required empty public constructor
        current_question = question;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_i_v_auser, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text_question = getView().findViewById(R.id.text_question);
        text_user_ans = getView().findViewById(R.id.user_answer);
        text_actual_ans_btn = getView().findViewById(R.id.set_button);

        IVA question = (IVA) current_question;
        text_question.setText(question.getQuestion());

        text_user_ans.setEnabled(false);

        index = ((QuizDashboard) getActivity()).index;
        answer_given = ((QuizDashboard) getActivity()).answer_given;
        if (answer_given.get(index) == "") {
            text_user_ans.setEnabled(true);

            setListener();
        } else {
            text_actual_ans_btn.setText(Integer.toString(question.getAnswer()));

            if (Integer.parseInt(answer_given.get(index)) == ((IVA) current_question).getAnswer()) {
                text_user_ans.setBackgroundColor(Color.GREEN);
//                text_actual_ans_btn.getBackground().setTint(Color.GREEN);
            } else {
                text_user_ans.setBackgroundColor(Color.RED);
//                text_actual_ans_btn.getBackground().setTint(Color.RED);

            }
        }


    }

    void setListener() {
        IVA question = (IVA) current_question;

        text_actual_ans_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(text_user_ans.getText().toString().matches("")){
                    Toast.makeText(getContext(), "Please put answer", Toast.LENGTH_SHORT).show();
                 }
                else{
                    if (question.getAnswer() == Integer.parseInt(text_user_ans.getText().toString())) {
                        text_user_ans.setBackgroundColor(Color.GREEN);
//                    text_actual_ans_btn.getBackground().setTint(Color.GREEN);
                        update_top_bar(1);

                    } else {
                        text_user_ans.setBackgroundColor(Color.RED);
//                    text_actual_ans_btn.getBackground().setTint(Color.RED);
                        update_top_bar(-1);

                    }
                    text_actual_ans_btn.setText(Integer.toString(question.getAnswer()));
                    answer_given.set(index, text_user_ans.getText().toString());
                    text_actual_ans_btn.setOnClickListener(null);
                    text_user_ans.setEnabled(false);
                }


            }

        });

    }


    void update_top_bar(int ans) {


        if (ans < 0) {
            ((QuizDashboard) getActivity()).question_solved++;
            ((QuizDashboard) getActivity()).question_unsolved--;
            ((QuizDashboard) getActivity()).question_wrong++;


        } else if (ans > 0) {
            ((QuizDashboard) getActivity()).question_solved++;
            ((QuizDashboard) getActivity()).question_unsolved--;
            ((QuizDashboard) getActivity()).question_correct++;

        } else {

        }
        ((QuizDashboard) getActivity()).updateTopBar();


    }


}

