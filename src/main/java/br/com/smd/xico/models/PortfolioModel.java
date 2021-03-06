package br.com.smd.xico.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.smd.xico.dto.PortfolioTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class PortfolioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String title;
    private String description;
    @ElementCollection
    @Column(name="user_id")
    @CollectionTable(name="portfolio_tanners", joinColumns = @JoinColumn(name = "portfolio_id")) 
    private List<Long> tanners;
    private Long likes;
    // @ElementCollection
    // @Column(name="user_id")
    // @CollectionTable(name="portfolio_views", joinColumns = @JoinColumn(name = "portfolio_id")) 
    private Long views;
    private LocalDate date;
    @ElementCollection
    @CollectionTable(name = "portfolio_tags", joinColumns = @JoinColumn(name = "portfolio_id"))
    private List<String> tags;
    @ElementCollection
    @CollectionTable(name = "portfolio_tools", joinColumns = @JoinColumn(name = "portfolio_id"))
    private List<String> tools;
    @ElementCollection
    @CollectionTable(name = "portfolio_files", joinColumns = @JoinColumn(name = "portfolio_id"))
    private List<String> files;
    @ManyToOne
    private UserModel user;

    public static PortfolioModel parse(PortfolioTO pto){
        return new PortfolioModel(
            null, 
            pto.getCategory(),
            pto.getTitle(), 
            pto.getDescription(), 
            new ArrayList<Long>(), 
            pto.getLikes(),
            pto.getViews(), 
            pto.getDate(),
            pto.getTags(),
            pto.getTools(),
            pto.getFiles(),
            null
        );
    }

}