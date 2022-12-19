/**
 * A class to determine the lowest-energy seam(s) in order to delete it/them
 */
public class SeamFinder
{
    public SeamFinder()
    {

    }

    public int[] findVerticalSeam(double[][] energy)
    {
        double[][] map = createVerticalEnergyMap(energy);
        int[] minSeam = new int[energy.length];

        double[] lastRow = new double[energy[0].length];
        System.arraycopy(map[energy.length - 1], 0, lastRow, 0, energy[0].length);

        minSeam[energy.length - 1] = lastCoordinate(lastRow);
        for (int row = energy.length - 2; row >= 0; row--)
        {
            int bottomRow = minSeam[row + 1];
            int left = bottomRow == energy[0].length ? energy[0].length : bottomRow - 1;
            int right = bottomRow == 0 ? 0 : bottomRow + 1;
            minSeam[row] = bottomRow - calculateOffset(
                    map[row][left],
                    map[row][bottomRow],
                    map[row][right]
            );
        }

        return minSeam;
    }

    public int[] findHorizontalSeam(double[][] energy)
    {
        double[][] map = createHorizontalEnergyMap(energy);
        int[] minSeam = new int[energy[0].length];

        double[] lastCol = new double[energy.length];
        for (int row = 0; row < energy.length; row++)
        {
            lastCol[row] = map[row][energy[0].length - 1];
        }

        minSeam[energy[0].length - 1] = lastCoordinate(lastCol);
        for (int col = energy[0].length - 2; col >= 0; col--)
        {
            int rightCol = minSeam[col + 1];
            int top = rightCol == 0 ? 0 : rightCol - 1;
            int bottom = rightCol == energy.length - 1 ? energy.length - 1 : rightCol + 1;
            minSeam[col] = rightCol + calculateOffset(
                    map[bottom][col],
                    map[rightCol][col],
                    map[top][col]
            );
        }

        return minSeam;
    }

    private int lastCoordinate(double[] finalEnergies)
    {
        double minEnergy = Double.MAX_VALUE;
        int minCoordinate = -1;
        for (int cell = 0; cell < finalEnergies.length; cell++)
        {
            if (finalEnergies[cell] < minEnergy)
            {
                minEnergy = finalEnergies[cell];
                minCoordinate = cell;
            }
        }
        return minCoordinate;
    }

    private int calculateOffset(double leftOrBottom, double same, double rightOrTop)
    {
        int offset = 0;
        if (leftOrBottom < same && leftOrBottom < rightOrTop)
        {
            offset = 1;
        } else if (rightOrTop < same && rightOrTop < leftOrBottom)
        {
            offset = -1;
        }
        return offset;
    }

    private double[][] createVerticalEnergyMap(double[][] energy)
    {
        double[][] verticalEnergyMap = new double[energy.length][energy[0].length];

        System.arraycopy(energy[0], 0, verticalEnergyMap[0], 0, verticalEnergyMap[0].length);

        for (int row = 1; row < verticalEnergyMap.length; row++)
        {
            int lastCell = verticalEnergyMap[0].length - 1;

            verticalEnergyMap[row][0] = Math.min(verticalEnergyMap[row - 1][0] + energy[row][0],
                    verticalEnergyMap[row - 1][1] + energy[row][0]);
            verticalEnergyMap[row][lastCell] = Math.min(
                    verticalEnergyMap[row - 1][lastCell] + energy[row][lastCell],
                    verticalEnergyMap[row - 1][lastCell - 1] + energy[row][lastCell]
            );

            for (int col = 1; col < lastCell; col++)
            {
                double currEnergy = energy[row][col];
                double left = verticalEnergyMap[row - 1][col - 1];
                double middle = verticalEnergyMap[row - 1][col];
                double right = verticalEnergyMap[row - 1][col + 1];
                verticalEnergyMap[row][col] = Math.min(Math.min(left + currEnergy,
                                middle + currEnergy),
                        right + currEnergy);
            }
        }

        return verticalEnergyMap;
    }

    private double[][] createHorizontalEnergyMap(double[][] energy)
    {
        double[][] horizontalEnergyMap = new double[energy.length][energy[0].length];

        for (int row = 0; row < horizontalEnergyMap.length; row++)
        {
            horizontalEnergyMap[row][0] = energy[row][0];
        }

        for (int col = 1; col < horizontalEnergyMap[0].length; col++)
        {
            int lastCell = horizontalEnergyMap.length - 1;

            horizontalEnergyMap[0][col] = Math.min(horizontalEnergyMap[0][col - 1] + energy[0][col],
                    horizontalEnergyMap[1][col - 1] + energy[0][col]);
            horizontalEnergyMap[lastCell][col] = Math.min(
                    horizontalEnergyMap[lastCell][col - 1] + energy[lastCell][col],
                    horizontalEnergyMap[lastCell - 1][col - 1] + energy[lastCell][col]
            );

            for (int row = 1; row < lastCell; row++)
            {
                double currEnergy = energy[row][col];
                double top = horizontalEnergyMap[row - 1][col - 1];
                double middle = horizontalEnergyMap[row][col - 1];
                double bottom = horizontalEnergyMap[row + 1][col - 1];
                horizontalEnergyMap[row][col] = Math.min(Math.min(top + currEnergy,
                                middle + currEnergy),
                        bottom + currEnergy);
            }
        }

        return horizontalEnergyMap;
    }
}
