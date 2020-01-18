package eg.gov.iti.jets.el_da7ee7;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class SignUp_Page extends AppCompatActivity
{
    private TextView Sign_Who;
    private EditText Sign_Name;
    private EditText Sign_Email;
    private EditText Sign_Password;
    private EditText Sign_ConfirmPassword;
    private EditText Sign_Phone;
    private Button Sign_SignUP;
    private RadioGroup radiogroup;
    private RadioButton Sign_Teacher;
    private RadioButton Sign_Student;
    private String Teacher;
    private String Student;
    private FirebaseAuth firebaseauth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseauth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        Sign_Who = (TextView)findViewById(R.id.Sign_WhoYouAre_ID);
        Sign_Name = (EditText)findViewById(R.id.Sign_UserName_ID);
        Sign_Email = (EditText)findViewById(R.id.Sign_UserEmail_ID);
        Sign_Password = (EditText)findViewById(R.id.Sign_UserPassword_ID);
        Sign_ConfirmPassword = (EditText)findViewById(R.id.Sign_UserPassword2_ID);
        Sign_Phone = (EditText)findViewById(R.id.Sign_UserPhone_ID);
        Sign_SignUP = (Button)findViewById(R.id.Sign_SignUp_ID);
        radiogroup = (RadioGroup)findViewById(R.id.Sign_RadioGroup_ID);
        Sign_Teacher = (RadioButton)findViewById(R.id.Sign_Teacher_ID);
        Sign_Student = (RadioButton)findViewById(R.id.Sign_Student_ID);

        if(Sign_Teacher.isChecked())
            Teacher = "T";
        else if(Sign_Student.isChecked())
            Student = "S";
        Sign_SignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    progressDialog.setMessage("Have a cup of Tea and a cheese sandwich until you are verified :\"D ...");
                    progressDialog.show();
                    //upload data to database
                    String Sign_UserEmail = Sign_Email.getText().toString().trim();//trim function is to remove all the white spaces that the user might have entered
                    String Sign_UserPassword = Sign_Password.getText().toString().trim();
                    //now we push that to the database
                    firebaseauth.createUserWithEmailAndPassword(Sign_UserEmail , Sign_UserPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            //here you need to tell the user if the task complete successfully of have an error
                            progressDialog.dismiss();
                            if(task.isSuccessful())
                            {
                                Toast.makeText(SignUp_Page.this , "Registration Succussful", Toast.LENGTH_SHORT).show();
                                //destroy the activity
                                finish();

                                Intent intent = new Intent(SignUp_Page.this , Login_Page.class);
                                intent.putExtra("T", Teacher);
                                intent.putExtra("S", Student);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(SignUp_Page.this,"Registration Failed" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean validate()
    {
        boolean valid = false;
        String Name = Sign_Name.getText().toString();
        String Email = Sign_Email.getText().toString();
        String Password = Sign_Password.getText().toString();
        String ConfirmPassword = Sign_ConfirmPassword.getText().toString();
        String Phone = Sign_Phone.getText().toString();
        if(Name.isEmpty() || Email.isEmpty() || Password.isEmpty() || ConfirmPassword.isEmpty() || Phone.isEmpty() || (!Sign_Student.isChecked() && !Sign_Teacher.isChecked()))
        {
            valid = false;
            Toast.makeText(getApplicationContext(),"Please fill all fields." , Toast.LENGTH_LONG).show();
        }
        else
        {
            if(Password.equals(ConfirmPassword))
            {
                valid = true;
            }
            else
            {
                valid = false;
                Toast.makeText(getApplicationContext(),"Passwords don't match, Please Try again." , Toast.LENGTH_LONG).show();
            }
        }
        return valid;
    }
}