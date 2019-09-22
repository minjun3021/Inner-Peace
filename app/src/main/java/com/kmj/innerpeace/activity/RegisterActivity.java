package com.kmj.innerpeace.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kmj.innerpeace.R;

public class RegisterActivity extends AppCompatActivity {
    EditText id, pw, name, phone;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setup();
    }

    void setup() {
        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#121319"));

        id = findViewById(R.id.register_id);
        pw = findViewById(R.id.register_pw);
        name = findViewById(R.id.register_name);

        phone = findViewById(R.id.register_phonenumber);
        register = findViewById(R.id.register_btn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!id.getText().toString().replace(" ", "").equals("")) {
                    if (!pw.getText().toString().replace(" ", "").equals("")) {
                        if (!name.getText().toString().replace(" ", "").equals("")) {

                            if (!phone.getText().toString().replace(" ", "").equals("")) {
                                //TODO retrofit
                                tryRegister();
                            } else {
                                Toast.makeText(RegisterActivity.this, "phone number를 입력하세요.", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(RegisterActivity.this, "name를 입력하세요.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "pw를 입력하세요.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "id를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        phone.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    tryRegister();
                    return true;
                }
                return false;
            }
        });
    }

    void tryRegister() {
        Toast.makeText(this, "Wait", Toast.LENGTH_SHORT).show();
    }
}
