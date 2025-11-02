package pt.psoft.g1.psoftg1.usermanagement.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import pt.psoft.g1.psoftg1.shared.model.Name;
import lombok.Getter;
import lombok.Setter;

/**
 * Domain model for User (isolated from persistence & frameworks)
 */
@Getter
@Setter
public class User implements UserDetails {

    private Long id;
    private boolean enabled = true;
    private String username;
    private String password;
    private Name name;
    private final Set<Role> authorities = new HashSet<>();

    protected User() {}

    public User(String username, String password) {
        this.username = username;
        setPassword(password);
    }

    public static User newUser(String username, String password, String name) {
        User u = new User(username, password);
        u.setName(name);
        return u;
    }

	public void setName(String name) {
		this.name = new Name(name);
	}

    public static User newUser(String username, String password, String name, String role) {
        User u = new User(username, password);
        u.setName(name);
        u.addAuthority(new Role(role));
        return u;
    }

    public void setPassword(final String password) {
        this.password = password; // encryption handled externally (e.g., service)
    }

    public void addAuthority(final Role r) {
        authorities.add(r);
    }

    public boolean isAccountNonExpired() { return isEnabled(); }
    public boolean isAccountNonLocked() { return isEnabled(); }
    public boolean isCredentialsNonExpired() { return isEnabled(); }
}
