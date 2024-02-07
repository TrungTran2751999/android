package com.huewaco.cskh.webservice.objects;

import android.content.Context;

import com.huewaco.cskh.activity.AParent;
import com.huewaco.cskh.helper.CommonHelper;
import com.huewaco.cskh.objects.ContractDetailObject;

import org.json.JSONObject;

import java.util.Date;

public class GetHddtDetailResponse extends CommonResponse {


    public ContractDetailObject getContract() {
        return contract;
    }

    ContractDetailObject contract = new ContractDetailObject();


    public GetHddtDetailResponse(Context context, String result, AParent currentActivity) {
        super(context,result,currentActivity);
        if (CommonHelper.checkValidString(result) && (!this.hasError())) {
            parseStringToJson((result));
        }
    }

    private void parseStringToJson(String str) {

        JSONObject obj = null;
        try {

            obj = new JSONObject(str);
        } catch (Exception e1) {
            System.out.print(e1.getMessage());
        }
        if (obj != null) {
            try {

                contract.setContractYear(obj.getInt("ContractYear"));
                contract.setContractNo(obj.getString("ContractNo"));
                contract.setMaDDK(obj.getString("Maddk"));
                contract.setContractIssueDate( CommonHelper.getDateFromString(obj.getString("ContractIssueDate"),null));
                contract.setContractStatus(obj.getString("Status"));
                try{
                    contract.setEContractPdf(obj.getString("eContractPdf"));
                }
                catch(Exception e1){}
                contract.setCustomerSignerName(obj.getString("CustomerSignerName"));
                contract.setCustomerSignerNote(obj.getString("CustomerSignerNote"));
                contract.setBuyerName(obj.getString("BuyerName"));
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
    }



}
