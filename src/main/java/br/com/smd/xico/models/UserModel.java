package br.com.smd.xico.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private String email;
    private String password;
    private String description;
    private LocalDate entryDate;
    @ElementCollection
    @CollectionTable(name = "user_tools", joinColumns = @JoinColumn(name = "user_id"))
    private List<String> tools;
    @OneToMany(mappedBy = "user")
    private List<PortfolioModel> projects;
}