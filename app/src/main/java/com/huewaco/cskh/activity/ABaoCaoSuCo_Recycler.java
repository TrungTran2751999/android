package com.huewaco.cskh.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.huewaco.cskh.adapter.ListViewBaoCaoSuCoRecyclerAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.helper.ImageLoaderOnline;
import com.huewaco.cskh.objects.BaoCaoSuCo_Post;
import com.huewaco.cskh.objects.ImgInPostObj;
import com.huewaco.cskh.webservice.objects.GetAllBaoCaoSuCoResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;
import java.util.Collections;

public class ABaoCaoSuCo_Recycler extends AParent {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoaderOnline.getInstance().clearQueue();
    }
    enum TypeTOP {
        TIEUBIEU,
        DAXULY,
        DANGXULY
    }
    enum TypeBOTTOM {
        CANHAN,
        CONGDONG
    }
    enum TypeLoad{
        FIRST,
        NEW,
        OLD
    }
    class TypeObjec{
        TypeTOP typeTop;
        TypeBOTTOM typeBOTTOM;

        public TypeTOP getTypeTop() {
            return typeTop;
        }

        public void setTypeTop(TypeTOP typeTop) {
            this.typeTop = typeTop;
        }

        public TypeBOTTOM getTypeBOTTOM() {
            return typeBOTTOM;
        }

        public void setTypeBOTTOM(TypeBOTTOM typeBOTTOM) {
            this.typeBOTTOM = typeBOTTOM;
        }
    }
    private ArrayList<TypeObjec> mArrTypeObj = new ArrayList<>();

    private boolean findTypeObjInArrayObj(TypeObjec obj){
            if(mArrTypeObj != null && mArrTypeObj.size()>0){
                for(TypeObjec type : mArrTypeObj){
                    if(type.getTypeTop() == obj.getTypeTop() && type.getTypeBOTTOM() == obj.getTypeBOTTOM()){

                         return true;
                    }
                }
            }

        return false;
    }

    private boolean isLoadingMoreNew = false;
    private boolean isLoadingMoreOld = false;
    //ViewGroup header, footer;
    private ViewGroup id_fr_bottombar,id_ly_bottom_bar_add;

    private Button id_btn_congdong,id_btn_canhan;
    private ImageView id_img_congdong, id_img_canhan;
    private ViewGroup id_ly_canhan, id_ly_congdong;

    private Button  id_btn_tieubieu, id_btn_daxuly, id_btn_dangxuly;

    private RecyclerView id_rcl_list;

    private ImageView id_img_addsuco;
    private ArrayList<BaoCaoSuCo_Post> mArrBaoCaoSuCoPostsAll = new ArrayList<>();
    private ArrayList<BaoCaoSuCo_Post> mArrBaoCaoSuCoPostsShow = new ArrayList<>();
    private ListViewBaoCaoSuCoRecyclerAdapter adapter;

    private ViewGroup id_ly_tieubieu,id_ly_daxuly,id_ly_dangxuly;
    private ImageView id_img_tieubieu,id_img_daxuly,id_img_dangxuly;
    private TypeTOP WHAT_TYPE_TOP = TypeTOP.DAXULY;
    private TypeBOTTOM WHAT_TYPE_BOTTOM = TypeBOTTOM.CONGDONG;

    private LinearLayoutManager linearLayoutManager;

    private SwipeRefreshLayout id_swp_list;
    private boolean isLoadFirstCompleted = false;
    private int totalItemCount,lastVisibleItem,visibleThreshold=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baocaosuco_recycler);
        initTopbarView();
        initCommonView(this);
        initComponent();
        addListener();



    }
     void  showDataType(){
        new FilterDataTask().execute();
    }
    @Override
    protected void initComponent() {


        id_swp_list = (SwipeRefreshLayout)findViewById(R.id.id_swp_list);

        id_fr_bottombar= (ViewGroup)findViewById(R.id.id_fr_bottombar);
        id_ly_bottom_bar_add = (ViewGroup)findViewById(R.id.id_ly_bottom_bar_add);

        id_img_congdong = (ImageView)findViewById(R.id.id_img_congdong);
        id_img_canhan = (ImageView)findViewById(R.id.id_img_canhan);

        id_ly_congdong = (ViewGroup)findViewById(R.id.id_ly_congdong);
        id_ly_canhan = (ViewGroup)findViewById(R.id.id_ly_canhan);

        id_img_tieubieu = (ImageView)findViewById(R.id.id_img_tieubieu);
        id_img_daxuly = (ImageView)findViewById(R.id.id_img_daxuly);
        id_img_dangxuly = (ImageView)findViewById(R.id.id_img_dangxuly);

        id_ly_tieubieu = (ViewGroup)findViewById(R.id.id_ly_tieubieu);
        id_ly_daxuly = (ViewGroup)findViewById(R.id.id_ly_daxuly);
        id_ly_dangxuly = (ViewGroup)findViewById(R.id.id_ly_dangxuly);


        id_btn_tieubieu = (Button)findViewById(R.id.id_btn_tieubieu);
        id_btn_daxuly = (Button)findViewById(R.id.id_btn_daxuly);
        id_btn_congdong = (Button)findViewById(R.id.id_btn_congdong);
        id_btn_canhan = (Button)findViewById(R.id.id_btn_canhan);
        id_btn_dangxuly = (Button)findViewById(R.id.id_btn_dangxuly);
        id_img_addsuco = (ImageView)findViewById(R.id.id_img_addsuco);

        id_rcl_list = (RecyclerView) findViewById(R.id.id_rcl_list);
        linearLayoutManager = new LinearLayoutManager(this);
        id_rcl_list.setLayoutManager(linearLayoutManager);
        //linearLayoutManager.setAutoMeasureEnabled(true);

        id_tv_title.setText(getString(R.string.tab_dichvu_bcaosuco));
        id_btn_left.setBackgroundResource(R.drawable.btn_back);
        adapter = new ListViewBaoCaoSuCoRecyclerAdapter(this, mArrBaoCaoSuCoPostsShow, ABaoCaoSuCo_Recycler.this);
        id_rcl_list.setAdapter(adapter);
        id_rcl_list.setHasFixedSize(true);
        id_rcl_list.setItemViewCacheSize(20);
        id_rcl_list.setDrawingCacheEnabled(true);
        id_rcl_list.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

    }

    //private static int firstVisibleInListview;

    @Override
    protected void addListener() {
        id_swp_list.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!isLoadingMoreNew && isLoadFirstCompleted){

                                new LoadMoreDataNewTask().execute();
                            }

            }
        });
        id_rcl_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }
                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        totalItemCount = linearLayoutManager.getItemCount();
                        lastVisibleItem = linearLayoutManager
                                .findLastVisibleItemPosition();

                        TypeObjec newCurrentTypeObj = new TypeObjec();
                        newCurrentTypeObj.setTypeBOTTOM(WHAT_TYPE_BOTTOM);
                        newCurrentTypeObj.setTypeTop(WHAT_TYPE_TOP);
                        if(!findTypeObjInArrayObj(newCurrentTypeObj)){

                            if (isLoadFirstCompleted && (!isLoadingMoreOld) && totalItemCount <= (lastVisibleItem + visibleThreshold)) {


                                new LoadMoreDataOldTask().execute();
                            }
                        }
                        //start show/hide bottom bar
                        if (dy > 0) {
                            if(mArrBaoCaoSuCoPostsShow.size() > 4){
                                id_fr_bottombar.setVisibility(View.GONE);
                                id_ly_bottom_bar_add.setVisibility(View.GONE);
                            }else{
                                id_fr_bottombar.setVisibility(View.VISIBLE);
                                id_ly_bottom_bar_add.setVisibility(View.VISIBLE);
                            }

                        } else if (dy < 0) {
                            id_fr_bottombar.setVisibility(View.VISIBLE);
                            id_ly_bottom_bar_add.setVisibility(View.VISIBLE);
                        } else {

                        }
                        //end show/hide bottom bar
                    }
        });

        id_ly_congdong.setOnClickListener(this);
        id_ly_canhan.setOnClickListener(this);

        id_img_congdong.setOnClickListener(this);
        id_img_canhan.setOnClickListener(this);

        id_btn_congdong.setOnClickListener(this);
        id_btn_canhan.setOnClickListener(this);

        id_btn_tieubieu.setOnClickListener(this);
        id_btn_daxuly.setOnClickListener(this);
        id_btn_dangxuly.setOnClickListener(this);

        id_img_addsuco.setOnClickListener(this);

        id_ly_tieubieu.setOnClickListener(this);
        id_ly_daxuly.setOnClickListener(this);
        id_ly_dangxuly.setOnClickListener(this);

        new LoadDataFirstTask().execute();
    }
    private void setStateFirstLaunching_UI(TypeTOP i, TypeBOTTOM j){
        if(j == TypeBOTTOM.CONGDONG){
            id_img_canhan.setImageResource(R.drawable.canhan_ic);
            id_btn_canhan.setTextColor(getApplication().getResources().getColor(R.color.black));
            id_img_congdong.setImageResource(R.drawable.congdong_ic_on);
            id_btn_congdong.setTextColor(getApplication().getResources().getColor(R.color.red));
        }else if(j == TypeBOTTOM.CANHAN){
            id_img_canhan.setImageResource(R.drawable.canhan_ic_on);
            id_btn_canhan.setTextColor(getApplication().getResources().getColor(R.color.red));
            id_img_congdong.setImageResource(R.drawable.congdong_ic);
            id_btn_congdong.setTextColor(getApplication().getResources().getColor(R.color.black));
        }
        //
        if(i == TypeTOP.TIEUBIEU){
            id_img_tieubieu.setImageResource(R.drawable.tieudiem_ic_on);
            id_btn_tieubieu.setTextColor(getApplication().getResources().getColor(R.color.white));
            id_ly_tieubieu.setSelected(true);
            id_img_daxuly.setImageResource(R.drawable.daxuly_ic);
            id_btn_daxuly.setTextColor(getApplication().getResources().getColor(R.color.black));
            id_ly_daxuly.setSelected(false);
            id_img_dangxuly.setImageResource(R.drawable.dangxuly_ic);
            id_btn_dangxuly.setTextColor(getApplication().getResources().getColor(R.color.black));
            id_ly_dangxuly.setSelected(false);
        }else if(i == TypeTOP.DAXULY){
            id_img_tieubieu.setImageResource(R.drawable.tieudiem_ic);
            id_btn_tieubieu.setTextColor(getApplication().getResources().getColor(R.color.black));
            id_ly_tieubieu.setSelected(false);
            id_img_daxuly.setImageResource(R.drawable.daxuly_ic_on);
            id_btn_daxuly.setTextColor(getApplication().getResources().getColor(R.color.white));
            id_ly_daxuly.setSelected(true);
            id_img_dangxuly.setImageResource(R.drawable.dangxuly_ic);
            id_btn_dangxuly.setTextColor(getApplication().getResources().getColor(R.color.black));
            id_ly_dangxuly.setSelected(false);
        }else if(i == TypeTOP.DANGXULY){
            id_img_tieubieu.setImageResource(R.drawable.tieudiem_ic);
            id_btn_tieubieu.setTextColor(getApplication().getResources().getColor(R.color.black));
            id_ly_tieubieu.setSelected(false);
            id_img_daxuly.setImageResource(R.drawable.daxuly_ic);
            id_btn_daxuly.setTextColor(getApplication().getResources().getColor(R.color.black));
            id_ly_daxuly.setSelected(false);
            id_img_dangxuly.setImageResource(R.drawable.dangxuly_ic_on);
            id_btn_dangxuly.setTextColor(getApplication().getResources().getColor(R.color.white));
            id_ly_dangxuly.setSelected(true);

        }
        showDataType();

    }
    private void setStateUIBottom(TypeBOTTOM i){
        WHAT_TYPE_BOTTOM = i;
        if(i == TypeBOTTOM.CONGDONG){
            id_img_canhan.setImageResource(R.drawable.canhan_ic);
            id_btn_canhan.setTextColor(getApplication().getResources().getColor(R.color.black));
            id_img_congdong.setImageResource(R.drawable.congdong_ic_on);
            id_btn_congdong.setTextColor(getApplication().getResources().getColor(R.color.red));
        }else if(i == TypeBOTTOM.CANHAN){
            id_img_canhan.setImageResource(R.drawable.canhan_ic_on);
            id_btn_canhan.setTextColor(getApplication().getResources().getColor(R.color.red));
            id_img_congdong.setImageResource(R.drawable.congdong_ic);
            id_btn_congdong.setTextColor(getApplication().getResources().getColor(R.color.black));
        }
        showDataType();

    }

    private void setStateUITOP(TypeTOP i){
        WHAT_TYPE_TOP = i;
        if(i == TypeTOP.TIEUBIEU){
            id_img_tieubieu.setImageResource(R.drawable.tieudiem_ic_on);
            id_btn_tieubieu.setTextColor(getApplication().getResources().getColor(R.color.white));
            id_ly_tieubieu.setSelected(true);
            id_img_daxuly.setImageResource(R.drawable.daxuly_ic);
            id_btn_daxuly.setTextColor(getApplication().getResources().getColor(R.color.black));
            id_ly_daxuly.setSelected(false);
            id_img_dangxuly.setImageResource(R.drawable.dangxuly_ic);
            id_btn_dangxuly.setTextColor(getApplication().getResources().getColor(R.color.black));
            id_ly_dangxuly.setSelected(false);
        }else if(i == TypeTOP.DAXULY){
            id_img_tieubieu.setImageResource(R.drawable.tieudiem_ic);
            id_btn_tieubieu.setTextColor(getApplication().getResources().getColor(R.color.black));
            id_ly_tieubieu.setSelected(false);
            id_img_daxuly.setImageResource(R.drawable.daxuly_ic_on);
            id_btn_daxuly.setTextColor(getApplication().getResources().getColor(R.color.white));
            id_ly_daxuly.setSelected(true);
            id_img_dangxuly.setImageResource(R.drawable.dangxuly_ic);
            id_btn_dangxuly.setTextColor(getApplication().getResources().getColor(R.color.black));
            id_ly_dangxuly.setSelected(false);
        }else if(i == TypeTOP.DANGXULY){
            id_img_tieubieu.setImageResource(R.drawable.tieudiem_ic);
            id_btn_tieubieu.setTextColor(getApplication().getResources().getColor(R.color.black));
            id_ly_tieubieu.setSelected(false);
            id_img_daxuly.setImageResource(R.drawable.daxuly_ic);
            id_btn_daxuly.setTextColor(getApplication().getResources().getColor(R.color.black));
            id_ly_daxuly.setSelected(false);
            id_img_dangxuly.setImageResource(R.drawable.dangxuly_ic_on);
            id_btn_dangxuly.setTextColor(getApplication().getResources().getColor(R.color.white));
            id_ly_dangxuly.setSelected(true);

        }
        showDataType();

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_right:
                startActivity(new Intent(this,ABaoCaoSuCo_RecyclerSearch.class));
                break;
            case R.id.id_ly_canhan:
                if(WHAT_TYPE_BOTTOM != TypeBOTTOM.CANHAN) {
                    setStateUIBottom(TypeBOTTOM.CANHAN);
                }
                break;
            case R.id.id_img_canhan:
                id_ly_canhan.performClick();
                break;
            case R.id.id_btn_canhan:
                id_ly_canhan.performClick();
                break;
            case R.id.id_ly_congdong:
                if(WHAT_TYPE_BOTTOM != TypeBOTTOM.CONGDONG){
                    setStateUIBottom(TypeBOTTOM.CONGDONG);
                }

                break;
            case R.id.id_img_congdong:
                id_ly_congdong.performClick();
                break;
            case R.id.id_btn_congdong:
                id_ly_congdong.performClick();
                break;
            case R.id.id_ly_tieubieu:
                if(WHAT_TYPE_TOP != TypeTOP.TIEUBIEU)
                    setStateUITOP(TypeTOP.TIEUBIEU);
                break;
            case R.id.id_btn_tieubieu:
                id_ly_tieubieu.performClick();
                break;
            case R.id.id_ly_daxuly:
                if(WHAT_TYPE_TOP != TypeTOP.DAXULY)
                    setStateUITOP(TypeTOP.DAXULY);
                break;
            case R.id.id_btn_daxuly:
                id_ly_daxuly.performClick();
                break;
            case R.id.id_ly_dangxuly:
                if(WHAT_TYPE_TOP != TypeTOP.DANGXULY)
                    setStateUITOP(TypeTOP.DANGXULY);
                break;
            case R.id.id_btn_dangxuly:
                id_ly_dangxuly.performClick();
                break;
            case R.id.id_img_addsuco:
                startActivity(new Intent(this, ABaoCaoSuCo.class));
                break;
            default:
                break;
        }
    }
    long findIdWithCondition(boolean isLatest){
        ArrayList<BaoCaoSuCo_Post> newArrayWorking;
        if(isLatest){
            ArrayList<BaoCaoSuCo_Post> newArrayASC = new ArrayList<>();
            newArrayASC.addAll(mArrBaoCaoSuCoPostsAll);
            newArrayWorking = newArrayASC;
        }else{
            //oldest
            ArrayList<BaoCaoSuCo_Post> newArrayDESC = new ArrayList<>();
            newArrayDESC.addAll(mArrBaoCaoSuCoPostsAll);
            Collections.reverse(newArrayDESC);
            newArrayWorking = newArrayDESC;
        }

        long id = -1;
        if(WHAT_TYPE_TOP == TypeTOP.DAXULY){
            for(BaoCaoSuCo_Post post : newArrayWorking){
                if(post.isDaXuLy()){
                    if(WHAT_TYPE_BOTTOM == TypeBOTTOM.CANHAN){
                        if(GlobalVariable.KHACH_HANG!=null && CommonHelper.checkValidString(GlobalVariable.KHACH_HANG.getMa_khang())){
                            if(post.getIdKH().equalsIgnoreCase(GlobalVariable.KHACH_HANG.getMa_khang())){
                                return post.getId();
                            }
                        }
                    }else{
                        return post.getId();
                    }
                }
            }
        }else if(WHAT_TYPE_TOP == TypeTOP.DANGXULY){
            for(BaoCaoSuCo_Post post : newArrayWorking){
                if(!post.isDaXuLy()){
                    if(WHAT_TYPE_BOTTOM == TypeBOTTOM.CANHAN){
                        if(GlobalVariable.KHACH_HANG!=null && CommonHelper.checkValidString(GlobalVariable.KHACH_HANG.getMa_khang())){
                            if(post.getIdKH().equalsIgnoreCase(GlobalVariable.KHACH_HANG.getMa_khang())){
                                return post.getId();
                            }
                        }
                    }else{
                        return post.getId();
                    }
                }
            }
        }else if(WHAT_TYPE_TOP == TypeTOP.TIEUBIEU){
            for(BaoCaoSuCo_Post post : newArrayWorking){
                if(post.isLaTieuBieu()){
                    if(WHAT_TYPE_BOTTOM == TypeBOTTOM.CANHAN){
                        if(GlobalVariable.KHACH_HANG!=null && CommonHelper.checkValidString(GlobalVariable.KHACH_HANG.getMa_khang())){
                            if(post.getIdKH().equalsIgnoreCase(GlobalVariable.KHACH_HANG.getMa_khang())){
                                return post.getId();
                            }
                        }
                    }else{
                        return post.getId();
                    }
                }
            }
        }


        return id;
    }
    /**
     * type: 1 => the first load
     * type: 2 => load new
     * type: 3 => load old
     * */
    private void initDataWS(TypeLoad typeLoad){
        String add_filter = "&DaKiemTra=1";
        if(WHAT_TYPE_TOP == TypeTOP.DAXULY){
            add_filter += "&DaXuLy=true";
        }else if(WHAT_TYPE_TOP == TypeTOP.DANGXULY){
            add_filter += "&DaXuLy=false";
        }else if(WHAT_TYPE_TOP == TypeTOP.TIEUBIEU){
            add_filter += "&LaTieuBieu=true";
        }

        if(WHAT_TYPE_BOTTOM == TypeBOTTOM.CANHAN){
            if(GlobalVariable.KHACH_HANG != null){
                if(CommonHelper.checkValidString(GlobalVariable.KHACH_HANG.getMa_khang())){
                    add_filter+="&IdKH="+GlobalVariable.KHACH_HANG.getMa_khang();
                }
            }

        }

        if(typeLoad == TypeLoad.FIRST){
            GetAllBaoCaoSuCoResponse getAllBaoCaoSuCoResponse = new ResultFromWebservice().get10FirstBaoCaoSuCoResponsePOST(getApplicationContext(),0,add_filter);

            ArrayList<BaoCaoSuCo_Post> mArrNew = getAllBaoCaoSuCoResponse.getmArrItem();
            //check if not existing => add
            ArrayList<Long> mArrIds = new ArrayList<>();
            for(BaoCaoSuCo_Post p : mArrBaoCaoSuCoPostsAll){
                mArrIds.add(p.getId());
            }
            for(BaoCaoSuCo_Post post : mArrNew){
                if(!mArrIds.contains(post.getId())){
                    mArrBaoCaoSuCoPostsAll.add(post);
                }
            }
            //check if not existing => add
            Collections.sort(mArrBaoCaoSuCoPostsAll,BaoCaoSuCo_Post.sort_baoCaoSuCo_Post_DESC);
        }else if(typeLoad == TypeLoad.NEW){

            if(mArrBaoCaoSuCoPostsAll != null && mArrBaoCaoSuCoPostsAll.size()>0){
                long LatestId = findIdWithCondition(true);
                if(LatestId != -1){
                    GetAllBaoCaoSuCoResponse getNewBaoCaoSuCoResponse = new ResultFromWebservice().get10NewBaoCaoSuCoResponsePOST(getApplicationContext(),LatestId,add_filter);
                    ArrayList<BaoCaoSuCo_Post> mArrNew = getNewBaoCaoSuCoResponse.getmArrItem();
                    if(mArrNew != null && mArrNew.size() >0){
                        //check if not existing => add
                        ArrayList<Long> mArrIds = new ArrayList<>();
                        for(BaoCaoSuCo_Post p : mArrBaoCaoSuCoPostsAll){
                            mArrIds.add(p.getId());
                        }
                        for(BaoCaoSuCo_Post post : mArrNew){
                            if(!mArrIds.contains(post.getId())){
                                mArrBaoCaoSuCoPostsAll.add(post);
                            }
                        }
                        //check if not existing => add
                    }

                    Collections.sort(mArrBaoCaoSuCoPostsAll,BaoCaoSuCo_Post.sort_baoCaoSuCo_Post_DESC);
                }else{
                    GetAllBaoCaoSuCoResponse getNewBaoCaoSuCoResponse = new ResultFromWebservice().get10FirstBaoCaoSuCoResponsePOST(getApplicationContext(),0,add_filter);
                    ArrayList<BaoCaoSuCo_Post> mArrNew = getNewBaoCaoSuCoResponse.getmArrItem();
                    if(mArrNew != null && mArrNew.size() >0){
                        //check if not existing => add
                        ArrayList<Long> mArrIds = new ArrayList<>();
                        for(BaoCaoSuCo_Post p : mArrBaoCaoSuCoPostsAll){
                            mArrIds.add(p.getId());
                        }
                        for(BaoCaoSuCo_Post post : mArrNew){
                            if(!mArrIds.contains(post.getId())){
                                mArrBaoCaoSuCoPostsAll.add(post);
                            }
                        }
                        //check if not existing => add
                    }

                    Collections.sort(mArrBaoCaoSuCoPostsAll,BaoCaoSuCo_Post.sort_baoCaoSuCo_Post_DESC);
                }
            }
        }else if(typeLoad == TypeLoad.OLD){
            if(mArrBaoCaoSuCoPostsAll != null && mArrBaoCaoSuCoPostsAll.size()>0){

                long OldestId = findIdWithCondition(false);
                if(OldestId != -1){
                    GetAllBaoCaoSuCoResponse getOldBaoCaoSuCoResponse = new ResultFromWebservice().get10OldBaoCaoSuCoResponsePOST(getApplicationContext(),OldestId,add_filter);
                    ArrayList<BaoCaoSuCo_Post> mArrOld = getOldBaoCaoSuCoResponse.getmArrItem();

                    if(mArrOld != null && mArrOld.size() >0){
                        //check if not existing => add
                        ArrayList<Long> mArrIds = new ArrayList<>();
                        for(BaoCaoSuCo_Post p : mArrBaoCaoSuCoPostsAll){
                            mArrIds.add(p.getId());
                        }
                        for(BaoCaoSuCo_Post post : mArrOld){
                            if(!mArrIds.contains(post.getId())){
                                mArrBaoCaoSuCoPostsAll.add(post);
                            }
                        }
                        //check if not existing => add
                    }else{
                        //if not found => deny request
                        TypeObjec newCurrentTypeObj = new TypeObjec();
                        newCurrentTypeObj.setTypeBOTTOM(WHAT_TYPE_BOTTOM);
                        newCurrentTypeObj.setTypeTop(WHAT_TYPE_TOP);
                        if(!findTypeObjInArrayObj(newCurrentTypeObj)){
                            mArrTypeObj.add(newCurrentTypeObj);
                        }

                    }

                    Collections.sort(mArrBaoCaoSuCoPostsAll,BaoCaoSuCo_Post.sort_baoCaoSuCo_Post_DESC);
                }else{
                    GetAllBaoCaoSuCoResponse getOldBaoCaoSuCoResponse = new ResultFromWebservice().get10FirstBaoCaoSuCoResponsePOST(getApplicationContext(),0,add_filter);
                    ArrayList<BaoCaoSuCo_Post> mArrOld = getOldBaoCaoSuCoResponse.getmArrItem();

                    if(mArrOld != null && mArrOld.size() >0){
                        //check if not existing => add
                        ArrayList<Long> mArrIds = new ArrayList<>();
                        for(BaoCaoSuCo_Post p : mArrBaoCaoSuCoPostsAll){
                            mArrIds.add(p.getId());
                        }
                        for(BaoCaoSuCo_Post post : mArrOld){
                            if(!mArrIds.contains(post.getId())){
                                mArrBaoCaoSuCoPostsAll.add(post);
                            }
                        }
                        //check if not existing => add
                    }else{
                        TypeObjec newCurrentTypeObj = new TypeObjec();
                        newCurrentTypeObj.setTypeBOTTOM(WHAT_TYPE_BOTTOM);
                        newCurrentTypeObj.setTypeTop(WHAT_TYPE_TOP);
                        if(!findTypeObjInArrayObj(newCurrentTypeObj)){
                            mArrTypeObj.add(newCurrentTypeObj);
                        }

                    }


                    Collections.sort(mArrBaoCaoSuCoPostsAll,BaoCaoSuCo_Post.sort_baoCaoSuCo_Post_DESC);
                }

            }
        }

    }
    private void initData(){
        mArrBaoCaoSuCoPostsAll.clear();
        mArrBaoCaoSuCoPostsShow.clear();

        for(int i = 0; i< 20; i++){
            BaoCaoSuCo_Post post1 = new BaoCaoSuCo_Post();

            post1.setId(i);
            if(i%3==0){
                if(GlobalVariable.KHACH_HANG != null) {
                    post1.setIdKH(GlobalVariable.KHACH_HANG.getIdKh());
                }else{
                    post1.setIdKH("khno"+i);
                }
            }else{
                post1.setIdKH("khno"+i);
            }

            post1.setTenKhachHang("Nguyễn Văn Bình P"+i);
            post1.setSoDienThoai("123456789"+i);
            post1.setDiaChiKhachHang("Số "+i + " Bùi thị Xuân - TP. Huế - TTH.");
            post1.setNoiDung("Nội dung test thứ "+i+" - Từ một đơn vị chỉ thực hiện cấp nước đô thị, phạm vi cấp nước hẹp, chủ yếu cấp nước cho Thành phố Huế, trải qua 105 năm xây dựng và phát triển, đặc biệt trong 20 năm trở lại đây HueWACO đã có những nỗ lực vượt bậc với những nhóm giải pháp đồng bộ được thực hiện, hàng trăm sáng kiến cải tiến kỹ thuật và quản trị doanh nghiệp được nghiên cứu, triển khai, cùng với chính sách quản lý đầu tư hiệu quả; đến năm 2013 đã đưa công suất cấp nước toàn hệ thống tăng 3,6 lần (từ 50.000 lên 180.000 m3/ngày đêm), nước ghi thu tăng 6,9 lần (từ 5,8 lên 40,0 triệu m3), khách hàng chính tăng 23 lần (từ 8.400 lên 193.000) so với năm 1993; cấp nước an toàn, bền vững cho hơn 830.000 người, đạt tỷ lệ 73,5% dân số toàn tỉnh, trong đó khu vực đô thị đạt 90,6% và nông thôn là 60,1%.\\n\" +\n" +
                    "                    \"Với việc mở rộng phạm vi cấp nước, quy mô tài sản của HueWACO tăng nhanh, năm 2013 đạt 647,6 tỷ đồng, tăng 17 lần so với 20 năm trước (năm 1993 là 38,1 tỷ), trong khi chi phí khấu hao vẫn duy trì ở mức thấp, khoảng 23,5 tỷ đồng/năm (5% tổng giá trị tài sản), không đáp ứng nhu cầu đầu tư; trong điều kiện thiếu vốn, ngân sách cấp hạn chế, ngoài việc sử dụng nguồn khấu hao cơ bản HueWACO phải vay thương mại trên 150 tỷ đồng với lãi suất cao, ứng trước tiền nước của khách hàng có dự án đầu tư mới (trên 50 tỷ) để đầu tư, phát triển cấp nước, chủ yếu là đầu tư cho khu vực nông thôn.");
            post1.setNgayTao("2019-11-21T10:51:35.27"+i);
            post1.setDiaChiSuCoTheoMobile("117 bao vinh, Huong vinh, Huong Tra, TT Hue");
            post1.setDiaChiSuCoKhachHangChon("117 bao vinh, Huong vinh, Huong Tra, TT Hue");
            post1.setDiaChiDaXacNhan("117 bao vinh, Huong vinh, Huong Tra, TT Hue");
            post1.setKinhDoTheoMobile(108.0802100);
            post1.setViDoTheoMobile(16.2377700);
            post1.setKinhDoKhachHangChon(108.0802100);
            post1.setViDoKhachHangChon(16.2377700);
            post1.setDaKiemTra(i%2);
            post1.setDaXuLy(i%2==0?true:false);
            post1.setLaTieuBieu(i%3==1?true:false);
            ArrayList<ImgInPostObj> mArrImgs = new ArrayList<>();
            for(int j = 0; j <3; j++){
                ImgInPostObj imgP = new ImgInPostObj();
                imgP.setId(j);
                if(i%2 == 0 ){
                   if(j ==0){
                       imgP.setTenFile("207141_20191121105135.271_1.png");
                   }else if(j ==1){
                       imgP.setTenFile("207141_20191121105135.271_2.png");
                   }else if(j ==2){
                       imgP.setTenFile("207141_20191121105135.271_3.png");
                   }
                }else{
                    if(j ==0){
                        imgP.setTenFile("207141_20191121105135.271_2.png");
                    }else if(j ==1){

                        imgP.setTenFile("207141_20191121105135.271_1.png");
                    }else if(j ==2){
                        imgP.setTenFile("207141_20191121105135.271_3.png");
                    }
                }

                imgP.setIdBaoCaoSuCo(i);
                imgP.setBase64(getString(R.string.test_base64));
                mArrImgs.add(imgP);
            }
            post1.setImages(mArrImgs);

            mArrBaoCaoSuCoPostsAll.add(post1);

        }
    }
    private ArrayList<BaoCaoSuCo_Post> filterData(){
        ArrayList<BaoCaoSuCo_Post> mArrReturn = new ArrayList<>();
//Filter Top
        ArrayList<BaoCaoSuCo_Post> mArCommonTopFilter1 = new ArrayList<>();
        ArrayList<BaoCaoSuCo_Post> mArCommonBottomFilter2 = new ArrayList<>();
        if(WHAT_TYPE_TOP == TypeTOP.TIEUBIEU){
            for(BaoCaoSuCo_Post post : mArrBaoCaoSuCoPostsAll){
                if(post.isLaTieuBieu()){
                    mArCommonTopFilter1.add(post);
                }
            }
        }else if(WHAT_TYPE_TOP == TypeTOP.DAXULY){
            for(BaoCaoSuCo_Post post : mArrBaoCaoSuCoPostsAll){
                if(post.isDaXuLy()){
                    mArCommonTopFilter1.add(post);
                }
            }
        }else if(WHAT_TYPE_TOP == TypeTOP.DANGXULY){
            for(BaoCaoSuCo_Post post : mArrBaoCaoSuCoPostsAll){
                if(!post.isDaXuLy()){
                    mArCommonTopFilter1.add(post);
                }
            }
        }
//Filter Bottom
        if(WHAT_TYPE_BOTTOM == TypeBOTTOM.CANHAN){
            for(BaoCaoSuCo_Post post : mArCommonTopFilter1){
                if(GlobalVariable.KHACH_HANG!=null) {
                    if (post.getIdKH().equalsIgnoreCase(GlobalVariable.KHACH_HANG.getMa_khang())) {
                        mArCommonBottomFilter2.add(post);
                    }
                }
            }
            mArrReturn = mArCommonBottomFilter2;
        }else if(WHAT_TYPE_BOTTOM == TypeBOTTOM.CONGDONG){
            mArrReturn = mArCommonTopFilter1;
        }
        return mArrReturn;
    }
    private void addMoreData(){
        for(int i = 0; i< 2; i++){
            BaoCaoSuCo_Post post1 = new BaoCaoSuCo_Post();

            post1.setId(i);
            if(i%3==0){
                if(GlobalVariable.KHACH_HANG != null) {
                    post1.setIdKH(GlobalVariable.KHACH_HANG.getIdKh());
                }else{
                    post1.setIdKH("khno"+i);
                }
            }else{
                post1.setIdKH("khno"+i);
            }

            post1.setTenKhachHang("Nguyễn Văn Bình P"+i);
            post1.setSoDienThoai("123456789"+i);
            post1.setDiaChiKhachHang("Số "+i + " Bùi thị Xuân - TP. Huế - TTH.");
            post1.setNoiDung("Nội dung test thứ "+i+" - Từ một đơn vị chỉ thực hiện cấp nước đô thị, phạm vi cấp nước hẹp, chủ yếu cấp nước cho Thành phố Huế, trải qua 105 năm xây dựng và phát triển, đặc biệt trong 20 năm trở lại đây HueWACO đã có những nỗ lực vượt bậc với những nhóm giải pháp đồng bộ được thực hiện, hàng trăm sáng kiến cải tiến kỹ thuật và quản trị doanh nghiệp được nghiên cứu, triển khai, cùng với chính sách quản lý đầu tư hiệu quả; đến năm 2013 đã đưa công suất cấp nước toàn hệ thống tăng 3,6 lần (từ 50.000 lên 180.000 m3/ngày đêm), nước ghi thu tăng 6,9 lần (từ 5,8 lên 40,0 triệu m3), khách hàng chính tăng 23 lần (từ 8.400 lên 193.000) so với năm 1993; cấp nước an toàn, bền vững cho hơn 830.000 người, đạt tỷ lệ 73,5% dân số toàn tỉnh, trong đó khu vực đô thị đạt 90,6% và nông thôn là 60,1%.\\n\" +\n" +
                    "                    \"Với việc mở rộng phạm vi cấp nước, quy mô tài sản của HueWACO tăng nhanh, năm 2013 đạt 647,6 tỷ đồng, tăng 17 lần so với 20 năm trước (năm 1993 là 38,1 tỷ), trong khi chi phí khấu hao vẫn duy trì ở mức thấp, khoảng 23,5 tỷ đồng/năm (5% tổng giá trị tài sản), không đáp ứng nhu cầu đầu tư; trong điều kiện thiếu vốn, ngân sách cấp hạn chế, ngoài việc sử dụng nguồn khấu hao cơ bản HueWACO phải vay thương mại trên 150 tỷ đồng với lãi suất cao, ứng trước tiền nước của khách hàng có dự án đầu tư mới (trên 50 tỷ) để đầu tư, phát triển cấp nước, chủ yếu là đầu tư cho khu vực nông thôn.");
            post1.setNgayTao("2019-11-21T10:51:35.27"+i);
            post1.setDiaChiSuCoTheoMobile("117 bao vinh, Huong vinh, Huong Tra, TT Hue");
            post1.setDiaChiSuCoKhachHangChon("117 bao vinh, Huong vinh, Huong Tra, TT Hue");
            post1.setDiaChiDaXacNhan("117 bao vinh, Huong vinh, Huong Tra, TT Hue");
            post1.setKinhDoTheoMobile(108.0802100);
            post1.setViDoTheoMobile(16.2377700);
            post1.setKinhDoKhachHangChon(108.0802100);
            post1.setViDoKhachHangChon(16.2377700);
            post1.setDaKiemTra(i%2);
            post1.setDaXuLy(i%2==0?true:false);
            post1.setLaTieuBieu(i%3==1?true:false);
            ArrayList<ImgInPostObj> mArrImgs = new ArrayList<>();
            for(int j = 0; j <3; j++){
                ImgInPostObj imgP = new ImgInPostObj();
                imgP.setId(j);
                imgP.setTenFile("207141_20191121105135.271_2.png");
                imgP.setIdBaoCaoSuCo(i);
                imgP.setBase64(getString(R.string.test_base64));
                mArrImgs.add(imgP);
            }
            post1.setImages(mArrImgs);

            mArrBaoCaoSuCoPostsAll.add(post1);
            Collections.sort(mArrBaoCaoSuCoPostsAll,BaoCaoSuCo_Post.sort_baoCaoSuCo_Post_DESC);

        }

    }
    public class LoadDataFirstTask extends AsyncTask<String, Void, ArrayList<BaoCaoSuCo_Post>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        public ArrayList<BaoCaoSuCo_Post> doInBackground(String... params) {
//            initData();
            initDataWS(TypeLoad.FIRST);
            /*
            try {
                Thread.currentThread();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
            return null;
        }

        @Override
        public void onPostExecute(ArrayList<BaoCaoSuCo_Post> result) {
            setStateFirstLaunching_UI(WHAT_TYPE_TOP,WHAT_TYPE_BOTTOM);
            isLoadFirstCompleted = true;
//            firstVisibleInListview = linearLayoutManager.findFirstVisibleItemPosition();
            disMissLoading();
        }
    }
    public class LoadMoreDataNewTask extends AsyncTask<String, Void, ArrayList<BaoCaoSuCo_Post>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoadingMoreNew = true;
        }

        @Override
        public ArrayList<BaoCaoSuCo_Post> doInBackground(String... params) {
            //addMoreData();
            initDataWS(TypeLoad.NEW);
//            try {
//                Thread.currentThread();
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            return filterData();
        }

        @Override
        public void onPostExecute(ArrayList<BaoCaoSuCo_Post> result) {
            mArrBaoCaoSuCoPostsShow = result;
            adapter.refresh(mArrBaoCaoSuCoPostsShow);
            isLoadingMoreNew = false;
            id_swp_list.setRefreshing(false);
        }
    }
    public class LoadMoreDataOldTask extends AsyncTask<String, Void, ArrayList<BaoCaoSuCo_Post>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            isLoadingMoreOld = true;
            mArrBaoCaoSuCoPostsShow.add(null);//show loading more at the bottom
            adapter.notifyItemInserted(mArrBaoCaoSuCoPostsShow.size() - 1);//focus at the bottom

        }

        @Override
        public ArrayList<BaoCaoSuCo_Post> doInBackground(String... params) {
            //addMoreData();
            initDataWS(TypeLoad.OLD);
//            try {
//                Thread.currentThread();
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            return filterData();
        }

        @Override
        public void onPostExecute(ArrayList<BaoCaoSuCo_Post> result) {
            mArrBaoCaoSuCoPostsShow.remove(mArrBaoCaoSuCoPostsShow.size() - 1);//remove the null row
            int scrollPosition = mArrBaoCaoSuCoPostsShow.size();
            adapter.notifyItemRemoved(scrollPosition);//focus at the loading position
            //
            mArrBaoCaoSuCoPostsShow = result;
            adapter.refresh(mArrBaoCaoSuCoPostsShow);
            isLoadingMoreOld = false;
        }
    }

    public class FilterDataTask extends AsyncTask<String, Void, ArrayList<BaoCaoSuCo_Post>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            id_prgbar_loading.setVisibility(View.VISIBLE);

        }

        @Override
        public ArrayList<BaoCaoSuCo_Post>  doInBackground(String... params) {
            /*
            try {
                Thread.currentThread();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
            return filterData();
        }

        @Override
        public void onPostExecute(ArrayList<BaoCaoSuCo_Post> result) {
            mArrBaoCaoSuCoPostsShow = result;
            adapter.refresh(mArrBaoCaoSuCoPostsShow);
            id_prgbar_loading.setVisibility(View.GONE);
        }
    }
    @Override
    public void showMap(BaoCaoSuCo_Post post){
            AMap.kinhDo = post.getKinhDoTheoMobile();
            AMap.vido = post.getViDoTheoMobile();
            AMap.title = post.getDiaChiSuCoTheoMobile();
            this.startActivity(new Intent(ABaoCaoSuCo_Recycler.this, AMap.class));
    }
    @Override
    public void showGalleryImgs(BaoCaoSuCo_Post post){
        if(post != null){
            if(post.getImages() != null){
                AShowGalleryImages_BaoCaoSuCo.mArrImg = post.getImages();
                this.startActivity(new Intent(ABaoCaoSuCo_Recycler.this, AShowGalleryImages_BaoCaoSuCo.class));
            }
        }

    }
    @Override
    public void showPhanHoiKh(BaoCaoSuCo_Post post, int position){
        for(BaoCaoSuCo_Post postx : mArrBaoCaoSuCoPostsShow){
            if(postx != null){
                postx.setShowPhanHoiChoKh(false);
            }

        }
        if(post != null){
            post.setShowPhanHoiChoKh(true);
        }

        adapter.refresh(mArrBaoCaoSuCoPostsShow);
        id_rcl_list.setSelected(true);
        id_rcl_list.scrollToPosition(position);
    }
}
