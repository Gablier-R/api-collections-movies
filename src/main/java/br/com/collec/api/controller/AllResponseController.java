package br.com.collec.api.controller;

import br.com.collec.api.payload.AllResponseDTO;
import br.com.collec.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static br.com.collec.utils.Constants.DEFAULT_PAGE_NUMBER;
import static br.com.collec.utils.Constants.DEFAULT_PAGE_SIZE;


@RestController
@RequestMapping("/all")
public class AllResponseController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<AllResponseDTO> getAllResource(@RequestParam( defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                         @RequestParam( defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize){
        return new ResponseEntity<>(userService.mapToPageableAllResource(pageNo, pageSize), HttpStatus.OK);
    }
}
