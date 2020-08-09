package br.com.smd.xico.services;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {
    
    public String salvarImagem(MultipartFile imageFile, Long resourceID, String directory){
        String fileName = resourceID +"_"+ imageFile.getOriginalFilename();
        Path basePath = Paths.get(directory+"\\"+resourceID);
        Path targetPath = Paths.get(directory+"\\"+resourceID+"\\"+fileName);
            // String folder = "/CASaMovel/usuarioAvatar";
        try{
            if (!directoryExists(basePath)) Files.createDirectories(basePath);  
            Files.copy(imageFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
                
        } catch (Exception e){
            e.printStackTrace();
            return "Erro ao tentar salvar arquivo -" + e;
        }
    }

    private boolean directoryExists(Path basePath) {
        return Files.exists(basePath);
    }

	public ResponseEntity<?> getResource(Long portfolioID, String filename, String directory) {
        Path targetPath = Paths.get(directory+"\\"+portfolioID+"\\"+filename);
	    // Resource resource = null;
        try {
            var resource = new FileSystemResource(targetPath);
            System.out.println("Path: "+ targetPath.toString());
            var bytes = StreamUtils.copyToByteArray(resource.getInputStream());
            return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG) // "application/octet-stream"
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(bytes);
        } 
        catch(FileNotFoundException ex){
            System.out.println("Arquivo não encontrado!!!!!!");
            return ResponseEntity.notFound().build();
        } 
        catch (Exception e) {
            System.out.println("Outro erro qualquer!!!!!!");
            return ResponseEntity.status(500).build();
        }
	}
}