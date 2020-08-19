package br.com.smd.xico.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private Long userID;
    private String title;
    private LocalDate date;
    private List<String> files;
    private List<Tag> tags;
    private List<Long> likes; 

    public static PortfolioTO parse(PortfolioModel portfolioModel){
        return new PortfolioTO (
            portfolioModel.getUser().getId(),
            portfolioModel.getTitle(),
            portfolioModel.getDate() ,
            portfolioModel.getFiles(),
            portfolioModel.getTags() ,
            portfolioModel.getLikes()
        );
    }

    public static List<PortfolioTO> parse(List<PortfolioModel> portfolioModelList){
        var list = new ArrayList<PortfolioTO>();
        portfolioModelList.forEach((port) -> list.add(PortfolioTO.parse(port)));
        return list;
    }

    
}
