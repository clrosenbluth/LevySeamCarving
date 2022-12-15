public class SeamRemover
{
    // These are the steps for next week:

    // Vertical seams:
    // 1. Read image -> BufferedImage -> Color[][] or int[][]
    // 2. create double[][] energy (from Color array)
    // 3. find lowest-energy vertical seam (from energy array)
    // 4. remove seam (from Color array)
        // two copies of the array (row by row): everything before the seam, then everything after the seam. Use System.arrayCopy
    // 5. go to step 2
    // Repeat until width is as desired

    // Same for horizontal

    // to consider: create a 2D array of pixel classes

}
