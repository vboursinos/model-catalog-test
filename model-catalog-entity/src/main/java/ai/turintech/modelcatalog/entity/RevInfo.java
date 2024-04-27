package ai.turintech.modelcatalog.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.*;

@Entity
@RevisionEntity
@Table(name = "revinfo")
public class RevInfo {
  @Id
  @RevisionNumber
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "maxValueSequenceGenerator")
  @GenericGenerator(
      name = "maxValueSequenceGenerator",
      strategy = "ai.turintech.modelcatalog.utils.MaxValueSequenceGenerator",
      parameters = {
        @org.hibernate.annotations.Parameter(name = "sequence_name", value = "revinfo_seq"),
        @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
        @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
      })
  @Column(name = "rev")
  private Long rev;

  @RevisionTimestamp
  @Column(name = "revtstmp")
  private LocalDateTime revtstmp;

  public RevInfo() {}

  public RevInfo(Long rev, LocalDateTime revtstmp) {
    this.rev = rev;
    this.revtstmp = revtstmp;
  }

  public Long getRev() {
    return rev;
  }

  public void setRev(Long rev) {
    this.rev = rev;
  }

  public LocalDateTime getRevtstmp() {
    return revtstmp;
  }

  public void setRevtstmp(LocalDateTime revtstmp) {
    this.revtstmp = revtstmp;
  }
}
