import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class EnergyDeterminationTest
{

    @Test
    public void getEnergyFrom3x3Array()
    {
        // given
        EnergyDetermination determination = new EnergyDetermination();
        Color[][] image = new Color[][] {
                {new Color(943), new Color(863), new Color(364)},
                {new Color(759), new Color(724), new Color(45)},
                {new Color(586), new Color(183), new Color(567)}
        };

        Color[][] anticipatedColors = new Color[][] {
                {new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255)},
                {new Color(255, 255, 255), new Color(0, 0, 0), new Color(255, 255, 255)},
                {new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255)}
        };
        BufferedImage anticipatedImage = new BufferedImage(anticipatedColors.length,
                anticipatedColors[0].length,
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < anticipatedColors.length; i++)
        {
            for (int j = 0; j < anticipatedColors[0].length; j++)
            {
                anticipatedImage.setRGB(i, j, anticipatedColors[i][j].getRGB());
            }
        }

        // when
        BufferedImage energyImage = determination.getEnergyImageFromArray(image);

        // then
        boolean sameImage = true;
        for (int row = 0; row < energyImage.getHeight(); row++)
        {
            for (int pixel = 0; pixel < energyImage.getWidth(); pixel++)
            {
                sameImage = energyImage.getRGB(row, pixel) == anticipatedImage.getRGB(row, pixel);
                if (!sameImage)
                {
                    break;
                }
            }
        }

        assertTrue(sameImage);

    }

    @Test
    public void getEnergyFrom4x4Array()
    {
        // given
        EnergyDetermination determination = new EnergyDetermination();
        Color[][] image = new Color[][] {
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

        Color[][] anticipatedColors = new Color[][] {
                {
                    new Color(255, 255, 255),
                    new Color(255, 255, 255),
                    new Color(255, 255, 255),
                    new Color(255, 255, 255)
                },
                {
                    new Color(255, 255, 255),
                    new Color(44, 44, 44),
                    new Color(40, 40, 40),
                    new Color(255, 255, 255)
                },
                {
                    new Color(255, 255, 255),
                    new Color(28, 28, 28),
                    new Color(0, 0, 0),
                    new Color(255, 255, 255)
                },
                {
                    new Color(255, 255, 255),
                    new Color(255, 255, 255),
                    new Color(255, 255, 255),
                    new Color(255, 255, 255)
                }
        };
        BufferedImage anticipatedImage = new BufferedImage(anticipatedColors.length,
                anticipatedColors[0].length,
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < anticipatedColors.length; i++)
        {
            for (int j = 0; j < anticipatedColors[0].length; j++)
            {
                anticipatedImage.setRGB(i, j, anticipatedColors[i][j].getRGB());
            }
        }

        // when
        BufferedImage energyImage = determination.getEnergyImageFromArray(image);

        // then
        boolean sameImage = true;
        for (int row = 0; row < energyImage.getHeight(); row++)
        {
            for (int pixel = 0; pixel < energyImage.getWidth(); pixel++)
            {
                sameImage = energyImage.getRGB(row, pixel) == anticipatedImage.getRGB(row, pixel);
                if (!sameImage)
                {
                    break;
                }
            }
        }

        assertTrue(sameImage);

    }

}