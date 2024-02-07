package com.huewaco.cskh.activity;

import android.content.Context;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.huewaco.cskh.back.OnBackPressListener;
import com.huewaco.cskh.fragment.FParent;
import com.huewaco.cskh.fragment.FParentActivity;
import com.huewaco.cskh.fragment.FTabCaiDat;
import com.huewaco.cskh.fragment.FTabDichVu;
import com.huewaco.cskh.fragment.FTabThongBao;
import com.huewaco.cskh.fragment.FTabTraCuu;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;

import java.util.ArrayList;


public class AMain extends FParentActivity {

	private View viewIndicator0, viewIndicator1, viewIndicator2;
	private ViewGroup id_ly_background_change_color0, id_ly_background_change_color1, id_ly_background_change_color2, id_ly_background_change_color3,id_ly_background_change_color4;
	private int tabIndex;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fpActivity = this;

		initComponent();		
		addListener();
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
		mTabHost.setup();
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(4);
		mViewPager.setKeepScreenOn(true);


		mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);

		mTabsAdapter.addTab(createTabSpec(getResources().getString(R.string.tab_thongbao), R.drawable.tab_indicator_focus_thongbao, 0), FTabThongBao.class, null);
		mTabsAdapter.addTab(createTabSpec(getResources().getString(R.string.tab_tracuu), R.drawable.tab_indicator_focus_tracuu, 1), FTabTraCuu.class, null);
		mTabsAdapter.addTab(createTabSpec(getResources().getString(R.string.tab_dichvu), R.drawable.tab_indicator_focus_dichvu, 2), FTabDichVu.class, null);
		mTabsAdapter.addTab(createTabSpec(getResources().getString(R.string.tab_caidat), R.drawable.tab_indicator_focus_caidat, 3), FTabCaiDat.class, null);

		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}


		viewIndicator0 = mTabHost.getTabWidget().getChildTabViewAt(0);
		id_ly_background_change_color0 = (ViewGroup) viewIndicator0.findViewById(R.id.id_ly_background_change_color);

		viewIndicator1 = mTabHost.getTabWidget().getChildTabViewAt(1);
		id_ly_background_change_color1 = (ViewGroup) viewIndicator1.findViewById(R.id.id_ly_background_change_color);

		viewIndicator2 = mTabHost.getTabWidget().getChildTabViewAt(2);
		id_ly_background_change_color2 = (ViewGroup) viewIndicator2.findViewById(R.id.id_ly_background_change_color);


		//changeIconBarColor();

		mViewPager.setCurrentItem(tabIndex);
		initCommonView(this);
		//showBadgeNumber();
		try{
			FCM();
		}catch (Exception e){
			Log.d("abc","==============::: "+e.getMessage());
			CommonHelper.showToast(getApplicationContext(),e.getMessage());
		}

	}

	@Override
	public void onClick(View v) {
		
	}

	@Override
	protected void initComponent() {
		
	}

	@Override
	protected void addListener() {
		
	}
	
	TabHost.TabSpec createTabSpec(String tab, int iconInt, int index) {
		TabHost.TabSpec spec = mTabHost.newTabSpec(tab);
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, mTabWidget, false);
		ViewGroup id_ly_notif = (ViewGroup) tabIndicator.findViewById(R.id.id_ly_notif);
		if (index != 2) {
			id_ly_notif.setVisibility(View.GONE);
		}
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(tab);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setBackgroundResource(iconInt);

		spec.setIndicator(tabIndicator);
		return spec;
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}
	public class TabsAdapter extends FragmentPagerAdapter implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
		private final Context mContext;
		private final TabHost mTabHost;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			FParent fragment = (FParent) super.instantiateItem(container, position);
			registeredFragments.put(position, fragment);
			return fragment;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			registeredFragments.remove(position);
			super.destroyItem(container, position, object);
		}

		final class TabInfo {
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		@SuppressWarnings("deprecation")
		public TabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mTabHost = tabHost;
			mViewPager = pager;
			mTabHost.setOnTabChangedListener(this);
			mViewPager.setAdapter(this);

			mViewPager.setOnPageChangeListener(this);

		}

		public Fragment getRegisteredFragment(int position) {
			return registeredFragments.get(position);
		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
			tabSpec.setContent(new DummyTabFactory(mContext));
			String tag = tabSpec.getTag();

			TabInfo info = new TabInfo(tag, clss, args);
			mTabs.add(info);
			mTabHost.addTab(tabSpec);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			return (FParent) Fragment.instantiate(mContext, info.clss.getName(), info.args);

		}

		@Override
		public void onTabChanged(String tabId) {
			int position = mTabHost.getCurrentTab();
			mViewPager.setCurrentItem(position);
			GlobalVariable.POSITION = position;
			
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			TabWidget widget = mTabHost.getTabWidget();
			int oldFocusability = widget.getDescendantFocusability();
			widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			mTabHost.setCurrentTab(position);
			widget.setDescendantFocusability(oldFocusability);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
	}
	public void onBackPressed() {
		// currently visible tab Fragment
		OnBackPressListener currentFragment = (OnBackPressListener) mTabsAdapter.getRegisteredFragment(mViewPager.getCurrentItem());
		boolean bl = false;
		if (currentFragment != null) {
			// lets see if the currentFragment or any of its childFragment can
			// handle onBackPressed
			bl = currentFragment.onBackPressed();
		} else {

			// this Fragment couldn't handle the onBackPressed call
			finish();
		}
		// nếu ở trong tin nhan chat one có xóa tin nhắn thì khi back
		// ra tab tin
		// nhan chinh se refresh list tin nhan chinh

		if (!bl) {
			finish();
		}
	}
	public void changeIconBarColor() {
		id_ly_background_change_color0.setBackgroundColor(GlobalVariable.COLOR_BAR);
		id_ly_background_change_color1.setBackgroundColor(GlobalVariable.COLOR_BAR);
		id_ly_background_change_color2.setBackgroundColor(GlobalVariable.COLOR_BAR);
		id_ly_background_change_color3.setBackgroundColor(GlobalVariable.COLOR_BAR);
		id_ly_background_change_color4.setBackgroundColor(GlobalVariable.COLOR_BAR);
	}
}
