package com.github.poad.examples.webauthn.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

@ConfigurationProperties(prefix = "webauthn")
public class WebAuthnConfig {

    @ConfigurationProperties(prefix = "webauthn.rp")
    public static class RelyingParty {
        private final String id;
        private final String name;

        @ConstructorBinding
        RelyingParty(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    @ConfigurationProperties(prefix = "webauthn.origin")
    public static class Origin {
        private final String protocol;
        private final String host;
        private final int port;

        @ConstructorBinding
        Origin(String protocol, String host, int port) {
            this.protocol = protocol;
            this.host = host;
            this.port = port;
        }

        public String getProtocol() {
            return protocol;
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }

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

        @Override
        public int hashCode() {
            return Objects.hash(protocol, host, port);
        }
    }

    private final RelyingParty rp;
    private final Long timeout;
    private final Origin origin;

    @ConstructorBinding
    WebAuthnConfig(RelyingParty rp, Long timeout, Origin origin) {
        this.rp = rp;
        this.timeout = timeout;
        this.origin = origin;
    }

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
