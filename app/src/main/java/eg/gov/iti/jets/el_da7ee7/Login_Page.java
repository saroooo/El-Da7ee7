package eg.gov.iti.jets.el_da7ee7;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Page extends AppCompatActivity
{
    private EditText UserEmail;
    private EditText UserPassword;
    private Button LogIn;
    private TextView NewUser;
    private Button SignUp;
    private String Teacher;
    private String Student;
//    private TextView LogInWith;
//    private Button Facebook;
//    private Button Google;
    //to get the instanse of the firebase authentication
    private FirebaseAuth firebaseauth;
    //progress dialog
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Teacher = getIntent().getStringExtra("T");
        Student = getIntent().getStringExtra("S");

        UserEmail = (EditText)findViewById(R.id.UserEmail_ID);
        UserPassword = (EditText)findViewById(R.id.UserPassword_ID);
        LogIn = (Button)findViewById(R.id.LogIn_ID);
        NewUser = (TextView)findViewById(R.id.NewUser_ID);
        SignUp = (Button)findViewById(R.id.SignUp_ID);
//        LogInWith = (TextView)findViewById(R.id.LogInWith_ID);
//        Facebook = (Button)findViewById(R.id.Facebook_ID);
//        Google = (Button)findViewById(R.id.Google_ID);
        firebaseauth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        //checking if the user is aleady loged in
        FirebaseUser User = firebaseauth.getCurrentUser();
        //so that the user don't login every time he open the app
        if(User != null)
        {
            //destroy the activity
            //finish();
            //go to the student or teacher home
            if(Student.equals("S"))
            {
                finish();
                Intent intent = new Intent(Login_Page.this, Student_Home.class);
                startActivity(intent);
            }
            else if(Teacher.equals("T"))
            {
                finish();
                Intent intent = new Intent(Login_Page.this, Teacher_Home.class);
                startActivity(intent);
            }
            else
                Toast.makeText(Login_Page.this,"Please fill all fields." , Toast.LENGTH_SHORT).show();
        }
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(UserEmail.getText().toString().trim() , UserPassword.getText().toString().trim());
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Page.this , SignUp_Page.class);
                startActivity(intent);
            }
        });
    }

    private void validate(String email , String Password)
    {
        progressDialog.setMessage("Have a cup of Tea and a cheese sandwich until it Loads :\"D ...");
        progressDialog.show();
        firebaseauth.signInWithEmailAndPassword(email , Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {
                    Toast.makeText(Login_Page.this,"Log In Succussful" , Toast.LENGTH_SHORT).show();
                    //finish();
                    if(Student.equals("S"))
                    {
                        finish();
                        Intent intent = new Intent(Login_Page.this, Student_Home.class);
                        startActivity(intent);
                    }
                    else if(Teacher.equals("T"))
                    {
                        finish();
                        Intent intent = new Intent(Login_Page.this, Teacher_Home.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(Login_Page.this,"Please fill all fields." , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Log In Failed" , Toast.LENGTH_SHORT).show