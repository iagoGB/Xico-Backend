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
    private String newImage = null;

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

	public ResponseEntity<?> update(Long userID, UserTO userTO, MultipartFile image) {

        if (image != null){
            // Enviou uma foto para atualizar
            System.out.println("Atualizar imagem");
            newImage = s3storageService.salvarImagem(image, userTO.getId(), this.PROFILEHTTPPATH);
        } else {
            System.out.println("Não veio nova imagem");
        }

        return userRepository.findById(userID)
            .map(record -> {
                record.setName(userTO.getName());
                record.setLastName(userTO.getLastName());
                record.setEmail(userTO.getEmail());
                record.setNickname(userTO.getNickname());
                record.setEntryDate(userTO.getEntryDate());
                record.setFb(userTO.getFb());
                record.setIg(userTO.getIg());
                record.setDescription(userTO.getDescription());
                record.setTools(userTO.getTools());
                if (newImage != null)
                    record.setImage(newImage);
                var updated = userRepository.save(record);
                return ResponseEntity.ok().body(UserTO.parse(updated));
            }).orElse(ResponseEntity.notFound().build());
    }
    
    public ResponseEntity <?> delete(Long id) {
        return userRepository.findById(id)
                .map(record -> {
                    userRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }


}
