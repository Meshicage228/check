package ru.clevertec.check.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.check.dto.ProductDto;
import ru.clevertec.check.exceptions.BadRequestException;
import ru.clevertec.check.service.impl.ProductServiceImpl;
import ru.clevertec.check.utils.markers.DefaultCheckMarker;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;

    @GetMapping
    public ResponseEntity<ProductDto> getById(@RequestParam("id") Integer id) {
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(productService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@Validated(DefaultCheckMarker.class) @RequestBody ProductDto dto,
                                            BindingResult bindingResult) throws BadRequestException {
        if(bindingResult.hasFieldErrors()){
            throw new BadRequestException();
        }
        ProductDto saved = productService.save(dto);

        return ResponseEntity.status(CREATED)
                .body(saved);
    }

    @PutMapping
    public ResponseEntity<Void> putUpdate(@Validated(DefaultCheckMarker.class) @RequestBody ProductDto dto,
                                          BindingResult bindingResult,
                                          @RequestParam("id") Integer id) throws BadRequestException {
        if(bindingResult.hasFieldErrors()){
            throw new BadRequestException();
        }
        productService.fullUpdateProduct(dto, id);

        return ResponseEntity.status(ACCEPTED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestParam("id") Integer id) {
        productService.deleteById(id);

        return ResponseEntity.status(ACCEPTED).build();
    }
}
