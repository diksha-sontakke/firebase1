package com.example.firebasebasic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    boolean showContent = false;
    EditText regis_Name,regis_Email,regis_Password,regis_ConfirmPass;
    TextView signup_btn,signing_btn;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    // defining our own password pattern
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");

    FirebaseDatabase db;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regis_Name = findViewById(R.id.regis_Name);
        regis_Email = findViewById(R.id.regis_Email);
        regis_Password = findViewById(R.id.regis_Password);
        regis_ConfirmPass = findViewById(R.id.regis_ConfirmPass);

        signing_btn = findViewById(R.id.signin_btn);
        signup_btn = findViewById(R.id.signup_btn);

        SplashScreen splashScreen= SplashScreen.installSplashScreen(this);
        //to keep the splash screen on the screen for a long period
        //set he onPreDrawListener to root view
        final View content = findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if(showContent){
                    content.getViewTreeObserver().removeOnPreDrawListener(this);
                }
                showContentAfterSomeTime(); // a method to alter the boolean
                return false;
            }
        });


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = regis_Name.getText().toString();
                String email = regis_Email.getText().toString();
                String password = regis_Password.getText().toString();
                String cpassword = regis_ConfirmPass.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) ||
                        TextUtils.isEmpty(password) || TextUtils.isEmpty(cpassword)) {

                    Toast.makeText(MainActivity.this, "Please Enter Valid Data ", Toast.LENGTH_SHORT).show();
                } else if (!email.matches(emailPattern)) {
                    regis_Email.setError("Invalid email");

                    Toast.makeText(MainActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();

                } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
                    regis_Password.setError("Invalid Password");

                    Toast.makeText(MainActivity.this, "Please Enter 6 character Password", Toast.LENGTH_SHORT).show();
                }  else if (!password.equals(cpassword)) {

                    Toast.makeText(MainActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                }else{

                    Users users =new Users(name,email);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Users");
                    reference.child(name).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(MainActivity.this,"Successfully Updated",Toast.LENGTH_SHORT).show();

                        }
                    });


                }

            }
        });







    }
    private void showContentAfterSomeTime() {
        //use handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showContent =true;

            }
        },3000);
    }
}