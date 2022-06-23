package example.micronaut;

import io.micronaut.testresources.testcontainers.AbstractTestContainersProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.vault.VaultContainer;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VaultProvider extends AbstractTestContainersProvider<VaultContainer<?>> {

    private static final Logger LOG = LoggerFactory.getLogger(VaultProvider.class);

    public static final String ROOT_KEY = "root";
    public static final int VAULT_PORT = 8200;
    public static final String VAULT_CLIENT_URI_PROPERTY = "vault.client.uri";
    public static final String DEFAULT_IMAGE = "vault";

    @Override
    protected String getSimpleName() {
        return DEFAULT_IMAGE;
    }

    @Override
    protected String getDefaultImageName() {
        return DEFAULT_IMAGE;
    }

    @Override
    protected VaultContainer<?> createContainer(DockerImageName imageName, Map<String, Object> requestedProperties, Map<String, Object> testResourcesConfiguration) {
        LOG.info("REQUEST PROPERTIES: {}", requestedProperties);
        LOG.info("TEST RESOURCES CONFIG: {}", testResourcesConfiguration);
        List<String> strings = (List<String>) testResourcesConfiguration.get("containers.vault.secrets");
        return new VaultContainer<>(imageName)
                .withVaultToken(testResourcesConfiguration.get("containers.vault.token").toString())
                .withSecretInVault(
                        testResourcesConfiguration.get("containers.vault.path").toString(),
                        strings.get(0),
                        strings.subList(1, strings.size()).toArray(new String[0])
                );
    }

    @Override
    protected Optional<String> resolveProperty(String propertyName, VaultContainer container) {
        return Optional.of("http://" + container.getHost() + ":" + container.getMappedPort(VAULT_PORT));
    }

    @Override
    public List<String> getResolvableProperties(Map<String, Collection<String>> propertyEntries, Map<String, Object> testResourcesConfig) {
        return Collections.singletonList(VAULT_CLIENT_URI_PROPERTY);
    }
}
