import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeamRemoverTest
{
    SeamRemover remover = new SeamRemover();
    double[][] energy = new double[][] {
            {1, 4, 3, 5, 2},
            {3, 2, 5, 2, 3},
            {5, 2, 4, 2, 1}
    };

    @Test
    public void removeHorizontalSeam()
    {
        // Given
        double[][] anticipatedEnergy = new double[][] {
                {3, 4, 5, 5, 2},
                {5, 2, 4, 2, 3}
        };
        int[] seam = new int[] {0, 1, 0, 1, 2};

        // When
        double[][] newEnergy = remover.removeHorizontalSeam(energy, seam);

        // Then
        for (int row = 0; row < newEnergy.length; row++)
        {
            assertArrayEquals(anticipatedEnergy[row], newEnergy[row]);
        }
    }

    @Test
    public void removeVerticalSeam()
    {
        // Given
        double[][] anticipatedEnergy = new double[][] {
                {4, 3, 5, 2},
                {3, 5, 2, 3},
                {5, 4, 2, 1}
        };
        int[] seam = new int[] {0, 1, 1};

        // When
        double[][] newEnergy = remover.removeVerticalSeam(energy, seam);

        // Then
        for (int row = 0; row < newEnergy.length; row++)
        {
            assertArrayEquals(anticipatedEnergy[row], newEnergy[row]);
        }
    }

}