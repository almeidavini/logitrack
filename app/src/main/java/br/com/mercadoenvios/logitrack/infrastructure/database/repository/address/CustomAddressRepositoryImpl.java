package br.com.mercadoenvios.logitrack.infrastructure.database.repository.address;

import br.com.mercadoenvios.logitrack.infrastructure.database.entity.AddressEntity;
import br.com.mercadoenvios.logitrack.infrastructure.database.mapper.AddressEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomAddressRepositoryImpl implements CustomAddressRepository {

    private final DatabaseClient databaseClient;
    private final AddressEntityMapper mapper;

    @Override
    public Mono<AddressEntity> findAddressByIdUser(String userId) {
        var query = """
                SELECT
                    id,
                    user_id,
                    street,
                    number,
                    city,
                    state,
                    zip_code,
                    country,
                    created_at,
                    updated_at
                FROM
                    addresses
                WHERE
                    user_id = :user_id;
                """;

        return databaseClient.sql(query)
                .bind("user_id", userId)
                .map(mapper::map)
                .one();
    }

}
