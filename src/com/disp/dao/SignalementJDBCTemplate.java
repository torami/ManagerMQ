package com.disp.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class SignalementJDBCTemplate implements SignalementDAO {
   private DataSource dataSource;
   private JdbcTemplate jdbcTemplateObject;
   
   public void setDataSource(DataSource dataSource) {
      this.dataSource = dataSource;
      this.jdbcTemplateObject = new JdbcTemplate(dataSource);
   }

   public void create(int id, String importance, String object,String description,String comment,String place,int idreporter) {
      String SQL = "insert into signalement (id, importance, object, description, comment, place, idperson) values (?, ?, ?, ?, ?, ?, ?)";
      
      jdbcTemplateObject.update( SQL, id,importance,object,description,comment,place,idreporter);
      System.out.println("Created Record ID = "+id+"#importance ="+importance+"#object ="+
      object+"#description ="+description+"#comment ="+comment+"#place ="+place+"#idreporter ="+idreporter);
      return;
   }

   public Signalement getSignalement(Integer id) {
      String SQL = "select * from signalement where id = ?";
      Signalement signalement = jdbcTemplateObject.queryForObject(SQL, 
                        new Object[]{id}, new SignalementMapper());
      return signalement;
   }

   public List<Signalement> listSignalements() {
      String SQL = "select * from signalement";
      List <Signalement> signalements = jdbcTemplateObject.query(SQL, 
                                new SignalementMapper());
      return signalements;
   }

   public void delete(Integer id){
      String SQL = "delete from signalement where id = ?";
      jdbcTemplateObject.update(SQL, id);
      System.out.println("Deleted Record with ID = " + id );
      return;
   }

//   public void update(Integer id, Integer age){
//      String SQL = "update Student set age = ? where id = ?";
//      jdbcTemplateObject.update(SQL, age, id);
//      System.out.println("Updated Record with ID = " + id );
//      return;
//   }

}
