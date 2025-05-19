package br.com.mercadoenvios.logitrack.domain.facade;

import br.com.mercadoenvios.logitrack.application.dto.user.RegisterUserRequest;
import br.com.mercadoenvios.logitrack.application.dto.user.RegisterUserResponse;
import br.com.mercadoenvios.logitrack.application.mapper.RegisterUserMapper;
import br.com.mercadoenvios.logitrack.domain.mapper.AddressMapper;
import br.com.mercadoenvios.logitrack.domain.mapper.UserMapper;
import br.com.mercadoenvios.logitrack.domain.service.address.AddressService;
import br.com.mercadoenvios.logitrack.domain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final AddressService addressService;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final RegisterUserMapper registerUserMapper;

    public Mono<RegisterUserResponse> registerUser(RegisterUserRequest request) {
        return userMapper.map(request)
                .flatMap(userService::registerUser)
                .flatMap(user -> addressMapper.map(request, user)
                        .flatMap(address -> addressService.registerAddress(address, user)
                                .flatMap(addressResult -> registerUserMapper.map(user, addressResult))));
    }

    public Mono<RegisterUserResponse> findUser(String id) {
        return userService.findUser(id)
                .flatMap(user -> addressService.findAddressByIdUser(id)
                        .flatMap(address -> registerUserMapper.map(user, address)));
    }
}
