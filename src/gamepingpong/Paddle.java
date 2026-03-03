/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepingpong;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Tran Quoc Thai - CE190243
 */
public class Paddle {
    int x, y;
    int width = 40, height = 90;
    int speed = 100;
    BufferedImage image;

    public Paddle(int x, int y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.image = img;
    }

    public void moveUp() {
    if (y - speed >= 40) y -= speed;
    else y = 40;
}

    public void moveDown() {
        if (y + height < 480) y += speed;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    void move() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}