package com.monk.retrofit.bean;

import java.util.List;

/**
 * @author monk
 * @date 2019-02-19
 */
public class Bean {
    public boolean IsSuccess;
    public String Code;
    public String Message;
    public List<ReturnObjectBean> ReturnObject;

    public static class ReturnObjectBean {
        public String DepartmentTitle;
        public String DepartmentGuid;
        public String EmployeeGuid;
        public String EmployeeName;
        public String Phone;
        public String AvatarUrl;
        public String ShiftGroupName;
        public boolean DefaultCopier;


    }
}
