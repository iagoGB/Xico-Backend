package br.com.smd.xico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.smd.xico.dto.PortfolioTO;
import br.com.smd.xico.models.PortfolioModel;
import br.com.smd.xico.repositories.PortfolioRespository;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRespository portfolioRespository;
    @Autowired
    private StorageService storageService;
    @Value("${portfolio.images.root.path}")
    private String PORTFOLIOSTORAGEPATH;
    @Value("${portfolio.images.root.http}")
    private String PORTIFOLIOHTTPPATH;

    public ResponseEntity<?> save(final MultipartFile[] images, final PortfolioTO portfolioTO) {
       
        // var portfolioDTO = new PortfolioTO("Um titulo qualquer", LocalDate.now(), Arrays.asList(Tag.DESIGN, Tag.AUDIOVISUAL));
        final var portolioModel = PortfolioModel.parse(portfolioTO);
        final PortfolioModel saved = portfolioRespository.save(portolioModel);
        for (final MultipartFile image : images) {
            final String imageSavedPath = storageService.salvarImagem(image, saved.getId(), PORTFOLIOSTORAGEPATH);
            final String uri = (PORTIFOLIOHTTPPATH+"/"+saved.getId()+"/images/"+imageSavedPath);            
            System.out.println("Endereço HTTP da imagem: "+uri);
            saved.getImages().add(uri);
        }
        portfolioRespository.save(saved);
        
        return ResponseEntity.ok().build();

    }

	public ResponseEntity<?> getResource(Long portfolioID, String filename) {
		return storageService.getResource( portfolioID, filename, PORTFOLIOSTORAGEPATH);
	}


}