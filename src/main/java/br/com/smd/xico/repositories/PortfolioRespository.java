package br.com.smd.xico.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.smd.xico.models.PortfolioModel;

public interface PortfolioRespository extends JpaRepository<PortfolioModel, Long> {

    List<PortfolioModel> findAllByOrderByDateDesc();

    List<PortfolioModel> findAllByOrderByLikesDesc();

    List<PortfolioModel> findAllByOrderByViewsDesc();

    List<PortfolioModel> findAllByToolsEquals(String tool);

    List<PortfolioModel> findAllByTagsEquals(String tag);

    List<PortfolioModel> findAllByTitleContainsIgnoreCase(String tag);

}
