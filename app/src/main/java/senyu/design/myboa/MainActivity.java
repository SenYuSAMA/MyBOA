package senyu.design.myboa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import senyu.design.myboa.utils.SPUtils;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean firstCome = (Boolean)SPUtils.get(this,SPUtils.firstCome,true);
        if(firstCome){
            SPUtils.put(this,SPUtils.firstCome,false);
            Intent i = new Intent(MainActivity.this, ExampleGuideActivity.class);
            startActivity(i);
        }
        else{
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ExampleGuideActivity.class);
                startActivity(i);
            }
        });
        }
    }
}
