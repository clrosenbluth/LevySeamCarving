/**
 * A class to determine the lowest-energy seam(s) in order to delete it/them
 */
public class SeamFinder
{
    // take individual energies
    // from a given border pixel:
    // create a seam of lowest-possible energy to the opposite border

    // from each pixel, seam can go in 3 possible directions: left, down, right

    // Each lower pixel stores its minimum total energy

    // Minimum energy = its energy plus the minimum sum energy in the three places above it

    // Seam is generated by going from minimum sum at the bottom upward
    // through the lowest energies in the 3 above places

    // This is done for vertical and horizontal seams

    // Return 1 lowest seam, with a set of x's or y's (depending on which direction it is going)

    // When moving between a pair of rows/cols, start with the second and calculate upward

    // Might be a good idea to make a Seam object with an array of offset values, not just a seam array
}
