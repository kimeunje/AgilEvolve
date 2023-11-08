package com.taskagile.domain.common.model;

import java.io.Serializable;

public class AbstractBaseId implements Serializable {

  private static final long serialVersionUID = 7555874202L;

  private long id;

  public AbstractBaseId(long id) {
    this.id = id;
  }

  public long value() {
    return id;
  }

  public boolean isValid() {
    return id > 0;
  }
}
