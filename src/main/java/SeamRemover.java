public class SeamRemover
{
    public SeamRemover() {}

    public double[][] removeHorizontalSeam(double[][] energy, int[] seam)
    {
        double[][] newEnergy = new double[energy.length - 1][energy[0].length];
        for (int col = 0; col < seam.length; col++)
        {
            for (int row = 0; row < seam[col]; row++)
            {
                newEnergy[row][col] = energy[row][col];
            }
            for (int row = seam[col] + 1; row <= newEnergy.length; row++)
            {
                newEnergy[row - 1][col] = energy[row][col];
            }
        }
        return newEnergy;
    }

    public double[][] removeVerticalSeam(double[][] energy, int[] seam)
    {
        double[][] newEnergy = new double[energy.length][energy[0].length - 1];
        for (int row = 0; row < seam.length; row++)
        {
            if (seam[row] >= 0)
            {
                System.arraycopy(energy[row], 0, newEnergy[row], 0, seam[row]);
            }
            if (energy[0].length - (seam[row] + 1) >= 0)
            {
                System.arraycopy(
                        energy[row],
                        seam[row] + 1,
                        newEnergy[row],
                        seam[row] + 1 - 1,
                        energy[0].length - (seam[row] + 1)
                );
            }
        }
        return newEnergy;
    }
}
