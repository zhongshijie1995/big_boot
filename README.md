# big_boot

Springboot大操场，集成一些基于Springboot的实验

## 数据库版本管理
实现功能：
- [x] 数据库（表结构+数据）更新和回滚
    - [x] 按变更集
    - [x] 按实施日期
    - [x] 按基线
- [x] 数据库版本基线标记
- [x] 数据库间表结构比较
- [x] 数据库版本文档生成

### 使用方法
1. 在`db/liquibase.properties`中指定数据库环境；
2. 在`db/changelog.xml`中编辑纳入版本管理的数据库变更脚本文件夹；
3. 运行`src/main/java/com/zhongshijie1995/liquibase/LiquibaseCli.java`根据提示进行操作。

### 涉及文件及说明

- db
  - liquibase 数据库版本管理工具
  - changelog.xml 数据库变更日志登记文件
  - liquibase.properties 数据库环境信息
  - yyyyMMdd-ProjectName 数据库变更窗口脚本文件
- src/java/main/com/zhongshijie1995/liquibase
  - LiquibaseCli.java 数据库变更实施工具

## Springboot服务

### Restful

<table>
  <tr>
    <th>模块</th>
    <th>请求URL</th>
    <th>请求结果</th>
  </tr>
  <tr>
    <td rowspan="3">info</td>
    <td>info/version</td>
    <td>版本号</td>
  </tr>
  <tr>
    <td>info/time</td>
    <td>构建时间</td>
  </tr>
  <tr>
    <td>info/all</td>
    <td>版本信息汇总</td>
  </tr>
</table>

## GithubAction持续集成
