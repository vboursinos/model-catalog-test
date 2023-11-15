package ai.turintech.modelcatalog.to;

import ai.turintech.components.data.common.to.AbstractTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModelPaginatedListTO extends AbstractTO implements Serializable {
  private static final long serialVersionUID = -6078657183320503265L;

  @JsonInclude(JsonInclude.Include.ALWAYS)
  private List<ModelTO> docs = new ArrayList<>();

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private long totalDocs;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private int limit;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private int totalPages;

  private int page;

  private int pagingCounter;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Boolean hasPrevPage;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Boolean hasNextPage;

  private Integer prevPage;
  private Integer nextPage;

  public ModelPaginatedListTO() {}

  public ModelPaginatedListTO(
      List<ModelTO> docs,
      long totalDocs,
      int limit,
      int totalPages,
      int page,
      int pagingCounter,
      Boolean hasPrevPage,
      Boolean hasNextPage,
      Integer prevPage,
      Integer nextPage) {
    this.docs = docs;
    this.totalDocs = totalDocs;
    this.limit = limit;
    this.totalPages = totalPages;
    this.page = page;
    this.pagingCounter = pagingCounter;
    this.hasPrevPage = hasPrevPage;
    this.hasNextPage = hasNextPage;
    this.prevPage = prevPage;
    this.nextPage = nextPage;
  }

  public List<ModelTO> getDocs() {
    return docs;
  }

  public void setDocs(List<ModelTO> docs) {
    this.docs = docs;
  }

  public long getTotalDocs() {
    return totalDocs;
  }

  public void setTotalDocs(long totalDocs) {
    this.totalDocs = totalDocs;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getPagingCounter() {
    return pagingCounter;
  }

  public void setPagingCounter(int pagingCounter) {
    this.pagingCounter = pagingCounter;
  }

  public Boolean getHasPrevPage() {
    return hasPrevPage;
  }

  public void setHasPrevPage(Boolean hasPrevPage) {
    this.hasPrevPage = hasPrevPage;
  }

  public Boolean getHasNextPage() {
    return hasNextPage;
  }

  public void setHasNextPage(Boolean hasNextPage) {
    this.hasNextPage = hasNextPage;
  }

  public Integer getPrevPage() {
    return prevPage;
  }

  public void setPrevPage(Integer prevPage) {
    this.prevPage = prevPage;
  }

  public Integer getNextPage() {
    return nextPage;
  }

  public void setNextPage(Integer nextPage) {
    this.nextPage = nextPage;
  }

  @Override
  public String toString() {
    return "ModelPaginatedListDTO{"
        + "docs="
        + docs
        + ", totalDocs="
        + totalDocs
        + ", limit="
        + limit
        + ", totalPages="
        + totalPages
        + ", page="
        + page
        + ", pagingCounter="
        + pagingCounter
        + ", hasPrevPage="
        + hasPrevPage
        + ", hasNextPage="
        + hasNextPage
        + ", prevPage="
        + prevPage
        + ", nextPage="
        + nextPage
        + '}';
  }
}
