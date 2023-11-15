package ai.turintech.modelcatalog.rest.support.apidoc.customizer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * A OpenApi customizer to set up {@link io.swagger.v3.oas.models.OpenAPI} with JHipster settings.
 */
@Component
public class CustomOpenApiCustomizer implements OpenApiCustomizer, Ordered {

  /** The default order for the customizer. */
  public static final int DEFAULT_ORDER = 0;

  private int order = DEFAULT_ORDER;

  // TODO- Include a configuration object to the constructor to be able to handle all configuration
  // parameters
  // those parameters are hardcoded for now to speed up things.
  public CustomOpenApiCustomizer() {}

  /** {@inheritDoc} */
  @Override
  public void customise(OpenAPI openAPI) {
    Contact contact =
        new Contact().name("jose@turintech.ai").url("www.turintech.ai").email("jose@turintech.ai");

    openAPI.info(
        new Info()
            .contact(contact)
            .title("Minister of Java World")
            .description("Model catalog API")
            .version("0.0.1-SNAPSHOT")
            .termsOfService("Use it carefully")
            .license(
                new License()
                    .name("Copyright Turintech.ai ALL rights reserved.")
                    .url("wwww.turintech.ai")));

    /*for (server : properties.getServers()) {
        openAPI.addServersItem(new Server().url(server.getUrl()).description(server.getDescription()));
    }*/
  }

  /**
   * Setter for the field <code>order</code>.
   *
   * @param order a int.
   */
  public void setOrder(int order) {
    this.order = order;
  }

  /** {@inheritDoc} */
  @Override
  public int getOrder() {
    return order;
  }
}
