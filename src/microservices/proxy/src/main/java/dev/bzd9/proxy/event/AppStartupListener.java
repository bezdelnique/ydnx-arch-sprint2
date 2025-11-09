package dev.bzd9.proxy.event;

import dev.bzd9.proxy.config.ProxyConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    private final ProxyConfig proxyConfig;

    public AppStartupListener(ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
      log.info("Proxy config: {}", proxyConfig);
    }

}
