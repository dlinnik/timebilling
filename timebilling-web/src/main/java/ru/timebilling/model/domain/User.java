package ru.timebilling.model.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "user")
public class User extends AppAwareBaseEntity {
	
	@Column(name = "username", nullable = false, length = 256)
    private String username;
	@Column(name = "password", nullable = false, length = 256)
    private String password;
    @Column(unique = true, name = "email", nullable = false, length = 256)
    private String email;

	@Column(name = "accountexpired")
    private boolean accountExpired;
	@Column(name = "accountlocked")
    private boolean accountLocked;
	@Column(name = "enabled")
    private boolean enabled;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.ALL})
    private Role role;


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
    	if(role!=null){
    		role.setUser(this);
    	}
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}