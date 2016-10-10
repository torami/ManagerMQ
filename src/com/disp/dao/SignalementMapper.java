package com.disp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
/**
 *This class allow us to Get as a result set the reporting 
 * @author Rami
 * */
public class SignalementMapper implements RowMapper<Signalement> {
   public Signalement mapRow(ResultSet rs, int rowNum) throws SQLException {
      Signalement reporting = new Signalement();
      reporting.setId(rs.getInt("id"));
      reporting.setImportance(rs.getString("importance"));
      reporting.setObject(rs.getString("object"));
      reporting.setDescription(rs.getString("description"));
      reporting.setComment(rs.getString("comment"));
      reporting.setPlace(rs.getString("place"));
      reporting.setIdreporter(rs.getInt("idperson"));
      return reporting;
   }
}