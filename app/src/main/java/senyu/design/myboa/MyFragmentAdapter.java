package senyu.design.myboa;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList;

    public MyFragmentAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    public MyFragmentAdapter(FragmentManager fragmentManager,List<Fragment> list){
        super(fragmentManager);
        mList = list;
    }

    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
