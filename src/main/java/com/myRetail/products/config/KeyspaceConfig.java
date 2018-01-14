package com.myRetail.products.config;


import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.datastax.driver.core.schemabuilder.SchemaStatement;
import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class KeyspaceConfig {

    //@Bean
    public Cluster getCluster() {
        final Cluster.Builder clusterBuilder = Cluster.builder()
                .addContactPoints(
                        "35.170.43.15", "54.210.29.116", "52.73.8.42" // US_EAST_1 (Amazon Web Services (VPC))
                )
                .withLoadBalancingPolicy(DCAwareRoundRobinPolicy.builder().withLocalDc("US_EAST_1").build()) // your local data centre
                .withPort(9042)
                .withAuthProvider(new PlainTextAuthProvider("iccassandra", "2c80dc59064da3c5e450e752e99cbd99"));



        try (final Cluster cluster = clusterBuilder.build()) {
            final Metadata metadata = cluster.getMetadata();
            System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());

            for (final Host host: metadata.getAllHosts()) {
                System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(), host.getRack());
            }
            Session session = cluster.newSession();
            createKeyspace(session);
            return cluster;
        }


    }

    private void createKeyspace(Session session) {


        session.execute("CREATE KEYSPACE IF NOT EXISTS  productprice_keyspace WITH replication = {"
                + " 'class': 'SimpleStrategy', "
                + " 'replication_factor': '3' "
                + "};" );
    }
}
