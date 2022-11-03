package com.zy_admin.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Object data;
    private JsonResult meta;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JsonResult getJsonResult() {
        return meta;
    }

    public void setJsonResult(JsonResult meta) {
        this.meta = meta;
    }
}
