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
    private final int maxPossibleEnergy = 390150;

    public EnergyDetermination() {}

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
        int reds1 = upper.getRed() - lower.getRed();
        int reds2 = left.getRed() - right.getRed();
        int greens1 = upper.getGreen() - lower.getGreen();
        int greens2 = left.getGreen() - right.getGreen();
        int blues1 = upper.getBlue() - lower.getBlue();
        int blues2 = left.getBlue() - right.getBlue();

        return (reds1 * reds1) + (greens1 * greens1) + (blues1 * blues1)
                + (reds2 * reds2) + (greens2 * greens2) + (blues2 * blues2);
    }
}
