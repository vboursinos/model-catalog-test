package ai.turintech.modelcatalog.rest.config;

import ai.turintech.components.jpa.search.data.entity.SearchConfig;
import ai.turintech.components.jpa.search.data.mapper.RsqlMapper;
import ai.turintech.components.jpa.search.repository.SearchConfigRepository;
import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.entity.Model;
import ai.turintech.modelcatalog.to.ModelTO;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public ModelTO modelTO() {
        return new ModelTO(); // Or create an instance as needed
    }

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

    @Bean
    public Mapper dozerMapper() {
        return DozerBeanMapperBuilder.buildDefault();
    }

//    @Bean
//    public SearchConfigRepository searchConfigRepository(){
//        return new SearchConfigRepository() {
//            @Override
//            public Iterable<SearchConfig> findAll(Sort sort) {
//                return null;
//            }
//
//            @Override
//            public Page<SearchConfig> findAll(Pageable pageable) {
//                return null;
//            }
//
//            @Override
//            public List<SearchConfig> findByClase(String nombreClase) {
//                return null;
//            }
//
//            @Override
//            public SearchConfig findByFilter(String nombreClase, String nombreFiltro) {
//                return null;
//            }
//        };
//    }

    @Bean
    public RsqlMapper rsqlMapper(){
        return new RsqlMapper();
    }
}