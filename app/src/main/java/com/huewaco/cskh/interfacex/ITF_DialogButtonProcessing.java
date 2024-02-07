package com.huewaco.cskh.interfacex;

import android.app.Dialog;

/**
 * Created by duongminhkiet on 10/21/16.
 */
public interface ITF_DialogButtonProcessing {
    public void okBtn(int type);
    public void cancelBtn(int type);
    public void okBtn(int type, String tenKh, String sdt, String diachi);
    public void okBtn(int type, String tenKh, String sdt, String diachi, String hdtimvitrisuco);
}
