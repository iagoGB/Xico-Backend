package br.com.smd.xico.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.smd.xico.dto.PortfolioTO;
import br.com.smd.xico.dto.TagTO;
import br.com.smd.xico.models.PortfolioModel;
import br.com.smd.xico.models.UserModel;
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

	public ResponseEntity<?> findByID(Long portfolioID) {
        Optional<PortfolioModel> result = this.portfolioRespository.findById(portfolioID);
        if (!result.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok().body(PortfolioTO.parse(result.get()));
	}

	public ResponseEntity<?> findAll() {
		return  ResponseEntity.ok().body( PortfolioTO.parse(portfolioRespository.findAll()));
	}

    @Transactional
	public ResponseEntity<?> updateViews(Long portfolioID) {
        return portfolioRespository.findById(portfolioID)
        .map(record -> {
            if (record.getViews() == null)
                record.setViews((long) 0);
            record.setViews(record.getViews() + 1);
            portfolioRespository.save(record);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
    
    @Transactional
	public ResponseEntity<?> updateLikes(Long portfolioID, Long tannerID) {
        return userRepository.findById(tannerID)
        .map( user -> {
            return updateLike(portfolioID, user);
        }).orElse(ResponseEntity.notFound().build());
	}

    private ResponseEntity<?> updateLike(Long portfolioID, UserModel user) {
        return portfolioRespository.findById(portfolioID)
        .map( portfolio -> {
            if (portfolio.getLikes() == null )
                portfolio.setLikes((long) 0);
             
            if (portfolio.getTanners().contains(user.getId())){
                portfolio.getTanners().remove(user.getId());
                portfolio.setLikes(portfolio.getLikes() - 1);

            } else {
                portfolio.getTanners().add(user.getId());
                portfolio.setLikes(portfolio.getLikes() + 1);
            }

            portfolioRespository.save(portfolio);
            return ResponseEntity.ok().build();

        }).orElse(ResponseEntity.notFound().build());
    }

	public ResponseEntity<?> findByTool(String tool) {
		return ResponseEntity.ok().body(PortfolioTO.parse(portfolioRespository.findAllByToolsEquals(tool)));
	}

	public ResponseEntity<?> findByMoreViews() {
        return ResponseEntity.ok().body(PortfolioTO.parse(portfolioRespository.findAllByOrderByViewsDesc()));

	}

	public ResponseEntity<?> findByMoreLikes() {
        return ResponseEntity.ok().body(PortfolioTO.parse(portfolioRespository.findAllByOrderByLikesDesc()));

	}

	public ResponseEntity<?> findByTag(TagTO tag) {
        return ResponseEntity.ok().body(PortfolioTO.parse(portfolioRespository.findAllByTagsEquals(tag.getValue())));
	}

	public ResponseEntity<?> findByDate() {
        return ResponseEntity.ok().body(PortfolioTO.parse(portfolioRespository.findAllByOrderByDateDesc()));
    }
    
    public ResponseEntity<?> findByTitle(String title) {
        return ResponseEntity.ok().body(PortfolioTO.parse(portfolioRespository.findAllByTitleContainsIgnoreCase(title)));
	}


}