import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * Image: produces a 2d Color array from an image filename
 */

public class Image
{
    public Image()
    {
    }

    public Color[][] getImage(String fileName) throws IOException
    {
        BufferedImage image = getBufferedImage(fileName);
        return getColorArrayFromImage(image);
    }

    private BufferedImage getBufferedImage(String fileName) throws IOException
    {
        return ImageIO.read(Image.class.getResourceAsStream(fileName));
    }

    private Color[][] getColorArrayFromImage(BufferedImage originalImage)
    {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        Color[][] colors = new Color[width][height];
        for (int i = 0; i < colors.length; i++)
        {
            for (int j = 0; j < colors[0].length; j++)
            {
                colors[i][j] = new Color(originalImage.getRGB(i, j));
            }
        }
        return colors;
    }

    public Color[][] createImage(double[][] energy)
    {
        Color[][] newImage = new Color[energy.length][energy[0].length];
        for (int row = 0; row < energy.length; row++)
        {
            for (int col = 0; col < energy[0].length; col++)
            {
                int color = (int) energy[row][col];
                newImage[row][col] = new Color(color, color, color);
            }
        }
        return newImage;
    }
}
