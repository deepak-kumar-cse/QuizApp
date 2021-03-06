package com.example.quizapp.ui.addquestion;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.R;
import com.example.quizapp.backend.Question.Question;
import com.example.quizapp.backend.firebase.FireBaseManager;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SWAAddQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SWAAddQuestionFragment extends Fragment {
    EditText question, answer, message;
    TextView creator;
    Button exit, submit;
    FireBaseManager fireBaseManager = new FireBaseManager();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SWAAddQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SWAAddQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SWAAddQuestionFragment newInstance(String param1, String param2) {
        SWAAddQuestionFragment fragment = new SWAAddQuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_s_w_a_add_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        question=view.findViewById(R.id.question);
//        question=getView().findViewById(R.id.question);
        answer=getView().findViewById(R.id.answer);
        exit=getView().findViewById(R.id.exit);
        submit=getView().findViewById(R.id.submit);

        creator=getView().findViewById(R.id.creator);
        message=view.findViewById(R.id.message);




        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                String cr=AddQuestionDashboard.device_id;
                String std=((Spinner)getActivity().findViewById(R.id.spinner_standard)).getSelectedItem().toString().trim();
                String sub=((Spinner)getActivity().findViewById(R.id.spinner_subject)).getSelectedItem().toString().trim();
                String chapter=((Spinner)getActivity().findViewById(R.id.spinner_chapter)).getSelectedItem().toString().trim();
                String qs=question.getText().toString().trim();
                String  ans=answer.getText().toString().trim();
                String msg=message.getText().toString().trim();

                if (cr.matches("") || std.matches("") || sub.matches("") || chapter.matches("") || qs.matches("") || ans.matches("") ||  msg.matches("")) {
                    Toast.makeText(getContext(), "Please fill in all the required fields.", Toast.LENGTH_SHORT).show();
                }

                else {
                    Question question = Question.createSWA(cr,"Root",std,sub,  chapter, qs, ans, msg);


                    AddToFirebase.add(getActivity(), question);



                }

            }
        });


    }

}