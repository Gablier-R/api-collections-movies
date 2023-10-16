package br.com.collec.controller;

import br.com.collec.payload.AllResponseDTO;
import br.com.collec.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static br.com.collec.Utils.Constants.DEFAULT_PAGE_NUMBER;
import static br.com.collec.Utils.Constants.DEFAULT_PAGE_SIZE;


@RestController
@RequestMapping("v1/api")
public record AllResponseController(UserService userService) {

    @GetMapping("/all")
    public ResponseEntity<AllResponseDTO> getAllResource(@RequestParam( defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                      @RequestParam( defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize){
        return new ResponseEntity<>(userService.mapToPageableAllResource(pageNo, pageSize), HttpStatus.OK);
    }
}
