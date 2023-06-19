[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/ydb-platform/ydb-java-yc/blob/master/LICENSE)
[![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo1.maven.org%2Fmaven2%2Ftech%2Fydb%2Fauth%2Fyc-auth-provider%2Fmaven-metadata.xml)](https://mvnrepository.com/artifact/tech.ydb.auth/ydb-auth-provider)
[![Build](https://img.shields.io/github/actions/workflow/status/ydb-platform/ydb-java-yc/build.yaml?branch=develop)](https://github.com/ydb-platform/ydb-java-yc/actions/workflows/build.yaml)

## YDB Yandex Cloud IAM provider
> Module to connect to YDB inside yandex-cloud

## Overview <a name="Overview"></a>

Currently package provides authentication provider to connect to YDB inside yandex-cloud.

#### Minimum requirements ####

To use YDB Yandex Cloud IAM provider you will need **Java 1.8+**.

#### Usage
The recommended way to use the YDB Yandex Cloud IAM provider in your project is to consume it from Maven.
Specify the YDB Java Protobuf module in the dependencies:

```xml
<dependency>
    <groupId>tech.ydb.auth</groupId>
    <artifactId>yc-auth-provider</artifactId>
    <version>2.1.0</version>
</dependency>
```
