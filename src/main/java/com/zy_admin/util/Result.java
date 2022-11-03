package com.zy_admin.util;

public class Result {
    private Object data;
    private JsonResult meta;

    public Result(){}

    public Result(Object data, JsonResult meta) {
        this.data = data;
        this.meta = meta;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JsonResult getMeta() {
        return meta;
    }

    public void setMeta(JsonResult meta) {
        this.meta = meta;
    }
}
