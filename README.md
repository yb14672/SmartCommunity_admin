## 系统介绍

智慧社区 安全 高效 智能，多社区管理，一套系统支持N个社区入住
社区管理、业主入住、房屋绑定、访客通行 投诉表扬 社区交流 在线报修 在线缴费 咨询建议 通知公告等
后期开放:

- 智能门禁 使用蓝牙或者人脸识别门禁，无需携带钥匙,便捷管理。原有社区及单元门禁改动方便，成本低。未来社区门禁发展趋势，更安全、更高效
- 智能摄像头 24小时实时监控，实力保障社区环境安全
- 智慧停车场 实现车库在线缴费、在线查询、自动缴费等功能，提高物业管理效率，节约停车时间，实现无感出入
- 社区O2O 线上广告 社区团购 以及周边其他商业，一站式服务，为您打造便捷生态

## 软件架构

### 		前后端分离

#### 				后端

- springboot框架基石
- Minio 图片存储服务
- SpringMVC控制层
- MybatisPlus持久层
- Redis缓存
- Mysql数据库
- 短信服务使用阿里云短信服务

#### 前端

- vue2.x
- vue-element-admin

## 功能模块

### 后台

- 登录

> 根据账户、密码、验证码登录


- 个人中心

> 个人信息展示、设置


- 系统管理

    - 菜单管理

  > 系统菜单的添加、设置、排序、缓存等


    - 角色管理

  > 系统角色权限添加、修改、设置等


    - 字典管理

  > 系统数据状态字典操作


    - 部门管理

  > 用户所属的部门管理


    - 岗位管理

  > 用户就职岗位管理


    - 用户管理

  > 系统用户的管理


    - 日志管理

  > 系统操作日志


- 社区资产

    - 小区信息

  > 记录不同小区的详细信息及其管理操作


    - 楼栋信息

  > 记录小区内不同楼栋的具体信息及其管理操作


    - 单元信息

  > 记录每个单元的详细信息及其管理操作


    - 房屋信息

  > 记录每户房屋的详细信息及其管理操作


- 小区管理

    - 业主审核

  > 审核业主通过小程序的房屋绑定信息


    - 业主信息

  > 审核完成的业主信息


- 互动管理

    - 社区互动

  > 对业主通过小程序的互动信息进行记录、监督、追踪


- 访客管理

    - 访客邀请

  > 对业主邀请的访客进行记录、监督、追踪


- 投诉建议

  > 对业主提出的投诉建议进行追踪、处理


- 报修管理

    - 报修信息

  > 对报事报修信息记录、追踪、处理


### 小程序

- 个人中心


- 社区


- 生活


- 首页
