/**
 * Created by: Kiet.Duong
 * Dec-29-2014
 **/
package com.huewaco.cskh.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.customview.edittext.CustomEditText;
import com.customview.edittext.DrawableClickListener;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.back.BackPressImpl;
import com.huewaco.cskh.back.OnBackPressListener;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;

import java.util.ArrayList;

public class FParent extends Fragment implements OnClickListener,
        OnBackPressListener, OnTouchListener {
    protected FParentActivity fpActivity;
    protected String title;
    protected void changeColor() {

    }
    protected void changeText() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(GlobalVariable.EXPIRED){
            CommonHelper.showWarning(fpActivity,getString(R.string.het_han_su_dung));
        }

    }
    public FParent() {
        setRetainInstance(true);
    }

    public void refreshData() {

    }

    protected void checkFragmentToRefresh() {
        ArrayList<FParent> mArrFP = new ArrayList<FParent>();
        addFragment((FParent) fpActivity.mTabsAdapter.getRegisteredFragment(fpActivity.mViewPager.getCurrentItem()), mArrFP);
        int size = mArrFP.size();
        if (mArrFP != null && size > 1) {
            mArrFP.get(size - 2).refreshData();
        }
    }

    private void addFragment(FParent fp, ArrayList<FParent> mArrFP) {
        mArrFP.add(fp);
        int childCount = fp.getChildFragmentManager().getBackStackEntryCount();
        if (childCount > 0) {
            FragmentManager childFragmentManager = fp.getChildFragmentManager();
            FParent childFragment = (FParent) childFragmentManager.getFragments().get(0);
            addFragment((FParent) childFragment, mArrFP);
        }
    }

    @Override
    public boolean onBackPressed() {
        //refreshData();
        checkFragmentToRefresh();
        return new BackPressImpl(this).onBackPressed();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onAttach(Activity activity) {
        if (!(activity instanceof FParentActivity)) {
            throw new IllegalStateException(getClass().getSimpleName() + " must be attached to a FragmentActivity.");
        }
        fpActivity = (FParentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        System.gc();
        fpActivity = null;
        super.onDetach();
    }

    protected void initComponent(View v) {
    }

    ;

    protected void addListener() {
    }

    ;

    @Override
    public void onClick(View v) {

    }

    protected ViewGroup id_ly_topbar, id_ly_left, id_ly_center, id_ly_right;
    protected Button id_btn_left, id_btn_left2, id_btn_left3, id_btn_left4, id_btn_right, id_btn_right2, id_btn_right3, id_btn_right4;
    protected TextView id_tv_title, id_tv_title_sub;
    protected CustomEditText id_edt_search_main;

    protected void showView(View v) {
        v.setVisibility(View.VISIBLE);
    }

    protected void hideView(View v) {
        v.setVisibility(View.GONE);
    }

    protected void setTitle(String title) {
        if (CommonHelper.checkValidString(title)) {
            id_tv_title.setText(title);
        }
    }

    //FParent classX;
    protected void initCommonView(View v, FParent classX) {
        id_edt_search_main = (CustomEditText)v.findViewById(R.id.id_edt_search_main);
        id_ly_topbar = (ViewGroup) v.findViewById(R.id.id_ly_topbar);
        id_ly_left = (ViewGroup) v.findViewById(R.id.id_ly_left);
        id_ly_center = (ViewGroup) v.findViewById(R.id.id_ly_center);
        id_ly_right = (ViewGroup) v.findViewById(R.id.id_ly_right);


        id_btn_left = (Button) v.findViewById(R.id.id_btn_left);
        id_btn_left2 = (Button) v.findViewById(R.id.id_btn_left2);
        id_btn_left3 = (Button) v.findViewById(R.id.id_btn_left3);
        id_btn_left4 = (Button) v.findViewById(R.id.id_btn_left4);

        id_btn_right = (Button) v.findViewById(R.id.id_btn_right);
        id_btn_right2 = (Button) v.findViewById(R.id.id_btn_right2);
        id_btn_right3 = (Button) v.findViewById(R.id.id_btn_right3);
        id_btn_right4 = (Button) v.findViewById(R.id.id_btn_right4);

        id_tv_title = (TextView) v.findViewById(R.id.id_tv_title);
        id_tv_title_sub = (TextView) v.findViewById(R.id.id_tv_title_sub);

        id_btn_left.setOnClickListener(this);
        id_btn_left2.setOnClickListener(this);
        id_btn_left3.setOnClickListener(this);
        id_btn_left4.setOnClickListener(this);

        id_btn_right.setOnClickListener(this);
        id_btn_right2.setOnClickListener(this);
        id_btn_right3.setOnClickListener(this);
        id_btn_right4.setOnClickListener(this);

        //left
        hideView(id_btn_left);
        hideView(id_btn_left2);
        hideView(id_btn_left3);
        hideView(id_btn_left4);
        //right
        hideView(id_btn_right);
        hideView(id_btn_right2);
        hideView(id_btn_right3);
        hideView(id_btn_right4);
        //title
        hideView(id_tv_title_sub);
        hideView(id_edt_search_main);

        if (classX instanceof FTabThongBao) {
            //showView(id_btn_left);
            id_tv_title.setText(getResources().getString(R.string.tab_thongbao));
        } else if (classX instanceof FTabTraCuu) {
            //showView(id_btn_left);
            id_tv_title.setText(getResources().getString(R.string.tab_tracuu));
        } else if (classX instanceof FTabDichVu) {
            //showView(id_btn_left);
            id_tv_title.setText(getResources().getString(R.string.tab_dichvu));
        } else if (classX instanceof FTabCaiDat) {
            //showView(id_btn_left);
            id_tv_title.setText(getResources().getString(R.string.tab_caidat));
        } else if (classX instanceof FTabThongBaoList) {
            showView(id_btn_left);
            hideView(id_btn_right);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });

            //showView(id_btn_right);
            id_btn_right.setBackgroundResource(R.drawable.btn_delete);
        } else if (classX instanceof FTabThongBaoListDetail) {
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });

            showView(id_btn_right);
            id_btn_right.setBackgroundResource(R.drawable.btn_delete);
        } else if (classX instanceof FTabTraCuu0LichGhiNuoc) {
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
        } else if (classX instanceof FTabTraCuu1DiemThuTien) {
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
        } else if (classX instanceof FTabTraCuu3CSTieuThu) {
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
        } else if (classX instanceof FTabTraCuu4TToanTienNuoc) {
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
        } else if (classX instanceof FTabTraCuu5HDonTienNuoc) {
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
        } else if (classX instanceof FTabCDDichVu1ThayDoiThongTinKH) {
            showView(id_btn_left);
            showView(id_btn_right);
            id_btn_right.setBackgroundResource(android.R.drawable.edit_text);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            /* id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    fpActivity.onBackPressed();
                }
            });*/
        } else if (classX instanceof FTabDichVu0DKyCapNuoc) {
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
        } else if (classX instanceof FTabDichVu2NangDoiDo) {
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
        } else if (classX instanceof FTabDichVu3HoiTraLoi) {
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
        }else if(classX instanceof  FTabDichVu3HoiTraLoiDetail){
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
//        } else if (classX instanceof FTabCDThongTinChung) {
//            showView(id_btn_left);
//            id_btn_left.setBackgroundResource(R.drawable.btn_back);
//            id_btn_left.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    fpActivity.onBackPressed();
//                }
//            });
        } else if (classX instanceof FTabCDDoiMatKhau) {
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
        } else if (classX instanceof FTabTraCuu2LichCatNuoc) {
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
        }else if(classX instanceof  FTabTraCuu7LienKetNganHang){
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
        }else if(classX instanceof  FTabDichVuGCSHome){
            showView(id_tv_title_sub);
            showView(id_btn_left);
            showView(id_btn_right);
            id_btn_right.setBackgroundResource(R.drawable.gcs_icon_btn);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
        }else if(classX instanceof  FTabDichVuThanhToanOnline){
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
        }else if(classX instanceof FTabDichVuThanhToanQuaViDienTu){
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
        }else if(classX instanceof FTabDichVuThanhToanVNPAY){
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
        }else if(classX instanceof FTabDichVu4Hddt){
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
        }else if(classX instanceof FTabDichVuGCSHomeLoadMore){
            showView(id_btn_left);
            id_btn_left.setBackgroundResource(R.drawable.btn_back);
            id_btn_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    fpActivity.onBackPressed();
                }
            });
        }


    }

    protected void setEditTextListener(final CustomEditText edt, final Drawable left, final Drawable right) {

        if (edt.getText().toString().trim().length() > 0) {
            edt.setCompoundDrawables(left, null, right, null);
        }
        edt.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = edt.getText().toString().trim().length();
                if (length > 0) {
                    edt.setCompoundDrawables(left, null, right, null);
                } else {
                    edt.setCompoundDrawables(left, null, null, null);
                }
            }
        });
        edt.setDrawableClickListener(new DrawableClickListener() {

            public void onClick(DrawablePosition target) {
                switch (target) {
                    case LEFT:
                        break;
                    case RIGHT:
                        edt.setCompoundDrawables(left, null, null, null);
                        edt.setText("");
                        break;
                    case TOP:
                        break;
                    case BOTTOM:
                        break;
                    default:
                        break;
                }
            }

        });
    }

    private ProgressDialog dialogLoading;

    public void showLoading() {
        if (dialogLoading != null) {
            dialogLoading.dismiss();
        }
        if (dialogLoading == null) {
            dialogLoading = new ProgressDialog(fpActivity);
        }
        dialogLoading.setTitle(this.getResources().getString(R.string.app_name));
        dialogLoading.setMessage(getString(R.string.processing));
        dialogLoading.setCanceledOnTouchOutside(false);
        dialogLoading.setCancelable(false);
        dialogLoading.show();
    }

    public void disMissLoading() {

        if (dialogLoading != null) {
            dialogLoading.dismiss();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

}
