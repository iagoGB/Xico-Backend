package br.com.smd.xico.services;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3StorageService  {
    @Value("${aws.s3.accesskey}")
    private String ACCESSKEY;
    @Value("${aws.s3.secretkey}")
    private String SECRETKEY;


    public String salvarImagem(MultipartFile imageFile, Long resourceID, String directory) {
        String resourceUrl = null;
        try {
            AWSCredentials credentials = new BasicAWSCredentials(
                this.ACCESSKEY,
                this.SECRETKEY
            );
            AmazonS3Client s3client = (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_2)
                .build();
            List<Bucket> buckets = s3client.listBuckets();
            var bucketName = buckets.get(1).getName();
            var metadata = new ObjectMetadata();
            var fileInputStream = imageFile.getInputStream();
            var targetPath = directory+"/"+resourceID+"/"+imageFile.getOriginalFilename();
            metadata.setContentType(imageFile.getContentType());
            metadata.setContentLength(imageFile.getSize());
            var por = new PutObjectRequest( 
                bucketName, 
                targetPath,
                fileInputStream,
                metadata
            );  
            s3client.putObject(por.withCannedAcl(CannedAccessControlList.PublicRead));
            resourceUrl = s3client.getResourceUrl(bucketName,targetPath);
            System.out.println("Url do recurso: "+resourceUrl);

        } catch (Exception e) {
            System.err.println(e);
        }
        return resourceUrl;
    }


    public ResponseEntity<?> getResource(Long portfolioID, String filename, String directory) {
        Path targetPath = Paths.get(directory + "\\" + portfolioID + "\\" + filename);
        // Resource resource = null;
        try {
            var resource = new FileSystemResource(targetPath);
            System.out.println("Path: " + targetPath.toString());
            var bytes = StreamUtils.copyToByteArray(resource.getInputStream());
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG) // "application/octet-stream"
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(bytes);
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo não encontrado!!!!!!");
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println("Outro erro qualquer!!!!!!");
            return ResponseEntity.status(500).build();
        }
    }
}
