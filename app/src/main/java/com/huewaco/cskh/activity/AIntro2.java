package com.huewaco.cskh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.huewaco.cskh.helper.GlobalVariable;

public class AIntro2 extends AParent {
    private Button id_btn_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aintro);
        initComponent();
        addListener();
    }

    @Override
    protected void initComponent() {
        id_btn_continue = (Button) findViewById(R.id.id_btn_continue);
    }

    @Override
    protected void addListener() {
        id_btn_continue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_continue:
                startActivity(new Intent(getApplicationContext(),ALogin3.class));
                if(GlobalVariable.IS_ANIMATION_ACTIVITY){
                    overridePendingTransition(R.anim.next_1, R.anim.next_2);
                }
                break;
            default:
                break;
        }
    }
}
