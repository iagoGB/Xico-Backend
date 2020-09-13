package br.com.smd.xico.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.smd.xico.models.Category;
import br.com.smd.xico.models.PortfolioModel;
import br.com.smd.xico.utils.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PortfolioTO {
    private Long id;
    private Long userID;
    private Category category;
    private String title;
    private String description;
    private LocalDate date;
    private List<String> files;
    private List<String> tags;
    private List<Long> likes; 
    private List<Long> views;

    public static PortfolioTO parse(PortfolioModel portfolioModel){
        return new PortfolioTO (
            portfolioModel.getId(),
            portfolioModel.getUser().getId(),
            portfolioModel.getCategory(),
            portfolioModel.getTitle(),
            portfolioModel.getDescription(),
            portfolioModel.getDate() ,
            portfolioModel.getFiles(),
            portfolioModel.getTags() ,
            portfolioModel.getLikes(),
            portfolioModel.getViews()
        );
    }

    public static List<PortfolioTO> parse(List<PortfolioModel> portfolioModelList){
        var list = new ArrayList<PortfolioTO>();
        portfolioModelList.forEach((port) -> list.add(PortfolioTO.parse(port)));
        return list;
    }

    
}
