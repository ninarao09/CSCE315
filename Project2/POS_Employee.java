import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.xml.sax.Attributes;
import java.sql.DriverManager;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Connor
 */
public class POS_Employee extends javax.swing.JFrame {

    public MenuItem[] items = new MenuItem[19];
    private String selectedID;
    
    private void addMenuItems() {
        // TODO: Grab IDs, names, descriptions, availability, and prices and add to array
        // This function is called in the constructor so that the array will be populated as the window is opening
        dbSetup my = new dbSetup();
        Connection conn = null;
        
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/db906_group7_project2", my.user, my.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        JOptionPane.showMessageDialog(null,"Populating Database...");
        String name     = "";
        double price    = 0.0;
        String id       = "";
        Boolean avail   = true;
        String desc     = "";
        // *********** NAME ***********
        try {
            
            Statement stmt      = conn.createStatement();
            ResultSet rsName    = stmt.executeQuery("SELECT name FROM menu");
            
            int i = 0;
            while(rsName.next()){
                name = rsName.getString("name")+"\n";
                MenuItem toAdd = new MenuItem();
                toAdd.itemName = name.trim();
                items[i] = toAdd;
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getClass().getName()+": "+e.getMessage());
        }
        // *********** DESCRIPTION ***********
        try {
            
            Statement stmt      = conn.createStatement();
            ResultSet rsDesc    = stmt.executeQuery("SELECT description FROM menu");
            
            int i = 0;
            while(rsDesc.next()){
                desc = rsDesc.getString("description")+"\n";
                items[i].itemDescript = desc.trim();
                i++;
            } 
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getClass().getName()+": "+e.getMessage());
        }
        // *********** PRICE ***********
        try {
            
            Statement stmt     = conn.createStatement();
            ResultSet rsPrice    = stmt.executeQuery("SELECT price FROM menu");
            
            int i = 0;
            while(rsPrice.next()){
                price = rsPrice.getDouble("price");
                items[i].price = price;
                i++;
            } 
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getClass().getName()+": "+e.getMessage());
        }
        // *********** ID ***********
        try {
            
            Statement stmt     = conn.createStatement();
            ResultSet rsID    = stmt.executeQuery("SELECT \"menuID\" FROM menu");
            
            int i = 0;
            while(rsID.next()){
                id = rsID.getString("menuID")+"\n";
                items[i].itemID = id.trim();
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getClass().getName()+": "+e.getMessage());
        }
        // *********** AVAILABILITY ***********
        try {
            
            Statement stmt     = conn.createStatement();
            ResultSet rsAvail    = stmt.executeQuery("SELECT availability FROM menu");
            
            int i = 0;
            while(rsAvail.next()){
                avail = rsAvail.getBoolean("availability");
                items[i].itemAvail = avail;
                i++;
            }    
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getClass().getName()+": "+e.getMessage());
        }  
        // TEST
        /*
        for (int i = 0; i < 19; i++){
            JOptionPane.showMessageDialog(null, items[i].itemName + ","
             + items[i].itemDescript + "," + items[i].itemID + ","
             + items[i].itemAvail + "," + items[i].price );
        }
        */
        
        try {
            conn.close();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Connection NOT Closed.");
        }
    }
    
    private void displayCurrEmp() {
        // TODO: Get full name of current employee and display it in:
        //      - currEmpDisplay
        currEmpDisplay.setText(currUser);
    }
    
    private void displayItemInfo(String currID) {
        String itemName = "";
        String itemDescript = "";
        double itemPrice = 0.0;
        boolean itemAvail = true;
        
        for (MenuItem item : items) {
            if (item.itemID.equals(currID)) {
                itemName = item.itemName;
                itemDescript = item.itemDescript;
                itemPrice = item.price;
                itemAvail = item.itemAvail;
            }
        }
        
        itemNameDisplay.setText(itemName);
        descriptDisplay.setText(itemDescript);
        itemPriceDisplay.setText(String.valueOf(itemPrice));
        itemAvailDisplay.setText(String.valueOf(itemAvail));
    }
    
    private void displayTotalSales() {
        // TODO: calculate total sales from table... only needs to be done
        //              once per new window
        dbSetup my = new dbSetup();
        Connection conn = null;
        
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/db906_group7_project2", my.user, my.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        JOptionPane.showMessageDialog(null,"DB Opened...");
        
        try {
            double totalSalesPrice = 0.0;
            Statement stmt = conn.createStatement();
            ResultSet rsSum1 = stmt.executeQuery("SELECT SUM(\"E1\") FROM orders WHERE \"E1\" >= 1");
            while(rsSum1.next()) {
                double itemSalesPrice1 = rsSum1.getDouble(1) * 5;
                totalSalesPrice += itemSalesPrice1;
            }
            
            ResultSet rsSum2 = stmt.executeQuery("SELECT SUM(\"E2\") FROM orders WHERE \"E2\" >= 1");
            while(rsSum2.next()) {
                double itemSalesPrice2 = rsSum2.getDouble(1) * 5.75;
                totalSalesPrice += itemSalesPrice2;
            }
            
            ResultSet rsSum3 = stmt.executeQuery("SELECT SUM(\"E3\") FROM orders WHERE \"E3\" >= 1");
            while(rsSum3.next()) {
                double itemSalesPrice3 = rsSum3.getDouble(1) * 6.5;
                totalSalesPrice += itemSalesPrice3;
            }
            
            ResultSet rsSum4 = stmt.executeQuery("SELECT SUM(\"E4\") FROM orders WHERE \"E4\" >= 1");
            while(rsSum4.next()) {
            double itemSalesPrice4 = rsSum4.getDouble(1) * 3.5;
            totalSalesPrice += itemSalesPrice4;
            }
            
            ResultSet rsSum5 = stmt.executeQuery("SELECT SUM(\"E5\") FROM orders WHERE \"E5\" >= 1");
            while(rsSum5.next()) {
            double itemSalesPrice5 = rsSum5.getDouble(1) * 25;
            totalSalesPrice += itemSalesPrice5;
            }
                    
            ResultSet rsSum6 = stmt.executeQuery("SELECT SUM(\"E6\") FROM orders WHERE \"E6\" >= 1");
            while(rsSum6.next()) {
            double itemSalesPrice6 = rsSum6.getDouble(1) * 60;
            totalSalesPrice += itemSalesPrice6;  
            }
            
            ResultSet rsSum7 = stmt.executeQuery("SELECT SUM(\"E7\") FROM orders WHERE \"E7\" >= 1");
            while(rsSum7.next()) {
            double itemSalesPrice7 = rsSum7.getDouble(1) * 120;
            totalSalesPrice += itemSalesPrice7;        
            }
            
            ResultSet rsSum8 = stmt.executeQuery("SELECT SUM(\"S1\") FROM orders WHERE \"S1\" >= 1");
            while(rsSum8.next()) {
            double itemSalesPrice8 = rsSum8.getDouble(1) * 1.25;
            totalSalesPrice += itemSalesPrice8;   
            }
            
            ResultSet rsSum9 = stmt.executeQuery("SELECT SUM(\"S2\") FROM orders WHERE \"S2\" >= 1");
            while(rsSum9.next()) {
            double itemSalesPrice9 = rsSum9.getDouble(1) * 0.5;
            totalSalesPrice += itemSalesPrice9; 
            }
            
            ResultSet rsSum10 = stmt.executeQuery("SELECT SUM(\"S3\") FROM orders WHERE \"S3\" >= 1");
            while(rsSum10.next()) {
            double itemSalesPrice10 = rsSum10.getDouble(1) * 1;
            totalSalesPrice += itemSalesPrice10; 
            }
            
            ResultSet rsSum11 = stmt.executeQuery("SELECT SUM(\"S4\") FROM orders WHERE \"S4\" >= 1");
            while(rsSum11.next()) {
            double itemSalesPrice11 = rsSum11.getDouble(1) * 0.1;
            totalSalesPrice += itemSalesPrice11; 
            }
            
            ResultSet rsSum12 = stmt.executeQuery("SELECT SUM(\"B1\") FROM orders WHERE \"B1\" >= 1");
            while(rsSum12.next()) {
            double itemSalesPrice12 = rsSum12.getDouble(1) * 1.5;
            totalSalesPrice += itemSalesPrice12; 
            }
            
            ResultSet rsSum13 = stmt.executeQuery("SELECT SUM(\"B2\") FROM orders WHERE \"B2\" >= 1");
            while(rsSum13.next()) {
            double itemSalesPrice13 = rsSum13.getDouble(1) * 2;
            totalSalesPrice += itemSalesPrice13; 
            }
            
            ResultSet rsSum14 = stmt.executeQuery("SELECT SUM(\"B3\") FROM orders WHERE \"B3\" >= 1");
            while(rsSum14.next()) {
            double itemSalesPrice14 = rsSum14.getDouble(1) * 2;
            totalSalesPrice += itemSalesPrice14; 
            }
            
            ResultSet rsSum15 = stmt.executeQuery("SELECT SUM(\"B4\") FROM orders WHERE \"B4\" >= 1");
            while(rsSum15.next()) {
            double itemSalesPrice15 = rsSum15.getDouble(1) * 3.5;
            totalSalesPrice += itemSalesPrice15; 
            }
            
            ResultSet rsSum16 = stmt.executeQuery("SELECT SUM(\"B5\") FROM orders WHERE \"B5\" >= 1");
            while(rsSum16.next()) {
            double itemSalesPrice16 = rsSum16.getDouble(1) * 2;
            totalSalesPrice += itemSalesPrice16; 
            }
            
            ResultSet rsSum17 = stmt.executeQuery("SELECT SUM(\"D1\") FROM orders WHERE \"D1\" >= 1");
            while(rsSum17.next()) {
            double itemSalesPrice17 = rsSum17.getDouble(1) * 1.5;
            totalSalesPrice += itemSalesPrice17; 
            }
            
            ResultSet rsSum18 = stmt.executeQuery("SELECT SUM(\"D2\") FROM orders WHERE \"D2\" >= 1");
            while(rsSum18.next()) {
            double itemSalesPrice18 = rsSum18.getDouble(1) * 1.5;
            totalSalesPrice += itemSalesPrice18; 
            }
            
            ResultSet rsSum19 = stmt.executeQuery("SELECT SUM(\"D3\") FROM orders WHERE \"D3\" >= 1");
            while(rsSum19.next()) {
            double itemSalesPrice19 = rsSum19.getDouble(1) * 1.25;
            totalSalesPrice += itemSalesPrice19; 
            }
            
            totalSalesDisplay.setText(String.valueOf(totalSalesPrice));
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getClass().getName()+": "+e.getMessage());
        }
        
        try {
            conn.close();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Connection NOT Closed.");
        }
    }
    
    private void displayItemSales(String currID) {
        // TODO: Calculate sales of selected item from database
        
        dbSetup my = new dbSetup();
        Connection conn = null;
        
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/db906_group7_project2", my.user, my.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        JOptionPane.showMessageDialog(null,"DB Opened...");

        // SELECT SUM("currID") FROM orders
        // WHERE "currID" >= 1
        
        double itemSalesPrice = 0.0;
        try {
            
            Statement stmt = conn.createStatement();
            ResultSet rsSum = stmt.executeQuery("SELECT SUM(\"" + currID + "\") FROM orders WHERE \"" + currID + "\" >= 1");
            
            double priceOfItem = 0.0;
            for (int i = 0; i < 19; i++){
                if (currID.equals(items[i].itemID)){
                    priceOfItem = items[i].price;
                   }
            }
            
            while (rsSum.next()) {
                itemSalesPrice = rsSum.getDouble(1) * priceOfItem;
                
            }
            
            
            //System.out.println(itemSalesPrice);
            
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getClass().getName()+": "+e.getMessage());
        }
        
        itemSalesDisplay.setText(String.valueOf(itemSalesPrice));
        
        try {
            conn.close();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Connection NOT Closed.");
        }
    }
    
    private void displayMostANDLeastItems() {

        dbSetup my = new dbSetup();

        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/db906_group7_project2", my.user, my.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        JOptionPane.showMessageDialog(null, "DB Opened...");

        // crete a list of quantity of each item sold
        String[] itemIDs = {"E1", "E2", "E3", "E4", "E5", "E6", "E7", "S1", "S2", "S3", "S4", "B1", "B2", "B3", "B4", "B5", "D1", "D2", "D3"};
        int[] quanSold = new int[19];
        int most1 = 0;
        int most2 = 0;
        int least1 = Integer.MAX_VALUE;
        int least2 = Integer.MAX_VALUE;
        int ndx1Most = 0;
        int ndx2Most = 0; 
        int ndx1Least = 0;
        int ndx2Least = 0;
        try {
             
            Statement stmt = conn.createStatement();
            for (int i = 0; i < 19; i++) {
                ResultSet rsSum = stmt.executeQuery("SELECT SUM(\"" + itemIDs[i] + "\") FROM orders");
                while(rsSum.next()) {
                    quanSold[i] = rsSum.getInt(1);
                }
            }

            // find the largest two elements
            for (int i = 0; i < quanSold.length; i++) {
                if (quanSold[i] > most1) {
                    most2 = most1;
                    most1 = quanSold[i];
                    ndx1Most = i;
                } else if (quanSold[i] > most2) {
                    most2 = quanSold[i];
                    ndx2Most = i;
                }
            }  

            // find the smallest two elements
            for (int i = 0; i < quanSold.length; i++) {
                if (quanSold[i] < least1) {
                    least2 = least1;
                    least1 = quanSold[i];
                    ndx1Least = i;
                } else if (quanSold[i] < least2) {
                    least2 = quanSold[i];
                    ndx2Least = i;
                }
            }
            
            // 2 most trending
            //______.setText(String.valueOf(items[ndx1Most].itemName));
            //______.setText(String.valueOf(items[ndx2Most].itemName]);
            JOptionPane.showMessageDialog(null, "Two most trending items: " + items[ndx1Most].itemName + ", " + items[ndx2Most].itemName + "\nTwo least trending items: " + items[ndx1Least].itemName + ", " + items[ndx2Least].itemName);
            // 2 least trending
            //______.setText(String.valueOf(items[ndx1Least].itemName));
            //______.setText(String.valueOf(items[ndx2Least].itemName));
            

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getClass().getName()+": "+e.getMessage());
        }
    }
    
    /**
     * Creates new form POS_Employee
     */
    public POS_Employee() {
        initComponents();
        currUser = "$test1";
        addMenuItems();
        displayCurrEmp();
        displayTotalSales();
    }
    
    String currUser = "";
    
    public POS_Employee(String cU){
        currUser = cU;
        initComponents();
        addMenuItems();
        displayCurrEmp();
        displayTotalSales();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuItemsPanel = new javax.swing.JPanel();
        E1 = new javax.swing.JButton();
        E2 = new javax.swing.JButton();
        E3 = new javax.swing.JButton();
        E4 = new javax.swing.JButton();
        E5 = new javax.swing.JButton();
        E6 = new javax.swing.JButton();
        E7 = new javax.swing.JButton();
        S1 = new javax.swing.JButton();
        S2 = new javax.swing.JButton();
        S3 = new javax.swing.JButton();
        S4 = new javax.swing.JButton();
        B1 = new javax.swing.JButton();
        B2 = new javax.swing.JButton();
        B3 = new javax.swing.JButton();
        B4 = new javax.swing.JButton();
        B5 = new javax.swing.JButton();
        D1 = new javax.swing.JButton();
        D2 = new javax.swing.JButton();
        D3 = new javax.swing.JButton();
        trendsButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        descriptDisplay = new javax.swing.JTextField();
        itemNameDisplay = new javax.swing.JTextField();
        itemPriceDisplay = new javax.swing.JTextField();
        itemAvailDisplay = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        itemSalesDisplay = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        totalSalesDisplay = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        currEmpDisplay = new javax.swing.JTextField();
        addEmpBtn = new javax.swing.JButton();
        logOutBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menuItemsPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        E1.setBackground(new java.awt.Color(102, 0, 0));
        E1.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        E1.setForeground(new java.awt.Color(255, 255, 255));
        E1.setText("Chicken Fingers");
        E1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        E1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E1ActionPerformed(evt);
            }
        });

        E2.setBackground(new java.awt.Color(102, 0, 0));
        E2.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        E2.setForeground(new java.awt.Color(255, 255, 255));
        E2.setText("Chicken Sandwich");
        E2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        E2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E2ActionPerformed(evt);
            }
        });

        E3.setBackground(new java.awt.Color(102, 0, 0));
        E3.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        E3.setForeground(new java.awt.Color(255, 255, 255));
        E3.setText("Club Sandwich");
        E3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        E3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E3ActionPerformed(evt);
            }
        });

        E4.setBackground(new java.awt.Color(102, 0, 0));
        E4.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        E4.setForeground(new java.awt.Color(255, 255, 255));
        E4.setText("Grilled Cheese");
        E4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        E4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E4ActionPerformed(evt);
            }
        });

        E5.setBackground(new java.awt.Color(102, 0, 0));
        E5.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        E5.setForeground(new java.awt.Color(255, 255, 255));
        E5.setText("25 Pack");
        E5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        E5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E5ActionPerformed(evt);
            }
        });

        E6.setBackground(new java.awt.Color(102, 0, 0));
        E6.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        E6.setForeground(new java.awt.Color(255, 255, 255));
        E6.setText("50 Pack");
        E6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        E6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E6ActionPerformed(evt);
            }
        });

        E7.setBackground(new java.awt.Color(102, 0, 0));
        E7.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        E7.setForeground(new java.awt.Color(255, 255, 255));
        E7.setText("100 Pack");
        E7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        E7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E7ActionPerformed(evt);
            }
        });

        S1.setBackground(new java.awt.Color(102, 0, 0));
        S1.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        S1.setForeground(new java.awt.Color(255, 255, 255));
        S1.setText("French Fries");
        S1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        S1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                S1ActionPerformed(evt);
            }
        });

        S2.setBackground(new java.awt.Color(102, 0, 0));
        S2.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        S2.setForeground(new java.awt.Color(255, 255, 255));
        S2.setText("Texas Toast");
        S2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        S2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                S2ActionPerformed(evt);
            }
        });

        S3.setBackground(new java.awt.Color(102, 0, 0));
        S3.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        S3.setForeground(new java.awt.Color(255, 255, 255));
        S3.setText("Potato Salad");
        S3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        S3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                S3ActionPerformed(evt);
            }
        });

        S4.setBackground(new java.awt.Color(102, 0, 0));
        S4.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        S4.setForeground(new java.awt.Color(255, 255, 255));
        S4.setText("Layne's Sauce");
        S4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        S4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                S4ActionPerformed(evt);
            }
        });

        B1.setBackground(new java.awt.Color(102, 0, 0));
        B1.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        B1.setForeground(new java.awt.Color(255, 255, 255));
        B1.setText("Soda, 20oz");
        B1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        B1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B1ActionPerformed(evt);
            }
        });

        B2.setBackground(new java.awt.Color(102, 0, 0));
        B2.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        B2.setForeground(new java.awt.Color(255, 255, 255));
        B2.setText("Sweet Tea, 20oz");
        B2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        B2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B2ActionPerformed(evt);
            }
        });

        B3.setBackground(new java.awt.Color(102, 0, 0));
        B3.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        B3.setForeground(new java.awt.Color(255, 255, 255));
        B3.setText("Lemonade, 20oz");
        B3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        B3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B3ActionPerformed(evt);
            }
        });

        B4.setBackground(new java.awt.Color(102, 0, 0));
        B4.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        B4.setForeground(new java.awt.Color(255, 255, 255));
        B4.setText("Milkshake, 20oz");
        B4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        B4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B4ActionPerformed(evt);
            }
        });

        B5.setBackground(new java.awt.Color(102, 0, 0));
        B5.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        B5.setForeground(new java.awt.Color(255, 255, 255));
        B5.setText("Gatorade");
        B5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        B5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B5ActionPerformed(evt);
            }
        });

        D1.setBackground(new java.awt.Color(102, 0, 0));
        D1.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        D1.setForeground(new java.awt.Color(255, 255, 255));
        D1.setText("Brownie");
        D1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        D1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D1ActionPerformed(evt);
            }
        });

        D2.setBackground(new java.awt.Color(102, 0, 0));
        D2.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        D2.setForeground(new java.awt.Color(255, 255, 255));
        D2.setText("Cookie");
        D2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        D2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D2ActionPerformed(evt);
            }
        });

        D3.setBackground(new java.awt.Color(102, 0, 0));
        D3.setFont(new java.awt.Font("Leelawadee UI", 0, 10)); // NOI18N
        D3.setForeground(new java.awt.Color(255, 255, 255));
        D3.setText("Ice Cream Cone");
        D3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        D3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D3ActionPerformed(evt);
            }
        });

        trendsButton.setBackground(new java.awt.Color(153, 153, 153));
        trendsButton.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        trendsButton.setForeground(new java.awt.Color(0, 0, 0));
        trendsButton.setText("Item Trends");
        trendsButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        trendsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trendsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuItemsPanelLayout = new javax.swing.GroupLayout(menuItemsPanel);
        menuItemsPanel.setLayout(menuItemsPanelLayout);
        menuItemsPanelLayout.setHorizontalGroup(
            menuItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuItemsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuItemsPanelLayout.createSequentialGroup()
                        .addComponent(E1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(E2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(E3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(E4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(E5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(menuItemsPanelLayout.createSequentialGroup()
                        .addComponent(E6, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(E7, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(S1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(S2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(S3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(menuItemsPanelLayout.createSequentialGroup()
                        .addGroup(menuItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(S4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(trendsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(menuItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(menuItemsPanelLayout.createSequentialGroup()
                                .addComponent(B5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(D1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(D2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(D3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(menuItemsPanelLayout.createSequentialGroup()
                                .addComponent(B1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menuItemsPanelLayout.setVerticalGroup(
            menuItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuItemsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(E1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(menuItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(E6, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(E7, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(S1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(S2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(S3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(menuItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(S4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(menuItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(B5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(D3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trendsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setText("Description:");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setText("Item Name:");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("Price:");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setText("Availability:");

        descriptDisplay.setEditable(false);
        descriptDisplay.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        itemNameDisplay.setEditable(false);
        itemNameDisplay.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        itemPriceDisplay.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        itemPriceDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPriceDisplayActionPerformed(evt);
            }
        });

        itemAvailDisplay.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        itemAvailDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAvailDisplayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(descriptDisplay)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(itemNameDisplay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(itemPriceDisplay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(itemAvailDisplay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(itemNameDisplay)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(itemPriceDisplay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(itemAvailDisplay)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setText("Item Sales:");

        itemSalesDisplay.setEditable(false);
        itemSalesDisplay.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setText("Overall Sales:");

        totalSalesDisplay.setEditable(false);
        totalSalesDisplay.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(itemSalesDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(totalSalesDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(itemSalesDisplay)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(totalSalesDisplay)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Current User:");

        currEmpDisplay.setEditable(false);
        currEmpDisplay.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        addEmpBtn.setBackground(new java.awt.Color(102, 0, 0));
        addEmpBtn.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        addEmpBtn.setForeground(new java.awt.Color(255, 255, 255));
        addEmpBtn.setText("Add User");
        addEmpBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        addEmpBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmpBtnActionPerformed(evt);
            }
        });

        logOutBtn.setBackground(new java.awt.Color(102, 0, 0));
        logOutBtn.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        logOutBtn.setForeground(new java.awt.Color(255, 255, 255));
        logOutBtn.setText("Log Out");
        logOutBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        logOutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(currEmpDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addEmpBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logOutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addComponent(menuItemsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(currEmpDisplay))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(addEmpBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(logOutBtn)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(menuItemsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void E1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E1ActionPerformed
        displayItemInfo("E1");
        selectedID = "E1";
        displayItemSales(selectedID);
    }//GEN-LAST:event_E1ActionPerformed

    private void E2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E2ActionPerformed
        displayItemInfo("E2");
        selectedID = "E2";
        displayItemSales(selectedID);
    }//GEN-LAST:event_E2ActionPerformed

    private void E3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E3ActionPerformed
        displayItemInfo("E3");
        selectedID = "E3";
        displayItemSales(selectedID);
    }//GEN-LAST:event_E3ActionPerformed

    private void E4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E4ActionPerformed
        displayItemInfo("E4");
        selectedID = "E4";
        displayItemSales(selectedID);
    }//GEN-LAST:event_E4ActionPerformed

    private void E5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E5ActionPerformed
        displayItemInfo("E5");
        selectedID = "E5";
        displayItemSales(selectedID);
    }//GEN-LAST:event_E5ActionPerformed

    private void E6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E6ActionPerformed
        displayItemInfo("E6");
        selectedID = "E6";
        displayItemSales(selectedID);
    }//GEN-LAST:event_E6ActionPerformed

    private void E7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_E7ActionPerformed
        displayItemInfo("E7");
        selectedID = "E7";
        displayItemSales(selectedID);
    }//GEN-LAST:event_E7ActionPerformed

    private void S1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_S1ActionPerformed
        displayItemInfo("S1");
        selectedID = "S1";
        displayItemSales(selectedID);
    }//GEN-LAST:event_S1ActionPerformed

    private void S2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_S2ActionPerformed
        displayItemInfo("S2");
        selectedID = "S2";
        displayItemSales(selectedID);
    }//GEN-LAST:event_S2ActionPerformed

    private void S3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_S3ActionPerformed
        displayItemInfo("S3");
        selectedID = "S3";
        displayItemSales(selectedID);
    }//GEN-LAST:event_S3ActionPerformed

    private void S4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_S4ActionPerformed
        displayItemInfo("S4");
        selectedID = "S4";
        displayItemSales(selectedID);
    }//GEN-LAST:event_S4ActionPerformed

    private void B1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B1ActionPerformed
        displayItemInfo("B1");
        selectedID = "B1";
        displayItemSales(selectedID);
    }//GEN-LAST:event_B1ActionPerformed

    private void B2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B2ActionPerformed
        displayItemInfo("B2");
        selectedID = "B2";
        displayItemSales(selectedID);
    }//GEN-LAST:event_B2ActionPerformed

    private void B3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B3ActionPerformed
        displayItemInfo("B3");
        selectedID = "B3";
        displayItemSales(selectedID);
    }//GEN-LAST:event_B3ActionPerformed

    private void B4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B4ActionPerformed
        displayItemInfo("B4");
        selectedID = "B4";
        displayItemSales(selectedID);
    }//GEN-LAST:event_B4ActionPerformed

    private void B5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B5ActionPerformed
        displayItemInfo("B5");
        selectedID = "B5";
        displayItemSales(selectedID);
    }//GEN-LAST:event_B5ActionPerformed

    private void D1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D1ActionPerformed
        displayItemInfo("D1");
        selectedID = "D1";
        displayItemSales(selectedID);
    }//GEN-LAST:event_D1ActionPerformed

    private void D2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D2ActionPerformed
        displayItemInfo("D2");
        selectedID = "D2";
        displayItemSales(selectedID);
    }//GEN-LAST:event_D2ActionPerformed

    private void D3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_D3ActionPerformed
        displayItemInfo("D3");
        selectedID = "D3";
        displayItemSales(selectedID);
    }//GEN-LAST:event_D3ActionPerformed

    private void itemPriceDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPriceDisplayActionPerformed
         // TODO: Update table with newPrice for selectedID
        //get price from display text
        // send that value using a query from the database
        dbSetup my = new dbSetup();
        Connection conn = null;
       
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/db906_group7_project2", my.user, my.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
       
        //gets the text value from the interface
        double newPrice = Double.parseDouble(itemPriceDisplay.getText());
       
        try { //executes the query
           
            Statement stmt      = conn.createStatement();
            int rowsInserted = stmt.executeUpdate("UPDATE menu SET price=" + newPrice + " WHERE \"menuID\"=' " + selectedID + "';");
            JOptionPane.showMessageDialog(null,"Price for " + selectedID + " is now " + newPrice);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getClass().getName()+": "+e.getMessage());
        }
       
       
        addMenuItems();
        displayItemInfo(selectedID);
        
        try {
            conn.close();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Connection NOT Closed.");
        }
    }//GEN-LAST:event_itemPriceDisplayActionPerformed

    private void itemAvailDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAvailDisplayActionPerformed
        Boolean newAvail = Boolean.parseBoolean(itemAvailDisplay.getText());
        // TODO: Update table with newAvail for selectedID
        dbSetup my = new dbSetup();
        Connection conn = null;
       
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/db906_group7_project2", my.user, my.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
       
       
        try { //executes the query
           
            Statement stmt      = conn.createStatement();
            int rowsInserted = stmt.executeUpdate("UPDATE menu SET availability=" + newAvail + " WHERE \"menuID\"=' " + selectedID + "';");
            JOptionPane.showMessageDialog(null,"Availability for " + selectedID + " is now " + newAvail);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getClass().getName()+": "+e.getMessage());
        }
       
       
        addMenuItems();
        displayItemInfo(selectedID);
        
        try {
            conn.close();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Connection NOT Closed.");
        }
    }//GEN-LAST:event_itemAvailDisplayActionPerformed

    private void addEmpBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmpBtnActionPerformed
        POS_AddNewEmp emp = new POS_AddNewEmp();
        emp.setVisible(true);
    }//GEN-LAST:event_addEmpBtnActionPerformed

    private void logOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutBtnActionPerformed
        this.dispose();
        POS_Login login = new POS_Login();
        login.setVisible(true);
    }//GEN-LAST:event_logOutBtnActionPerformed

    private void trendsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trendsButtonActionPerformed
        // TODO add your handling code here:
        displayMostANDLeastItems();
    }//GEN-LAST:event_trendsButtonActionPerformed

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
            java.util.logging.Logger.getLogger(POS_Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(POS_Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(POS_Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(POS_Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new POS_Employee().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton B1;
    private javax.swing.JButton B2;
    private javax.swing.JButton B3;
    private javax.swing.JButton B4;
    private javax.swing.JButton B5;
    private javax.swing.JButton D1;
    private javax.swing.JButton D2;
    private javax.swing.JButton D3;
    private javax.swing.JButton E1;
    private javax.swing.JButton E2;
    private javax.swing.JButton E3;
    private javax.swing.JButton E4;
    private javax.swing.JButton E5;
    private javax.swing.JButton E6;
    private javax.swing.JButton E7;
    private javax.swing.JButton S1;
    private javax.swing.JButton S2;
    private javax.swing.JButton S3;
    private javax.swing.JButton S4;
    private javax.swing.JButton addEmpBtn;
    private javax.swing.JTextField currEmpDisplay;
    private javax.swing.JTextField descriptDisplay;
    private javax.swing.JTextField itemAvailDisplay;
    private javax.swing.JTextField itemNameDisplay;
    private javax.swing.JTextField itemPriceDisplay;
    private javax.swing.JTextField itemSalesDisplay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton logOutBtn;
    private javax.swing.JPanel menuItemsPanel;
    private javax.swing.JTextField totalSalesDisplay;
    private javax.swing.JButton trendsButton;
    // End of variables declaration//GEN-END:variables
}
