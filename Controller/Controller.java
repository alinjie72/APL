/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.*;
import Model.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

;
/**
 *
 * @author Ali PF Njie
 */
public class Controller implements ActionListener{
    // Views
    Login login = new Login();
    Menu menu = new Menu();
    Teams teams = new Teams();
    NewTeam newTeam = new NewTeam();
    UpdateTeam updateTeam = new UpdateTeam();
    TeamPlayers teamPlayers = new TeamPlayers();
    NewPlayer newPlayer = new NewPlayer();
    ReleasePlayer releasePlayer = new ReleasePlayer();
    TransferPlayer transfer = new TransferPlayer();
    UpdatePlayer updatePlayer = new UpdatePlayer();
    
    // Model
    DataModel database = new DataModel();
    //TeamModel teamModel = new TeamModel();
    
    //  Other variables
    DefaultListModel teamNames;
    DefaultTableModel players;
    DefaultListModel playerNames;
    DefaultComboBoxModel teamName;
    
    /**
     * Adds action listeners to all buttons in the view
     */
    public Controller(){
        // Show the login page
        login.setVisible(true);
        
        
       
    }
    public void control(){
         login.getLoginBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // If username and password are correct, load the teams view and dispose the login view
              if(login.getUserName().equals("admin")&&login.getPassword().equals("admin")){
                  //loadTeamNames();
                  //teams.setVisible(true);
                  menu.getTeams().setModel(getTeamNames());
                  menu.setVisible(true);
                  login.dispose();
              }  
              // else, disllay dialog box to show error
              else{
                  JOptionPane.showMessageDialog(login, "Incorrect username or password");
              }
            }
            });
        
        // ADDING ACTION LISTENERS FOR THE TEAM VIEW
        
        // Add action listener for the create team button on team view
        menu.getNewTeamBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                newTeam.setVisible(true);
            }
        });
        // Add action listener for the update team button on team view
        teams.getUpdateTeamBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(teams.getTeamList().isSelectionEmpty())
                    JOptionPane.showMessageDialog(teams, "Choose a team");
                else
                updateTeam.setVisible(true);
            }
        });
        // Add action listener for the view team button on team view
        menu.getViewTeamBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(menu.getTeams().isSelectionEmpty())
                    JOptionPane.showMessageDialog(teams, "Choose a team");
                else{
                //loadPlayers((String)menu.getTeams().getSelectedValue());
                menu.getPlayers().setModel(getPlayerNames((String)menu.getTeams().getSelectedValue()));
                String team = (String)menu.getTeams().getSelectedValue();
                menu.getTeamName().setText(team);
                menu.getManagerName().setText(database.getManagerName(team));
                //teamPlayers.setVisible(true);
                }
            }
        });
        // Add action listener for the delete team button on team view
        menu.getDeleteTeamBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(menu.getTeams().isSelectionEmpty())
                    JOptionPane.showMessageDialog(teams, "Choose a team");
                else {
                database.deleteTeam((String)menu.getTeams().getSelectedValue());
                menu.getTeams().setModel(getTeamNames());
                //loadTeamNames();
                }
            }
        });
        // Add action listener for the log out button on team view
        menu.getLogoutBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                menu.dispose();
                login.setVisible(true);
            }
        });
        // Add action listener for the save team button on team view
        menu.getSaveTeamBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(menu.getTeams().isSelectionEmpty())
                    JOptionPane.showMessageDialog(teams, "Choose a team");
                else
                database.saveToFile((String)menu.getTeams().getSelectedValue());
            }
        });
        // Add action listener for the load team button on team view
        menu.getLoadTeamBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser chooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("TEXT file", "txt");
                chooser.setFileFilter(filter);
                int status = chooser.showOpenDialog(null);
                if (status == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = chooser.getSelectedFile();
                    String name= selectedFile.getName();
                    database.loadTeam(selectedFile);
                    menu.getTeams().setModel(getTeamNames());
                    //loadTeamNames();
                } 
            }
        });
        
         menu.getNewPlayerBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                newPlayer.setVisible(true); 
            }
        });
         menu.getUpdatePlayerBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                updatePlayer.setVisible(true); 
            }
        });
         menu.getTransferPlayerBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                transfer.setVisible(true); 
            }
        });
        menu.getPlayers().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ResultSet r = database.searchPlayer((String)menu.getPlayers().getSelectedValue());
                if(r==null)
                    return;
                try{
                    
                    r.next();
                    menu.getPlayerName().setText(r.getString("PName"));
                    menu.getPlayerRole().setText(r.getString("PRole"));
                    menu.getPlayerYearGroup().setText(r.getString("PYearGroup"));
                } catch(Exception ex){
                    System.out.println(ex.toString());
                    //System.exit(0);
                }
            }
        });
        menu.getTeams().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                menu.getPlayers().setModel(getPlayerNames((String)menu.getTeams().getSelectedValue()));
                String team = (String)menu.getTeams().getSelectedValue();
                menu.getTeamName().setText(team);
                menu.getManagerName().setText(database.getManagerName(team));
              
            }
        });
        
        // ADDING ACTION LISTENERS FOR THE NEW TEAM VIEW
        newTeam.getCreateBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                database.addTeam(newTeam.getTeamName().getText(), newTeam.getManagerName().getText());
                newTeam.dispose();
                //loadTeamNames();
            }
        });
        
        // update team
        updateTeam.getUpdateBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String t = (String) teams.getTeamList().getSelectedValue();
                updateTeam.dispose();
                database.updateTeam(t, updateTeam.getTeamName(), updateTeam.getManagerName());
                //loadTeamNames();
            }
        });
        
        
        // team players
        menu.getNewPlayerBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                newPlayer.setVisible(true);
            }
        });
        menu.getUpdatePlayerBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                updatePlayer.setPlayerName(menu.getPlayerName().getText());
                updatePlayer.setPlayerRole(menu.getPlayerRole().getText());
                updatePlayer.setPlayerYearGroup(Integer.parseInt(menu.getPlayerYearGroup().getText()));
                updatePlayer.setVisible(true);
            }
        });
        menu.getReleasePlayerBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                releasePlayer.getPlayer().setModel(getPlayerNamesCombo(menu.getTeamName().getText()));
                releasePlayer.getPlayer().addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        ResultSet temp = database.searchPlayer((String)releasePlayer.getPlayer().getSelectedItem());
                        try{
                            temp.next();
                            releasePlayer.getPlayerRole().setText(temp.getString("PRole"));
                            releasePlayer.getPlayerYearGroup().setText(temp.getString("PYearGroup"));
                        } catch(Exception ex){
                        System.out.println(ex.toString());
                        System.exit(0);
                        }
                    }
                });
                
                releasePlayer.setVisible(true);
                releasePlayer.getCurrentTeam().setText(menu.getTeamName().getText());
                
            }
        });
        menu.getTransferPlayerBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                transfer.getCurrentTeam().setText(menu.getTeamName().getText());
                transfer.getPlayer().setModel(getPlayerNamesCombo(menu.getTeamName().getText()));
                transfer.getTransferTo().setModel(getTeamNamesCombo());
                transfer.setVisible(true);
            }
        });
        
        // New Player
        newPlayer.getCreateBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                database.addPlayer(newPlayer.getPlayerName().getText(), newPlayer.getPlayerRole().getText()
                        , Integer.parseInt(newPlayer.getPlayerYearGroup().getText()), 
                        menu.getTeamName().getText());
                newPlayer.dispose();
                newPlayer.getPlayerName().setText("");
                newPlayer.getPlayerRole().setText("");
                newPlayer.getPlayerYearGroup().setText("");
                menu.getPlayers().setModel(getPlayerNames(menu.getTeamName().getText()));
            }
        });
        
        // Update Player
         updatePlayer.getUpdateBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                database.updatePlayer(menu.getPlayerName().getText(), updatePlayer.getPlayerName(), updatePlayer.getPlayerRole(), 
                        updatePlayer.getPlayerYearGroup());
                menu.getPlayers().setModel(getPlayerNames(menu.getTeamName().getText()));
                updatePlayer.dispose();
            }
        });
         
        // Transfer player
          transfer.getTransferBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                database.transferPlayer((String)transfer.getPlayer().getSelectedItem(), 
                        (String)transfer.getTransferTo().getSelectedItem());
                transfer.dispose();
                menu.getPlayers().setModel(getPlayerNames(menu.getTeamName().getText()));
            }
        });
          
        // Release Player
           releasePlayer.getReleaseBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                releasePlayer.dispose();
                database.releasePlayer((String)releasePlayer.getPlayer().getSelectedItem());
                menu.getPlayers().setModel(getPlayerNames(menu.getTeamName().getText()));
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        
    }
    
    /**
     *
     */
    public DefaultListModel getTeamNames(){
        teamNames = new DefaultListModel();
        database.initialize();
        ResultSet temp = database.getTeams();
        try{
            while(temp.next()){
                teamNames.addElement(temp.getString("TName"));
            }
        } catch(Exception e){
            System.out.println(e.toString());
            System.exit(0);
        }
        //teams.getTeamList().setModel(teamNames);
        return teamNames;
    }
    
    // Method to load players of a team

    /**
     *
     * @param team
     */
        public void loadPlayers(String team){
        Object[] names = {"Player Name", "Player Role", "Player Year Group"};
        players = new DefaultTableModel(names, 0){
            @Override
             public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        database.initialize();
        
        ResultSet temp = database.getPlayers(team);
        try{
            while(temp.next()){
                Object[] values = {temp.getString("PName"), temp.getString("PRole"), 
                    temp.getString("PYearGroup")};
                players.addRow(values);
            }
        }catch (Exception e){
                    System.out.println(e.toString());
                    System.exit(0);
                }
        teamPlayers.getPlayers().setModel(players);
        teamPlayers.setTeamName(team);
        teamPlayers.setManagerName(database.getManagerName(team));
    }
    
    /**
     *
     * @param team
     * @return
     */
    public DefaultListModel getPlayerNames(String team){
        playerNames = new DefaultListModel();
        ResultSet temp = database.getPlayerNames(team);
        try {
            while(temp.next()){
            playerNames.addElement(temp.getString("PName"));
            }
        } catch(Exception e){
            System.out.println(e.toString());
            System.exit(0);
        }
        //transfer.getPlayer().setModel(playerNames);
        return playerNames;
    }
    
    public DefaultComboBoxModel getPlayerNamesCombo(String team){
        DefaultComboBoxModel playerNamesCombo = new DefaultComboBoxModel();
        ResultSet temp = database.getPlayerNames(team);
        try {
            while(temp.next()){
            playerNamesCombo.addElement(temp.getString("PName"));
            }
        } catch(Exception e){
            System.out.println(e.toString());
            System.exit(0);
        }
        //transfer.getPlayer().setModel(playerNames);
        return playerNamesCombo;
    }
    
    /**
     *
     * @return
     */
    public DefaultComboBoxModel getTeamNamesCombo(){
        teamName = new DefaultComboBoxModel();
        ResultSet temp = database.getTeams();
         try {
            while(temp.next()){
            teamName.addElement(temp.getString("TName"));
            }
        } catch(Exception e){
            System.out.println(e.toString());
            System.exit(0);
        }
        //transfer.getPlayer().setModel(playerNames);
        return teamName;
    }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args){
        Login l = new Login();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Controller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        Controller controller = new Controller();
        controller.control();
    }
}
