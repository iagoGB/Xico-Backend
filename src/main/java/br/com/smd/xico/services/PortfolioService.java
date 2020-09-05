package br.com.smd.xico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.smd.xico.dto.PortfolioTO;
import br.com.smd.xico.models.PortfolioModel;
import br.com.smd.xico.repositories.PortfolioRespository;
import br.com.smd.xico.repositories.UserRepository;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRespository portfolioRespository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private S3StorageService s3storageService;
    @Value("${portfolio.images.http.folder}")
    private String PORTFOLIOSTORAGEPATH;
    @Value("${portfolio.images.http.folder}")
    private String PORTIFOLIOHTTPPATH;

    @Transactional
    public ResponseEntity<?> save(MultipartFile[] images, PortfolioTO portfolioTO) {
        var user =  userRepository.findById(portfolioTO.getUserID());
        var portolioModel = PortfolioModel.parse(portfolioTO);
        portolioModel.setUser(user.get());
        var saved = portfolioRespository.save(portolioModel);
        for (MultipartFile image : images) {
            String imageSavedPath = s3storageService.salvarImagem(image, saved.getId(), this.PORTFOLIOSTORAGEPATH);
            saved.getFiles().add(imageSavedPath);
        }
        portfolioRespository.save(saved);
        
        return ResponseEntity.ok().build();

    }

	public ResponseEntity<?> getResource(Long portfolioID, String filename) {
		return s3storageService.getResource( portfolioID, filename, this.PORTFOLIOSTORAGEPATH);
	}

	public ResponseEntity<?> update(Long portfolioID, PortfolioTO portfolioTO) {
        var findById = portfolioRespository.findById(portfolioID);
        var portfolioModel = findById.get();
        portfolioModel.setFiles(portfolioTO.getFiles());
        portfolioRespository.save(portfolioModel);
        return ResponseEntity.ok().build();
	}


}