import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeamFinderTest
{
    SeamFinder seamFinder = new SeamFinder();
    double[][] energy = new double[][]{
            {1, 4, 3, 5, 2},
            {3, 2, 5, 2, 3},
            {5, 2, 4, 2, 1}
    };

    @Test
    public void findVerticalSeam()
    {
        // Given
        int[] anticipatedSeam = new int[]{0, 1, 1};

        // When
        int[] newSeam = seamFinder.findVerticalSeam(energy);

        // Then
        assertArrayEquals(anticipatedSeam, newSeam);

    }

    @Test
    public void findHorizontalSeam()
    {
        // Given
        int[] anticipatedSeam = new int[]{0, 1, 0, 1, 2};

        // When
        int[] newSeam = seamFinder.findHorizontalSeam(energy);

        // Then
        assertArrayEquals(anticipatedSeam, newSeam);
    }

}