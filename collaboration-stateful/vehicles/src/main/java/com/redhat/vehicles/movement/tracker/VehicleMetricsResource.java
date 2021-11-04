package com.redhat.vehicles.movement.tracker;

import java.util.ArrayList;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.StoreQueryParameters;


@Path("/vehicle/metrics")
public class VehicleMetricsResource {

    @Inject
    KafkaStreams streams;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<VehicleMetrics> list() {
        List<VehicleMetrics> vehicleMetrics = new ArrayList<>();

        // TODO: query the store
        ReadOnlyKeyValueStore<Integer, VehicleMetrics> store = streams
                .store(StoreQueryParameters.fromNameAndType(
                        "vehicle-metrics-store",
                        QueryableStoreTypes.keyValueStore()
                ));

        store.all().forEachRemaining(row -> { vehicleMetrics.add(row.value); });
        return vehicleMetrics;
    }
}