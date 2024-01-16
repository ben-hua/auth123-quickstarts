# Using Keycloak Authorization Services and Policy Enforcer to Protect JAX-RS Applications

在此示例中，我们构建了一个非常简单的微服务，它提供两个endpoints：

* `/api/users/me` - 只有被授予“user”角色的用户才能访问。
* `/api/admin` - 只有被授予“admin”角色的用户才能访问。

这些endpoints是受保护的，只有在客户端请求中带有bearer token时才能访问，
这个token必须有效（例如：签名、过期和受众）并且受到微服务的信任。

bearer token是由Keycloak服务器颁发，这个token代表了一个授权的的主体。

这是一个非常简单的示例，使用 RBAC 策略来管理对资源的访问。
但是，Keycloak 支持其他类型的策略，您可以使用它们来执行更细粒度的访问控制。
通过使用此示例，您将看到您的应用程序与授权策略完全解耦，并且强制执行完全基于所访问的资源。

**keycloak服务，控制台演示地址** <https://dev.auth123.top/admin/quickstart/console/>

## Requirements

To compile and run this demo you will need:

* JDK 11+
* Apache Maven 3.8.6
* Keycloak 授权认证服务

## 启动Keycloak授权认证服务器

> **注意**：这个demo工程无需启动 Keycloak 服务器 - 使用了在线keycloak服务，控制台地址： <https://dev.auth123.top/admin/quickstart/console/>

### 启用quarkus应用

````bash
mvn quarkus:dev
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

## 引用

1. 本示例Quarkus官方示例代码,只是将keycloak地址替换了，[原github地址](https://github.com/quarkusio/quarkus-quickstarts/tree/main/security-keycloak-authorization-quickstart)
