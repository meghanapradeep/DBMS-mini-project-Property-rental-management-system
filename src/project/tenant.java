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

import java.util.regex.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



public class tenant extends javax.swing.JFrame {
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    /**
     * Creates new form home
     */
    public tenant() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        table_display();
        nameCBFillData();
            //setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
public void table_display(){
   DefaultTableModel model = (DefaultTableModel)tenantTb.getModel();
try{
    
        Connection con = ConnectionProvider.getCon();   
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        
    
  String query = "SELECT T.tenant_ID,T.tenant_name,A.apt_ID,T.contact_number,T.t_address,T.id_proof,T.status,A.apt_name,B.building_name from apartment A,building B,tenant T where B.building_ID = A.building_ID and A.apt_ID = T.apt_ID;";
                
                
       ResultSet res = stmt.executeQuery(query); 
       
            while(res.next())
            {
                String tid = res.getString("tenant_ID");
                String tname = res.getString("tenant_name");
                String aptid = res.getString("apt_ID");
                String contactnum = res.getString("contact_number");
                String taddress = res.getString("t_address");
                String idproof = res.getString("id_proof");
                String status = res.getString("status");
                String aptname = res.getString("apt_name");               
                String bname = res.getString("building_name");
                
                
                model.addRow(new Object[]{tid,tname,aptid,contactnum,taddress,idproof,status,aptname,bname});
                
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
   
        String query = "SELECT apt_name,apt_ID FROM apartment order by apt_name;";
        ResultSet rse = stmt.executeQuery(query); 
            while(rse.next())
            {                                             
                 nameCB.addItem(rse.getString("apt_name")); 
                
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
     
      String tenantname = tenantnameTF.getText();
        String contactnum = contactnumTF.getText();
        String taddress = taddressTF.getText();
        String idproof = idproofTF.getText();
        String status = (String)statusCB.getSelectedItem();
        if(tenantnameTF.getText().isEmpty() | contactnumTF.getText().isEmpty() | taddressTF.getText().isEmpty() | idproofTF.getText().isEmpty()  ){
            JOptionPane.showMessageDialog(null, "please enter the required details");
              
         }
        else{
                if(validate(tenantname,contactnum,taddress,idproof)){
                try{
                Connection con = ConnectionProvider.getCon();   
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                
                  String query = "SELECT apt_ID FROM apartment order by apt_name;";
                ResultSet rsebuild = stmt.executeQuery(query); 
          rsebuild.first();
          rsebuild.absolute(capindex);              
                                                                                                             
      stmt.executeUpdate("insert into tenant(tenant_name,apt_ID,contact_number,t_address,id_proof,status) values('"+tenantname+"','"+rsebuild.getInt("apt_ID")+"','"+contactnum+"','"+taddress+"','"+idproof+"','"+status+"');");
 
     JOptionPane.showMessageDialog(null,"successfully inserted the tenant details");
        
            tenantnameTF.setText("");
            contactnumTF.setText("");
            taddressTF.setText("");
            idproofTF.setText("");
            statusCB.setSelectedItem(null);
           
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
     new tenant().setVisible(true);
     
 }
 
 public void update() throws Exception
 {
        DefaultTableModel model = (DefaultTableModel)tenantTb.getModel();
       int selected = tenantTb.getSelectedRow();
        int tenantid = Integer.parseInt(model.getValueAt(selected,0).toString()); 
        String tenantname = tenantnameTF.getText();
        String contactnum = contactnumTF.getText();
        String taddress = taddressTF.getText();
        String idproof = idproofTF.getText();
        String status = (String)statusCB.getSelectedItem();
        
   if(validate(tenantname,contactnum,taddress,idproof)){   

        try{
                Connection con = ConnectionProvider.getCon();   
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
    
       
        stmt.executeUpdate("update tenant set tenant_name='"+tenantname+"',contact_number='"+contactnum+"',t_address='"+taddress+"',id_proof='"+idproof+"',status='"+status+"' where tenant_ID="+tenantid+";");
        
       
        JOptionPane.showMessageDialog(null,"successfully updated the record");
       
            tenantnameTF.setText("");
            contactnumTF.setText("");
            taddressTF.setText("");
            idproofTF.setText("");
            statusCB.setSelectedItem(null);
      
            addBtn.setEnabled(true);
            

        }  
   
    catch(HeadlessException | SQLException e)
    {
        JOptionPane.showMessageDialog(null,"error in connectivity for updation process");
        //setVisible(false);
            new tenant().setVisible(true);
    }        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:        // TODO add your handling code here:
 }else{
       throw new Exception("check the input");
   }
   display();
 }
 
 public void delete()
 {
     DefaultTableModel model = (DefaultTableModel)tenantTb.getModel();
       int selected = tenantTb.getSelectedRow();
        int tenantid = Integer.parseInt(model.getValueAt(selected,0).toString()); 
        try{
                Connection con = ConnectionProvider.getCon();   
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
    
       
        stmt.executeUpdate("update tenant set status = 'INACTIVE' where tenant_ID="+tenantid+";");
        
       
        JOptionPane.showMessageDialog(null,"successfully updated the record for changing status of tenant");
       
            tenantnameTF.setText("");
            contactnumTF.setText("");
            taddressTF.setText("");
            idproofTF.setText("");
            statusCB.setSelectedItem(null);
      
            addBtn.setEnabled(true);
            

        }  
   
    catch(HeadlessException | SQLException e)
    {
        JOptionPane.showMessageDialog(null,"error in connectivity for DELETION process");
        setVisible(false);
            new tenant().setVisible(true);
    } 
        
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
        jLabel5 = new javax.swing.JLabel();
        tenantnameTF = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tenantTb = new javax.swing.JTable();
        addBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        nameCB = new javax.swing.JComboBox<>();
        tenantnamelbl = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        numlbl = new javax.swing.JLabel();
        final_maintenance = new javax.swing.JLabel();
        contactnumTF = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        taddressTF = new javax.swing.JTextArea();
        idproofTF = new javax.swing.JTextField();
        statusCB = new javax.swing.JComboBox<>();
        delBtn = new javax.swing.JButton();
        idprooflbl = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

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
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
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
                .addGap(45, 45, 45)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("PROPERTY RENTAL MANAGEMENT SYSTEM");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("TENANT DETAILS");

        jLabel3.setText("TENANT  NAME");

        jLabel5.setText("CONTACT NUMBER");

        tenantnameTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tenantnameTFKeyReleased(evt);
            }
        });

        tenantTb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "tenant_ID", "tenant_name", "apt_ID", "contact_number", "t_address", "id_proof", "status", "apt_name", "building_name"
            }
        ));
        tenantTb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tenantTbMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tenantTb);

        addBtn.setBackground(new java.awt.Color(102, 153, 255));
        addBtn.setForeground(new java.awt.Color(255, 255, 255));
        addBtn.setText("ADD RECORDS");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        editBtn.setBackground(new java.awt.Color(102, 153, 255));
        editBtn.setForeground(new java.awt.Color(255, 255, 255));
        editBtn.setText("EDIT CHANGES");
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        jLabel6.setText("APARTMENT  NAME");

        tenantnamelbl.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        tenantnamelbl.setForeground(new java.awt.Color(255, 0, 0));

        jLabel7.setText("TENANT ADDRESS");

        jLabel8.setText("ID PROOF");

        numlbl.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        numlbl.setForeground(new java.awt.Color(255, 0, 0));

        final_maintenance.setText("STATUS");

        contactnumTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                contactnumTFKeyReleased(evt);
            }
        });

        taddressTF.setColumns(20);
        taddressTF.setRows(5);
        jScrollPane1.setViewportView(taddressTF);

        idproofTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                idproofTFKeyReleased(evt);
            }
        });

        statusCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTIVE", "INACTIVE" }));

        delBtn.setBackground(new java.awt.Color(102, 153, 255));
        delBtn.setForeground(new java.awt.Color(255, 255, 255));
        delBtn.setText("DELETE");
        delBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delBtnActionPerformed(evt);
            }
        });

        idprooflbl.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        idprooflbl.setForeground(new java.awt.Color(255, 51, 51));

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(47, 47, 47)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(final_maintenance, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addComponent(idproofTF, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(addBtn)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(statusCB, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(editBtn))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(delBtn)
                                    .addComponent(idprooflbl, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(contactnumTF, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(numlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(nameCB, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(62, 62, 62)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tenantnameTF, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tenantnamelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(165, 165, 165)
                                .addComponent(jButton1)))
                        .addGap(0, 236, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(nameCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tenantnamelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tenantnameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(numlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(contactnumTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idproofTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idprooflbl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(final_maintenance, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBtn)
                    .addComponent(editBtn)
                    .addComponent(delBtn))
                .addGap(31, 31, 31)
                .addComponent(jButton1)
                .addGap(62, 62, 62))
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

    private void tenantTbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tenantTbMouseClicked
DefaultTableModel model = (DefaultTableModel)tenantTb.getModel();
int selected = tenantTb.getSelectedRow();
int tenantID = Integer.parseInt(model.getValueAt(selected,0).toString());

tenantnameTF.setText(model.getValueAt(selected,1).toString());
int aptID = Integer.parseInt(model.getValueAt(selected,2).toString());
contactnumTF.setText(model.getValueAt(selected,3).toString());
taddressTF.setText(model.getValueAt(selected,4).toString());
idproofTF.setText(model.getValueAt(selected,5).toString());


 String statuscombosub = model.getValueAt(selected,6).toString();
for(int i=0;i<statusCB.getItemCount();i++)
{
    if(statusCB.getItemAt(i).toString().equalsIgnoreCase(statuscombosub))
    {
        statusCB.setSelectedIndex(i);
        
    }
}
String namecombosub = model.getValueAt(selected,7).toString();
for(int i=0;i<nameCB.getItemCount();i++)
{
    if(nameCB.getItemAt(i).toString().equalsIgnoreCase(namecombosub))
    {
        nameCB.setSelectedIndex(i);
        
    }
}


addBtn.setEnabled(false);    
//delBtn.setEnabled(false); 

// TODO add your handling code here:
    }//GEN-LAST:event_tenantTbMouseClicked

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
try {
            update();
               
        } catch (Exception e) {
            System.out.println("Exception check the logs");
            JOptionPane.showMessageDialog(null, "Check the input");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_editBtnActionPerformed

    private void tenantnameTFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tenantnameTFKeyReleased
String PATTERN  = "^[a-zA-Z_ ]{0,30}$";
Pattern patt = Pattern.compile(PATTERN);
Matcher match = patt.matcher(tenantnameTF.getText());
if(!match.matches())
    tenantnamelbl.setText("naming is incorrect");
else
    tenantnamelbl.setText(null);          // TODO add your handling code here:
    }//GEN-LAST:event_tenantnameTFKeyReleased

    private void contactnumTFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contactnumTFKeyReleased
String PATTERN  = "^[0-9]{10}$";
Pattern patt = Pattern.compile(PATTERN);
Matcher match = patt.matcher(contactnumTF.getText());
if(!match.matches())
    numlbl.setText("contact number is incorrect");
else
    numlbl.setText(null);        // TODO add your handling code here:
    }//GEN-LAST:event_contactnumTFKeyReleased

    private void idproofTFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idproofTFKeyReleased
String PATTERN  = "^[a-zA-Z_ ]{0,30}$";
Pattern patt = Pattern.compile(PATTERN);
Matcher match = patt.matcher(idproofTF.getText());
if(!match.matches())
    idprooflbl.setText("proof details is incorrect");
else
    idprooflbl.setText(null);        // TODO add your handling code here:
    }//GEN-LAST:event_idproofTFKeyReleased

    private void delBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delBtnActionPerformed
delete();
display();
                    // TODO add your handling code here:
    }//GEN-LAST:event_delBtnActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
new payment().setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
new owner().setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
  new building().setVisible(true);      // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
new apartments().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
this.setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
new misc_expenses().setVisible(true);        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(tenant.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tenant.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tenant.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tenant.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new tenant().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JTextField contactnumTF;
    private javax.swing.JButton delBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JLabel final_maintenance;
    private javax.swing.JTextField idproofTF;
    private javax.swing.JLabel idprooflbl;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> nameCB;
    private javax.swing.JLabel numlbl;
    private javax.swing.JComboBox<String> statusCB;
    private javax.swing.JTextArea taddressTF;
    private javax.swing.JTable tenantTb;
    private javax.swing.JTextField tenantnameTF;
    private javax.swing.JLabel tenantnamelbl;
    // End of variables declaration//GEN-END:variables
 public Boolean validate(String tenantname, String contactnum, String taddr,String idproof) {
        String PATTERN = "^[a-zA-Z_ ]{0,30}$";
        Pattern patt = Pattern.compile(PATTERN);
        Matcher match = patt.matcher(tenantnameTF.getText());
        String PATTERN2 = "^[0-9]{0,10}$";
        Pattern patt2 = Pattern.compile(PATTERN2);
        Matcher match2 = patt2.matcher(contactnumTF.getText());
        String PATTERN3 = "^[#.0-9a-zA-Z\\s,-_ ]{0,30}$";
        Pattern patt3 = Pattern.compile(PATTERN3);
        Matcher match3 = patt3.matcher(taddressTF.getText());
        String PATTERN4 = "^[a-zA-Z_ ]{0,30}$";
        Pattern patt4 = Pattern.compile(PATTERN4);
        Matcher match4 = patt4.matcher(idproofTF.getText());
        
        if (!match.matches() | !match2.matches() | !match3.matches() | !match4.matches()) {
            return false;
        } else {
            return true;        // TODO add your handling code here:                       
        }
        
    }
}
