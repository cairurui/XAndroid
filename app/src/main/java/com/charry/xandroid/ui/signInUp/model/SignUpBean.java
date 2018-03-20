package com.charry.xandroid.ui.signInUp.model;

import java.util.List;

/**
 * Created by xiaocai on 2018/3/20.
 */

public class SignUpBean {


    /**
     * data : {"collectIds":[],"email":"","icon":"","id":3979,"password":"123456","type":0,"username":"xiaocai"}
     * errorCode : 0
     * errorMsg :
     */

    public DataBean data;
    public int errorCode;
    public String errorMsg;

    public static class DataBean {
        /**
         * collectIds : []
         * email :
         * icon :
         * id : 3979
         * password : 123456
         * type : 0
         * username : xiaocai
         */

        public String email;
        public String icon;
        public int id;
        public String password;
        public int type;
        public String username;
        public List<?> collectIds;
    }
}
