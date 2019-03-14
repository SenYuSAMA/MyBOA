package senyu.design.myboa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.chechezhi.ui.guide.AbsGuideActivity;
import com.chechezhi.ui.guide.SingleElement;
import com.chechezhi.ui.guide.SinglePage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ExampleGuideActivity.class);
                startActivity(i);
            }
        });
    }
}
