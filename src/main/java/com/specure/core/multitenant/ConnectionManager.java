package com.specure.core.multitenant;

import com.specure.core.config.ClientTenantConfig;
import com.specure.core.config.DataSourceConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Service
@AllArgsConstructor
public class ConnectionManager {

    private DataSourceConfig dataSourceProperties;
    private ClientTenantConfig clientTenantConfig;

    public Connection checkConnection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }

    public DataSource buildDataSource(String tenantId) {
        HikariConfig config = new HikariConfig();
        config.setMinimumIdle(dataSourceProperties.getMinIdle());
        config.setMaximumPoolSize(dataSourceProperties.getPoolSize());
        config.setDriverClassName(dataSourceProperties.getDriverClassName());
        config.setJdbcUrl(clientTenantConfig.getClientTenantMapping().get(tenantId).getUrl());
        config.setUsername(clientTenantConfig.getClientTenantMapping().get(tenantId).getUsername());
        config.setPassword(clientTenantConfig.getClientTenantMapping().get(tenantId).getPassword());
        return new HikariDataSource(config);
    }

    public DriverManagerDataSource buildDefaultDataSource() {
        DriverManagerDataSource defaultDataSource = new DriverManagerDataSource();
        defaultDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        ClientTenantConfig.DatasourceClientCredential datasourceClientCredential = clientTenantConfig.getClientTenantMapping().get(clientTenantConfig.getDefaultTenant());
        defaultDataSource.setUrl(datasourceClientCredential.getUrl());
        defaultDataSource.setUsername(datasourceClientCredential.getUsername());
        defaultDataSource.setPassword(datasourceClientCredential.getPassword());
        return defaultDataSource;
    }

}
