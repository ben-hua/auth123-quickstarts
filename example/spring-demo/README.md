# Getting Started

在此示例中，我们构建了一个非常简单的微服务，它提供两个endpoints：

* `/api/users/me` - 只有被授予“user”角色的用户才能访问。
* `/api/admin` - 只有被授予“admin”角色的用户才能访问。

这些endpoints是受保护的，只有在客户端请求中带有bearer token时才能访问，
这个token必须有效（例如：签名、过期和受众）并且受到微服务的信任。

bearer token是由Keycloak服务器颁发，这个token代表了一个授权的的主体。

这是一个非常简单的示例，使用 Role-based access control (RBAC) 策略来管理对资源的访问。
但是，Keycloak 支持其他类型的策略，您可以使用它们来执行更细粒度的访问控制。
通过使用此示例，您将看到您的应用程序与授权策略完全解耦，并且强制执行完全基于所访问的资源。
**auth123.top 提供了开箱即用的Keycloak免费服务**，demo控制台地址： <https://dev.auth123.top/admin/quickstart/console/> 账号：admin/admin

用户信息：

| Username | Password | Roles        |
|----------|----------|--------------|
| admin    | admin    | admin,user   |
| alice    | alice    | user         |

## Requirements

To compile and run this demo you will need:

* JDK 17+
* Apache Maven 3.8.6
* Keycloak 授权认证服务

## 启动Keycloak授权认证服务器

> **注意**：这个demo工程无需启动 Keycloak 服务器 - 使用了在线keycloak服务，控制台地址： <https://dev.auth123.top/admin/quickstart/console/>

### 启用quarkus应用

````bash
mvn spring-boot:run
````

### 测试应用程序

该应用程序使用bearer token授权，所以第一件事是从 Keycloak 服务器获取访问令牌
以便访问应用程序的资源：

```bash
export access_token=$(\
    curl --insecure -X POST https://dev.auth123.top/realms/quickstart/protocol/openid-connect/token \
    --user backend-service:secret \
    -H 'content-type: application/x-www-form-urlencoded' \
    -d 'username=alice&password=alice&grant_type=password' | jq --raw-output '.access_token' \
 )
```

上面的示例得到了用户“alice”的访问令牌。

任何用户都可以访问`http://localhost:8080/api/users/me` 端点
它基本上返回一个 JSON 有效负载，其中包含有关用户的详细信息。

```bash
curl -v -X GET \
  http://localhost:8080/api/users/me \
  -H "Authorization: Bearer "$access_token
```

`http://localhost:8080/api/admin`只能由具有“admin”角色的用户访问。如果您尝试使用先前颁发的访问令牌以下方式访问此端点
  您应该收到“403”响应。

```bash
curl -v -X GET \
   http://localhost:8080/api/admin \
   -H "Authorization: Bearer "$access_token
```

为了访问这个endpoint，您应该获取“admin”用户的令牌：

```bash
export access_token=$(\
    curl --insecure -X POST https://dev.auth123.top/realms/quickstart/protocol/openid-connect/token \
    --user backend-service:secret \
    -H 'content-type: application/x-www-form-urlencoded' \
    -d 'username=admin&password=admin&grant_type=password' | jq --raw-output '.access_token' \
 )
```

## Reference Documentation

For further reference, please consider the following sections:

* [OAuth2 Resource Server](https://docs.spring.io/spring-boot/docs/3.2.1/reference/htmlsingle/index.html#web.security.oauth2.server)
* [Keycloak Authorization Services](https://www.keycloak.org/docs/latest/authorization_services/)
* [Keycloak Documentation](https://www.keycloak.org/documentation)
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.1/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.1/reference/htmlsingle/index.html#web)
* [Spring Security](https://docs.spring.io/spring-boot/docs/3.2.1/reference/htmlsingle/index.html#web.security)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
