package br.com.smd.xico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.smd.xico.dto.PortfolioTO;
import br.com.smd.xico.dto.TagTO;
import br.com.smd.xico.services.PortfolioService;

@Controller
@CrossOrigin
@RequestMapping("/portfolio")
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return portfolioService.findAll();
    }

    @GetMapping("/tools")
    public ResponseEntity<?> findByTool(@RequestParam(value = "value") String tool) {
        return portfolioService.findByTool(tool);
    }

    @PostMapping("/tags")
    public ResponseEntity<?> findByTag(@RequestBody TagTO tag) {
        return portfolioService.findByTag(tag);
    }

    @PostMapping("/title")
    public ResponseEntity<?> findByTitle(@RequestBody TagTO title) {
        return portfolioService.findByTitle(title.getValue());
    }

    
    @GetMapping("/moreRecents")
    public ResponseEntity<?> findByNews() {
        return portfolioService.findByDate();
    }

    @GetMapping("/moreViews")
    public ResponseEntity<?> findByMoreViews(){
        return portfolioService.findByMoreViews();
    }

    @GetMapping("/moreLikes")
    public ResponseEntity<?> findByMoreLikes(){
        return portfolioService.findByMoreLikes();
    }
    
    @GetMapping("{id}")
    public ResponseEntity<?> findByID(@PathVariable(value ="id") Long portfolioID){
        return portfolioService.findByID(portfolioID);
    }

    
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

    @PutMapping("/{id}")
    public ResponseEntity<?> update( 
        @PathVariable(value ="id") Long portfolioID,
        @RequestParam(value = "imagens", required = false) MultipartFile[] images,
        @RequestParam("portfolio") PortfolioTO portfolioTO
    ){
        return portfolioService.update(portfolioID,portfolioTO,images);
    }

    @PutMapping("/{id}/views")
    public ResponseEntity<?> updateViews( 
        @PathVariable(value ="id") Long portfolioID
    ){
        return portfolioService.updateViews(portfolioID);
    }

    @PutMapping("/{id}/likes")
    public ResponseEntity<?> updateLikes( 
        @PathVariable(value ="id") Long portfolioID,
        @RequestParam("tanner") Long tannerID
    ){
        return portfolioService.updateLikes(portfolioID,tannerID);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value ="id") Long portfolioID){
        return portfolioService.delete(portfolioID);
    }


}