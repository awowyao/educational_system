package com.cwy.educationaladministrationsystem.model;

import lombok.Data;

@Data
public class result {
    public static final int NOT_LOGGED_IN = -1;

    public static final int SUCCESS = 0;

    public static final int FAIL = 1;
    //表示临时响应并需要请求者继续执行操作的状态代码。
    public static final int ROLE_ERROR = 2;
    //  角色问题
    public static final int PERMISSION_DENIED = 3;
    //   权限问题

    public static final int INVALID_PARAMETER = 4;
    //    400 用户参数

    public static final int INTERNAL_SERVER_ERROR = 5;
    //  服务器
    public static final long serialVersionUID = 1L;
    //    序列的标识符/反序列化的对象序列化类。
    private Integer code;

    private String msg;

    private Object data;

    public result(Integer code, String message, Object data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public result() {

    }


}
