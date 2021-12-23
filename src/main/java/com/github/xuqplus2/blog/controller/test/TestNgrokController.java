package com.github.xuqplus2.blog.controller.test;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@Slf4j
@RestController
@RequestMapping("blog/test/ngrok")
public class TestNgrokController {

    @RequestMapping
    public String a(@RequestBody String text) {
        log.debug("text>{}", text);
        try {
            for (String s : text.split("\n")) {
                if (s.contains("\u0012\u0013")) {
                    log.info("s>{}", s);
                    int l = s.indexOf("http");
                    int r = s.indexOf("ngrok.io") + 8;
                    String domain = new URL(s.substring(l, r)).getHost();
                    log.info("domain>{}", domain);
                    triggerJenkinsBuild(domain);
                    break;
                }
            }
        } catch (Exception e) {

        }
        return "ok";
    }

    DateTime lastTrigger = null;
    int triggerCD = 1000; // time in seconds

    synchronized void triggerJenkinsBuild(String domain) throws Exception {
        if (null != lastTrigger && DateTime.now().minusSeconds(triggerCD).isBefore(lastTrigger)) {
            return;
        }
        final String tokenName = "token-4-xuqplus";
        final String jenkinsUsername = "xuqplus";
        final String jenkinsUserToken = "11a41b785bba7d9ed80a1c908f3fc1454a";
        final String cause = "Triggered+by+blob+-+try+update+the+domain+to+";
        final String urlTemplate = "https://%s/jenkins/job/ngrok-domain-trigger/buildWithParameters?token=%s&cause=%s%s&DOMAIN=%s";
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        String response = okHttpClient.newCall(new Request.Builder()
                .url(String.format(urlTemplate, domain, tokenName, cause, domain, domain))
                .addHeader("Authorization", Credentials.basic(jenkinsUsername, jenkinsUserToken))
                .build()).execute().body().string();
        log.info("response>{}", response);
        lastTrigger = DateTime.now();
    }
}
