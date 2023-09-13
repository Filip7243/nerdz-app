package com.nerdz.nerdzapp.model.user;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity(name = "user")
@Table(name = "user")
public class AppUser implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Enumerated(STRING)
    private Authority authority;
    private Boolean isExpired;
    private Boolean isLocked;
    private Boolean areCredentialsExpired;
    private Boolean isEnabled;
    private Boolean isPremium;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Temporal(TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public AppUser() {
    }

    public AppUser(String email, String password) {
        this.email = email;
        this.password = password;

        this.authority = Authority.USER;
        this.isExpired = false;
        this.isLocked = false;
        this.areCredentialsExpired = false;
        this.isEnabled = false; // until email is not confirmed user is not enabled
        this.isPremium = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPremium() {
        return isPremium;
    }

    public void setPremium(Boolean premium) {
        isPremium = premium;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        if (updatedAt.isBefore(this.updatedAt))
            throw new IllegalArgumentException("Updated date cannot be before current update date");
        else this.updatedAt = updatedAt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(authority.name()));
    }

    @Override
    public String getPassword() {
        return password;  // TODO: change in future
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !areCredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(id, appUser.id) && Objects.equals(email, appUser.email) &&
                authority == appUser.authority && Objects.equals(isExpired, appUser.isExpired) && Objects.equals(isLocked, appUser.isLocked) &&
                Objects.equals(areCredentialsExpired, appUser.areCredentialsExpired) && Objects.equals(isEnabled, appUser.isEnabled) &&
                Objects.equals(isPremium, appUser.isPremium) && Objects.equals(createdAt, appUser.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, authority, isExpired, isLocked, areCredentialsExpired, isEnabled, isPremium, createdAt);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", authority=" + authority +
                ", isExpired=" + isExpired +
                ", isLocked=" + isLocked +
                ", areCredentialsExpired=" + areCredentialsExpired +
                ", isEnabled=" + isEnabled +
                ", isPremium=" + isPremium +
                ", createdAt=" + createdAt +
                '}';
    }
}
