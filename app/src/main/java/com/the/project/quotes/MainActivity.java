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


public class MainActivity extends AppCompatActivity {
    private EditText inputTitle, inputQuote, inputName;
    private Button submit,email;
    private TextInputLayout inputLayoutTitle, inputLayoutQuote, inputLayoutName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit=(Button)findViewById(R.id.submit);
        email=(Button)findViewById(R.id.email);

        inputLayoutTitle = (TextInputLayout) findViewById(R.id.input_layout_title);
        inputLayoutQuote = (TextInputLayout) findViewById(R.id.input_layout_quote);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputTitle = (EditText) findViewById(R.id.input_title);
        inputQuote = (EditText) findViewById(R.id.input_quote);
        inputName = (EditText) findViewById(R.id.input_name);
        inputTitle.addTextChangedListener(new MyTextWatcher(inputTitle));
        inputQuote.addTextChangedListener(new MyTextWatcher(inputQuote));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendEmail();
            }
        });

    }
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
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private boolean validateTitle() {
        if (inputTitle.getText().length()>40) {
            inputLayoutTitle.setError("Length should be less than 40 characters");
            requestFocus(inputTitle);
            return false;
        } else {
            inputLayoutTitle.setErrorEnabled(false);
        }

        return true;
    }
    private void SendEmail() {
        if (!validateTitle()) {
            return;
        }

        if (!validateQuote()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Theprojectquote@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Send quotes");
        intent.putExtra(Intent.EXTRA_TEXT, "Title- "+inputTitle.getText().toString()+"\n\nQuote- "+inputQuote.getText().toString()
        +"\n\nName- "+inputName.getText().toString());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }

            }
    private void submitForm() {
        if (!validateTitle()) {
            return;
        }

        if (!validateQuote()) {
            return;
        }

        SharedPreferences.Editor editor = getSharedPreferences("mysharedPref", MODE_PRIVATE).edit();
        editor.putString("name", inputName.getText().toString());
        editor.putString("quote",inputQuote.getText().toString());
        editor.putString("title", inputTitle.getText().toString());
        editor.apply();


        Intent myIntent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(myIntent);    }
    private boolean validateQuote() {
        if (inputQuote.getText().length()>300) {
            inputLayoutQuote.setError("Length should be less than 300 characters");
            requestFocus(inputQuote);
            return false;
        } else {
            inputLayoutQuote.setErrorEnabled(false);
        }

        return true;
    }
}
