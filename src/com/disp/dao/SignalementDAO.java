package com.disp.dao;

import java.util.List;

import javax.sql.DataSource;

public interface SignalementDAO {
   /** 
    * This is the method to be used to initialize
    * database resources ie. connection.
    */
   public void setDataSource(DataSource ds);
   /** 
    * This is the method to be used to create
    * a record in the Reporting table.
    */
   public void create(int id, String importance, String object,String description,String comment,String place,int idreporter);
   /** 
    * This is the method to be used to list down
    * a record from the Reporting table corresponding
    * to a passed reporting id.
    */
   public Signalement getSignalement(Integer id);
   /** 
    * This is the method to be used to list down
    * all the records from the Reporting table.
    */
   public List<Signalement> listSignalements();
   /** 
    * This is the method to be used to delete
    * a record from the Reporting table corresponding
    * to a passed reporting id.
    */
   public void delete(Integer id);
   /** 
    * This is the method to be used to update
    * a record into the Reporting table.
    */
//   public void update(Integer id, Integer age);
}