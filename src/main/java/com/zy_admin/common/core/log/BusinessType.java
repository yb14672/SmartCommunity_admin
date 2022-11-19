package com.zy_admin.common.core.log;

/**
 * 枚举类，里面有操作日志的功能
 */
public enum BusinessType {
    // 新增
    INSERT,
    // 修改
    UPDATE,
    // 删除
    DELETE,
    //授权
    EMPOWER,
    //导出
    EXPORT,
    //导入
    IMPORT,
    //强退
    EXIT,
    //生成代码
    GENERATING,
    //清空代码
    CLEAR,
    // 其它
    OTHER,
}
