# auth123-quickstarts

使用keycloak身份认证系统保护APP，WEB, API的示例代码

前3个demo示例都使用名称为quickstart的realm。
**auth123.top 提供了开箱即用的Keycloak免费服务**，demo控制台地址： <https://dev.auth123.top/admin/quickstart/console/> 账号：admin/admin

## 示例清单

- [spring-demo](/example/spring-demo/): 演示Spring-boot集成keycloak保护restful API
- [quarkus-demo](/example/quarkus-demo/): 演示Quarkus集成keycloak保护restful API
- [react-demo](/example/react-demo/): 演示Ant.design（react）集成keycloak保护Web页面
- [q-admin](https://github.com/ben-hua/q-admin): 演示完整的前后台管理平台，包含完整的用户权限，包括API权限控制，菜单页面权限控制

## quickstart realm详情

- **realm**: quickstart

- **roles**: admin, user

- **users**:

        | Username | Password | Roles        |
        |----------|----------|--------------|
        | admin    | admin    | admin,user   |
        | alice    | alice    | user         |

- **client**: backend-service

- **Policy**: 使用 Role-based access control (RBAC) 策略

        | protected resource        | uris          | Roles        |
        |---------------------------|---------------|--------------|
        | Administrator resource    | /api/admin/*  | admin,user   |
        | User resource             | /api/users/*  | user         |

## 其他

- 如需创建自己的realm, 请提issue。 格式 title： add a new realm: xxx；内容中提供邮件地址。默认账号admin/admin
