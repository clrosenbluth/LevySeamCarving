import java.awt.*;

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

    public EnergyDetermination()
    {
    }

    public double[][] calculateEnergy(Color[][] image)
    {
        return getEnergy(getBrightness(image));
    }

    private int[][] getBrightness(Color[][] image)
    {
        maxEnergy = Double.MIN_VALUE;
        minEnergy = Double.MAX_VALUE;
        int width = image.length;
        int height = image[0].length;

        int[][] brightnessArray = new int[width][height];
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                int energy;
                if (i == 0
                        || j == 0
                        || i == width - 1
                        || j == height - 1)
                {
                    energy = maxPossibleEnergy;
                } else
                {
                    Color left = image[i - 1][j];
                    Color right = image[i + 1][j];
                    Color upper = image[i][j - 1];
                    Color lower = image[i][j + 1];

                    energy = calculateCellEnergy(left, right, upper, lower);
                }
                brightnessArray[i][j] = energy;
                maxEnergy = Math.max(maxEnergy, energy);
                minEnergy = Math.min(minEnergy, energy);
            }
        }
        return brightnessArray;
    }

    private double[][] getEnergy(int[][] brightness)
    {
        int width = brightness.length;
        int height = brightness[0].length;
        double[][] energy = new double[width][height];

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                double color;
                if (i == 0
                        || j == 0
                        || i == width - 1
                        || j == height - 1)
                {
                    color = 255;
                } else
                {
                    color = (((brightness[i][j] - minEnergy)
                            / (maxEnergy - minEnergy))
                            * 255);
                }
                energy[i][j] = color;
            }
        }

        return energy;
    }

    private int calculateCellEnergy(Color left,
                                    Color right,
                                    Color upper,
                                    Color lower)
    {
        int redsVertical = upper.getRed() - lower.getRed();
        int redsHorizontal = left.getRed() - right.getRed();
        int greensVertical = upper.getGreen() - lower.getGreen();
        int greensHorizontal = left.getGreen() - right.getGreen();
        int bluesVertical = upper.getBlue() - lower.getBlue();
        int bluesHorizontal = left.getBlue() - right.getBlue();

        return (redsVertical * redsVertical)
                + (greensVertical * greensVertical)
                + (bluesVertical * bluesVertical)
                + (redsHorizontal * redsHorizontal)
                + (greensHorizontal * greensHorizontal)
                + (bluesHorizontal * bluesHorizontal);
    }
}
