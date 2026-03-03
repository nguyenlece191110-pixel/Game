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
public class Ball {

    int x, y;
    int diameter = 22;
    int xVelocity = 5;
    int yVelocity = 3;
    BufferedImage image;

    public Ball(int x, int y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.image = img;
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;

        // Va chạm trần - KHÔNG CHO CHẠM VÀO KHUNG THỜI GIAN
        if (y <= 40) {
            y = 40;
            yVelocity = -yVelocity;
        }

        // Va chạm đáy
        if (y >= 480 - diameter) {
            y = 480 - diameter;
            yVelocity = -yVelocity;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, diameter, diameter, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, diameter, diameter);
    }
}