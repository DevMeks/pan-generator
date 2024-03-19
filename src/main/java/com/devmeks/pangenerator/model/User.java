package com.devmeks.pangenerator.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@EntityListeners(AuditTrailListener.class)
public class User implements UserDetails {


  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  private Long id;

  @Column(unique = true)
  private String username;
  private String passwordHash; // Store securely (hashed and salted)

  @Column(unique = true)
  private String email;

  @ManyToMany(fetch = FetchType.EAGER) // Eagerly fetch roles for security purposes
  @JoinTable(name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  @CreatedBy
  @Column(nullable = false, updatable = false)
  private String createdBy;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  @Setter
  private LocalDateTime createdAt;

  @LastModifiedBy
  @Column
  private String modifiedBy;

  @LastModifiedDate
  @Column
  @Setter
  private LocalDateTime modified;

  @Column(columnDefinition = "BOOLEAN default 'false'")
  private boolean status;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public String getPassword() {
    return passwordHash;
  }

  @Override
  public String getUsername() {
    return username;
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
    return false;
  }

  @Override
  public boolean isEnabled() {
    return status;
  }


}
