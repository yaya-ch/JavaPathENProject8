package tourguide.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collection;

import static springfox.documentation.builders.PathSelectors.any;

/**
 * The Swagger documentation configuration class.
 *
 * @author Yahia CHERIFI
 * @since V1.0.1
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * The Docket bean.
     * @return a new Docket instance
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("tourguide.controller"))
                .paths(any()).build().apiInfo(apiDocInfoData());
    }

    /**
     * Provides information about the api.
     * @return a new instance of ApiInfo
     */
    private ApiInfo apiDocInfoData() {
        Collection<VendorExtension> vendorExtensions = new ArrayList<>();
        return new ApiInfo(
                "Tour Guide Api",
                "Spring boot REST API for the Tour Guide application",
                "1.0.1",
                "https://swagger.io/specification/",
                new Contact("Yahia CHERIFI",
                        "https://yaya-ch.github.io/3-page-web/",
                        "fake@email.com"),
                "Apache Licence Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                vendorExtensions);
    }
}
