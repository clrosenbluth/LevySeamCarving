import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * A class to determine the energy of a given pixel in a picture to be resized.
 * Energy is based on level of contrast between the pixel and its neighbors.
 * Higher energy corresponds to higher contrast.
 */

public class EnergyDetermination
{
    private double maxEnergy;
    private double minEnergy;
    private int maxWidth;
    private int maxHeight;
    private final int MAX_ENERGY = 390150;

    public EnergyDetermination() {}

    public BufferedImage getEnergyImageFromFile(String fileName) throws Exception
    {
        BufferedImage originalImage = getInputStream(fileName);
        Color[][] readImage = readImage(originalImage);
        double[][] brightness = calculateEnergy(readImage);
        Color[][] energyArray = convertToEnergyArray(brightness);
        return convertToEnergyImage(energyArray);
    }

    // todo: set this up for testing
    public BufferedImage getEnergyImageFromArray(String fileName) throws Exception
    {
        BufferedImage originalImage = getInputStream(fileName);
        Color[][] readImage = readImage(originalImage);
        double[][] brightness = calculateEnergy(readImage);
        Color[][] energyArray = convertToEnergyArray(brightness);
        return convertToEnergyImage(energyArray);
    }

    private BufferedImage getInputStream(String fileName) throws IOException
    {
        return ImageIO.read(EnergyDetermination.class.getResourceAsStream(fileName));
    }

    private Color[][] readImage(BufferedImage originalImage)
    {
        maxWidth = originalImage.getWidth();
        maxHeight = originalImage.getHeight();
        Color[][] colors = new Color[maxWidth][maxHeight];
        for (int i = 0; i < colors.length; i++)
        {
            for (int j = 0; j < colors[0].length; j++)
            {
                colors[i][j] = new Color(originalImage.getRGB(i, j));
            }
        }
        return colors;
    }

    private double[][] calculateEnergy(Color[][] image)
    {
        maxEnergy = Double.MIN_VALUE;
        minEnergy = Double.MAX_VALUE;

        double[][] brightness = new double[maxWidth][maxHeight];
        for (int i = 0; i < maxWidth; i++)
        {
            for (int j = 0; j < maxHeight; j++)
            {
                double energy;
                if (i == 0
                    || j == 0
                    || i == maxWidth - 1
                    || j == maxHeight - 1)
                {
                    energy = MAX_ENERGY;
                }
                else
                {
                    Color left = image[i-1][j];
                    Color right = image[i+1][j];
                    Color upper = image[i][j-1];
                    Color lower = image[i][j+1];
                    energy = Math.pow(upper.getRed() - lower.getRed(), 2)
                            + Math.pow(upper.getGreen() - lower.getGreen(), 2)
                            + Math.pow(upper.getBlue() - lower.getBlue(), 2)
                            + Math.pow(left.getRed() - right.getRed(), 2)
                            + Math.pow(left.getGreen() - right.getGreen(), 2)
                            + Math.pow(left.getBlue() - right.getBlue(), 2);
                }
                brightness[i][j] = energy;
                maxEnergy = Math.max(maxEnergy, energy);
                minEnergy = Math.min(minEnergy, energy);
            }
        }
        return brightness;
    }

    private Color[][] convertToEnergyArray(double[][] brightness)
    {
        Color[][] energy = new Color[maxWidth][maxHeight];

        for (int i = 0; i < maxWidth; i++)
        {
            for (int j = 0; j < maxHeight; j++)
            {
                int color;
                if (i == 0
                        || j == 0
                        || i == maxWidth - 1
                        || j == maxHeight - 1)
                {
                    color = 255;
                }
                else
                {
                    color = (int) (((brightness[i][j] - minEnergy) / (maxEnergy - minEnergy)) * 255);
                }
                energy[i][j] = new Color(color, color, color);
            }
        }

        return energy;
    }

    private BufferedImage convertToEnergyImage(Color[][] energyArray)
    {
        BufferedImage energyImage = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < maxWidth; i++)
        {
            for (int j = 0; j < maxHeight; j++)
            {
                energyImage.setRGB(i, j, energyArray[i][j].getRGB());
            }
        }
        return energyImage;
    }

    // Test with small 2D pixel array - ex. 3x3
    // Make it an int[y][x] or a Color[y][x]
    // Make sure it's a proper 3x3 array of energy values when finished
}
