package com.github.tommwq.riskmanagement.repository;

import com.github.tommwq.utility.database.SQLiteUtil;
import com.github.tommwq.riskmanagement.domain.Industry;
import com.github.tommwq.riskmanagement.repository.api.IndustryRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SQLiteIndustryRepository implements IndustryRepository {

  private Connection sqliteConnection;
  public SQLiteIndustryRepository() throws SQLException {
    sqliteConnection = DriverManager.getConnection("jdbc:sqlite:risk.db");
    SQLiteUtil.createTableInNeed(sqliteConnection, "industry", "create table industry (id INTEGER PRIMARY KEY AUTOINCREMENT, name text, debtAssetRatio double, quickRatio double, roe double);");
  }

  @Override
  public Industry createIndustry(String industryName) {
    String query = String.format("insert into industry (name,debtAssetRatio,quickRatio,roe) values ('%s', 0.0, 0.0, 0.0)", industryName);
    try(Statement statement = sqliteConnection.createStatement()) {
      statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
      ResultSet resultSet = statement.getGeneratedKeys();
      if (!resultSet.next()) {
        throw new RuntimeException("unknown error: fail to execute query: " + query);
      }

      int id = resultSet.getInt(0);
      resultSet.close();
      return new Industry(id);
    } catch (SQLException e) {
      throw new RuntimeException("fail to create industry", e);
    }
  }

  @Override
  public void updateIndustry(Industry industry) {
    String query = String.format("update industry set name='%s', debtAssetRatio=%f, quickRatio=%f, roe=%f where id=%d",
                                 industry.name(),
                                 industry.debtAssetRatio(),
                                 industry.quickRatio(),
                                 industry.roe(),
                                 industry.id());
    try(Statement statement = sqliteConnection.createStatement()) {
      statement.executeUpdate(query);
    } catch (SQLException e) {
      throw new RuntimeException("fail to create industry", e);
    }
  }

  @Override
  public List<Industry> queryAllIndustry() {
    String query = "select id,name,debtAssetRatio,quickRatio,roe from industry";
    try(Statement statement = sqliteConnection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(query);
      ArrayList<Industry> result = new ArrayList<>();
      while (resultSet.next()) {
        int id = resultSet.getInt(0);
        String name = resultSet.getString(1);
        double debtAssetRatio = resultSet.getDouble(2);
        double quickRatio = resultSet.getDouble(3);
        double roe = resultSet.getDouble(4);

        result.add(new Industry(id, name, debtAssetRatio, quickRatio, roe));
      }
      resultSet.close();

      return result;
    } catch (SQLException e) {
      throw new RuntimeException("fail to create industry", e);
    }
  }
}
