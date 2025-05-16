package br.com.mercadoenvios.logitrack.application.controller;

import br.com.mercadoenvios.logitrack.application.dto.user.RegisterUserRequest;
import br.com.mercadoenvios.logitrack.application.dto.user.RegisterUserResponse;
import br.com.mercadoenvios.logitrack.domain.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("logitrack/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade service;

    @PostMapping
    public Mono<RegisterUserResponse> createUser(@RequestBody @Valid RegisterUserRequest request) {
        return service.registerUser(request);
    }

    @GetMapping("/{id}")
    public Mono<RegisterUserResponse> getUser(@PathVariable String id) {
        return service.findUser(id);
    }

}
