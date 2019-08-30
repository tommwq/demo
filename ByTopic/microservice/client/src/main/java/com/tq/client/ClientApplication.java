package com.tq.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;

public class ClientApplication {

  private static ManagedChannel channel(String host, int port) {
    return ManagedChannelBuilder.forAddress(host, port)
      .usePlaintext()
      .build();
  }

  private static ManagedChannel channel(int port) {
    return channel("localhost", port);
  }

  private static void testService() throws Exception {
    ManagedChannel chan = channel(50053);
    new GreetServiceTester(chan).test();
    new RegistryServiceTester(chan).test();
    new ConfigurationServiceTester(chan).test();
    
    chan.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  private static void testSQLite() {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection("jdbc:sqlite:test.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      String sql = "select serviceName,serviceVersion,configurationVersion,configurationContent from configuration";
      ResultSet resultSet = statement.executeQuery(sql);
      while (resultSet.next()) {
        String serviceName = resultSet.getString("serviceName");
        System.out.println(serviceName);
      }
    } catch (SQLException e) {
      e.printStackTrace(System.err);
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        e.printStackTrace(System.err);
      }
    }
  }
    
  public static void main(String[] args) throws Exception {
    // testService();
    testSQLite();
  }
}
