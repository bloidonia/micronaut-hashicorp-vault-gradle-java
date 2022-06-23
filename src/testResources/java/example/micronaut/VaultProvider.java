package example.micronaut;

import io.micronaut.testresources.testcontainers.AbstractTestContainersProvider;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.vault.VaultContainer;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VaultProvider extends AbstractTestContainersProvider<VaultContainer<?>> {

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
    protected Optional<String> resolveProperty(String propertyName, VaultContainer container) {
        return Optional.of("http://" + container.getHost() + ":" + container.getMappedPort(VAULT_PORT));
    }

    @Override
    protected VaultContainer<?> createContainer(DockerImageName imageName, Map properties) {
        System.out.println(properties);
        return new VaultContainer<>(imageName)
                .withVaultToken(properties.get("token").toString());
    }

    @Override
    public List<String> getResolvableProperties(Map<String, Collection<String>> propertyEntries, Map<String, Object> testResourcesConfig) {
        return Collections.singletonList(VAULT_CLIENT_URI_PROPERTY);
    }
}
