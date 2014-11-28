package Model;

import java.util.*;
import java.io.*;
import java.sql.*;

public class DataModel {
 java.sql.Connection conn = null;

 //Method to connect to DB
 public void initialize(){ 
 try {
 Class.forName("com.mysql.jdbc.Driver").newInstance();
 conn = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/miniproject?user=root&password=");
 
 }
 catch (Exception e) {
  System.out.println(e.toString());
  System.exit(0);
 } 
 System.out.println("Connection established");
 }


 //Method to add team to database
 public void addTeam(String teamName, String manager){
  try{
   PreparedStatement p=conn.prepareStatement("INSERT INTO Team SET TName=? ,Manager =?");
   p.setString(1, teamName);
   p.setString(2, manager);
   p.execute();
  } catch(Exception e){
   System.out.println(e.toString());
   return;
  }
 }

 //Method to update a team
 public void updateTeam(String current, String teamName, String manager){
  try{
   PreparedStatement p = conn.prepareStatement("UPDATE Team SET TName=?, Manager=? WHERE TName=?");
   p.setString(1, teamName);
   p.setString(2, manager);
   p.setString(3, current);
   p.execute();
  } catch(Exception e){
   System.out.println(e.toString());
   return;
  }
 }

 //Method to delete a team
 public void deleteTeam(String name){
  try{
   PreparedStatement r = conn.prepareStatement("DELETE FROM Players WHERE TName=?");
   r.setString(1, name);
   r.execute();
   PreparedStatement p = conn.prepareStatement("DELETE FROM Team WHERE TName=?");
   p.setString(1, name);
   p.execute();
  } catch(Exception e){
   System.out.println(e.toString());
   return;
  }
 }

 //Method to load a team from text file
 public void loadTeam(File file){
  try{
   FileReader reader = new FileReader(file);
   Scanner in = new Scanner(reader);
   
   //Inserting team into database
   String tName = in.nextLine();
   String manager = in.nextLine();
   PreparedStatement p = conn.prepareStatement("INSERT INTO Team SET TName=?,Manager=?");
   p.setString(1,tName);
   p.setString(2,manager);
   p.execute();
   
   //Inserting players to database
   while(in.hasNextLine()){
    String name = in.nextLine();
    System.out.println(name);
    String role = in.next();
    System.out.println(role);
    int year = in.nextInt();
    System.out.println(year);
    in.nextLine();
    PreparedStatement q = conn.prepareStatement("INSERT INTO Players SET PName=?, PRole=?, PYearGroup=?,TName=?");
    q.setString(1,name);
    q.setString(2,role);
    q.setInt(3,year);
    q.setString(4, tName);
    q.execute();
   }
  } catch(Exception e){
   System.out.println(e.toString());
   return;
  }
 }
 // Method to get current teams from database
 public ResultSet getTeams(){
    java.sql.ResultSet u = null;
    try{
        java.sql.Statement s= conn.createStatement();
        String query = "SELECT TName FROM Team";
         u = s.executeQuery(query);
         return u;
    } catch(Exception e){
        System.out.println(e.toString());
        System.exit(0);
    }
    return u;
 }
 
 //Method to save a team into a File
 public void saveToFile(String tName){
  try{
   java.sql.Statement s = conn.createStatement();
   String q1 ="SELECT * FROM Team WHERE TName='"+tName+"'";
   String q2 ="SELECT * FROM Players WHERE TName='"+tName+"'";
   java.sql.ResultSet u = s.executeQuery(q1);
   //java.sql.ResultSet r = s.executeQuery(q2);

   PrintWriter writer = new PrintWriter(new File(tName+".txt"));
   //Printing the team name and manager name
   while(u.next()){
    writer.println(u.getString("TName"));
    writer.println(u.getString("Manager"));
   }
   u = s.executeQuery(q2);
   // Printing player infos
   while(u.next()){
    writer.println(u.getString("PName"));
    writer.println(u.getString("PRole")+"\t\t\t"+ u.getString("PYearGroup"));
   }
   writer.close();
   System.out.println("saved");
  } catch(Exception e){
   System.out.println(e.toString());
   return;
  }
 }

 /** Method that deal with Player table
 */

 //Method to add a player
 public void addPlayer(String name, String role, int yearG, String tName){
  try{
   System.out.println("adding");
   PreparedStatement p = conn.prepareStatement("INSERT INTO Players SET PName=?, PRole=?, PYearGroup=?,TName=?");
   System.out.println("adding");

   p.setString(1,name);
   p.setString(2, role);
   p.setInt(3,yearG);
   p.setString(4,tName);
   p.execute();
  } catch(Exception e){
   System.out.println(e.toString());
   return;
  }
 }

 //Method to update a player
 public void transferPlayer(String pName, String tName){
  try{
   PreparedStatement p = conn.prepareStatement("UPDATE Players SET TName=? WHERE PName=?");
   p.setString(1,tName);
   p.setString(2,pName);
   p.execute();
  } catch(Exception e){
   System.out.println(e.toString());
   return;
  }
 }
 
 public void updatePlayer(String current, String pName, String pRole, int pYear){
     try{
   PreparedStatement p = conn.prepareStatement("UPDATE Players SET PName=?,PRole=?,PYearGroup=? WHERE PName=?");
   p.setString(1,pName);
   p.setString(2,pRole);
   p.setInt(3, pYear);
   p.setString(4, current);
   p.execute();
  } catch(Exception e){
   System.out.println(e.toString());
   return;
  }
 }

 //Method to release a player
 public void releasePlayer(String name){
  try{
   PreparedStatement p = conn.prepareStatement("DELETE FROM Players WHERE PName=?");
   p.setString(1,name);
   p.execute();
  } catch(Exception e){
   System.out.println(e.toString());
   System.exit(0);
  }
 }
 
 // Method to search player
 public ResultSet searchPlayer(String player){
     java.sql.ResultSet u = null;
    try{
        java.sql.Statement s= conn.createStatement();
        String query = "SELECT * FROM Players WHERE PName='"+player+"'";
         u = s.executeQuery(query);
         return u;
    } catch(Exception e){
        System.out.println(e.toString());
        System.exit(0);
    }
    return u;
 }
 // Method to get players
 public ResultSet getPlayers(String team){
    java.sql.ResultSet u = null;
    try{
        java.sql.Statement s= conn.createStatement();
        String query = "SELECT * FROM Players WHERE TName='"+team+"'";
         u = s.executeQuery(query);
         return u;
    } catch(Exception e){
        System.out.println(e.toString());
        System.exit(0);
    }
    return u;
 }
 
 // Method to get only player names
 public ResultSet getPlayerNames(String team){
     java.sql.ResultSet u = null;
    try{
        java.sql.Statement s= conn.createStatement();
        String query = "SELECT PName FROM Players WHERE TName='"+team+"'";
         u = s.executeQuery(query);
         return u;
    } catch(Exception e){
        System.out.println(e.toString());
        System.exit(0);
    }
    return u;
 }
 
 public String getManagerName(String team){
     java.sql.ResultSet u = null;
     String temp = null;
     try{
        java.sql.Statement s= conn.createStatement();
        String query = "SELECT Manager FROM Team WHERE TName='"+team+"'";
         u = s.executeQuery(query);
         while(u.next())
            temp= u.getString("Manager");
    } catch(Exception e){
        System.out.println(e.toString());
        System.exit(0);
    }
    return temp;
 }

 // Temporary method to test
 public static void main(String [] args) {
  System.out.println("This program demos DB CRUD operations");
  DataModel db = new DataModel();
  db.initialize();
  //db.addPlayer("Israel Oladejo", "ST", 2016, "Field Marshals");
  //db.updateTeam("Elites", "Emmanuel Ampedu");
  //db.transferPlayer("Israel Oladejo", "League of Legends");
  //db.saveToFile("League of Legends");
  db.loadTeam(new File("League of Legends.txt"));
 }

}