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

import br.com.smd.xico.dto.UserTO;
import br.com.smd.xico.services.UserService;

@Controller
@CrossOrigin
@RequestMapping("/usuario")
public class UserController {
    @Autowired 
    private UserService userService;

    @PostMapping(consumes ={"multipart/form-data"})
    public ResponseEntity<?> save(
        @RequestParam("imagem") MultipartFile image,
        @RequestParam("usuario") UserTO userTO
    ){  
        System.out.println(userTO);
        return userService.save(image, userTO);
    }
   
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable(value ="id") Long userID){
        return userService.getUser(userID);
    }
   
}