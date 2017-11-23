package com.the.project.quotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {
    private EditText inputTitle, inputQuote, inputName;
    private Button submit, email;
    //private TextInputLayout inputLayoutTitle, inputLayoutQuote, inputLayoutName;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit = (Button) findViewById(R.id.submit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        // inputLayoutQuote = (TextInputLayout) findViewById(R.id.input_layout_quote);
        //inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputTitle = (EditText) findViewById(R.id.input_title);
        inputQuote = (EditText) findViewById(R.id.input_quote);
        inputName = (EditText) findViewById(R.id.input_name);
        textView = (TextView) findViewById(R.id.result);
        //inputTitle.addTextChangedListener(new MyTextWatcher(inputTitle));
        //inputQuote.addTextChangedListener(new MyTextWatcher(inputQuote));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

    }

    /*
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_title:
                    validateTitle();
                    break;
                case R.id.input_quote:

                    break;

            }

        }}
        */
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validateTitle() {
        if (inputTitle.getText().length() > 40) {
            textView.setText("Length should be less than 40 characters");
            requestFocus(inputTitle);
            return false;
        } else if (inputTitle.getText().toString().contains(" ")) {
            textView.setText("Title should not contain spaces");
            requestFocus(inputTitle);
            return false;
        }
        return true;
    }

    private void submitForm() {
        if (!validateTitle()) {
            return;
        }

        if (!validateQuote()) {
            return;
        }
        textView.setText("");
        SharedPreferences.Editor editor = getSharedPreferences("mysharedPref", MODE_PRIVATE).edit();
        editor.putString("name", inputName.getText().toString());
        editor.putString("quote", inputQuote.getText().toString());
        editor.putString("title", inputTitle.getText().toString());
        editor.apply();


        Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(myIntent);
    }

    private boolean validateQuote() {
        if (inputQuote.getText().length() > 140) {
            textView.setText("Length should be less than 140 characters");
            requestFocus(inputQuote);
            return false;
        }

        return true;
    }

}
