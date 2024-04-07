package com.devmeks.pangenerator.model;

import com.devmeks.pangenerator.util.OTP;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends AbstractAuditingEntity implements UserDetails  {


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  @JdbcTypeCode(SqlTypes.UUID)
  @Getter
  private UUID id;

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

  @JsonIgnore
  @ManyToOne
  @Setter
  @JoinColumn(name="organizationName")
  private Organization organization;

  @Column(columnDefinition = "BOOLEAN default 'false'")
  @Setter
  private boolean enabled;

  @Getter
  private String otp;

  @Getter
  private LocalDateTime otpExpiryDate;



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
    return enabled;
  }


}
