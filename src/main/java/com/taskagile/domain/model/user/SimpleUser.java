package com.taskagile.domain.model.user;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserDetails 객체는 Authentication 객체에 저장되며,
 * Authentication 객체는 HttpSession에 저장되기 때문에
 * UserDetails 데이터를 적게 유지하는 것이 중요하므로
 * 그에 맞게 커스터마이징한다.
 */
public class SimpleUser implements UserDetails {
  private static final long serialVersionUID = 2244624228L;

  private UserId userId;
  private String username;
  private String password;

  public SimpleUser(User user) {
    this.userId = user.getId();
    this.username = user.getUsername();
    this.password = user.getPassword();
  }

  public UserId getUserId() {
    return this.userId;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof SimpleUser))
      return false;
    SimpleUser that = (SimpleUser) o;
    return Objects.equals(username, that.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username);
  }

  @Override
  public String toString() {
    return "SimpleUser{" +
        "userId=" + userId +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
