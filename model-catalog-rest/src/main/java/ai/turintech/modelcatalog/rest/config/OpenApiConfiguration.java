package ai.turintech.modelcatalog.rest.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import ai.turintech.modelcatalog.rest.resource.ModelCatalogRestResourcesPackage;
import ai.turintech.modelcatalog.rest.support.apidoc.customizer.CustomOpenApiCustomizer;
import ai.turintech.modelcatalog.rest.support.constants.ApplicationProfiles;


@Configuration
@Profile(ApplicationProfiles.SPRING_PROFILE_API_DOCS)
public class OpenApiConfiguration {

    public static final String API_FIRST_PACKAGE = ModelCatalogRestResourcesPackage.class.getPackageName();

    @Bean
    @ConditionalOnMissingBean(name = "apiFirstGroupedOpenAPI")
    public GroupedOpenApi apiFirstGroupedOpenAPI(
        CustomOpenApiCustomizer jhipsterOpenApiCustomizer
    ) {
        return GroupedOpenApi
            .builder()
            .group("openapi")
            .addOpenApiCustomizer(jhipsterOpenApiCustomizer)
            .packagesToScan(API_FIRST_PACKAGE)
            .pathsToMatch("/api/**")
            .build();
    }
}
