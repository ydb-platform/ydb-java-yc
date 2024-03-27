package tech.ydb.auth.iam;

import java.nio.file.Paths;

import tech.ydb.auth.AuthProvider;


/**
 * This provider is using AuthProvider of Yandex Cloud SDK for Java.
 * Usage example:
 *
 * <pre>{@code

 GrpcTransport transport = GrpcTransport.forEndpoint("some-ydb-endpoint", "my_db")
      .withAuthProvider(CloudAuthHelper.getAuthProviderFromEnviron())
      .build();

 } </pre>
 *
 * @author Vasilii Briginets
 */
public class CloudAuthHelper {

    public static AuthProvider getAuthProviderFromEnviron() {
        return () -> {
            String anonCredentials = System.getenv("YDB_ANONYMOUS_CREDENTIALS");
            if (anonCredentials != null && anonCredentials.equals("1")) {
                return null;
            }

            String saKeyFile = System.getenv("YDB_SERVICE_ACCOUNT_KEY_FILE_CREDENTIALS");
            if (saKeyFile != null) {
                return CloudAuthIdentity.serviceAccountIdentity(Paths.get(saKeyFile), null);
            }

            String metadataCredentials = System.getenv("YDB_METADATA_CREDENTIALS");
            if (metadataCredentials != null && metadataCredentials.equals("1")) {
                return CloudAuthIdentity.metadataIdentity(null);
            }

            String accessToken = System.getenv("YDB_ACCESS_TOKEN_CREDENTIALS");
            if (accessToken != null) {
                return CloudAuthIdentity.iamTokenIdentity(accessToken);
            }

            return CloudAuthIdentity.metadataIdentity(null);
        };
    }

    public static AuthProvider getMetadataAuthProvider() {
        return getMetadataAuthProvider(null);
    }

    public static AuthProvider getServiceAccountFileAuthProvider(String filePath) {
        return () -> CloudAuthIdentity.serviceAccountIdentity(Paths.get(filePath), null);
    }

    public static AuthProvider getServiceAccountJsonAuthProvider(String json) {
        return () -> CloudAuthIdentity.serviceAccountIdentity(json, null);
    }

    public static AuthProvider getMetadataAuthProvider(String metadataURL) {
        return () -> CloudAuthIdentity.metadataIdentity(metadataURL);
    }

    public static AuthProvider getServiceAccountFileAuthProvider(String filePath, String iamEndpoint) {
        return () -> CloudAuthIdentity.serviceAccountIdentity(Paths.get(filePath), iamEndpoint);
    }

    public static AuthProvider getServiceAccountJsonAuthProvider(String json, String iamEndpoint) {
        return () -> CloudAuthIdentity.serviceAccountIdentity(json, iamEndpoint);
    }
}
