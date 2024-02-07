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

public class GetThongBaoAllTheoLoaiResponse extends CommonResponse {
    private String Id;
    private String NoiDung;
    private boolean DaDoc;
    private String IdKh;
    private Integer Count;
    //
    ArrayList<ThongBaoListItemObj> mArrItem;

    //
    public GetThongBaoAllTheoLoaiResponse(Context context, String result, AParent currentActivity) {
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

                try {
                    obj = jsonArr.getJSONObject(i);
                    if (obj != null) {
                        ThongBaoListItemObj item = new ThongBaoListItemObj();
                        IdKh = obj.getString("IdKH");
                        if (CommonHelper.checkValidString(IdKh)) {
                            item.setIdKh(IdKh.toString().trim());
                        }
                        Count = obj.getInt("Count");
                        item.setCountThongBao(Count);
                        mArrItem.add(item);
                        JSONArray data = new JSONArray(obj.getString("Data"));
                        if (data != null && data.length() > 0){
                            for (int j = 0; j < data.length(); j++) {
                                JSONObject objData = data.getJSONObject(j);
                                ThongBaoListItemObj itemData = new ThongBaoListItemObj();
                                Id = objData.getString("Id");
                                NoiDung = objData.getString("NoiDung");
                                DaDoc = objData.getBoolean("DaDoc");
                                //
                                if (CommonHelper.checkValidString(Id)) {
                                    itemData.setId(Id.toString().trim());
                                }
                                if (CommonHelper.checkValidString(NoiDung)) {
                                    itemData.setNoiDung(NoiDung.toString().trim());
                                }
                                itemData.setDaDoc(DaDoc);
                                mArrItem.add(itemData);
                            }
                        }
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
