package com.huewaco.cskh.webservice.objects;

import android.content.Context;
import android.util.Log;

import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.Img2TextObj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GetTextFromBitmapAPIResponse /*extends CommonResponse*/ {
    String returnString = "";
    private Img2TextObj img2TextObj;

    private boolean isSuccess = true;
    public String getReturnString() {
        return returnString;
    }

    public GetTextFromBitmapAPIResponse(Context context, String result) {
        //super(context,result);
        if (CommonHelper.checkValidString(result)) {
            returnString = result;
        }else{

        }
        parseStringToJson((result));
    }

    Img2TextObj parseStringToJson(String str) {
        //JSONArray jsonArr = null;
        JSONObject obj = null;
        img2TextObj = new Img2TextObj();
        try {
            obj = new JSONObject(str);
        } catch (Exception e1) {
            isSuccess = false;
            System.out.print(e1.getMessage());
        }
        if (obj != null) {
            try {

                 String actual_number = "";// obj.getString("actual_number");
                if(obj.has("actual_number") && !obj.isNull("actual_number")){
                    actual_number = obj.getString("actual_number");
                }
                 int frame_count =0;//= obj.getInt("frame_count");
                if(obj.has("frame_count") && !obj.isNull("frame_count")){
                    frame_count = obj.getInt("frame_count");
                }
                 String frame_data = "";//obj.getString("frame_data");//
                if(obj.has("frame_data") && !obj.isNull("frame_data")){
                    frame_data = obj.getString("frame_data");
                }
                JSONArray jsonArr = null;
                if(obj.has("frame_shape") && !obj.isNull("frame_shape")){
                    jsonArr = obj.getJSONArray("frame_shape");
                    if(jsonArr != null && jsonArr.length() >0){
                        for(int i = 0; i< jsonArr.length(); i++){
                            JSONArray jr = jsonArr.getJSONArray(i);
                            ArrayList<String> marr = new ArrayList<>();
                            if(jr != null && jr.length() >0){
                                for(int j = 0; j< jr.length(); j++){
                                    String x = jr.get(j).toString();
                                    //Log.d("vvvvvvv",""+x);
                                    marr.add(x);

                                }
                            }
                            img2TextObj.getFrame_shape().add(marr);
                        }

                    }
                }

                    //Log.d("jsonArrjsonArrjsonArr1",""+jsonArr.toString());
                 String imagename= "";//obj.getString("imagename");
                if(obj.has("imagename") && !obj.isNull("imagename")){
                    imagename= obj.getString("imagename");
                }
                 int number_count = 0;//=obj.getInt("number_count");
                if(obj.has("number_count") && !obj.isNull("number_count")){
                    number_count = obj.getInt("number_count");
                }
//                 String number_shapes= obj.getString("number_shapes");//
                JSONArray jsonArr_number_shapes = null;
                if(obj.has("number_shapes") && !obj.isNull("number_shapes")){
                    jsonArr_number_shapes = obj.getJSONArray("number_shapes");
                    if(jsonArr_number_shapes != null && jsonArr_number_shapes.length()>0){
                        for(int i = 0; i< jsonArr_number_shapes.length(); i++){
                            JSONObject objx = jsonArr_number_shapes.getJSONObject(i);
//                        Log.d("xxxxx",""+objx.toString());
                            if(objx != null){
                                String key = objx.keys().next();
                                JSONArray jsonArr_ = objx.getJSONArray(key);
                                ArrayList<String> mArrx2 = new ArrayList<>();
                                if(jsonArr_ != null && jsonArr_.length()>0){
                                    for(int k = 0 ; k < jsonArr_.length(); k++){
                                        String vl = jsonArr_.get(k).toString();
                                        mArrx2.add(vl);
                                    }
                                }
                                img2TextObj.getNumber_shapes().put(key,mArrx2);

                                //Log.d("xxxxx2",""+objx.keys().next());
                            }
                        }
                    }
                }

//                Log.d("jsonArrjsonArrjsonArr2",""+jsonArr_number_shapes.toString());
                String predicted_number= "";//obj.getString("predicted_number");
                if(obj.has("predicted_number") && !obj.isNull("predicted_number")){
                    predicted_number= obj.getString("predicted_number");
                }

                 String read_number= "";//obj.getString("read_number");
                if(obj.has("read_number") && !obj.isNull("read_number")){
                    read_number= obj.getString("read_number");
                }
                 String score= "";//obj.getString("score");
                if(obj.has("score") && !obj.isNull("score")){
                    score= obj.getString("score");
                }
                String imagepath = "";
                if(obj.has("imagepath") && !obj.isNull("imagepath")){
                    imagepath= obj.getString("imagepath");
                }
                img2TextObj.setActual_number(actual_number);
                img2TextObj.setFrame_count(frame_count);
                img2TextObj.setFrame_data(frame_data);
                img2TextObj.setImagename(imagename);
                img2TextObj.setNumber_count(number_count);
                //img2TextObj.setNumber_shapes(number_shapes);
                img2TextObj.setPredicted_number(predicted_number);
                img2TextObj.setRead_number(read_number);
                img2TextObj.setScore(score);
                img2TextObj.setImagepath(imagepath);

/*
                String strx = "";
                String strxArr1 = "";
                String strxHM1 = "";
                for(ArrayList<String> m1 : img2TextObj.getFrame_shape()){
                    strxArr1+="\n Array: ";
                    for(String i : m1){
                        strxArr1+=" "+i+",";
                    }
                }
                HashMap<String,ArrayList<String>> xx;
                Set<Map.Entry<String, ArrayList<String>>> entries = img2TextObj.getNumber_shapes().entrySet();
                for (Map.Entry<String, ArrayList<String>> entry : entries) {
                    strxHM1+="\n key: "+entry.getKey();
                    for(String i : entry.getValue()){
                        strxHM1+="   => "+i+" , ";
                    }
//                    System.out.print("key: "+ entry.getKey());
//                    System.out.println(", Value: "+ entry.getValue());
                }

                    strx = img2TextObj.toString() + strxArr1+strxHM1;
                Log.d("ccccccccc",""+strx);
                */
            } catch (Exception e) {
                isSuccess = false;
                System.out.print(e.getMessage());
            }
        }
        return img2TextObj;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public void setReturnString(String returnString) {
        this.returnString = returnString;
    }

    public Img2TextObj getImg2TextObj() {
        return img2TextObj;
    }

    public void setImg2TextObj(Img2TextObj img2TextObj) {
        this.img2TextObj = img2TextObj;
    }
}
