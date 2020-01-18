package eg.gov.iti.jets.el_da7ee7;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    ImageView imageView;
    private static int Splash_Time_Out = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //to remove the title bar and make if full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //the two lines must be before the setcontentview line
        setContentView(R.layout.activity_main);
        //to hide the bar
        getSupportActionBar().hide();

        imageView = (ImageView)findViewById(R.id.imageView_ID);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //desplaying random images
        int[] images = new int[]{R.drawable.splash_1 , R.drawable.splash_2 , R.drawable.splash_3 , R.drawable.splash_4 ,
                                 R.drawable.splash_5 , R.drawable.splash_6 , R.drawable.splash_7 , R.drawable.splash_8 ,
                                 R.drawable.splash_9 , R.drawable.splash_10 , R.drawable.splash_11 , R.drawable.splash_12 ,
                                 R.drawable.splash_13 , R.drawable.splash_14};
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(images.length);
        this.imageView.setImageDrawable(getResources().getDrawable(images[random]));
/*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this , Login_Page.class);
                startActivity(intent);
            }
        } , Splash_Time_Out);
        */
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , Login_Page.class);
                startActivity(intent);
            }
        });
    }
}
