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
        int numRows = energy.length;
        int numCols = energy[0].length;

        double[][] map = createVerticalEnergyMap(energy);
        int[] minSeam = new int[numRows];

        double[] lastRow = new double[numCols];
        System.arraycopy(map[numRows - 1], 0, lastRow, 0, numCols);

        minSeam[numRows - 1] = lastCoordinate(lastRow);
        for (int row = numRows - 2; row >= 0; row--)
        {
            int bottomRow = minSeam[row + 1];
            int left = bottomRow == numCols ? numCols : bottomRow - 1;
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
        int numRows = energy.length;
        int numCols = energy[0].length;

        double[][] map = createHorizontalEnergyMap(energy);
        int[] minSeam = new int[numCols];

        double[] lastCol = new double[numRows];
        for (int row = 0; row < numRows; row++)
        {
            lastCol[row] = map[row][numCols - 1];
        }

        minSeam[numCols - 1] = lastCoordinate(lastCol);
        for (int col = numCols - 2; col >= 0; col--)
        {
            int rightCol = minSeam[col + 1];
            int top = rightCol == 0 ? 0 : rightCol - 1;
            int bottom = rightCol == numRows - 1 ? numRows - 1 : rightCol + 1;
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
        double[][] map = new double[energy.length][energy[0].length];

        System.arraycopy(energy[0], 0, map[0], 0, map[0].length);

        for (int row = 1; row < map.length; row++)
        {
            int lastCell = map[0].length - 1;
            double firstEnergy = energy[row][0];
            double topLeft = map[row - 1][0] + firstEnergy;
            double topLeftPlusOne = map[row - 1][1] + firstEnergy;

            double lastEnergy = energy[row][lastCell];
            double topRight = map[row - 1][lastCell] + lastEnergy;
            double topRightMinusOne = map[row - 1][lastCell - 1] + lastEnergy;

            map[row][0] = Math.min(topLeft, topLeftPlusOne);
            map[row][lastCell] = Math.min(topRight, topRightMinusOne);

            for (int col = 1; col < lastCell; col++)
            {
                double currEnergy = energy[row][col];
                double left = map[row - 1][col - 1] + currEnergy;
                double middle = map[row - 1][col] + currEnergy;
                double right = map[row - 1][col + 1] + currEnergy;
                map[row][col] = Math.min(Math.min(left, middle), right);
            }
        }

        return map;
    }

    private double[][] createHorizontalEnergyMap(double[][] energy)
    {
        double[][] map = new double[energy.length][energy[0].length];

        for (int row = 0; row < map.length; row++)
        {
            map[row][0] = energy[row][0];
        }

        for (int col = 1; col < map[0].length; col++)
        {
            int lastCell = map.length - 1;
            double lastEnergy = energy[lastCell][col];
            double leftBottom = map[lastCell][col - 1] + lastEnergy;
            double leftBottomMinusOne = map[lastCell - 1][col - 1] + lastEnergy;

            double firstEnergy = energy[0][col];
            double leftTop = map[0][col - 1] + firstEnergy;
            double leftTopPlusOne = map[1][col - 1] + firstEnergy;

            map[0][col] = Math.min(leftTop, leftTopPlusOne);
            map[lastCell][col] = Math.min(leftBottom, leftBottomMinusOne);

            for (int row = 1; row < lastCell; row++)
            {
                double currEnergy = energy[row][col];
                double top = map[row - 1][col - 1] + currEnergy;
                double middle = map[row][col - 1] + currEnergy;
                double bottom = map[row + 1][col - 1] + currEnergy;
                map[row][col] = Math.min(Math.min(top, middle), bottom);
            }
        }

        return map;
    }
}
