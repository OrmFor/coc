package com.yinmimoney.web.p2pnew.enums;

/**
 * Created by dyf on 2019/7/21 16:30
 */
public enum  EnumExpenseType {
    EXPENSE_IN("IN"),
    EXPENSE_OUT("OUT"),
    ;
    String name;

    EnumExpenseType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
