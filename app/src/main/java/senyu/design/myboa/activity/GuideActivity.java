package senyu.design.myboa.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;

import com.chechezhi.ui.guide.AbsGuideActivity;
import com.chechezhi.ui.guide.SingleElement;
import com.chechezhi.ui.guide.SinglePage;

import java.util.ArrayList;
import java.util.List;

import senyu.design.myboa.R;
import senyu.design.myboa.fragment.LastGuidePageFragment;

public class GuideActivity extends AbsGuideActivity {
//todo(@SenYu)安排自己的启动页素材

    @Override
    public List<SinglePage> buildGuideContent() {
        List<SinglePage> guideContent = new ArrayList<SinglePage>();
        SinglePage page01 = new SinglePage();
        page01.mBackground = ContextCompat.getDrawable(this, R.drawable.bg_page_01);
        SingleElement e01 = new SingleElement(200, 200, 400, 400, 0.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        SingleElement e02 = new SingleElement(700, 800, 700, 100, 0.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        page01.mElementsList.add(e01);
        page01.mElementsList.add(e02);
        guideContent.add(page01);

        SinglePage page02 = new SinglePage();
        page02.mBackground = ContextCompat.getDrawable(this,R.drawable.bg_page_02);
        SingleElement e03 = new SingleElement(400, 400, -100, -100, 1.0f, 0.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        SingleElement e04 = new SingleElement(700, 100, 700, -200, 1.0f, 0.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        page02.mElementsList.add(e03);
        page02.mElementsList.add(e04);
        guideContent.add(page02);

        SinglePage page03 = new SinglePage();
        page03.mBackground =ContextCompat.getDrawable(this,R.drawable.bg_page_03);
        SingleElement e05 = new SingleElement(-100, 2000, 100, 100, 1.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        SingleElement e06 = new SingleElement(100, 2000, 300, 120, 1.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        SingleElement e07 = new SingleElement(200, 2000, 600, 140, 1.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        SingleElement e08 = new SingleElement(300, 2000, 900, 160, 1.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        page03.mElementsList.add(e05);
        page03.mElementsList.add(e06);
        page03.mElementsList.add(e07);
        page03.mElementsList.add(e08);
        guideContent.add(page03);

        SinglePage page04 = new SinglePage();
        page04.mBackground = ContextCompat.getDrawable(this,R.drawable.bg_page_04);
        SingleElement e09 = new SingleElement(100, 100, 3000, 3000, 1.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        SingleElement e10 = new SingleElement(300, 120, 3000, 3000, 1.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        SingleElement e11 = new SingleElement(600, 140, 3000, 3000, 1.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        SingleElement e12 = new SingleElement(900, 160, 3000, 3000, 1.0f, 1.0f, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_stuff));
        page04.mElementsList.add(e09);
        page04.mElementsList.add(e10);
        page04.mElementsList.add(e11);
        page04.mElementsList.add(e12);
        guideContent.add(page04);

        SinglePage page05 = new SinglePage();
        page05.mCustomFragment = new LastGuidePageFragment();
        guideContent.add(page05);

        return guideContent;
    }

    @Override
    public Bitmap dotDefault() {
        return BitmapFactory.decodeResource(getResources(), R.drawable.ic_dot_default);
    }

    @Override
    public Bitmap dotSelected() {
        return BitmapFactory.decodeResource(getResources(), R.drawable.ic_dot_selected);
    }

    @Override
    public boolean drawDot() {
        return true;
    }

    public void entryApp() {
        // Time to entry your app! We just finish the activity, replace it with
        // your code.

        Intent intent = new Intent(GuideActivity.this,MainActivity.class);
        startActivity(intent);
    }

    /**
     * You need provide an id to the pager. You could define an id in
     * values/ids.xml and use it.
     */
    @Override
    public int getPagerId() {
        return R.id.guide_container;
    }
}