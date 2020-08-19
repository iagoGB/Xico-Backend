package br.com.smd.xico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.smd.xico.dto.UserTO;
import br.com.smd.xico.models.UserModel;
import br.com.smd.xico.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private S3StorageService s3storageService;
    @Autowired
    private UserRepository userRepository;
    @Value("${profile.images.http.folder}")
    private String PROFILEHTTPPATH;

    public ResponseEntity<?> save(MultipartFile image, UserTO userTO){
        var userModel = UserModel.parse(userTO);
        var userSaved = userRepository.save(userModel);
        var imagePath = s3storageService.salvarImagem(image, userSaved.getId(), this.PROFILEHTTPPATH);
        userSaved.setImage(imagePath);
        userSaved = userRepository.save(userSaved);
        return ResponseEntity.ok(UserTO.parse(userSaved));
    }

	public ResponseEntity<?> getUser(Long userID) {
        var user = userRepository.findById(userID);
        if (user.isPresent())
            return ResponseEntity.ok( UserTO.parse(user.get()));
        else return ResponseEntity.notFound().build();
	}

}
