package com.khanh.job.user_service.entity;

import com.khanh.job.domain.BaseEntity;
import com.khanh.job.domain.enums.UserRole;
import com.khanh.job.domain.enums.UserStatus;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = UserEntity.TABLE)
public class UserEntity extends BaseEntity {
  public static final String TABLE = "users";

  @Column(nullable = false)
  private String fullName;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String email;

  private @Nullable String phone;

  private @Nullable String profileImage;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private UserRole role = UserRole.ROLE_JOB_SEEKER;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private UserStatus status = UserStatus.ACTIVE;

  @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
  private @Nullable Instant lastLogin;

  @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
  private @Nullable Instant suspendedAt;

  @OneToMany(mappedBy = "candidate")
  @Builder.Default
  private List<Resume> resumes = new ArrayList<>();

  public UserDetails toUserDetail() {
    GrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
    Collection<GrantedAuthority> grantedAuthorities = Collections.singletonList(authority);

    return new org.springframework.security.core.userdetails.User(
        email, password, grantedAuthorities);
  }
}
