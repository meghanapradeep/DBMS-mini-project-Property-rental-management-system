/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author Meghana
 */

import java.awt.HeadlessException;
import java.sql.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



public class apartments extends javax.swing.JFrame {
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    /**
     * Creates new form home
     */
    public apartments() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        table_display();
        nameCBFillData();
            //setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
public void table_display(){
   DefaultTableModel model = (DefaultTableModel)apartmentTb.getModel();
try{
    
        Connection con = ConnectionProvider.getCon();   
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
    
  String query = "SELECT A.apt_ID,A.apt_name,A.sq_ft,A.type_of_apt,A.maintenance,A.floor_no,A.rent,A.start_date,B.building_ID,B.building_name from apartment A,building B where B.building_ID = A.building_ID;";
                
                
       ResultSet res = stmt.executeQuery(query); 
       
            while(res.next())
            {
                String bid = res.getString("building_ID");
                String aptname = res.getString("apt_name");
                String aptid = res.getString("apt_ID");
                String sqft = res.getString("sq_ft");
                String type = res.getString("type_of_apt");
                String mt = res.getString("maintenance");
                String floorno = res.getString("floor_no");
                int rent = res.getInt("rent");
                String startdate = res.getString("start_date");
                String bname = res.getString("building_name");
                
                
                model.addRow(new Object[]{bid,aptname,aptid,sqft,type,mt,floorno,rent,startdate,bname});
                
            }
        res.close();
    
    }
    catch(HeadlessException | SQLException e)
    {
        JOptionPane.showMessageDialog(null,"error in connectivity in table display function");
    }
}

public void nameCBFillData(){
   
try{
        Connection con = ConnectionProvider.getCon();   
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
   
        String query = "SELECT building_name,building_ID FROM building order by building_name;";
        ResultSet rse = stmt.executeQuery(query); 
            while(rse.next())
            {                                             
                 nameCB.addItem(rse.getString("building_name")); 
                
            }
           
      rse.close();
   
    }
    catch(HeadlessException | SQLException e)
    {
        JOptionPane.showMessageDialog(null,"error in connectivity while filling combobox");
    }
}


public void insert() throws Exception
 {
     int capindex;
     String sel = (String)nameCB.getSelectedItem();
     capindex = nameCB.getSelectedIndex()+1;
     
      String aptname = aptnameTF.getText();
        String floornum = (String)floornoCB.getSelectedItem();
        String typeofapt = (String)typeCB.getSelectedItem();
        String sqft = (String)sqftCB.getSelectedItem();
        String percost = (String)percostCB.getSelectedItem();
        
        int rent = Integer.parseInt(rentTF.getText());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(startdate.getDate());
        if(rentTF.getText().isEmpty() | date == null  ){
            JOptionPane.showMessageDialog(null, "please enter the required details");
              
         }
        else{
        
    if(validate(aptname,rent)){    
        try{
                Connection con = ConnectionProvider.getCon();   
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                
                  String query = "SELECT building_ID FROM building order by building_name;";
                ResultSet rsebuild = stmt.executeQuery(query); 
          rsebuild.first();
          rsebuild.absolute(capindex);              
                                                                                                             
      stmt.executeUpdate("insert into apartment(building_ID,apt_name,sq_ft,type_of_apt,maintenance,floor_no,rent,start_date) values('"+rsebuild.getInt("building_ID")+"','"+aptname+"','"+sqft+"','"+typeofapt+"','"+maintenance.getText()+"','"+floornum+"','"+rent+"','"+date+"');");
 
     JOptionPane.showMessageDialog(null,"successfully inserted the apartment details");
        
            aptnameTF.setText("");
            floornoCB.setSelectedItem(null);
            typeCB.setSelectedItem(null);
            sqftCB.setSelectedItem(null);
            percostCB.setSelectedItem(null);
            maintenance.setText("");
            rentTF.setText("");
            startdate.setDate(null);
            
            
      rsebuild.close();
      }
    catch(HeadlessException | SQLException e)
    {
        JOptionPane.showMessageDialog(null,"error in connectivity for insertion function");
        //setVisible(false);
            new owner().setVisible(true);
    }        // TODO add your handling code here:
 }else{
        throw new Exception("check the input");
    }
    display();
 }
 }


 public void display()
 {
     new apartments().setVisible(true);
     
 }
 
 public void update() throws Exception
 {
     DefaultTableModel model = (DefaultTableModel)apartmentTb.getModel();
       int selected = apartmentTb.getSelectedRow();
        int aptid = Integer.parseInt(model.getValueAt(selected,2).toString()); 
        String aptname = aptnameTF.getText();
        String floornum = (String)floornoCB.getSelectedItem();
        String typeofapt = (String)typeCB.getSelectedItem();
        String sqft = (String)sqftCB.getSelectedItem();
        String percost = (String)percostCB.getSelectedItem();
        
        int rent = Integer.parseInt(rentTF.getText());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(startdate.getDate());
    if(validate(aptname,rent)){
        try{
                Connection con = ConnectionProvider.getCon();   
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
    
       
        stmt.executeUpdate("update apartment set apt_name='"+aptname+"',floor_no='"+floornum+"',type_of_apt='"+typeofapt+"',sq_ft='"+sqft+"',maintenance='"+maintenance.getText()+"',rent='"+rent+"',start_date='"+date+"' where apt_ID="+aptid+";");
        
       
        JOptionPane.showMessageDialog(null,"successfully updated the record");
       
            aptnameTF.setText("");
            floornoCB.setSelectedItem(null);
            typeCB.setSelectedItem(null);
            sqftCB.setSelectedItem(null);
            percostCB.setSelectedItem(null);
            maintenance.setText("");
            rentTF.setText("");
            startdate.setDate(null);    
      
            addBtn.setEnabled(true);
            

        }  
   
    catch(HeadlessException | SQLException e)
    {
        JOptionPane.showMessageDialog(null,"error in connectivity for updation process");
        //setVisible(false);
            new apartments().setVisible(true);
    }        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:
 }else{
        throw new Exception("check the input");
    }
    display();
 }
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        aptnameTF = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        apartmentTb = new javax.swing.JTable();
        addBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        nameCB = new javax.swing.JComboBox<>();
        floornoCB = new javax.swing.JComboBox<>();
        aptnamelbl = new javax.swing.JLabel();
        typeCB = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        sqftCB = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        percostCB = new javax.swing.JComboBox<>();
        rentlbl = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        rentTF = new javax.swing.JTextField();
        startdate = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        final_maintenance = new javax.swing.JLabel();
        maintenance = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1175, 628));

        jPanel1.setBackground(new java.awt.Color(255, 204, 255));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setText("OWNER");
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setText("BUILDING");
        jButton3.setBorder(null);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setText("APARTMENTS");
        jButton4.setBorder(null);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setText("TENANTS");
        jButton5.setBorder(null);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setText("PAYMENTS");
        jButton6.setBorder(null);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setText("MISCELLANEOUS EXPENSES");
        jButton7.setBorder(null);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setText("REPORTS");
        jButton8.setBorder(null);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("PROPERTY RENTAL MANAGEMENT SYSTEM");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("APARTMENT DETAILS");

        jLabel3.setText("APARTMENT  NAME");

        jLabel4.setText("FLOOR NUMBER");

        jLabel5.setText("TYPE OF APARTMENT");

        aptnameTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                aptnameTFKeyReleased(evt);
            }
        });

        apartmentTb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "building_ID", "apt_name", "apt_ID", "sq_ft", "type_of_apt", "maintenance", "floor_no", "rent", "start_date", "building_name"
            }
        ));
        apartmentTb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                apartmentTbMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(apartmentTb);

        addBtn.setBackground(new java.awt.Color(102, 153, 255));
        addBtn.setForeground(new java.awt.Color(255, 255, 255));
        addBtn.setText("ADD APARTMENTS");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        editBtn.setBackground(new java.awt.Color(102, 153, 255));
        editBtn.setForeground(new java.awt.Color(255, 255, 255));
        editBtn.setText("EDIT DETAILING");
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        jLabel6.setText("BUILDING NAME");

        floornoCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40" }));

        aptnamelbl.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        aptnamelbl.setForeground(new java.awt.Color(255, 0, 0));

        typeCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1BHK", "2BHK", "3BHK", "4BHK", "PENT HOUSE" }));

        jLabel7.setText("SQUARE FEET");

        sqftCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1010", "1020", "1030", "1040", "1050", "1060", "1070", "1080", "1090", "2000", "2010", "2020", "2030", "2040", "2050", "2060", "2070", "2080", "2090", "3000", "3010", "3020", "3030", "3040", "3050", "3060", "3070", "3080", "3090", "4000", "4010", "4020", "4030", "4040", "4050", "4060", "4070", "4080", "4090", "5000", "5010", "5020", "5030", "5040", "5050", "6000" }));

        jLabel8.setText("MAINTENANCE COST PER SQ_FT");

        percostCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1.00", "1.05", "1.10", "1.15", "1.20", "1.25", "1.30", "1.35", "1.40", "1.45", "1.50", "1.55", "1.60", "1.65", "1.70", "1.75", "1.80", "1.85", "1.90", "2.00", "2.25", "2.50", "2.75", "3.00", "3.25", "3.50", "3.75", "4.00.4.25.4.50.4.75.5.00" }));
        percostCB.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                percostCBPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        percostCB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                percostCBMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                percostCBMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                percostCBMouseReleased(evt);
            }
        });
        percostCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                percostCBActionPerformed(evt);
            }
        });
        percostCB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                percostCBKeyReleased(evt);
            }
        });

        rentlbl.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        rentlbl.setForeground(new java.awt.Color(255, 51, 51));

        jLabel10.setText("RENT");

        rentTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rentTFKeyReleased(evt);
            }
        });

        jLabel11.setText("START DATE");

        final_maintenance.setText("MAINTENANCE");

        maintenance.setEditable(false);

        jButton1.setBackground(new java.awt.Color(102, 153, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("CLOSE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nameCB, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(aptnameTF, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(aptnamelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(final_maintenance, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(maintenance, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(percostCB, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(typeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(sqftCB, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(78, 78, 78)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(floornoCB, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(rentTF, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rentlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(startdate, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(92, 92, 92))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addBtn)
                        .addGap(18, 18, 18)
                        .addComponent(editBtn)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addContainerGap(141, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(nameCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(aptnamelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(aptnameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(typeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sqftCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(percostCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(final_maintenance, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(maintenance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(floornoCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rentTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addComponent(rentlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(startdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBtn)
                    .addComponent(editBtn)
                    .addComponent(jButton1))
                .addGap(92, 92, 92))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
try {
            insert();
               
        } catch (Exception e) {
            System.out.println("Exception check the logs");
            JOptionPane.showMessageDialog(null, "Check the input");
        }
        // TODO add your handling code here:
        
// TODO add your handling code here:
    }//GEN-LAST:event_addBtnActionPerformed

    private void apartmentTbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_apartmentTbMouseClicked
DefaultTableModel model = (DefaultTableModel)apartmentTb.getModel();
int selected = apartmentTb.getSelectedRow();
int buildingID = Integer.parseInt(model.getValueAt(selected,0).toString());

aptnameTF.setText(model.getValueAt(selected,1).toString());
int aptID = Integer.parseInt(model.getValueAt(selected,2).toString());
 String sqftcombosub = model.getValueAt(selected,3).toString();
for(int i=0;i<sqftCB.getItemCount();i++)
{
    if(sqftCB.getItemAt(i).toString().equalsIgnoreCase(sqftcombosub))
    {
        sqftCB.setSelectedIndex(i);
        
    }
}
String typecombosub = model.getValueAt(selected,4).toString();
for(int i=0;i<typeCB.getItemCount();i++)
{
    if(typeCB.getItemAt(i).toString().equalsIgnoreCase(typecombosub))
    {
        typeCB.setSelectedIndex(i);
        
    }
}
maintenance.setText(model.getValueAt(selected,5).toString());
String floornumcombosub = model.getValueAt(selected,6).toString();
for(int i=0;i<floornoCB.getItemCount();i++)
{
    if(floornoCB.getItemAt(i).toString().equalsIgnoreCase(floornumcombosub))
    {
        floornoCB.setSelectedIndex(i);
        
    }
}
rentTF.setText(model.getValueAt(selected,7).toString());
        java.util.Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse((String)model.getValueAt(selected,8));
            startdate.setDate(date);
        } catch (ParseException ex) {
            Logger.getLogger(apartments.class.getName()).log(Level.SEVERE, null, ex);
        }
String bnamecombosub = model.getValueAt(selected,9).toString();
for(int i=0;i<nameCB.getItemCount();i++)
{
    if(nameCB.getItemAt(i).toString().equalsIgnoreCase(bnamecombosub))
    {
        nameCB.setSelectedIndex(i);
        
    }
}


addBtn.setEnabled(false);    
                // TODO add your handling code here:
    }//GEN-LAST:event_apartmentTbMouseClicked

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
try {
            update();
              
        } catch (Exception e) {
            System.out.println("Exception check the logs");
            JOptionPane.showMessageDialog(null, "Check the input");
        }
        // TODO add your handling code here:
         
        // TODO add your handling code here:
    }//GEN-LAST:event_editBtnActionPerformed

    private void aptnameTFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_aptnameTFKeyReleased
String PATTERN  = "^[a-zA-Z][0-9]{0,10}$";
Pattern patt = Pattern.compile(PATTERN);
Matcher match = patt.matcher(aptnameTF.getText());
if(!match.matches())
    aptnamelbl.setText("naming is incorrect");
else
    aptnamelbl.setText(null);          // TODO add your handling code here:
    }//GEN-LAST:event_aptnameTFKeyReleased

    private void percostCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_percostCBActionPerformed

// TODO add your handling code here:
    }//GEN-LAST:event_percostCBActionPerformed

    private void rentTFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rentTFKeyReleased
String PATTERN  = "^[0-9]{0,7}$";
Pattern patt = Pattern.compile(PATTERN);
Matcher match = patt.matcher(rentTF.getText());
if(!match.matches())
    rentlbl.setText("rent entered is incorrect");
else
    rentlbl.setText(null);         // TODO add your handling code here:
    }//GEN-LAST:event_rentTFKeyReleased

    private void percostCBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_percostCBMouseClicked
  // TODO add your handling code here:
    }//GEN-LAST:event_percostCBMouseClicked

    private void percostCBMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_percostCBMousePressed
      // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_percostCBMousePressed

    private void percostCBMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_percostCBMouseReleased
       // TODO add your handling code here:
    }//GEN-LAST:event_percostCBMouseReleased

    private void percostCBKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_percostCBKeyReleased
     // TODO add your handling code here:
    }//GEN-LAST:event_percostCBKeyReleased

    private void percostCBPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_percostCBPopupMenuWillBecomeInvisible
double sqft = Double.parseDouble((String)sqftCB.getSelectedItem());
double percost = Double.parseDouble((String)percostCB.getSelectedItem());
double mt = sqft*percost;
String finalmt = String.valueOf(mt);
maintenance.setText(finalmt);        // TODO add your handling code here:
    }//GEN-LAST:event_percostCBPopupMenuWillBecomeInvisible

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
new tenant().setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
new owner().setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
new building().setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
this.setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
new payment().setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
new misc_expenses().setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
this.setVisible(false);
        new home().setVisible(true);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
new reports().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(apartments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(apartments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(apartments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(apartments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new apartments().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JTable apartmentTb;
    private javax.swing.JTextField aptnameTF;
    private javax.swing.JLabel aptnamelbl;
    private javax.swing.JButton editBtn;
    private javax.swing.JLabel final_maintenance;
    private javax.swing.JComboBox<String> floornoCB;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField maintenance;
    private javax.swing.JComboBox<String> nameCB;
    private javax.swing.JComboBox<String> percostCB;
    private javax.swing.JTextField rentTF;
    private javax.swing.JLabel rentlbl;
    private javax.swing.JComboBox<String> sqftCB;
    private com.toedter.calendar.JDateChooser startdate;
    private javax.swing.JComboBox<String> typeCB;
    // End of variables declaration//GEN-END:variables
 public boolean validate(String aptname,int rent) {
        String PATTERN = "^[a-zA-Z][0-9]{0,10}$";
        Pattern patt = Pattern.compile(PATTERN);
        Matcher match = patt.matcher(aptnameTF.getText());
        String PATTERN2 = "^[0-9]{0,7}$";
        Pattern patt2 = Pattern.compile(PATTERN2);
        Matcher match2 = patt2.matcher(rentTF.getText());
        
        if (!match.matches() | !match2.matches()) {
            return false;
        } else {
            return true;        // TODO add your handling code here:                       
        }
 }

}
