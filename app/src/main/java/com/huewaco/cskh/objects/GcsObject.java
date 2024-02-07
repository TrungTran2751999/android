package com.huewaco.cskh.objects;

import com.huewaco.cskh.helper.CommonHelper;

public class GcsObject {
    private String text;
    private boolean isValue;


    public boolean isValue() {
        return isValue;
    }

    public void setValue(boolean value) {
        isValue = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if(CommonHelper.checkValidString(text)){
            this.text = CommonHelper.keepOnlyNumberInString(text);
        }
        //this.text = text;
    }
}
