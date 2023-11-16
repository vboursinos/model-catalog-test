package ai.turintech.modelcatalog.callable;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelPaginatedListDTO;
import ai.turintech.modelcatalog.dtoentitymapper.ModelMapper;
import ai.turintech.modelcatalog.repository.ModelRepository;
import ai.turintech.modelcatalog.service.PaginationConverter;
import ai.turintech.modelcatalog.entity.Model;
import java.util.*;
import java.util.concurrent.Callable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
@Scope("prototype")
public class ModelCallable<T> implements Callable<T> {
  private String name;
  private ModelDTO modelDTO;

  private Pageable pageable;

  public ModelCallable(String name, Pageable pageable) {
    this.name = name;
    this.pageable = pageable;
  }

  @Autowired private ModelRepository modelRepository;

  @Autowired private List<JpaRepository> repositories;
  @Autowired private ModelMapper modelMapper;
  @Autowired private PaginationConverter paginationConverter;

    public ModelPaginatedListDTO findAll(){
        List<Model> models = modelRepository.findAll(pageable).getContent();
        ModelPaginatedListDTO paginatedList = paginationConverter.getPaginatedList(
                models.stream().map(modelMapper::toDto).toList(),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                modelRepository.count());
        return paginatedList;
    }

  @Override
  public T call() {
    if (name.equalsIgnoreCase("findAll")) {
      return (T) findAll();
    }
    return null;
  }
}
