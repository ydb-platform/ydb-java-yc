package tech.ydb.auth.iam;

import java.nio.file.Path;

import yandex.cloud.sdk.auth.provider.ApiKeyCredentialProvider;
import yandex.cloud.sdk.auth.provider.ComputeEngineCredentialProvider;
import yandex.cloud.sdk.auth.provider.CredentialProvider;
import yandex.cloud.sdk.auth.provider.IamTokenCredentialProvider;

import tech.ydb.auth.AuthIdentity;

public class CloudAuthIdentity implements AuthIdentity {
    private final CredentialProvider credentialProvider;

    CloudAuthIdentity(CredentialProvider credentialProvider) {
        this.credentialProvider = credentialProvider;
    }

    @Override
    public String getToken() {
        return credentialProvider.get().getToken();
    }

    @Override
    public void close() {
        credentialProvider.close();
    }

    public static AuthIdentity metadataIdentity(String metadataURL) {
        ComputeEngineCredentialProvider.Builder builder = ComputeEngineCredentialProvider.builder();
        if (metadataURL != null && !metadataURL.isEmpty()) {
            builder = builder.metadataServerUrl(metadataURL);
        }
        return new CloudAuthIdentity(builder.enableCache().build());
    }

    public static AuthIdentity iamTokenIdentity(String accessToken) {
        return new CloudAuthIdentity(
                IamTokenCredentialProvider.builder()
                        .token(accessToken)
                        .build()
        );
    }

    public static AuthIdentity serviceAccountIdentity(Path serviceAccountFile, String iamEndpoint) {
        ApiKeyCredentialProvider.Builder builder = ApiKeyCredentialProvider.builder().fromFile(serviceAccountFile);
        if (iamEndpoint != null && !iamEndpoint.isEmpty()) {
            builder = builder.cloudIAMEndpoint(iamEndpoint);
        }
        return new CloudAuthIdentity(builder.enableCache().build());
    }

    public static AuthIdentity serviceAccountIdentity(String serviceAccountJson, String iamEndpoint) {
        ApiKeyCredentialProvider.Builder builder = ApiKeyCredentialProvider.builder().fromJson(serviceAccountJson);
        if (iamEndpoint != null && !iamEndpoint.isEmpty()) {
            builder = builder.cloudIAMEndpoint(iamEndpoint);
        }
        return new CloudAuthIdentity(builder.enableCache().build());
    }

}
