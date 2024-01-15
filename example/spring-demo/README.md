# Getting Started

spring-demo: 使用keycloak保护Spring Boot REST Service的API

这个demo暴露了2个API:

* <http://localhost:8080/api/users/me> - 有user角色的可以访问
* <http://localhost:8080/api/admin>    - 有admin角色的可以访问

用户信息:

| Username | Password | Roles        |
|----------|----------|--------------|
| admin    | admin    | admin,user   |
| alice    | alice    | user         |

## 启动demo应用

````
mvn spring-boot:run
````

### 验证

首先获得alice的access token，然后验证两个API，第一个API会返回403没有授权

```shell
export access_token=$(\
    curl --insecure -X POST https://dev.auth123.top/realms/quarkus/protocol/openid-connect/token \
    --user backend-service:secret \
    -H 'content-type: application/x-www-form-urlencoded' \
    -d 'username=alice&password=alice&grant_type=password' | jq --raw-output '.access_token' \
 )
```

```shell
curl http://localhost:8080/api/admin \
  -H "Authorization: Bearer "$access_token
```

```shell
curl http://localhost:8080/api/users/me \
  -H "Authorization: Bearer "$access_token
```

获得admin的access token，然后验证两个API，都可以正常返回

```shell
export access_token=$(\
    curl --insecure -X POST https://dev.auth123.top/realms/quarkus/protocol/openid-connect/token \
    --user backend-service:secret \
    -H 'content-type: application/x-www-form-urlencoded' \
    -d 'username=admin&password=admin&grant_type=password' | jq --raw-output '.access_token' \
 )
```

## 依赖

* JDK 17
* Apache Maven 3.8.6
* Spring Boot 3.2.1
* Keycloak 23+

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
