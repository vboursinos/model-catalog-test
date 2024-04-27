package ai.turintech.modelcatalog.rest.config;

import ai.turintech.components.architecture.ArchitectureServicePackage;
import ai.turintech.components.jpa.search.JpaSearchPackage;
import ai.turintech.components.jpa.search.data.entity.JpaSearchEntityPackage;
import ai.turintech.components.jpa.search.repository.JpaSearchRepositoryPackage;
import ai.turintech.components.mapper.EnableIgvMap;
import ai.turintech.modelcatalog.dtoentitymapper.ModelCatalogDtoEntityMapperPackage;
import ai.turintech.modelcatalog.entity.ModelCatalogEntityPackage;
import ai.turintech.modelcatalog.facade.ModelPackageFacadePackage;
import ai.turintech.modelcatalog.repository.ModelCatalogRepositoryPackage;
import ai.turintech.modelcatalog.rest.ModelCatalogRestPackage;
import ai.turintech.modelcatalog.service.ModelCatalogServicePackage;
import ai.turintech.modelcatalog.todtomapper.ModelCatalogToDtoMapperPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(
    basePackageClasses = {
      ModelCatalogRestPackage.class,
      ModelCatalogToDtoMapperPackage.class,
      ModelPackageFacadePackage.class,
      ModelCatalogServicePackage.class,
      ModelCatalogDtoEntityMapperPackage.class,
      ModelCatalogRepositoryPackage.class,
      ModelCatalogEntityPackage.class,
      JpaSearchPackage.class,
      ArchitectureServicePackage.class
    })
@EnableJpaRepositories(
    basePackageClasses = {JpaSearchRepositoryPackage.class, ModelCatalogRepositoryPackage.class},
    repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@EntityScan(basePackageClasses = {ModelCatalogEntityPackage.class, JpaSearchEntityPackage.class})
@EnableIgvMap
@EnableJpaAuditing
public class PackageScanningConfig {}
