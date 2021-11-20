# HTTP/2 Client

Communication with Spring Boot Push Server over HTTP/2.

## Based:

- [JEP 110: HTTP/2 Client](https://openjdk.java.net/jeps/110)
- [Introducing Servlet 4.0 Server Push Using Spring Boot 2.1](https://dzone.com/articles/introducing-servlet-40-server-push-using-spring-bo)
- [HTTP/2 Server Push Via Java 11 HTTP Client API](https://dzone.com/articles/http2-server-push-via-http-client-api)

## Run

1. If needed generate a new pair of key:

```shell
keytool -genkey -keyalg RSA -alias tomcatssl -keystore keystore.jks -validity 365 -keysize 2048
```

2. Add `127.0.0.1 http2.local` entry in `/etc/hosts`
