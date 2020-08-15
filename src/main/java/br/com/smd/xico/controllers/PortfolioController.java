package br.com.smd.xico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.smd.xico.dto.PortfolioTO;
import br.com.smd.xico.services.PortfolioService;

@Controller
@CrossOrigin
@RequestMapping("/portfolio")
public class PortfolioController {
    @Autowired 
    private PortfolioService portfolioService;

    @PostMapping(consumes ={"multipart/form-data"})
    public ResponseEntity<?> save(
        @RequestParam("images") MultipartFile[] images,
        @RequestParam("portfolio") PortfolioTO portfolioTO
    ){
        return this.portfolioService.save(images, portfolioTO);
    }
   
    @GetMapping("{id}/images/{fileName:.+}")
    public ResponseEntity<?> downloadImage(
        @PathVariable(value ="id") Long portfolioID,
        @PathVariable(value ="fileName") String filename
    ){
        return portfolioService.getResource(portfolioID,filename);
    }
}