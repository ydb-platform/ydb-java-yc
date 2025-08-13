package yandex.cloud.sdk.auth.provider;

import java.time.Instant;

import yandex.cloud.sdk.auth.IamToken;

/**
 * Stores exchanged IAM token until it is expired. When it is expired, retrieves a new token from inner {@link CredentialProvider}
 */
public class CachingCredentialProvider implements CredentialProvider {
    private final CredentialProvider delegate;

    private transient volatile IamToken iamToken;

    public CachingCredentialProvider(CredentialProvider.Builder builder) {
        this.delegate = builder.build();
    }

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public IamToken get() {
        Instant now = Instant.now();
        Instant expiresAt = iamToken == null ? Instant.MIN : iamToken.getExpiresAt();
        if (expiresAt.isBefore(now)) {
            synchronized (this) {
                expiresAt = iamToken == null ? Instant.MIN : iamToken.getExpiresAt();
                if (expiresAt.isBefore(now)) {
                    this.iamToken = this.delegate.get();
                }
            }
        }

        return this.iamToken;
    }
}
