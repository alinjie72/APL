/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Norbert
 */
public class TeamModel {
    private String teamName ;
    private String manager ;
    private ArrayList<Player> squadList ;
    
    

public TeamModel(String name,String managr, ArrayList<Player> sList){
teamName = name ;
manager = managr ;
squadList = sList ;  

}    
    
public TeamModel(){
teamName = null;
manager = null ;
squadList = new ArrayList<Player>() ;

}
    
 public String getName(){
 
   return teamName ;  
 }   
 
 public String getMan(){
 
   return manager ;
 }

 public ArrayList<Player> getSquad(){
 
   return squadList ;
 }

public void setName(String name){

     teamName = name;
}

public void setMan(String man){

    manager = man ;
}

public void addPlayer(String name,int yr, int pos){
if(squadList.size()<= 25){    
Player neo = new Player(name,yr,pos);
squadList.add(neo);
}

else{
  System.out.println("No more vacancy this team!");
  System.exit(25);
          
  }
    
}

public void addPlayer(Player x){
if(squadList.size()<= 25){   
squadList.add(x);
}

else{
  System.out.println("No more vacancy this team!");
  System.exit(25);
          
  }
    
}

public boolean isPlayer(Player x){
        return squadList.contains(x);
}

public void removePlayer(Player dan){
    
if(squadList.contains(dan))
 squadList.remove(dan);

else
 System.out.println("There is no such player in this team");
}



public void changeMan(String man){
   manager = man;
}
 
public void printInfo(){
System.out.println("Team Name : " + teamName);
System.out.println("Manager : " + manager);
System.out.println("Players : ");

Iterator<Player> x =  squadList.iterator();  
while(x.hasNext()){
    Player obiwan = x.next();
    System.out.println(obiwan.getName() +" " + obiwan.getYear() +"  "+obiwan.getRole());
}
}

        
        
        
public static void main (String[] argv){

Player cro = new Player("Cristiano Ronaldo dos Santos Aviero",2016,3);
Player mid = new Player();


mid.setName("Messi TaxEvasi");
mid.setYear(2020);
mid.setRole(3);

System.out.println("Player 1 : " + cro.getName() + " , " + cro.getYear() + " , " + cro.getRole());
System.out.println("Player 2 : " + mid.getName() + " , " + mid.getYear() + " , " + mid.getRole());

TeamModel wala = new TeamModel();
wala.changeMan("Jose Mourinho");
wala.addPlayer(cro);
wala.addPlayer(mid);
System.out.println(wala.isPlayer(mid));
wala.removePlayer(mid);
wala.setName("Mobuntu Swallows");
wala.printInfo();
}
}