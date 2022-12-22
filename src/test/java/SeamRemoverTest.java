import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class SeamRemoverTest
{
    SeamRemover remover = new SeamRemover();

    Color[][] image = new Color[][]{
            {
                    new Color(197, 6, 0),
                    new Color(227, 164, 230),
                    new Color(71, 165, 128),
                    new Color(36, 106, 96)
            },
            {
                    new Color(127, 135, 191),
                    new Color(229, 40, 204),
                    new Color(238, 92, 93),
                    new Color(66, 49, 70)
            },
            {
                    new Color(220, 219, 54),
                    new Color(92, 23, 102),
                    new Color(189, 243, 45),
                    new Color(129, 81, 67)
            },
            {
                    new Color(88, 219, 232),
                    new Color(2, 51, 181),
                    new Color(177, 136, 83),
                    new Color(203, 145, 145)
            }
    };

    @Test
    public void removeHorizontalSeam()
    {
        // Given
        Color[][] anticipatedImage = new Color[][]{
                {
                        new Color(127, 135, 191),
                        new Color(227, 164, 230),
                        new Color(238, 92, 93),
                        new Color(36, 106, 96)
                },
                {
                        new Color(220, 219, 54),
                        new Color(92, 23, 102),
                        new Color(189, 243, 45),
                        new Color(66, 49, 70)
                },
                {
                        new Color(88, 219, 232),
                        new Color(2, 51, 181),
                        new Color(177, 136, 83),
                        new Color(203, 145, 145)
                }
        };
        int[] seam = new int[] {0, 1, 0, 2};

        // When
        Color[][] newImage = remover.removeHorizontalSeam(image, seam);

        // Then
        for (int row = 0; row < newImage.length; row++)
        {
            assertArrayEquals(anticipatedImage[row], newImage[row]);
        }
    }

    @Test
    public void removeVerticalSeam()
    {
        // Given
        int[] seam = new int[] {0, 1, 1, 2};
        Color[][] anticipatedImage = new Color[][] {
                {
                        new Color(227, 164, 230),
                        new Color(71, 165, 128),
                        new Color(36, 106, 96)
                },
                {
                        new Color(127, 135, 191),
                        new Color(238, 92, 93),
                        new Color(66, 49, 70)
                },
                {
                        new Color(220, 219, 54),
                        new Color(189, 243, 45),
                        new Color(129, 81, 67)
                },
                {
                        new Color(88, 219, 232),
                        new Color(2, 51, 181),
                        new Color(203, 145, 145)
                }
        };

        // When
        Color[][] newImage = remover.removeVerticalSeam(image, seam);

        // Then
        for (int row = 0; row < newImage.length; row++)
        {
            assertArrayEquals(anticipatedImage[row], newImage[row]);
        }
    }

}