package com.mycompany.image.thresholding.v2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Lienzo extends JPanel{

    private BufferedImage image;


    public Lienzo() {
        this.image = null;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(image != null){
            g.drawImage(image, 0, 0, null);
        } else {
            g.setColor(Color.GRAY);
            g.setFont(new Font( "SansSerif", Font.PLAIN, 20 ));
            g.drawString("Abre una imagen en Ficheros > Abrir", 20, 20);
        }
    }
    
    public void setImage(BufferedImage image){
        this.image = image;
        this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        repaint();
    }
    
}
