package br.com.mercadoenvios.logitrack.infrastructure.database.repository.user;

import br.com.mercadoenvios.logitrack.infrastructure.database.entity.UserEntity;
import br.com.mercadoenvios.logitrack.infrastructure.database.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {
    private final DatabaseClient databaseClient;
    private final UserEntityMapper mapper;

    @Override
    public Mono<Boolean> isUserRegistered(UserEntity user) {
        var query = """
                 SELECT
                     COUNT(1)
                 FROM
                     users
                 WHERE
                     email = :email;
                """;
        return databaseClient.sql(query)
                .bind("email", user.email())
                .map((row, metadata) -> row.get(0, Long.class))
                .one()
                .map(count -> count != null && count > 0)
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<UserEntity> findUserById(String id) {
        var query = """
                 SELECT
                    id,
                    name,
                    email,
                    status,
                    created_at,
                    updated_at
                 FROM
                    users
                 WHERE
                    id = :id;
                """;
        return databaseClient.sql(query)
                .bind("id", id)
                .map((row, metadata) -> mapper.map(row, metadata))
                .one();
    }
}
