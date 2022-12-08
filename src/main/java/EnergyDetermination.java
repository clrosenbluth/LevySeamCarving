import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * A class to determine the energy of a given pixel in a picture to be resized.
 * Energy is based on level of contrast between the pixel and its neighbors.
 * Higher energy corresponds to higher contrast.
 */

public class EnergyDetermination
{
    private String fileName;


    public EnergyDetermination(String fileName) {
        this.fileName = fileName;
    }

    private BufferedImage getInputStream() throws Exception
    {
        return ImageIO.read(EnergyDetermination.class.getResourceAsStream(fileName));
    }


    // Classes:
    // ImageIO - read image
    // BufferedImage - read out from ImageIO read method
    // Color - constructed from integer that comes from BufferedImage's getRGB(x, y) method

    // Energy algorithm:
    // Given a pixel, look at pixels (1) U (2) B (3) R (4) L
    // Compare top to bottom and left to right
    // Sum of comparison is energy value for middle pixel
    // Border energy is maximum energy

    // Energy formula:
    // A = (U.getRed() - D.getRed()) ^ 2 + (green - green)^2 + (blue - blue)^2
    // B = same for L - R
    // E = A + B

    // Border: X or Y are 0 or MAX_HEIGHT - 1 or MAX_WIDTH - 1
    // E = 255 ^ 3 = 16 581 375 (OR) (255^2) * 6 = 390 150

    // Energy image: Brightness = ((E - minEnergy) / (maxEnergy - minEnergy)) * 255
    // Max not including the borders
    // Color = new Color(Brightness, Brightness, Brightness)
    // borders are 255
    // feed color array back into buffered image, display it

    // Test with small 2D pixel array - ex. 3x3
    // Make it an int[y][x] or a Color[y][x]
    // Make sure it's a proper 3x3 array of energy values when finished
}
