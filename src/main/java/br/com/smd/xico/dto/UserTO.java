package br.com.smd.xico.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserTO {
    private Long id;
    private String name;
    private String image;
    private String email;
    private String password;
    private String description;
    private LocalDate entryDate;
    private List<String> tools;
    private List<PortfolioTO> projects; 
}