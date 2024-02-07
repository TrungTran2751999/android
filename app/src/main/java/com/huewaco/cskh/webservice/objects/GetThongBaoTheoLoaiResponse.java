package com.huewaco.cskh.webservice.objects;

import android.content.Context;
import android.util.Log;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.ThongBaoListItemObj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by diepthaibaotoan on 2/20/2017.
 */

public class GetThongBaoTheoLoaiResponse extends CommonResponse {
    private String Id;
    private String NoiDung;
    private boolean DaDoc;

    //
    ArrayList<ThongBaoListItemObj> mArrItem;

    //
    public GetThongBaoTheoLoaiResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result) && (!this.hasError())) {
            parseStringToJson((result));
        }
    }

    //
    private ArrayList<ThongBaoListItemObj> parseStringToJson(String str) {
        JSONArray jsonArr = null;
        JSONObject obj = null;
        mArrItem = new ArrayList<ThongBaoListItemObj>();
        try {
            jsonArr = new JSONArray(str);

        } catch (Exception e1) {
            System.out.print(e1.getMessage());
        }
        if (jsonArr != null && jsonArr.length() > 0) {
            for (int i = 0; i < jsonArr.length(); i++) {
                ThongBaoListItemObj thongBaoThanhToanListItemObj = new ThongBaoListItemObj();
                try {
                    obj = jsonArr.getJSONObject(i);
                    if (obj != null) {
                        Id = obj.getString("Id");
                        NoiDung = obj.getString("NoiDung");
                        DaDoc = obj.getBoolean("DaDoc");
                        //
                        if (CommonHelper.checkValidString(Id)) {
                            thongBaoThanhToanListItemObj.setId(Id.toString().trim());
                        }
                        if (CommonHelper.checkValidString(NoiDung)) {
                            thongBaoThanhToanListItemObj.setNoiDung(NoiDung.toString().trim());
                        }
                        thongBaoThanhToanListItemObj.setDaDoc(DaDoc);

                        mArrItem.add(thongBaoThanhToanListItemObj);
                    }
                } catch (Exception e) {
                    Log.e("abc", "Parse json thu " + i + "fail");
                }
            }
        } else {
            Log.e("abc", "Get ThongBaoThanhToan false");
        }

        return mArrItem;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public boolean isDaDoc() {
        return DaDoc;
    }

    public void setDaDoc(boolean daDoc) {
        DaDoc = daDoc;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public ArrayList<ThongBaoListItemObj> getmArrItem() {
        return mArrItem;
    }

    public void setmArrItem(ArrayList<ThongBaoListItemObj> mArrItem) {
        this.mArrItem = mArrItem;
    }
}
