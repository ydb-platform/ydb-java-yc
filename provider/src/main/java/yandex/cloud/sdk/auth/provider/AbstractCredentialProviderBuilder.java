package yandex.cloud.sdk.auth.provider;

public abstract class AbstractCredentialProviderBuilder<BUILDER extends AbstractCredentialProviderBuilder<BUILDER>>
        implements CredentialProvider.Builder {
    @Override
    public CredentialProvider build() {
        return providerBuild();
    }

    protected abstract CredentialProvider providerBuild();

}
