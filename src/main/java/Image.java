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

    public Color[][] getImageFromBufferedImage(BufferedImage image)
    {
        return getColorArrayFromImage(image);
    }

    public BufferedImage getBufferedImageFromArray(Color[][] image)
    {
        BufferedImage bufferedImage = new BufferedImage(image[0].length, image.length,
                BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < image.length; y++) {
            for (int x = 0; x < image[y].length; x++) {
                bufferedImage.setRGB(x, y, image[y][x].getRGB());
            }
        }

        return bufferedImage;
    }

    private BufferedImage getBufferedImage(String fileName) throws IOException
    {
        return ImageIO.read(Image.class.getResourceAsStream(fileName));
    }

    private Color[][] getColorArrayFromImage(BufferedImage originalImage)
    {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        Color[][] colors = new Color[height][width];
        for (int y = 0; y < colors.length; y++)
        {
            for (int x = 0; x < colors[0].length; x++)
            {
                colors[y][x] = new Color(originalImage.getRGB(x, y));
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
