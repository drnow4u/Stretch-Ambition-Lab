package com.example.http2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class Http2ClientApplicationTests {

    private static final ConcurrentMap<HttpRequest,
            CompletableFuture<HttpResponse<String>>> promisesMap
            = new ConcurrentHashMap<>();

    private static final Function<HttpRequest,
            HttpResponse.BodyHandler<String>> promiseHandler
            = (HttpRequest req) -> HttpResponse.BodyHandlers.ofString();

    @Test
    void contextLoads() throws GeneralSecurityException, IOException {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new X509TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new SecureRandom());

        HttpsURLConnection.setDefaultSSLSocketFactory(
                sslContext.getSocketFactory());

        HttpClient client = HttpClient.newBuilder()
                .sslContext(sslContext)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://http2.local:8443/account")) // Need to be added into /etc/hosts or DNS
                .build();

        client.sendAsync(request,
                        HttpResponse.BodyHandlers.ofString(), pushPromiseHandler())
                .thenApply(HttpResponse::body)
                .thenAccept((b) -> System.out.println("\nMain resource:\n" + b))
                .join();

        System.out.println("\nPush promises map size: " +
                promisesMap.size() + "\n");

        promisesMap.entrySet().forEach((entry) -> {
            System.out.println("Request = " + entry.getKey() +
                    ", \nResponse = " + entry.getValue().join().body());
        });
    }

    private static HttpResponse.PushPromiseHandler<String> pushPromiseHandler() {
        return HttpResponse.PushPromiseHandler.of(promiseHandler, promisesMap);
    }

}
