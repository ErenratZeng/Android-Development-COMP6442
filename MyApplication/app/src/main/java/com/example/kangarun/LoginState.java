package com.example.kangarun;

public class LoginState {
    private static LoginState instance;
    private String id;

    // 私有构造函数，阻止外部直接实例化对象
    private LoginState() {
        // 这里可以进行一些初始化操作
    }

    // 静态方法，用于获取 LoginState 类的唯一实例
    public static  LoginState getInstance() {
        if (instance == null) {
            instance = new LoginState();
        }
        return instance;
    }

    // 获取用户ID
    public String getUserId() {
        return id;
    }

    // 设置用户ID
    public void setUserId(String id) {
        this.id = id;
    }
}
