package com.wu.ming.common;

/**
 * 错误码
 *
 * @author yupi
 */
public enum ErrorCode {

    SUCCESS(200, "ok", "成功"),
    PARAMS_ERROR(40000, "请求参数格式错误", ""),
    NULL_ERROR(40001, "请求数据为空", ""),
    SYSTEM_ERROR(50000, "系统内部异常", ""),
    TYPE_ERROR(201,"格式不正确","");

    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述（详情）
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
