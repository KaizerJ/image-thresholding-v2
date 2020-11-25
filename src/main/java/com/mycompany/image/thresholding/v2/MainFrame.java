package com.mycompany.image.thresholding.v2;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Jonay
 */
public class MainFrame extends javax.swing.JFrame {

    private JFileChooser fc;
    private Mat originalImage;
    private int layer;
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        initFileChooser();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        fileMenuGroup = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenuGroup = new javax.swing.JMenu();
        thresholdingMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Umbralizar imagen v2");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 379, Short.MAX_VALUE)
        );

        fileMenuGroup.setText("Ficheros");

        openMenuItem.setText("Abrir imagen");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenuGroup.add(openMenuItem);
        fileMenuGroup.add(jSeparator1);

        exitMenuItem.setText("Salir");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenuGroup.add(exitMenuItem);

        menuBar.add(fileMenuGroup);

        editMenuGroup.setText("Editar");

        thresholdingMenuItem.setText("Umbralizar");
        thresholdingMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thresholdingMenuItemActionPerformed(evt);
            }
        });
        editMenuGroup.add(thresholdingMenuItem);

        menuBar.add(editMenuGroup);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void thresholdingMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thresholdingMenuItemActionPerformed
        String res = JOptionPane.showInputDialog(this, 
                                                 "Introduce el valor Umbral", 
                                                 "Establece el umbral", 
                                                 JOptionPane.PLAIN_MESSAGE);
        
        if( res == null ) return;
        
        try{
            int umbral = Integer.parseInt(res);
            if(umbral > 255){
                JOptionPane.showMessageDialog(this, 
                                     "Introduce un número entero entre 0 y 255", 
                                     "Umbral no válido", 
                                     JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(this.originalImage != null ){
                Mat processedImage = umbralizar(originalImage, umbral);
                ImageWindow iw = createImageWindow(
                        (BufferedImage) HighGui.toBufferedImage(processedImage)); 
                iw.setTitle("Umbral = " + umbral);
                //iw.setLayer(++layer);
            }
            
        } catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, 
                                     "Introduce un número entero entre 0 y 255", 
                                     "Umbral no válido", 
                                     JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_thresholdingMenuItemActionPerformed

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        int result = fc.showOpenDialog(this);
        
        if( result == JFileChooser.APPROVE_OPTION ){
            File file = fc.getSelectedFile();
            try{
                String path = Files.probeContentType(file.toPath());
                if (path == null || !path.startsWith("image/")){
                    JOptionPane.showMessageDialog(this, 
                                         "El archivo seleccionado no es una imagen válida", 
                                         "Archivo no válido", 
                                         JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                closeAllInternalFrames();
                this.layer = 0;
                this.originalImage = Imgcodecs.imread(
                        fc.getSelectedFile().getAbsolutePath());
                
                
                ImageWindow iw = createImageWindow(
                        (BufferedImage) HighGui.toBufferedImage(originalImage));
                iw.setTitle(file.getName());
                iw.setLayer(layer);

                this.thresholdingMenuItem.setEnabled(true);
            }catch(IOException e){
                JOptionPane.showMessageDialog(this, 
                                         "Se produjo un error al intentar abrir el fichero", 
                                         "Error de lectura del fichero", 
                                         JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        askBeforeExit();
    }//GEN-LAST:event_formWindowClosing

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        askBeforeExit();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    //Al readjustar la ventana mueve las internalframes que se vayan a quedar fuera
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        Dimension size = this.getSize();
        JInternalFrame[] frames = this.desktopPane.getAllFrames();
        
        for (JInternalFrame frame : frames) {
            Point loc = frame.getLocation();
            if(loc.x > size.width - 50){
                loc.x = size.width - 50;
            }
            
            if(loc.y > size.height - 100){
                loc.y = size.height - 100;
            }
            
            frame.setLocation(loc);
        }
    }//GEN-LAST:event_formComponentResized

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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu editMenuGroup;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenuGroup;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem thresholdingMenuItem;
    // End of variables declaration//GEN-END:variables
   
    private Mat umbralizar(Mat imagen_original, Integer umbral) {
        // crear dos imágenes en niveles de gris con el mismo
        // tamaño que la original
        Mat imagenGris = new Mat(imagen_original.rows(), imagen_original.cols(), CvType.CV_8U);
        Mat imagenUmbralizada = new Mat(imagen_original.rows(), imagen_original.cols(), CvType.CV_8U);
        // convierte a niveles de grises la imagen original
        Imgproc.cvtColor(imagen_original, imagenGris, Imgproc.COLOR_BGR2GRAY);
        // umbraliza la imagen:
        // - píxeles con nivel de gris > umbral se ponen a 1
        // - píxeles con nivel de gris <= umbra se ponen a 0
        Imgproc.threshold(imagenGris, imagenUmbralizada, umbral, 255, Imgproc.THRESH_BINARY);
        // se devuelve la imagen umbralizada
        return imagenUmbralizada;
    }
    
    private void initFileChooser() {
        FileFilter imgs = new FileNameExtensionFilter(
                "Imagénes (JPEG, PNG, BMP y TIFF)", "jpg", "jpeg", "png", "bmp",
                "dib", "tiff", "tif");
        
        this.fc = new JFileChooser();
        
        this.fc.addChoosableFileFilter(imgs);
    }

    private void closeAllInternalFrames() {
        JInternalFrame[] frames = this.desktopPane.getAllFrames();
        
        for (JInternalFrame frame : frames) {
            frame.dispose();
        }
    }

    private ImageWindow createImageWindow(BufferedImage bufferedImage) {
        ImageWindow imageWindow = new ImageWindow();
        desktopPane.add(imageWindow);
        imageWindow.setImage(bufferedImage);
        imageWindow.setVisible(true);
        
        imageWindow.addComponentListener(new ComponentListener(){
            @Override
            public void componentResized(ComponentEvent e) {}

            @Override
            public void componentMoved(ComponentEvent e) {
                MainFrame.this.updateMinimunSize();
            }

            @Override
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentHidden(ComponentEvent e) {}
        });
        
        return imageWindow;
    }

    private void askBeforeExit() {
        int res = JOptionPane.showConfirmDialog(this, "¿Desea salir?", "Salir", 
                                                JOptionPane.YES_NO_OPTION);
        
        if( res == JOptionPane.YES_OPTION ){
            System.exit(0);
        }
    }

    private void updateMinimunSize() {
        /*JInternalFrame[] frames = this.desktopPane.getAllFrames();
        int maxX = 0;
        int maxY = 0;
        for (JInternalFrame frame : frames) {
            Point frameLoc = frame.getLocation();
            if(frameLoc.x > maxX){
                maxX = frameLoc.x;
            }
            
            if(frameLoc.y > maxY){
                maxY = frameLoc.y;
            }
        }
        Point desktopPaneLoc = this.desktopPane.getLocation();
        this.setMinimumSize(new Dimension(maxX + desktopPaneLoc.x, maxY + desktopPaneLoc.y));
        System.out.println("MaxX = " + (maxX+desktopPaneLoc.x) + " MaxY = " + (maxY+desktopPaneLoc.y));*/
    }
}
