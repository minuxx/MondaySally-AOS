package com.moon.android.mondaysally.ui.main.gift;

import android.content.Context;
import android.util.AttributeSet;

import com.moon.android.mondaysally.data.entities.GiftOption;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

public class CustomTagContainerLayout extends TagContainerLayout {
    public CustomTagContainerLayout(Context context) {
        super(context);
    }

    public CustomTagContainerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTagContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOption(List<GiftOption> giftOptions) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < giftOptions.size(); i++) {
            String temp;
            temp = "" + giftOptions.get(i).getUsedClover() + "☘ / " + giftOptions.get(i).getMoney();
            stringList.add(temp);
        }
        super.setTags(stringList);
    }
}
