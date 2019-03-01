package com.balinasoft.test_task.platform;

import com.balinasoft.test_task.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResourceSup {

    public static String getString(int stringId) {
        return App.getContext().getString(stringId);
    }

    public static ArrayList<String> getDomens() {
        String[] mas = App.getContext().getResources().getStringArray(R.array.domens);
        return new ArrayList<>(Arrays.asList(mas));
    }
}
