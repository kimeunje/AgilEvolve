package com.taskagile.domain.model.user.events;

import org.springframework.util.Assert;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.model.user.User;

public class UserRegisteredEvent extends DomainEvent {
  private static final long serialVersionUID = 6649424869L;

  private User user;

  public UserRegisteredEvent(Object source, User user) {
    super(source);
    Assert.notNull(user, "`user` 파라미터는 null이면 안됩니다.");
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    UserRegisteredEvent that = (UserRegisteredEvent) o;
    return that.user.equals(this.user);
  }

  public int hashCode() {
    return this.user.hashCode();
  }

  public String toString() {
    return "UserRegisteredEvent{" +
        "user='" + user + '\'' +
        "timestamp='" + getTimestamp() + '\'' +
        '}';
  }
}
