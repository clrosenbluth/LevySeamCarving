import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class EnergyDeterminationTest
{
    @Test
    public void calculateEnergy()
    {
        // Given
        EnergyDetermination determination = new EnergyDetermination();

        Color[][] image4x4 = new Color[][] {
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

        double[][] anticipatedEnergy = new double[][] {
                {255, 255, 255, 255},
                {255, 44.84256213088113, 40.28681279479964, 255},
                {255, 28.35601886717666, 0, 255},
                {255, 255, 255, 255}
        };

        // When
        double[][] actualEnergy = determination.calculateEnergy(image4x4);

        // Then
        for (int row = 0; row < actualEnergy.length; row++)
        {
            for (int col = 0; col < actualEnergy[0].length; col++)
            {
                if (anticipatedEnergy[row][col] != actualEnergy[row][col])
                {
                    fail();
                }
            }
        }

    }
}