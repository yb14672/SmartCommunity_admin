package com.zy_admin.common.enums;

/**
 * @Author yb14672
 * @Description: 返回码定义
 * 规定:
 * #1表示成功
 * #1001～1999 区间表示参数错误
 * #2001～2999 区间表示用户错误
 * #3001～3999 区间表示接口异常
 * @Date Create in
 */
public enum ResultCode {
    /* 成功 */
    SUCCESS(200, "成功"),

    /* 默认失败 */
    COMMON_FAIL(999, "失败"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),
    NO_CHANGE_IN_PARAMETER(1005,"参数没有变化"),

    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),
    USER_ACCOUNT_SAME_PASSWORD(2010,"新旧密码不能一致"),
    USER_WRONG_ACCOUNT_OR_PASSWORD(2010, "账号或者密码不正确"),
    USER_AVATAR_UPLOAD_FAILED(2011, "图片上传失败"),
    USER_AVATAR_NULL(2012,"没有头像，记得上传哦"),
    USER_LOGIN_EXPIRED(2013,"登录已过期"),
    USER_TOKEN_INVALID(2014,"无效token"),
    USER_LOGIN_SUCCESS(2015,"登录成功"),
    USER_LOGOUT_FAIL(2016,"退出失败"),
    USER_LOGOUT_SUCCESS(2017,"退出成功"),
    ADDRESS_GET_FAIL(2018,"地址获取失败"),
    USER_REGISTER_FAIL(2019,"注册失败"),
    REPEAT_PHONE_NUMBER(2020,"手机号已被使用"),
    OWNER_UPDATE_FAIL(2021,"业主信息修改失败"),
    NO_ROLE(2022,"该用户没有角色"),
    OWNER_NOT_BOUND(2023,"业主未绑定"),
    USER_INFO_GET_FAIL(2023,"业主信息获取失败"),
    REPEAT_ID_CARD(2024,"身份证号已被绑定"),
    PASSWORD_UPDATE_FAIL(2025,"密码修改失败"),

    /* 业务错误 */
    NO_PERMISSION(3001, "没有权限"),
    DELETE_FAIL(3002, "删除失败，请稍后再试"),
    REPEAT_MENUNAME(3003, "该菜单名称已存在"),
    REPEAT_MENUPATH(3004, "该路由已存在"),
    MENU_HAVE_CHILDREN(3005, "该菜单有子集，若要删除请使用批量删除"),
    REPEAT_ROLE_KEY(3006,"当前权限码已存在"),
    REPEAT_ROLE_NAME(3007,"当前角色名已存在"),
    ADMIN_NOT_ALLOWED_DELETE(3008,"不允许删除管理员"),
    PARENT_CLASS_CANNOT_BE_ITSELF(3009,"修改时父类不能是自己"),
    REPEAT_DICT_NAME(3010,"字典名重复" ),
    REPEAT_DICT_TYPE(3011,"字典类型重复" ),
    REPEAT_DICT_DATA_LABEL(3012,"数据标签重复" ),
    REPEAT_DICT_DATA_VALUE(3013,"数据键值重复" ),
    DICT_HAVE_CHILDREN(3014, "该字典有子集，不允许删除"),
    DEPT_HAVE_CHILDREN(3015, "该部门有子集，不允许删除"),
    REPEAT_DEPTNAME(3016, "该部门名已存在"),
    DEPT_HAVE_USER(3017, "该部门有用户，不允许删除"),
    REPEAT_POST_CODE(3018,"岗位编码重复"),
    REPEAT_POST_NAME(3019,"岗位名称重复"),
    POST_ASSIGNED(3020,"岗位已被分配" ),
    DEPTID_NOT_ITEM(3021,"不能更改父级为该类的子级"),
    ROLE_HAS_BEEN_ASSIGNED(3022,"角色已被分配，不能删除"),
    DEPT_ASSIGNED(3023,"部门已被分配" ),
    REPEAT_NICK_NAME(3021,"昵称重复"),
    REPEAT_PHONENUMBER(3022,"电话重复"),
    REPEAT_EMAIL(3023,"邮箱重复"),
    REPEAT_USER_NAME(3024,"用户名重复"),
    DICT_NOCHANGE(3024,"字典信息没有改变" ),
    USER_TELREPEAT(3025,"手机号重复" ),
    PASSWORD_ILLEGAL(3025,"密码不合规" ),
    USER_REPEAT(3026,"导入有重复" ),
    FILE_REPEAT(3027,"导入文件有重复" ),
    DATA_REPEAT(3027,"数据不能为空" ),
    USERNAME_REPEAT(3028,"用户名有重复" ),
    EMAIL_REPEAT(3029,"邮箱有重复" ),
    EXCEL_EXPORT_FAILURE(3030,"Excel导出失败"),
    LOGIN_LOG_GET_FAIL(3031,"登录日志获取失败"),
    LOG_DELETE_FAIL(3032,"登录日志删除失败"),
    LOG_EMPTY_FAIL(3033,"登录日志清空失败"),
    COMMUNITY_GET_FAIL(3034,"小区信息获取失败"),
    COMMUNITY_ADD_FAIL(3035,"新增小区失败"),
    REPEAT_COMMUNITY_NAME(3036,"小区名重复"),
    COMMUNITY_UPDATE_FAIL(3037,"小区修改失败"),
    COMMUNITY_DELETE_FAIL(3038,"小区删除失败"),
    PARENT_CLASS_DEACTIVATE(3039,"父类已被停用" ),
    PARENT_CANNOT_BE_A_SUBSET(3040,"父类不能是自己的子集" ),
    TEL_NON_COMPLIANCE(3041,"电话号码不符合规则" ),
    EMAIL_NON_COMPLIANCE(3042,"邮箱不符合规则" ),
    MASSAGE_NULL(3043,"信息为空" ),
    BUILDING_REPEAT(3044,"楼层重复" ),
    BUILDING_NAME_REPEAT(3045,"楼层名字重复" ),
    BUILD_IDENTICAL(3046,"楼层信息完全相同" ),
    BUILD_HAVE_CHILD(3046,"楼层下面有单元不允许删除" ),
    UNIT_ADD_FAIL(3047,"单元新增失败"),
    UNIT_UPDATE_FAIL(3048,"单元修改失败"),
    UNIT_NAME_REPEAT(3049,"单元名重复"),
    UNIT_HAVE_PEOPLE(3050,"单元存在住户，不能删除"),
    COMMUNITY_HAVE_CHILD(3051,"小区存在楼栋，不能删除"),
    COMPANY_OWNS_PROPERTY(3052,"公司仍有负责的物业，不能删除"),
    ROOM_HAVE_BEEN(3053,"该房屋名在当前楼层下已存在"),
    FAIL_UNBIND_ROOM(3054,"房屋解绑失败"),
    ROOM_HAVE_OWNER(3056,"该房屋名已有业主，不允许删除"),
    BUILD_HAVA_CHILD(3046,"楼层下面有单元不允许删除" ),
    ROOMTREE_GET_FAIL(3057,"获取失败"),
    OWNER_ROOM_INSERT_FAIL(3058,"提交审核失败"),
    REPEAT_OWNER_ROOM(3059,"审核已提交，请勿重复提交"),
    VISITOR_APPLICATION_SUCCESSFULLY(3047,"提交访客申请成功，等待管理员允许"),
    VISITOR_APPLICATION_FAIL(3048,"访客提申请交失败"),
    OWNER_ROOM_GET_FAIL(3049,"房屋信息获取失败"),
    OWNER_ID_CARD_NOT_CERTIFICATION(3050,"未实名认证"),



    /* http自带请求码 */
    CONTINUE(100, "请继续发送请求的剩余部分"),
    SWITCHING_PROTOCOLS(101 , "协议切换"),
    PROCESSING(102, "请求将继续执行"),
    CHECKPOINT(103, "可以预加载"),
    OK(200, "请求已经成功处理"),
    CREATED(201, "请求已经成功处理，并创建了资源"),
    ACCEPTED(202, "请求已经接受，等待执行"),
    NON_AUTHORITATIVE_INFORMATION(203, "请求已经成功处理，但是信息不是原始的"),
    NO_CONTENT(204, "请求已经成功处理，没有内容需要返回"),
    RESET_CONTENT(205, "请求已经成功处理，请重置视图"),
    PARTIAL_CONTENT(206, "部分Get请求已经成功处理"),
    MULTI_STATUS(207, "请求已经成功处理，将返回XML消息体"),
    ALREADY_REPORTED(208, "请求已经成功处理，一个DAV的绑定成员被前一个请求枚举，并且没有被再一次包括"),
    IM_USED(226, "请求已经成功处理，将响应一个或者多个实例"),
    MULTIPLE_CHOICES(300, "提供可供选择的回馈"),
    MOVED_PERMANENTLY(301, "请求的资源已经永久转移"),
    FOUND(302, "请重新发送请求"),
    SEE_OTHER(303, "请以Get方式请求另一个URI"),
    NOT_MODIFIED(304, "资源未改变"),
    USE_PROXY(305, "请通过Location域中的代理进行访问"),
    TEMPORARY_REDIRECT(307, "请求的资源临时从不同的URI响应请求"),
    RESUME_INCOMPLETE(308, "请求的资源已经永久转移"),
    BAD_REQUEST(400, "请求错误，请修正请求"),
    UNAUTHORIZED(401, "没有被授权或者授权已经失效"),
    PAYMENT_REQUIRED(402, "预留状态"),
    FORBIDDEN(403, "请求被理解，但是拒绝执行"),
    NOT_FOUND(404, "资源未找到"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许被执行"),
    NOT_ACCEPTABLE(406, "请求的资源不满足请求者要求"),
    PROXY_AUTHENTICATION_REQUIRED(407, "请通过代理进行身份验证"),
    REQUEST_TIMEOUT(408, "请求超时"),
    CONFLICT(409, "请求冲突"),
    GONE(410, "请求的资源不可用"),
    LENGTH_REQUIRED(411, "Content-Length未定义"),
    PRECONDITION_FAILED(412, "不满足请求的先决条件"),
    REQUEST_ENTITY_TOO_LARGE(413, "请求发送的实体太大"),
    REQUEST_URI_TOO_LONG(414, "请求的URI超长"),
    UNSUPPORTED_MEDIA_TYPE(415, "请求发送的实体类型不受支持"),
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Range指定的范围与当前资源可用范围不一致"),
    EXPECTATION_FAILED(417, "请求头Expect中指定的预期内容无法被服务器满足"),
    UNPROCESSABLE_ENTITY(422, "请求格式正确，但是由于含有语义错误，无法响应"),
    LOCKED(423, "当前资源被锁定"),
    FAILED_DEPENDENCY(424, "由于之前的请求发生错误，导致当前请求失败"),
    UPGRADE_REQUIRED(426, "客户端需要切换到TLS1.0"),
    PRECONDITION_REQUIRED(428, "请求需要提供前置条件"),
    TOO_MANY_REQUESTS(429, "请求过多"),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "请求头超大，拒绝请求"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    NOT_IMPLEMENTED(501, "服务器不支持当前请求的部分功能"),
    BAD_GATEWAY(502, "响应无效"),
    SERVICE_UNAVAILABLE(503, "服务器维护或者过载，拒绝服务"),
    GATEWAY_TIMEOUT(504, "上游服务器超时"),
    HTTP_VERSION_NOT_SUPPORTED(505, "不支持的HTTP版本"),
    VARIANT_ALSO_NEGOTIATES(506, "服务器内部配置错误"),
    INSUFFICIENT_STORAGE(507, "服务器无法完成存储请求所需的内容"),
    LOOP_DETECTED(508, "服务器处理请求时发现死循环"),
    BANDWIDTH_LIMIT_EXCEEDED(509, "服务器达到带宽限制"),
    NOT_EXTENDED(510, "获取资源所需的策略没有被满足"),
    NETWORK_AUTHENTICATION_REQUIRED(511, "需要进行网络授权");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 根据code获取message
     *
     * @param code
     * @return
     */
    public static String getMessageByCode(Integer code) {
        for (ResultCode ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getMessage();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ResultCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}