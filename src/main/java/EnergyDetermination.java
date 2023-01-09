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
        int width = image[0].length;
        int height = image.length;

        int[][] brightnessArray = new int[width][height];
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int energy;
                if (y == 0
                    || x == 0
                    || y == height - 1
                    || x == width - 1)
                {
                    energy = maxPossibleEnergy;
                } else
                {
                    Color left = image[y - 1][x];
                    Color right = image[y + 1][x];
                    Color upper = image[y][x - 1];
                    Color lower = image[y][x + 1];

                    energy = calculateCellEnergy(left, right, upper, lower);
                }
                brightnessArray[x][y] = energy;
                maxEnergy = Math.max(maxEnergy, energy);
                minEnergy = Math.min(minEnergy, energy);
            }
        }
        return brightnessArray;
    }

    private double[][] getEnergy(int[][] brightness)
    {
        int width = brightness[0].length;
        int height = brightness.length;
        double[][] energy = new double[width][height];

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                double color;
                if (y == 0
                        || x == 0
                        || y == height - 1
                        || x == width - 1)
                {
                    color = 255;
                } else
                {
                    color = (((brightness[y][x] - minEnergy)
                            / (maxEnergy - minEnergy))
                            * 255);
                }
                energy[x][y] = color;
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
