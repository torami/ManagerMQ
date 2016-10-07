package com.disp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class SignalementMapper implements RowMapper<Signalement> {
   public Signalement mapRow(ResultSet rs, int rowNum) throws SQLException {
      Signalement reporting = new Signalement();
      reporting.setId(rs.getInt("id"));
      reporting.setImportance(rs.getString("importance"));
      reporting.setObject(rs.getString("object"));
      reporting.setDescription(rs.getString("description"));
      reporting.setComment(rs.getString("comment"));
      reporting.setPlace(rs.getString("place"));
      reporting.setIdreporter(rs.getInt("idreporter"));
      return reporting;
   }
}