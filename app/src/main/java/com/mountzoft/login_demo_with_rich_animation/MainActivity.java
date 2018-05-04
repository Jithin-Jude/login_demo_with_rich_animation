package com.mountzoft.login_demo_with_rich_animation;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private boolean isSigninScreen = true;
    private TextView tvSignupInvoker;
    private LinearLayout llSignup;
    private TextView tvSigninInvoker;
    private LinearLayout llSignin;
    private Button btnSignup;
    private Button btnSignin;
    LinearLayout llsignin,llsignup;

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        tvSignupInvoker = (TextView) findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = (TextView) findViewById(R.id.tvSigninInvoker);

        btnSignup= (Button) findViewById(R.id.btnSignup);
        btnSignin= (Button) findViewById(R.id.btnSignin);

        llSignup = (LinearLayout) findViewById(R.id.llSignup);
        llSignin = (LinearLayout) findViewById(R.id.llSignin);

        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = false;
                showSignupForm();
            }
        });

        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = true;
                showSigninForm();
            }
        });
        showSigninForm();
    }

    private void showSignupForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignup.requestLayout();

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right_to_left);
        btnSignup.startAnimation(clockwise);

    }

    private void showSigninForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_left_to_right);
        btnSignin.startAnimation(clockwise);
    }

    public void forgotPasswordBtnFunction(View view){
        Intent intent = new Intent(this,ForgotPassword.class);
        startActivity(intent);
    }

    public  void signInBtnFunction(View view){

        boolean flag = false;

        EditText inputUserName = (EditText) findViewById(R.id.loginEmail);
        EditText inputPassword = (EditText) findViewById(R.id.loginPassword);

        String userName = inputUserName.getText().toString();
        String password = inputPassword.getText().toString();

        DatabaseHelper myDb = new DatabaseHelper(this);
        Cursor res = myDb.getAllData();

        if(res.getCount() == 0) {
            Toast.makeText(this,"Nothing found",Toast.LENGTH_LONG).show();
            return;
        }

        while (res.moveToNext()) {

            if(res.getString(0).equals(userName) && res.getString(1).equals(password) ){
                flag = true;
                break;
            }
        }
        if(flag){
            Intent intent = new Intent(this, LoginSuccess.class);
            intent.putExtra("loggedUser", res.getString(0));
            startActivity(intent);
        }else {
            Toast.makeText(this,"You are not signed up !",Toast.LENGTH_LONG).show();
        }
    }

    public void signUpBtnFunction(View view){

        myDb = new DatabaseHelper(this);

        EditText inputUserName = (EditText) findViewById(R.id.email);
        EditText inputPassword = (EditText) findViewById(R.id.password);
        EditText inputConfirmPassword = (EditText) findViewById(R.id.confirmPassword);

        String userName = inputUserName.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();

        if(password.equals(confirmPassword)) {
            boolean isInserted = myDb.insertData(userName, password);
            if (isInserted) {
                Toast.makeText(this, "Sign Up complete", Toast.LENGTH_SHORT).show();
                showSigninForm();
            }
            else
                Toast.makeText(this, "Sign Up error", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Password mismatch !", Toast.LENGTH_LONG).show();
        }
    }


}
