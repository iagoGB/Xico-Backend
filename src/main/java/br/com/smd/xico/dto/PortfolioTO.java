package br.com.smd.xico.dto;

import java.time.LocalDate;
import java.util.List;

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
    private String title;
    private LocalDate date;
    private List<Tag> tags;
}
