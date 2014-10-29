package mysword.utils;

import java.sql.Connection;

/**
 * Created by Administrator on 2014/8/24.
 */
public class ConnectionImpl {
    private String connectionName;
    private String connectionId;
    private Connection connection;

    public ConnectionImpl(){}

    public ConnectionImpl(String connectionName, String connectionId, Connection connection){
        this.connectionName = connectionName;
        this.connectionId = connectionId;
        this.connection = connection;
    }

    public ConnectionImpl(Connection connection) {
        this.connection = connection;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
