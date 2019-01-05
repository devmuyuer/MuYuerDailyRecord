package yao.muyuer.dailyrecord.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
	private FragmentManager fm;
	private List<Fragment> list;

	public ViewPagerAdapter(FragmentManager fm,
			List<Fragment> list) {
		super(fm);
		this.fm=fm;
		this.list = list;
	}

	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		Fragment fg=list.get(arg0);
		
		return fg;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(list!=null)
			return list.size();
		return 0;
	}

}
