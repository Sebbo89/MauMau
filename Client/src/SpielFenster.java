
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.omg.CORBA.INTERNAL;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sebastian
 */
public class SpielFenster extends javax.swing.JFrame {
    IClient client;
    IServer server;
    int selectedCardID = -4711;
    ArrayList<JLabel> JLabelListe = new ArrayList();
    /**
     * Creates new form SpielFenster
     */
    public SpielFenster() {
        
    }
    
    public SpielFenster(IClient client, IServer server) throws RemoteException {
        this.client = client;
        this.server = server;
        initComponents();
        
        // ID von TopCard holen und als Icon setzen
        int dummy = server.getTopCardID();
        
        // 1. Image einlesen, String mit dummy-Zahl zusammensetzen. Dummy stellt ID der Karte da und Bild kann
        // kann somit zugeordnet werden
        BufferedImage topImg = null;
        try {
            URL topCardResource = SpielFenster.class.getResource( "img/" + dummy + ".png");
            topImg = ImageIO.read(new File(topCardResource.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 2. Image neu skalieren
        Image topImgSkaliert = topImg.getScaledInstance(jLabel2.getWidth(), jLabel2.getHeight(),
        Image.SCALE_SMOOTH);
        
        // 3. Image zuweisen an jLabel 2
        Icon topCardIcon = new ImageIcon(topImgSkaliert);
        jLabel2.setIcon(topCardIcon);
        
        // selbes für Hintergrund tun
        
        // 1. Image einlesen
        BufferedImage img = null;
        try {
            URL backResource = SpielFenster.class.getResource( "img/back.png");
            img = ImageIO.read(new File(backResource.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 2. Image neu skalieren
        Image dimg = img.getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(),
        Image.SCALE_SMOOTH);
        
        // 3. Image zuweisen an jLabel1
        Icon backIcon = new ImageIcon(dimg);
        jLabel1.setIcon(backIcon);
        
        // Hand des Spielers grafisch ausgeben
        spielerhandZeichnen();
        
        // Fenster abhängig von Spieleranzahl zeichnen
        switch (server.anzahlReadyListeAusgeben()) {
            // 2 Spieler
            case 2: {
                break;
            }
            // 3 Spieler
            case 3: {
                break;
            }
            // 4 Spieler
            case 4: {
                break;
            }
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
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        buttonPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Miau Miau v.0.0.1");

        jPanel1.setToolTipText("");

        jLabel2.setToolTipText("Topkarte");

        jLabel1.setToolTipText("Kartenstapel");

        jTextField1.setText("Miau! Miau! Das ist die Chateingabe!");
        jTextField1.setToolTipText("");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jButton1.setText("Karte spielen");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Karte ziehen");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Aussetzen");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 770, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(10, 10, 10)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            if (client.getSpielerAmZug()) {
                try {
                    if (selectedCardID >= 0) {
                        server.spieleKarte(selectedCardID);
                        if (client.getHand().size() == 1) {
                            popupZeigen("Du hast nur noch eine Karte! Miau!");
                        }
                    } else {
                        popupZeigen("Katzenmeister My Auz: Du bist nicht am Zug! Deine Zeit wird noch kommen!");
                        jTextArea1.append("\n" + "Katzenmeister My Auz: Bitte wähle zuerste eine Karte aus! Miauz genau!");
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(SpielFenster.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                popupZeigen("Katzenmeister My Auz: Du bist nicht am Zug! Deine Zeit wird noch kommen!");
                jTextArea1.append("\n" + "Katzenmeister My Auz: Du bist nicht am Zug! Deine Zeit wird noch kommen!");
                //this.jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(SpielFenster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        try {
            // TODO add your handling code here:
            this.server.spielerWechseln();
        } catch (RemoteException ex) {
            Logger.getLogger(SpielFenster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3MouseClicked

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
                Date d1 = new Date();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss ");
                String formattedDate = df.format(d1);
                try {
                    this.server.broadcastMessage( "\n" + "[" + formattedDate + "] " + this.client.getBenutzername() +": " +  jTextField1.getText());
                } catch (RemoteException ex) {
                    Logger.getLogger(ClientFenster.class.getName()).log(Level.SEVERE, null, ex);
                }

                jTextField1.setText(null);
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            if (client.getSpielerAmZug()) {
                // Eine Karte ziehen, falls noch nicht geschehen.
                if (client.getZiehenCounter() == 0) {
                    client.karteZiehen(1);
                    client.setZiehenCounter(1);
                    jPanelLoeschen();
                    spielerhandZeichnen();
                    revalidate();
                    
                } else {
                    popupZeigen("Du hast bereits eine Karte gezogen! Spiele eine Karte oder setze aus!");
                }
            } else {
                popupZeigen("Katzenmeister My Auz: Du bist nich am Zug! Deine Zeit wird noch kommen!");
                jTextArea1.append("\n" + "Katzenmeister My Auz: Du bist nich am Zug! Deine Zeit wird noch kommen!");
                //this.jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(SpielFenster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            // TODO add your handling code here:
            
                if (client.getSpielerAmZug()) {
                    if (client.getZiehenCounter() != 0) {
                        client.setZiehenCounter(0);
                        server.spielerWechseln();
                        popupZeigen("Du setzt eine Runde aus!");
                    } else {
                        popupZeigen("Ziehe zunächst eine Karte, bevor du aussetzt!");
                    }
                    
                } else {
                    popupZeigen("Katzenmeister My Auz: Du bist nich einmal am Zug! Wie willst du dann Aussetzen?!");
                    jTextArea1.append("\n" + "Katzenmeister My Auz: Du bist nich einmal am Zug! Wie willst du dann Aussetzen?!");
                    //this.jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
                }
        } catch (RemoteException ex) {
            Logger.getLogger(SpielFenster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(SpielFenster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SpielFenster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SpielFenster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SpielFenster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SpielFenster().setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
    
    void nachrichtInTextAreaEinfuegen(String message) {
        this.jTextArea1.append(message);
        this.jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
    }

    public void topCardIconAendern(int selectedCardID) {
        // ID von TopCard holen und als Icon setzen
                    int dummy = selectedCardID;

                    // 1. Image einlesen, String mit dummy-Zahl zusammensetzen. Dummy stellt ID der Karte da und Bild kann
                    // kann somit zugeordnet werden
                    BufferedImage topImg = null;
                    try {
                        URL topCardResource = SpielFenster.class.getResource( "img/" + dummy + ".png");
                        topImg = ImageIO.read(new File(topCardResource.getPath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // 2. Image neu skalieren
                    Image topImgSkaliert = topImg.getScaledInstance(jLabel2.getWidth(), jLabel2.getHeight(),
                    Image.SCALE_SMOOTH);

                    // 3. Image zuweisen an jLabel 2
                    Icon topCardIcon = new ImageIcon(topImgSkaliert);
                    jLabel2.setIcon(topCardIcon);
    }

    public void spielerhandZeichnen() throws RemoteException {
        ArrayList<Card> tmpHand = client.getHand();
        JLabelListe.clear();
        for (int i = 0; i < client.getHand().size(); i++) {
            // ID von Karte holen und als Icon setzen
            int cardID = client.getHand().get(i).getID();
            String cardIDString = Integer.toString(cardID);
            
            JLabel tmpLabel = new JLabel();
            tmpLabel.setName(cardIDString);
            JLabelListe.add(tmpLabel);
            JLabelListe.get(i).setSize(jLabel1.getWidth(), jLabel1.getHeight());
            jPanel2.add(JLabelListe.get(i));
            
            // ID von Karte holen und als Icon setzen
            /*int cardID = client.getHand().get(i).getID();
            String cardIDString = Integer.toString(cardID);*/
            

            // 1. Image einlesen, String mit dummy-Zahl zusammensetzen. Dummy stellt ID der Karte da und Bild kann
            // kann somit zugeordnet werden
            BufferedImage cardImg = null;
            try {
                URL cardResource = SpielFenster.class.getResource( "img/" + cardID + ".png");
                cardImg = ImageIO.read(new File(cardResource.getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 2. Image neu skalieren
            Image cardImgSkaliert = cardImg.getScaledInstance(JLabelListe.get(i).getWidth(), JLabelListe.get(i).getHeight(),
            Image.SCALE_SMOOTH);
            
            // 3. Image zuweisen an jLabel zuweisen
            Icon cardImgIcon = new ImageIcon(cardImgSkaliert);
            JLabelListe.get(i).setIcon(cardImgIcon);
                        
            //MouseListener einfügen, der die selektierte Karte setzt
            MouseAdapter adapt = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) 
                {
                    JLabel card;
                    try {
                        card = (JLabel) e.getSource();
                        
                    } catch (Exception ex) {
                        card = null;
                    }
                    
                    //String path = "initial";
                    if(card != null)
                    {
                        selectedCardID = Integer.parseInt(card.getName());
                    }
                         System.out.println(selectedCardID);
                    
                }
            };
     
            JLabelListe.get(i).addMouseListener(adapt);
                    
            }
    }

    void jPanelLoeschen() {
        jPanel2.removeAll();
        jPanel2.validate();
        jPanel2.repaint();
    }

    void popupZeigen(final String message) {
        Runnable doPopupZeigen = new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(rootPane, message);
            }
        };
        
        SwingUtilities.invokeLater(doPopupZeigen);
        
    }
    
    void popupZeigen2(final String message) {
        Runnable doPopupZeigen = new Runnable() {
            @Override
            public void run() {
                    JOptionPane.showMessageDialog(rootPane, message);
            }
        };
        
        SwingUtilities.invokeLater(doPopupZeigen);
        
    }

    void siebenerAbfragen() throws RemoteException {
        ArrayList<Card> tmpHand = client.getHand();
        
        //ImageIcon icon = new ImageIcon("bild.jpg");
        int antwort = JOptionPane.showConfirmDialog(rootPane, "Du hast eine 7 auf der Hand! Möchtest du sie spielen?", "Meldung", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE); //icon); 
        
        if (antwort == JOptionPane.OK_OPTION) {
            System.out.println("Alles ok!");
        } else if (antwort == JOptionPane.NO_OPTION) {
            client.karteZiehen(server.getSiebenerCounter());
            int tmpCounter = server.getSiebenerCounter();
            server.setSiebenerCounter(0);
            this.jPanelLoeschen();
            this.spielerhandZeichnen();
            server.broadcastMessage("\n" + client.getBenutzername() + " hat " + tmpCounter + " Karten aufgenommen!");       
        }
    } 

    void nachFarbeFragen() {
   
        final JFrame frageFenster = new JFrame();
        JPanel panel = new JPanel();
        frageFenster.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));      
        
        for (int i = 90; i < 94; i++) {    
            
            // 1. Image einlesen, String mit i-Zahl zusammensetzen. i stellt ID der Karte da und Bild kann
            // kann somit zugeordnet werden
            final JLabel tmpLabel = new JLabel();
            tmpLabel.setSize(140, 200);
            
            BufferedImage cardImg = null;
            try {
                URL cardResource = SpielFenster.class.getResource( "img/" + i + ".png");
                cardImg = ImageIO.read(new File(cardResource.getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 2. Image neu skalieren
            Image cardImgSkaliert = cardImg.getScaledInstance(tmpLabel.getWidth(), tmpLabel.getHeight(),
            Image.SCALE_SMOOTH);
            
            // 3. Image zuweisen an jLabel zuweisen
            Icon cardImgIcon = new ImageIcon(cardImgSkaliert);
            tmpLabel.setIcon(cardImgIcon);
            // Name des Labels setzen, um später Event abfangen zu können
            switch (i) {
                case 90: {
                    tmpLabel.setName("Herz");
                    tmpLabel.setToolTipText(tmpLabel.getName());
                    break;
                }
                case 91: {
                    tmpLabel.setName("Karo");
                    tmpLabel.setToolTipText(tmpLabel.getName());
                    break;
                }
                case 92: {
                    tmpLabel.setName("Kreuz");
                    tmpLabel.setToolTipText(tmpLabel.getName());
                    break;
                }
                case 93: {
                    tmpLabel.setName("Pik");
                    tmpLabel.setToolTipText(tmpLabel.getName());
                    break;
                }
            }
            panel.add(tmpLabel);

            tmpLabel.addMouseListener(new MouseAdapter() {
                // Überschreiben der Clicked-Methode
                public void mouseClicked(MouseEvent arg0) {
                    try {
                        
                        // Farbe der gewählten Karte zwischenspeichern und weiterverarbeiten in Switch
                        String gewaehlteFarbe = arg0.getComponent().getName();
                        String tmpFarbe = null;
                        switch (gewaehlteFarbe) {
                            case "Herz": {
                                server.alleSpielerFensterAktualisieren(90);
                                // Fenster zernichten!!!!!!
                                frageFenster.dispose();
                                break;
                            }
                            case "Karo": {
                                server.alleSpielerFensterAktualisieren(91);
                                // Fenster zernichten!!!!!!
                                frageFenster.dispose();
                                break;
                            }
                            case "Kreuz": {
                                server.alleSpielerFensterAktualisieren(92);
                                // Fenster zernichten!!!!!!
                                frageFenster.dispose();
                                break;
                            }
                            case "Pik": {
                                server.alleSpielerFensterAktualisieren(93);
                                // Fenster zernichten!!!!!!
                                frageFenster.dispose();
                                break;
                            }
                        }
                        
                    } catch (RemoteException ex) {
                        Logger.getLogger(SpielFenster.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
        }
        
        
        // Jetzt sind Karten erstellt, jetzt das JFrame packen und dann erst anzeigen
        frageFenster.pack();
        frageFenster.setLocationRelativeTo(null);
        frageFenster.setTitle("Wähle eine Farbe!");
        frageFenster.setVisible(true);
    }
}
