package br.com.mercadoenvios.logitrack.infrastructure.database.repository.parcel;

import br.com.mercadoenvios.logitrack.domain.model.Parcel;
import br.com.mercadoenvios.logitrack.infrastructure.database.entity.ParcelEntity;
import br.com.mercadoenvios.logitrack.infrastructure.database.mapper.ParcelEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomParcelRepositoryImpl implements CustomParcelRepository {
    private final DatabaseClient databaseClient;
    private final ParcelEntityMapper mapper;

    @Override
    public Mono<ParcelEntity> findParcelById(String id) {
        var query = """
                 SELECT
                    id,
                    description,
                    sender_id,
                    recipient_id,
                    fun_fact,
                    is_holiday,
                    estimated_delivery_date,
                    status,
                    created_at,
                    updated_at,
                    delivered_at
                 FROM
                    parcels
                 WHERE
                    id = :id;
                """;
        return databaseClient.sql(query)
                .bind("id", id)
                .map((row, metadata) -> mapper.map(row, metadata))
                .one();
    }

    @Override
    public Mono<Void> updateStatusParcel(ParcelEntity parcelEntity) {
        var query = """
                 UPDATE 
                    parcels
                 SET
                    status = :status
                 WHERE
                    id = :id;
                """;
        return databaseClient.sql(query)
                .bind("id", parcelEntity.id())
                .bind("status", parcelEntity.status())
                .fetch()
                .rowsUpdated()
                .then();
    }

    @Override
    public Mono<Void> updateCancelledParcel(ParcelEntity parcelEntity) {
        var query = """
                 UPDATE 
                    parcels
                 SET
                    status = :status
                 WHERE
                    id = :id;
                """;
        return databaseClient.sql(query)
                .bind("id", parcelEntity.id())
                .bind("status", Parcel.Status.CANCELLED.name())
                .fetch()
                .rowsUpdated()
                .then();
    }

    @Override
    public Mono<Void> updateDeliveredParcel(ParcelEntity parcelEntity) {
        var query = """
                 UPDATE 
                    parcels
                 SET
                    status = :status,
                    delivered_at = :delivered_at
                 WHERE
                    id = :id;
                """;
        return databaseClient.sql(query)
                .bind("id", parcelEntity.id())
                .bind("status", parcelEntity.status())
                .bind("delivered_at", parcelEntity.deliveredAt())
                .fetch()
                .rowsUpdated()
                .then();
    }
}
