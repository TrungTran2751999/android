package com.huewaco.cskh.webservice.objects;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.huewaco.cskh.activity.ADangNhap4;
import com.huewaco.cskh.activity.AMain;
import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.activity.R;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.helper.GlobalVariable;
import com.huewaco.cskh.objects.ParentObj;

import org.json.JSONArray;
import org.json.JSONObject;

public class CommonResponse extends ParentObj {
    private boolean mHasError;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    ;
    protected String mErrorMessage;
    protected AParent currentActivity;

    public CommonResponse(final Context context, String str,AParent currentActivity) {
        this.currentActivity = currentActivity;
        try {
            if(CommonHelper.checkValidString(str)){
                if(((str.equalsIgnoreCase("true") || (str.equalsIgnoreCase("false"))))){
                    mErrorMessage = "";
                    mHasError = false;
                }else{
                    JSONObject result = new JSONObject(str);
                    if (result == null
                            || (result.has("Message") && !result
                            .isNull("Message"))) {
                        if (result != null) {
                            mErrorMessage = result.getString("Message");
                        }
                        mHasError = true;
                    } else {
                        mErrorMessage = "";
                        mHasError = false;
                    }

                    if (mHasError) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                showDialog(context);
                            }
                        });

                    }
                }
            }else{//NULL
                this.mHasError = false;
                this.mErrorMessage = str+" : "+context.getString(R.string.no_data);
/*                 mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        CommonHelper.showWarning(context,mErrorMessage);
                    }
                });*/
            }


        } catch (Exception e) {
            try {
                //check truong hop du lieu tra ve la array JSON
                JSONArray jsonArr = new JSONArray(str);
                mErrorMessage = "";
                mHasError = false;
                //
            } catch (Exception e1) {
//                this.mHasError = true;
//                this.mErrorMessage = context.getString(R.string.loi_ket_noi_server);
//                System.out.print(e1.getMessage());
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        showDialog(context);
//                    }
//                });
                mErrorMessage = "";
                mHasError = false;

            }

        }
    }

    public boolean hasError() {
        return mHasError;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setError() {
        mHasError = true;
    }
    private void showDialog(final Context context){
        try{
            currentActivity.getErrorAndOut();
            /*
            GlobalVariable.logOut(context);
            Intent i = new Intent(context, ADangNhap4.class);
            context.startActivity(i);
            ((AParent) context).finish();
            */
        }catch (Exception e){

        }
        /*
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        try{
                            GlobalVariable.logOut(context);
                            Intent i = new Intent(context, ADangNhap4.class);
                            context.startActivity(i);
                            ((AMain) context).finish();
                        }catch (Exception e){

                        }

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(mErrorMessage).setPositiveButton(context.getString(R.string.dangnhaplai), dialogClickListener)
                .setNegativeButton(context.getString(R.string.tab_caidat_dangxuat_khong), dialogClickListener).show();
        */
    }
}
