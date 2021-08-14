
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Connor
 */
public class orderHistory extends javax.swing.JFrame {
    
    String currUser;
    
    private void displayCurrUser () {
        currUserDisplay.setText(currUser);
    }
    
    private void displayOrderHistory() {
        dbSetup my = new dbSetup();
        Connection conn = null;
        
        // Open the connection to the database
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/db906_group7_project2", my.user, my.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        JOptionPane.showMessageDialog(null,"DB Opened...");
        
        // Get the total number of orders for the given customer
        int numOrders = 0;
        try {
            Statement stmt = conn.createStatement();
            String sqlStmt = "SELECT COUNT(\"orderID\") AS count FROM orders WHERE \"userName\"=" + "'" + currUser + "';";
            ResultSet result = stmt.executeQuery(sqlStmt);
            while(result.next()){
                //JOptionPane.showMessageDialog(null, result.getInt("count"));
                numOrders = result.getInt("count");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getClass().getName()+": "+e.getMessage());
        }
        
        JOptionPane.showMessageDialog(null, "Found " + numOrders + " orders from user...");
        
        // Get all order IDs for current customer
        String[] orderIDs = new String[numOrders];
        String currID;
        
        try {  
            Statement stmt = conn.createStatement();
            ResultSet rsOrderIDs = stmt.executeQuery("SELECT \"orderID\" FROM orders WHERE \"userName\"=" + "'" + currUser + "';");
            
            int i = 0;
            while(rsOrderIDs.next()){
                currID = rsOrderIDs.getString("orderID");
                orderIDs[i] = currID;
                //JOptionPane.showMessageDialog(null, orderIDs[i]);
                i++;
            }    
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getClass().getName()+": "+e.getMessage());
        }
        
        // Get all order dates for current customer
        String[] orderDates = new String[numOrders];
        try {  
            Statement stmt = conn.createStatement();
            ResultSet rsOrderDates = stmt.executeQuery("SELECT \"orderDate\" FROM orders WHERE \"userName\"=" + "'" + currUser + "';");
            
            String currDate;
            int i = 0;
            while(rsOrderDates.next()){
                currDate = rsOrderDates.getString("orderDate");
                orderDates[i] = currDate;
                i++;
            }    
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getClass().getName()+": "+e.getMessage());
        }
        
        // Get all order items from current customer
        
        // TODO: Order History
        //  - For each order ID, get number of each item into an array (currOrderItems)
        //  - Create a string (currOrder), which consists of ID numbers corresponding to each item and their quantity
        //  - Store string of all items in array 'allOrderItems'
        String[] allOrderItems = new String[numOrders];
        int[] currOrderItems = new int[19];
        String currOrder = "";
        String[] allItemIDs = {"E1", "E2", "E3", "E4", "E5", "E6", "E7", "S1", "S2", "S3", "S4", "B1", "B2", "B3", "B4", "B5", "D1", "D2", "D3"};
        try {
            for (int i = 0; i < numOrders; i++) {    
                for (int j = 0; j < 19; j++) {
                    Statement stmt = conn.createStatement();
                    ResultSet rsOrderItems = stmt.executeQuery("SELECT \"" + allItemIDs[j] + "\" AS count FROM orders WHERE \"orderID\" = '" + orderIDs[i] + "';");
                   
                    int currCount = 0;
                    while (rsOrderItems.next()) {
                        currCount = rsOrderItems.getInt("count");
                        currOrderItems[j] = currCount;
                        //JOptionPane.showMessageDialog(null, "Count for " + allItemIDs[j] + " = " + currCount);
                    }
                }
                
                for (int k = 0; k < 19; k++) {
                    for (int l = 0; l < currOrderItems[k]; l++) {
                        if (currOrder.equals("")) {
                            currOrder = allItemIDs[k];
                        } else {
                            currOrder = currOrder + "," + allItemIDs[k];
                        }
                    }
                }
                
                //JOptionPane.showMessageDialog(null, currOrder);
                
                allOrderItems[i] = currOrder;
                currOrder = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getClass().getName()+": "+e.getMessage());
        }
        
        // Get all customizations for current customer
        String[] orderCustomizations = new String[numOrders];
        try {  
            Statement stmt = conn.createStatement();
            ResultSet rsOrderCustoms = stmt.executeQuery("SELECT customizations FROM orders WHERE \"userName\"=" + "'" + currUser + "';");
            
            String currCustom;
            int i = 0;
            while(rsOrderCustoms.next()){
                currCustom = rsOrderCustoms.getString("customizations");
                orderCustomizations[i] = currCustom;
                i++;
            }    
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getClass().getName()+": "+e.getMessage());
        }
        
        // Store all items for current customer in table
        DefaultTableModel OHTable = (DefaultTableModel) orderHistTable.getModel();
        OHTable.setRowCount(0);
        for (int i = 0; i < numOrders; i++) {
            OHTable.addRow(new Object[]{orderIDs[i], orderDates[i], allOrderItems[i], orderCustomizations[i]});
            //JOptionPane.showMessageDialog(null, orderIDs[0]);
        }
        // DEBUGGING
        //String thisOrder = orderIDs[3] + "..." + orderDates[3] + "..." + allOrderItems[3] + "..." + orderCustomizations[3];
        //JOptionPane.showMessageDialog(null, thisOrder);
        
        // Close the connection to the database
        try {
            conn.close();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Connection NOT Closed.");
        }
    }
    /**
     * Creates new form orderHistory
     */
    public orderHistory() {
        currUser = "MARY_SMITH";
        initComponents();
        displayCurrUser();
        displayOrderHistory();
    }
    
    public orderHistory(String cU){
        currUser = cU;
        initComponents();
        displayCurrUser();
        displayOrderHistory();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane1 = new java.awt.ScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        currUserDisplay = new javax.swing.JTextField();
        backToCustButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        orderHistTable = new javax.swing.JTable();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        scrollPane1.add(jScrollPane2);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        scrollPane1.add(jScrollPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Current User:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 12, 128, 35));

        currUserDisplay.setEditable(false);
        currUserDisplay.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        currUserDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currUserDisplayActionPerformed(evt);
            }
        });
        getContentPane().add(currUserDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 12, 190, 35));

        backToCustButton.setBackground(new java.awt.Color(102, 0, 0));
        backToCustButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        backToCustButton.setForeground(new java.awt.Color(255, 255, 255));
        backToCustButton.setText("Back To Order");
        backToCustButton.setToolTipText("");
        backToCustButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        backToCustButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToCustButtonActionPerformed(evt);
            }
        });
        getContentPane().add(backToCustButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 12, 190, 35));

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        orderHistTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Order ID", "Order Date", "Items", "Customizations"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(orderHistTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 880, 480));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void currUserDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_currUserDisplayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_currUserDisplayActionPerformed

    private void backToCustButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToCustButtonActionPerformed
        this.dispose();
        POS_Customer cust = new POS_Customer(currUser);
        cust.setVisible(true);
    }//GEN-LAST:event_backToCustButtonActionPerformed

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
            java.util.logging.Logger.getLogger(orderHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(orderHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(orderHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(orderHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new orderHistory().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backToCustButton;
    private javax.swing.JTextField currUserDisplay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable orderHistTable;
    private java.awt.ScrollPane scrollPane1;
    // End of variables declaration//GEN-END:variables
}
