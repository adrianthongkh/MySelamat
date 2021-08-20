package com.myselamat.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import com.myselamat.model.IsolationSurvey;

public class HomeIsoFragment extends Fragment {

    private IsolationSurvey isolationSurvey;
    private int counter = 1;

    public HomeIsoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent i = getActivity().getIntent();
        isolationSurvey = (IsolationSurvey) i.getSerializableExtra("survey");

        View main_view = inflater.inflate(R.layout.fragment_home_iso, container, false);
        updateView(main_view);


        return main_view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        ImageButton btn_right = view.findViewById(R.id.btn_right);
        ImageButton btn_left = view.findViewById(R.id.btn_left);
        Button btn_submit = getActivity().findViewById(R.id.btn_hi_submit);

        RadioGroup rg = view.findViewById(R.id.rg_response);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (counter == 5) {
                    isolationSurvey.setQuestion5((checkedId == R.id.rb_yes) ? false : true);
                    btn_submit.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!determineRadioSelected(view)) {
                    Toast.makeText(getContext(), "Response not selected.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (checkEmpty(counter+1)) {
                    rg.clearCheck();
                    rg.jumpDrawablesToCurrentState();
                } else
                    checkExisting(rg, counter+1);
                counter++;
                updateView(view);
            }
        });

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                determineRadioSelected(view);
                if (checkEmpty(counter-1)) {
                    rg.clearCheck();
                    rg.jumpDrawablesToCurrentState();

                } else
                    checkExisting(rg, counter-1);
                counter--;
                updateView(view);
            }
        });
    }

    public void updateView(View view) {

        TextView tv_question = view.findViewById(R.id.tv_hi_question);
        ImageButton btn_right = view.findViewById(R.id.btn_right);
        ImageButton btn_left = view.findViewById(R.id.btn_left);
        Button btn_submit = getActivity().findViewById(R.id.btn_hi_submit);

        TextView tv_nav = view.findViewById(R.id.tv_hi_nav);
        tv_nav.setText(counter + "/5");

        TextView tv_ques_header = view.findViewById(R.id.tv_hi_question_header);
        tv_ques_header.setText("Question " + counter);

        switch (counter) {

            case 1:
                tv_question.setText(isolationSurvey.getQuestion1String());
                btn_left.setVisibility(View.INVISIBLE);
                btn_right.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.INVISIBLE);
                break;
            case 2:
                tv_question.setText(isolationSurvey.getQuestion2String());
                btn_left.setVisibility(View.VISIBLE);
                btn_right.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.INVISIBLE);
                break;
            case 3:
                tv_question.setText(isolationSurvey.getQuestion3String());
                btn_left.setVisibility(View.VISIBLE);
                btn_right.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.INVISIBLE);
                break;
            case 4:
                tv_question.setText(isolationSurvey.getQuestion4String());
                btn_left.setVisibility(View.VISIBLE);
                btn_right.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.INVISIBLE);
                break;
            case 5:
                tv_question.setText(isolationSurvey.getQuestion5String());
                btn_left.setVisibility(View.VISIBLE);
                btn_right.setVisibility(View.INVISIBLE);
                break;
        }

    }

    private boolean determineRadioSelected(View view) {

        RadioGroup rg = view.findViewById(R.id.rg_response);
        int idSelected = rg.getCheckedRadioButtonId();
        boolean response;

        if (idSelected == -1)
            return false;

        if (idSelected == R.id.rb_yes)
            response = true;
        else
            response = false;

        switch (counter) {

            case 1:
                isolationSurvey.setQuestion1(response);
                break;
            case 2:
                isolationSurvey.setQuestion2(response);
                break;
            case 3:
                isolationSurvey.setQuestion3(response);
                break;
            case 4:
                isolationSurvey.setQuestion4(response);
                break;
            case 5:
                isolationSurvey.setQuestion5(response);
                break;

        }
        return true;
    }

    private boolean checkEmpty(int local_counter) {

        if (local_counter == 1 && isolationSurvey.getQuestion1() != null)
            return false;
        if (local_counter == 2 && isolationSurvey.getQuestion2() != null)
            return false;
        if (local_counter == 3 && isolationSurvey.getQuestion3() != null)
            return false;
        if (local_counter == 4 && isolationSurvey.getQuestion3() != null)
            return false;
        if (local_counter == 5 && isolationSurvey.getQuestion5() != null)
            return false;

        return true;
    }

    private void checkExisting(RadioGroup rg, int local_counter) {

        int idSelected = -1;

        switch (local_counter) {
            case 1:
                if (isolationSurvey.getQuestion1()!= null)
                    idSelected = ((isolationSurvey.getQuestion1()) ? R.id.rb_yes : R.id.rb_no);
                break;
            case 2:
                if (isolationSurvey.getQuestion2()!= null)
                    idSelected = ((isolationSurvey.getQuestion2()) ? R.id.rb_yes : R.id.rb_no);
                break;
            case 3:
                if (isolationSurvey.getQuestion3()!= null)
                    idSelected = ((isolationSurvey.getQuestion3()) ? R.id.rb_yes : R.id.rb_no);
                break;
            case 4:
                if (isolationSurvey.getQuestion4()!= null)
                    idSelected = ((isolationSurvey.getQuestion4()) ? R.id.rb_yes : R.id.rb_no);
                break;
            case 5:
                if (isolationSurvey.getQuestion5() != null)
                    idSelected = ((isolationSurvey.getQuestion5()) ? R.id.rb_yes : R.id.rb_no);
                break;
        }
        rg.check(idSelected);
        rg.jumpDrawablesToCurrentState();
    }
}