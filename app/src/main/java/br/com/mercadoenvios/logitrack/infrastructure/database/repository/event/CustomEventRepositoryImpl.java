package br.com.mercadoenvios.logitrack.infrastructure.database.repository.event;

import br.com.mercadoenvios.logitrack.infrastructure.database.entity.EventEntity;
import br.com.mercadoenvios.logitrack.infrastructure.database.mapper.EventEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomEventRepositoryImpl implements CustomEventRepository {
    private final DatabaseClient databaseClient;
    private final EventEntityMapper mapper;

    @Override
    public Flux<EventEntity> findEventsByParcelId(String id) {
        var query = """
         SELECT
            id,
            parcel_id,
            location,
            description,
            created_at
         FROM
            events
         WHERE
            parcel_id = :id
         ORDER BY
            created_at;
        """;

        return databaseClient.sql(query)
                .bind("id", id)
                .map((row, metadata) -> mapper.map(row, metadata))
                .all();
    }

    @Override
    public Mono<EventEntity> findEventById(String id) {
        var query = """
         SELECT
            id,
            parcel_id,
            location,
            description,
            created_at
         FROM
            events
         WHERE
            id = :id;
        """;

        return databaseClient.sql(query)
                .bind("id", id)
                .map((row, metadata) -> mapper.map(row, metadata))
                .one();
    }
}
