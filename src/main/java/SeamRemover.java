public class SeamRemover
{

    public SeamRemover()
    {
    }

    public double[][] removeHorizontalSeam(double[][] energy)
    {
        double[][] newEnergy = new double[energy.length][energy[0].length - 1];
        return newEnergy;
    }

    public double[][] removeVerticalSeam(double[][] energy)
    {
        double[][] newEnergy = new double[energy.length - 1][energy[0].length];
        return newEnergy;
    }

}
