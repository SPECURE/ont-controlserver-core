package com.specure.core.multitenant;

import com.specure.core.config.ClientTenantConfig;
import com.specure.core.config.DataSourceConfig;
import lombok.AllArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MultiTenantFlywayMigrationStrategy implements FlywayMigrationStrategy {

    private DataSourceConfig dataSourceConfig;
    private ClientTenantConfig clientTenantConfig;

    @Override
    public void migrate(Flyway flyway) {
        clientTenantConfig.getClientTenantMapping().values().iterator()
                .forEachRemaining(datasourceClientCredential ->
                        Flyway.configure()
                                .validateOnMigrate(false)
                                .table("_SCHEMA_VERSION")
                                .baselineOnMigrate(true)
                                .outOfOrder(false)
                                .dataSource(datasourceClientCredential.getUrl(),
                                        datasourceClientCredential.getUsername(),
                                        datasourceClientCredential.getPassword())
                                .load()
                                .migrate());
    }
}
