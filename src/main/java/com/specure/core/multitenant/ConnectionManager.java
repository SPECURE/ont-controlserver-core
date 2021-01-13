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
        config.setJdbcUrl(String.format(dataSourceProperties.getUrl(), clientTenantConfig.getClientTenantMapping().get(tenantId)));
        config.setUsername(dataSourceProperties.getUsername());
        config.setPassword(dataSourceProperties.getPassword());
        return new HikariDataSource(config);
    }

    public DriverManagerDataSource buildDefaultDataSource() {
        DriverManagerDataSource defaultDataSource = new DriverManagerDataSource();
        defaultDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        defaultDataSource.setUrl(String.format(dataSourceProperties.getUrl(), clientTenantConfig.getClientTenantMapping().values().iterator().next()));
        defaultDataSource.setUsername(dataSourceProperties.getUsername());
        defaultDataSource.setPassword(dataSourceProperties.getPassword());
        return defaultDataSource;
    }

}