package br.com.smd.xico.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Role implements GrantedAuthority {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }


}
