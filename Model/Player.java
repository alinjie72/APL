/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author Norbert
 */
public class Player {
    private String playerName ;
    private int year ;
    private String role ;
    public static String[]roles = {"GK","DEF","MID","ATT"} ;

public Player(String name, int yr, int pos){

  playerName = name ;
  year = yr;
  role = roles[pos];
}

public Player(){
 playerName = null;
 year = 0 ;
 role = "";
    
}

public String getName(){

return playerName;
}

public int getYear(){

 return year;   
}

public String getRole(){

return role;    
}

public void setName(String nom){

 playerName = nom;
}

public void setYear(int yr){

  year = yr;  
}

public void setRole(int pos){

   role = roles[pos]; 
}
public static void main (String[] argv){

Player cro = new Player("Cristiano Ronaldo dos Santos Aviero",2016,3);
Player mid = new Player();


mid.setName("Messi TaxEvasi");
mid.setYear(2020);
mid.setRole(3);

System.out.println("Player 1 : " + cro.getName() + " , " + cro.getYear() + " , " + cro.getRole());
System.out.println("Player 2 : " + mid.getName() + " , " + mid.getYear() + " , " + mid.getRole());

}
}