import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
    // maximum possible energy: (255^2) * 6 = 390,150
    // this would occur if the left and top colors had only 255s
    // and the right and bottom colors had only 0s for their
    // R G and B values
    private final int maxPossibleEnergy = 390150;

    public EnergyDetermination() {}

    public BufferedImage getBrightnessImageFromFile(String fileName) throws Exception
    {
        BufferedImage originalImage = getBufferedImageFromFile(fileName);
        Color[][] readImage = getColorArrayFromImage(originalImage);
        return getBrightnessImageFromColorArray(readImage);
    }

    public double[][] getEnergyFromFile(String fileName) throws Exception
    {
        BufferedImage originalImage = getBufferedImageFromFile(fileName);
        Color[][] readImage = getColorArrayFromImage(originalImage);
        return getEnergyFromColorArray(readImage);
    }

    public double[][] getEnergyArrayFromColorArray(Color[][] image)
    {
        return getEnergyFromColorArray(image);
    }

    public void getBrightnessImageFromFileToFile(String fileName) throws Exception
    {
        BufferedImage energyImage = getBrightnessImageFromFile(fileName);
        File outputfile = new File("saved.png");
        ImageIO.write(energyImage, "png", outputfile);
    }

    public BufferedImage getBrightnessImageFromColorArray(Color[][] image)
    {
        double[][] brightness = getEnergyFromColorArray(image);
        Color[][] energyArray = getEnergyFromBrightness(brightness);
        return getEnergyImageFromEnergyArray(energyArray);
    }

    private BufferedImage getBufferedImageFromFile(String fileName) throws IOException
    {
        return ImageIO.read(EnergyDetermination.class.getResourceAsStream(fileName));
    }

    private Color[][] getColorArrayFromImage(BufferedImage originalImage)
    {
        int maxWidth = originalImage.getWidth();
        int maxHeight = originalImage.getHeight();
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

    private double[][] getEnergyFromColorArray(Color[][] image)
    {
        maxEnergy = Double.MIN_VALUE;
        minEnergy = Double.MAX_VALUE;
        int maxWidth = image.length;
        int maxHeight = image[0].length;

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
                    energy = maxPossibleEnergy;
                } else
                {
                    Color left = image[i - 1][j];
                    Color right = image[i + 1][j];
                    Color upper = image[i][j - 1];
                    Color lower = image[i][j + 1];

                    int reds1 = upper.getRed() - lower.getRed();
                    int reds2 = left.getRed() - right.getRed();
                    int greens1 = upper.getGreen() - lower.getGreen();
                    int greens2 = left.getGreen() - right.getGreen();
                    int blues1 = upper.getBlue() - lower.getBlue();
                    int blues2 = left.getBlue() - right.getBlue();

                    energy = (reds1 * reds1)
                            + (greens1 * greens1)
                            + (blues1 * blues1)
                            + (reds2 * reds2)
                            + (greens2 * greens2)
                            + (blues2 * blues2);
                }
                brightness[i][j] = energy;
                maxEnergy = Math.max(maxEnergy, energy);
                minEnergy = Math.min(minEnergy, energy);
            }
        }
        return brightness;
    }

    private Color[][] getEnergyFromBrightness(double[][] brightness)
    {
        int maxWidth = brightness.length;
        int maxHeight = brightness[0].length;
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
                } else
                {
                    color = (int) (((brightness[i][j] - minEnergy)
                            / (maxEnergy - minEnergy))
                            * 255);
                }
                energy[i][j] = new Color(color, color, color);
            }
        }

        return energy;
    }

    private BufferedImage getEnergyImageFromEnergyArray(Color[][] energyArray)
    {
        int maxWidth = energyArray.length;
        int maxHeight = energyArray[0].length;
        BufferedImage energyImage = new BufferedImage(
                maxWidth,
                maxHeight,
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < maxWidth; i++)
        {
            for (int j = 0; j < maxHeight; j++)
            {
                energyImage.setRGB(i, j, energyArray[i][j].getRGB());
            }
        }
        return energyImage;
    }
}
