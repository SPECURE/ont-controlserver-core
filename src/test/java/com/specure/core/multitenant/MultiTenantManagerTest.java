package com.specure.core.multitenant;

import com.google.common.collect.ImmutableMap;
import com.specure.core.config.ClientTenantConfig;
import com.specure.core.config.ElasticIndexTenantConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import static com.specure.core.TestConstants.DEFAULT_STRING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MultiTenantManagerTest {

    @MockBean
    private ConnectionManager connectionManager;
    @MockBean
    private ElasticIndexTenantConfig elasticIndexTenantConfig;
    @MockBean
    private ClientTenantConfig clientTenantConfig;
    @Mock
    private DriverManagerDataSource driverManagerDataSource;
    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;

    private MultiTenantManager multiTenantManager;

    @Before
    public void setUp() {
        multiTenantManager = new MultiTenantManager(elasticIndexTenantConfig, connectionManager, clientTenantConfig);
    }

    @Test
    public void addTenant_correctTenantId_add() throws SQLException {
        when(connectionManager.buildDefaultDataSource()).thenReturn(driverManagerDataSource);
        when(connectionManager.buildDataSource(DEFAULT_STRING)).thenReturn(dataSource);
        when(connectionManager.checkConnection(dataSource)).thenReturn(connection);
        multiTenantManager.dataSource();
        multiTenantManager.addTenant(DEFAULT_STRING);
        assertTrue(multiTenantManager.isTenantExist(DEFAULT_STRING));
    }

    @Test
    public void setCurrentTenant_correctTenant_set() throws SQLException {
        when(clientTenantConfig.getClientTenantMapping()).thenReturn(
                ImmutableMap.of(DEFAULT_STRING, DEFAULT_STRING));
        when(connectionManager.buildDefaultDataSource()).thenReturn(driverManagerDataSource);
        when(connectionManager.buildDataSource(DEFAULT_STRING)).thenReturn(dataSource);
        when(connectionManager.checkConnection(dataSource)).thenReturn(connection);
        multiTenantManager.dataSource();
        multiTenantManager.addTenant(DEFAULT_STRING);
        multiTenantManager.setCurrentTenant(DEFAULT_STRING);
        assertEquals(DEFAULT_STRING, multiTenantManager.getCurrentTenant());
    }

    @Test
    public void setCurrentTenant_emptyTenant_set() throws SQLException {
        when(clientTenantConfig.getClientTenantMapping()).thenReturn(new HashMap<>());
        when(connectionManager.buildDefaultDataSource()).thenReturn(driverManagerDataSource);
        when(connectionManager.buildDataSource(DEFAULT_STRING)).thenReturn(dataSource);
        when(connectionManager.checkConnection(dataSource)).thenReturn(connection);
        when(clientTenantConfig.getDefaultTenant()).thenReturn(DEFAULT_STRING);
        multiTenantManager.dataSource();
        multiTenantManager.addTenant(DEFAULT_STRING);
        multiTenantManager.setCurrentTenant(DEFAULT_STRING);
        assertEquals(DEFAULT_STRING, multiTenantManager.getCurrentTenant());
    }

}
