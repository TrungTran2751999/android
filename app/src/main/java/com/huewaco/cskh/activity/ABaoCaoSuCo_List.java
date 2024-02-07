package com.huewaco.cskh.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huewaco.cskh.adapter.ListViewBaoCaoSuCoListAdapter;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.BaoCaoSuCo_Post;
import com.huewaco.cskh.objects.ImgInPostObj;
import com.huewaco.cskh.webservice.objects.GetAllBaoCaoSuCoResponse;
import com.huewaco.cskh.webservice.objects.GetDeviceTokenResponse;
import com.huewaco.cskh.webservice.processing.ResultFromWebservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ABaoCaoSuCo_List extends AParent {

    enum TypeTOP {
        TIEUBIEU,
        DAXULY,
        DANGXULY
    }
    enum TypeBOTTOM {
        CANHAN,
        CONGDONG
    }
    private boolean isLoadingMoreNew = false;
    private boolean isLoadingMoreOld = false;
    ViewGroup header, footer;
    private ViewGroup id_fr_bottombar,id_ly_bottom_bar_add;

    private Button id_btn_congdong,id_btn_canhan;
    private ImageView id_img_congdong, id_img_canhan;
    private ViewGroup id_ly_canhan, id_ly_congdong;

    private Button  id_btn_tieubieu, id_btn_daxuly, id_btn_dangxuly;

    private ListView id_lv_posts;
    private ImageView id_img_addsuco;
    private ArrayList<BaoCaoSuCo_Post> mArrBaoCaoSuCoPostsAll = new ArrayList<>();
    private ArrayList<BaoCaoSuCo_Post> mArrBaoCaoSuCoPostsShow = new ArrayList<>();
    private ListViewBaoCaoSuCoListAdapter adapter;

    private ViewGroup id_ly_tieubieu,id_ly_daxuly,id_ly_dangxuly;
    private ImageView id_img_tieubieu,id_img_daxuly,id_img_dangxuly;
    private TypeTOP WHAT_TYPE_TOP = TypeTOP.DAXULY;
    private TypeBOTTOM WHAT_TYPE_BOTTOM = TypeBOTTOM.CONGDONG;


    private ProgressBar id_prgbar_loading_footer;
    private ViewGroup id_ly_footer;
    private TextView id_tv_loading_footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baocaosuco_recycler);
        initComponent();
        addListener();
    }
     void  showDataType(){
        new FilterDataTask().execute();
    }
    @Override
    protected void initComponent() {
        initTopbarView();

        LayoutInflater inflater = getLayoutInflater();
        header = (ViewGroup)inflater.inflate(R.layout.header_listview_bcsc, id_lv_posts, false);
        footer = (ViewGroup)inflater.inflate(R.layout.footer_listview_bcsc, id_lv_posts, false);
        id_prgbar_loading_footer = (ProgressBar)footer.findViewById(R.id.id_prgbar_loading_footer);
        id_prgbar_loading_footer.setVisibility(View.GONE);
        id_ly_footer = (ViewGroup)footer.findViewById(R.id.id_ly_footer);
        id_tv_loading_footer = (TextView)footer.findViewById(R.id.id_tv_loading_footer);
        id_tv_loading_footer.setText(getString(R.string.readmore));

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
        id_lv_posts = (ListView)findViewById(R.id.id_lv_posts);


        id_tv_title.setText(getString(R.string.tab_dichvu_bcaosuco));
        id_btn_left.setBackgroundResource(R.drawable.btn_back);
        adapter = new ListViewBaoCaoSuCoListAdapter(this, mArrBaoCaoSuCoPostsShow, ABaoCaoSuCo_List.this);
        id_lv_posts.setAdapter(adapter);

        new LoadDataFistTask().execute();
    }

    @Override
    protected void addListener() {
        id_ly_footer.setOnClickListener(this);

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

        id_lv_posts.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem ;
            //private int chk = 1;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == 0){
                    //
//                    if(id_lv_posts.getHeaderViewsCount()==0){
//                        if(chk == 1 || chk ==2){
//                            CommonHelper.showToast(getApplicationContext(),""+scrollState+ " chk: "+chk);
//                            id_lv_posts.addHeaderView(header, null, false);
//                            if(!isLoadingMoreNew){
//                                isLoadingMoreNew = true;
//                                new LoadMoreDataNewTask().execute();
//                            }
//                        }
//
//
//                    }
                }
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
//                        && (id_lv_posts.getLastVisiblePosition() - id_lv_posts.getHeaderViewsCount() -
//                        id_lv_posts.getFooterViewsCount()) >= (adapter.getCount() - 1)) {
//                    id_lv_posts.addFooterView(header, null, false);
//                        if(!isLoadingMoreOld){
//                            isLoadingMoreOld = true;
//                            //new LoadMoreDataOldTask().execute();
//                        }
//                }
//                if (!view.canScrollList(View.SCROLL_AXIS_VERTICAL) && scrollState == SCROLL_STATE_IDLE)
//                {
//                    id_lv_posts.addFooterView(header, null, false);
//                    if(!isLoadingMoreOld){
//                        isLoadingMoreOld = true;
//                        //new LoadMoreDataOldTask().execute();
//                    }
//                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

//                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
//                {
//                    if(id_lv_posts.getFooterViewsCount()==0){
//
//                            id_lv_posts.addFooterView(footer, null, false);
//                            if(!isLoadingMoreOld){
//                                isLoadingMoreOld = true;
//                                new LoadMoreDataOldTask().execute();
//                            }
//
//
//
//                    }
//                }
                if(mLastFirstVisibleItem<firstVisibleItem)
                {
                    id_fr_bottombar.setVisibility(View.GONE);
                    id_ly_bottom_bar_add.setVisibility(View.GONE);
                    //chk = 0;
                }
                if(mLastFirstVisibleItem>firstVisibleItem)
                {
                    //CommonHelper.showToast(ABaoCaoSuCo_List.this,"up");
                    id_fr_bottombar.setVisibility(View.VISIBLE);
                    id_ly_bottom_bar_add.setVisibility(View.VISIBLE);
                    //chk = 2;
                }
                mLastFirstVisibleItem=firstVisibleItem;

            }
        });
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
            case R.id.id_ly_footer:
                if(!isLoadingMoreOld){
                    isLoadingMoreOld = true;
                    id_tv_loading_footer.setText(getString(R.string.loadmore));
                    id_prgbar_loading_footer.setVisibility(View.VISIBLE);
                    new LoadMoreDataOldTask().execute();
                }
                break;


            case R.id.id_ly_canhan:
                if(WHAT_TYPE_BOTTOM != TypeBOTTOM.CANHAN) {
//                    CommonHelper.showToast(this,"clicked");
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
//                    CommonHelper.showToast(this,"clicked");
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
    private void initDataWS(){
        mArrBaoCaoSuCoPostsAll.clear();
        mArrBaoCaoSuCoPostsShow.clear();
        GetAllBaoCaoSuCoResponse getAllBaoCaoSuCoResponse = new ResultFromWebservice().getAllBaoCaoSuCoResponse(getApplicationContext());
        mArrBaoCaoSuCoPostsAll = getAllBaoCaoSuCoResponse.getmArrItem();
        Collections.sort(mArrBaoCaoSuCoPostsAll,BaoCaoSuCo_Post.sort_baoCaoSuCo_Post_DESC);
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
                    if (post.getIdKH().equalsIgnoreCase(GlobalVariable.KHACH_HANG.getIdKh())) {
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
    public class LoadDataFistTask extends AsyncTask<String, Void, ArrayList<BaoCaoSuCo_Post>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        public ArrayList<BaoCaoSuCo_Post> doInBackground(String... params) {
//            initData();
            initDataWS();
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
            disMissLoading();
        }
    }
    public class LoadMoreDataNewTask extends AsyncTask<String, Void, ArrayList<BaoCaoSuCo_Post>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        public ArrayList<BaoCaoSuCo_Post> doInBackground(String... params) {
            //addMoreData();

            try {
                Thread.currentThread();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return filterData();
        }

        @Override
        public void onPostExecute(ArrayList<BaoCaoSuCo_Post> result) {
            mArrBaoCaoSuCoPostsShow = result;
            adapter.refresh(mArrBaoCaoSuCoPostsShow);
            if(id_lv_posts.getHeaderViewsCount()>0){
                id_lv_posts.removeHeaderView(header);
            }
            isLoadingMoreNew = false;
        }
    }
    public class LoadMoreDataOldTask extends AsyncTask<String, Void, ArrayList<BaoCaoSuCo_Post>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        public ArrayList<BaoCaoSuCo_Post> doInBackground(String... params) {
            //addMoreData();

            try {
                Thread.currentThread();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return filterData();
        }

        @Override
        public void onPostExecute(ArrayList<BaoCaoSuCo_Post> result) {
            mArrBaoCaoSuCoPostsShow = result;
            adapter.refresh(mArrBaoCaoSuCoPostsShow);
            id_tv_loading_footer.setText(getString(R.string.readmore));
            id_prgbar_loading_footer.setVisibility(View.GONE);
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
            this.startActivity(new Intent(ABaoCaoSuCo_List.this, AMap.class));
    }
    @Override
    public void showGalleryImgs(BaoCaoSuCo_Post post){
        if(post != null){
            if(post.getImages() != null){
                AShowGalleryImages_BaoCaoSuCo.mArrImg = post.getImages();
                this.startActivity(new Intent(ABaoCaoSuCo_List.this, AShowGalleryImages_BaoCaoSuCo.class));
            }
        }

    }
}
