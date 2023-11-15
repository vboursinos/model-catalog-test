package ai.turintech.modelcatalog.rest.config;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.entity.Model;
import ai.turintech.modelcatalog.to.ModelTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Class<ModelTO> modelTOClass() {
        return ModelTO.class;
    }

    @Bean
    public Class<Model> modelClass() {
        return Model.class;
    }

    @Bean
    public Class<ModelDTO> modelDTOClass() {
        return ModelDTO.class;
    }

}