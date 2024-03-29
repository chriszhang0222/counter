package com.chris.counter.util;

import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

@Data
public class Captcha {

    private String code;
    private BufferedImage bufferedImage;
    private Random random = new Random();

    public Captcha(int width, int height, int codeCount, int lineCount){
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        g.setColor(getRandomColor(200, 250));
        g.fillRect(0, 0, width, height);
        Font font = new Font("Fixedsys", Font.BOLD, height-5);
        g.setFont(font);
        for(int i=0;i<lineCount;i++){
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width);
            int ye = ys + random.nextInt(height);
            g.setColor(getRandomColor(1, 255));
            g.drawLine(xs, ys, xe, ye);
        }
        float yawpRate = 0.01f;
        int area = (int)(yawpRate * width * height);
        for(int i=0;i<area;i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            bufferedImage.setRGB(x, y, random.nextInt(255));
        }
        this.code = randomStr(codeCount);
        int fontWidth = width/codeCount;
        int fontHeight = height - 5;
        for(int i=0;i<codeCount;i++){
            String str = this.code.substring(i,i+1);
            g.setColor(getRandomColor(1, 255));
            g.drawString(str, i*fontWidth +3, fontHeight-3);

        }
    }

    private String randomStr(int codeCount) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        double r;
        int len = str.length()-1;
        for(int i=0;i<codeCount;i++){
            r = (Math.random()) * len;
            sb.append(str.charAt((int)r));
        }
        return sb.toString();
    }

    private Color getRandomColor(int fc, int bc) {
        if(fc > 255){
            fc = 255;
        }
        if(bc > 255){
            bc = 255;
        }
        int r = fc + random.nextInt(bc-fc);
        int g = fc + random.nextInt(bc-fc);
        int b = fc + random.nextInt(bc-fc);
        return new Color(r,g,b);
    }

    public String getBase64ByteStr() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        String s = Base64.getEncoder().encodeToString(baos.toByteArray());
        s = s.replaceAll("\n", "")
                .replace("\r", "");
        return "data:image/jpg;base64," + s;

    }

    public static void main(String[] args) {
    }
}
