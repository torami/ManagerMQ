package com.disp.dao;



import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.disp.dao.SignalementJDBCTemplate;

public class MainApp {
   public static void main(String[] args) {
      ApplicationContext context = 
             new ClassPathXmlApplicationContext("Beans.xml");

      SignalementJDBCTemplate studentJDBCTemplate = 
      (SignalementJDBCTemplate)context.getBean("SignalementJDBCTemplate");
      
      System.out.println("------Records Creation--------" );
      studentJDBCTemplate.create(2, "high", "Lampe","Changement d'une lampe", "Lampe de l'iut Lyon", "160, avenue de l'université Bron 69500", 3);
      studentJDBCTemplate.create(3, "Medium", "Nettoyage","Nettoyage du trotoire", "Nettoyer les feuilles d'automne ", "23B Route d'heyrieux 69800 ST Priest", 4);
     
      System.out.println("------Listing Multiple Records--------" );
      List<Signalement> signalement = studentJDBCTemplate.listSignalements();
      for (Signalement record : signalement) {
         System.out.print("ID : " + record.getId() );
         System.out.print(", Importance : " + record.getImportance() );
         System.out.println(", Object : " + record.getObject());
      }

      System.out.println("----Updating Record with ID = 2 -----" );
      //studentJDBCTemplate.update(2, 20);

      System.out.println("----Listing Record with ID = 2 -----" );
      Signalement signalement1 = studentJDBCTemplate.getSignalement(2);
      System.out.print("ID : " + signalement1.getId() );
      System.out.print(", Name : " + signalement1.getImportance() );
    
	  
   }
}