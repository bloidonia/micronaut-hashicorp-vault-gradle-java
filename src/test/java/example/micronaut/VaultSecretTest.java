package example.micronaut;

import io.micronaut.context.ApplicationContext;
import io.micronaut.security.oauth2.configuration.OauthClientConfiguration;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class VaultSecretTest {

    @Inject
    ApplicationContext applicationContext;

    @Test
    void secretsAreUsedForConfiguration() {
        OauthClientConfiguration oauthClientConfiguration = applicationContext.getBean(OauthClientConfiguration.class);

        assertEquals("hello", oauthClientConfiguration.getClientId());
        assertEquals("world", oauthClientConfiguration.getClientSecret());
    }
}
