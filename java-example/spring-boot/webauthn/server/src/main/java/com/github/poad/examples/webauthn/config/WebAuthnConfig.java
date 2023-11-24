package com.github.poad.examples.webauthn.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties(prefix = "webauthn")
public class WebAuthnConfig {

    public WebAuthnConfig(RelyingParty rp, Long timeout, Origin origin) {
        this.rp = rp;
        this.timeout = timeout;
        this.origin = origin;
    }

    public record RelyingParty(String id, String name) {
    }

    public record Origin(String protocol, String host, int port) {

        public String asString() {
                return toString();
            }

            public String asStringWithoutPort() {
                return protocol + "://" + host;
            }

            @Override
            public String toString() {
                return protocol + "://" + host + ":" + port;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Origin origin = (Origin) o;
                return port == origin.port &&
                        protocol.equals(origin.protocol) &&
                        host.equals(origin.host);
            }

    }

    private final RelyingParty rp;
    private final Long timeout;
    private final Origin origin;

    public RelyingParty getRp() {
        return rp;
    }

    @Bean
    public Long getTimeout() {
        return timeout;
    }

    public Origin getOrigin() {
        return origin;
    }
}
