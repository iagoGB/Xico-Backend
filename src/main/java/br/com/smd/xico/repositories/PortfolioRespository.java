package br.com.smd.xico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.smd.xico.models.PortfolioModel;

public interface PortfolioRespository extends JpaRepository<PortfolioModel, Long> {

}
