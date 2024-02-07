package com.huewaco.cskh.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.GlobalVariable;

//import android.support.annotation.Nullable;


public class FTabDichVuThanhToanOnline extends FParent {

	private ViewGroup id_ly_quathenganhang,id_ly_quavidientu,id_ly_quanganhanglienket;
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frag_tab_dichvu_thanhtoan_online, container, false);
		initCommonView(v, this);
		initComponent(v);
		addListener();
		setText();
		return v;
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	protected void initComponent(View v) {
		id_ly_quathenganhang = (ViewGroup)v.findViewById(R.id.id_ly_quathenganhang);
		id_ly_quavidientu = (ViewGroup)v.findViewById(R.id.id_ly_quavidientu);
		id_ly_quanganhanglienket = (ViewGroup)v.findViewById(R.id.id_ly_quanganhanglienket);
	}

	@Override
	protected void addListener() {
		id_ly_quathenganhang.setOnClickListener(this);
		id_ly_quavidientu.setOnClickListener(this);
		id_ly_quanganhanglienket.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.id_ly_quathenganhang:
				FTabDichVuThanhToanVNPAY frg0 = new FTabDichVuThanhToanVNPAY();
				frg0.title = getString(R.string.dichvu_pay_via_vnpay);
				FragmentTransaction transaction0 = getChildFragmentManager().beginTransaction();
				if (GlobalVariable.IS_ANIMATION) {
					transaction0.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
				}
				transaction0.addToBackStack(null);
				transaction0.replace(R.id.id_fr_tab_dichvu_thanhtoan_online, frg0).commit();
				break;
			case R.id.id_ly_quavidientu:
				FTabDichVuThanhToanQuaViDienTu frg1 = new FTabDichVuThanhToanQuaViDienTu();
				frg1.title = getString(R.string.dichvu_pay_via_vidientu);
				FragmentTransaction transaction21 = getChildFragmentManager().beginTransaction();
				if (GlobalVariable.IS_ANIMATION) {
					transaction21.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
				}
				transaction21.addToBackStack(null);
				transaction21.replace(R.id.id_fr_tab_dichvu_thanhtoan_online, frg1).commit();
				break;
			case R.id.id_ly_quanganhanglienket:
			FTabTraCuu7LienKetNganHang frg = new FTabTraCuu7LienKetNganHang();
            frg.title = getString(R.string.dichvu_pay_via_nganhanglienket);
            FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
            if (GlobalVariable.IS_ANIMATION) {
                transaction2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            transaction2.addToBackStack(null);
            transaction2.replace(R.id.id_fr_tab_dichvu_thanhtoan_online, frg).commit();
				break;
		default:
			break;
		}

	}

	public void setText() {
		id_tv_title.setText(title);
	}
}
