package com.oxygen.yallagoom.interfaces;

import com.oxygen.yallagoom.entity.Group;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sabbah on 2/5/2018.
 */

public interface DeleteFragmentCallback {
    void processFinish(int position,ArrayList<Group.MyGroup.Data> group,int check);

}
