package com.github.xuqplus2.blog.scheduled;

import com.github.xuqplus2.blog.repository.OAuthCallbackAddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
public class CallbackAddressTask {

    @Autowired
    OAuthCallbackAddressRepository oAuthCallbackAddressRepository;

    @Transactional
    @Scheduled(fixedDelay = 1000L * 60 * 10)
    public void cleanCallbackAddress() {
        run();
    }

    private void run() {
        try {
            oAuthCallbackAddressRepository.deleteByCreateAtLessThan(System.currentTimeMillis() - 1000L * 60 * 20);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
