package br.com.smd.xico.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.smd.xico.dto.UserTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class UserModel implements UserDetails {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String nickname;
    private String image;
    @Column(unique = true)
    private String email;
    private String password;
    private String description;
    private String entryDate;
    @ElementCollection
    @CollectionTable(name = "user_interests", joinColumns = @JoinColumn(name = "user_id"))
    private List<String> interests;
    private String fb;
    private String ig;
    @ElementCollection
    @CollectionTable(name = "user_tools", joinColumns = @JoinColumn(name = "user_id"))
    private List<String> tools;
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<PortfolioModel> projects;
    @ManyToMany
    private List<Role> roles = new ArrayList<>();

    public static UserModel parse(UserTO userTO) {
        return new UserModel(null, userTO.getName(), userTO.getLastName(),userTO.getNickname(),null, userTO.getEmail(), new BCryptPasswordEncoder().encode(userTO.getPassword()),
                userTO.getDescription(), userTO.getEntryDate(), userTO.getInterests(), userTO.getFb(), userTO.getIg(), userTO.getTools(), new ArrayList<PortfolioModel>(), new ArrayList<>());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public String getPassword() {
        return this.password;
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

   
}