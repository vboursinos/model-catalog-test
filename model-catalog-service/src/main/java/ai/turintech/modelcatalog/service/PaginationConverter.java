package ai.turintech.modelcatalog.service;

import ai.turintech.modelcatalog.dto.ModelDTO;
import ai.turintech.modelcatalog.dto.ModelPaginatedListDTO;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PaginationConverter {

  public ModelPaginatedListDTO getPaginatedList(
      List<ModelDTO> allModelList, int page, int size, long totalListSize) {

    List<ModelDTO> modelDTOS;

    modelDTOS = allModelList;

    int totalPageNum = 0;
    if (size > 0) {
      if (totalListSize % size == 0) {
        totalPageNum = (int) (totalListSize / size);
      } else {
        totalPageNum = (int) (totalListSize / size + 1);
      }
    }
    page = page + 1;
    ModelPaginatedListDTO producedFilePaginationResponse =
        new ModelPaginatedListDTO(
            modelDTOS,
            totalListSize,
            totalListSize < size ? (int) totalListSize : size,
            totalPageNum <= 0 ? 1 : totalPageNum,
            page,
            page,
            page - 1 > 0,
            page < totalPageNum,
            page <= 1 ? null : page - 1,
            page >= totalPageNum ? null : page + 1);

    return producedFilePaginationResponse;
  }
}
