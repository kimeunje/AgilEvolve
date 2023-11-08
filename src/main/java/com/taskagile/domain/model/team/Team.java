package com.taskagile.domain.model.team;

import java.util.Date;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import com.taskagile.domain.common.model.AbstractBaseEntity;
import com.taskagile.domain.model.user.UserId;

@Entity
@Table(name = "team")
public class Team extends AbstractBaseEntity {

  private static final long serialVersionUID = 4860936361L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 128)
  private String name;

  @Column(name = "userId")
  private long userId;

  @Column(name = "archived")
  private boolean archived;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_date", nullable = false)
  private Date createdDate;

  /**
   * 팀 생성
   *
   * @param name      - 팀 이름
   * @param creatorId - 팀을 생성한 사용자의 식별자 값
   */
  public static Team create(String name, UserId creatorId) {
    Team team = new Team();
    team.name = name;
    team.archived = false;
    team.userId = creatorId.value();
    team.createdDate = new Date();
    return team;
  }

  public TeamId getId() {
    return new TeamId(id);
  }

  public String getName() {
    return name;
  }

  public UserId getUserId() {
    return new UserId(userId);
  }

  public boolean isArchived() {
    return archived;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Team))
      return false;
    Team team = (Team) o;
    return userId == team.userId &&
        Objects.equals(name, team.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, userId);
  }

  @Override
  public String toString() {
    return "Team{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", userId=" + userId +
        ", archived=" + archived +
        ", createdDate=" + createdDate +
        '}';
  }
}
