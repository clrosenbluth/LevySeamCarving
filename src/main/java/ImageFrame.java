import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFrame extends JFrame {

    private final JLabel imageLabel;
    private ImageIcon original;
    private Image imageMaker = new Image();
    private EnergyDetermination energyDeterminer = new EnergyDetermination();
    private SeamFinder seamFinder = new SeamFinder();
    private SeamRemover seamRemover = new SeamRemover();

    public ImageFrame() {

        setLayout(new BorderLayout());

        // This is where the image will be stored.
        imageLabel = new JLabel();

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());
        JButton resizeButton = new JButton("Resize");
        resizeButton.addActionListener(event -> {
            // This will set the image based on the current size of the label
            setSeamImageSize(imageLabel.getWidth(), imageLabel.getHeight());
        });
        northPanel.add(resizeButton);

        JButton loadButton = new JButton("Load");
        northPanel.add(loadButton);
        loadButton.addActionListener(event -> {
            chooseFile();
        });

        add(northPanel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);

        BufferedImage image;
        try {
            image = ImageIO.read(ImageFrame.class.getResourceAsStream("/broadwayTower.jpg"));
            loadSeamImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setTitle("Seam Carving");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION)
        {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            try {
                loadSeamImage(ImageIO.read(file));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void loadSeamImage(BufferedImage image) {

        // add code here to load the image into your seam carver code
        ImageIcon imageIcon = new ImageIcon(image);
        imageLabel.setIcon(imageIcon);
        original = imageIcon;

        setSize(image.getWidth(null), image.getHeight(null));
        pack();
    }

    private void setSeamImageSize(int width, int height) {
        // generate a newImage with the new width and height
        Color[][] image = imageMaker.getImageFromBufferedImage((BufferedImage) original.getImage());
        double[][] energy;

        while (image[0].length > width)
        {
            System.out.println(image.length);
            System.out.println(image[0].length);
            energy = energyDeterminer.calculateEnergy(image);
            int[] seam = seamFinder.findVerticalSeam(energy);
            image = seamRemover.removeVerticalSeam(image, seam);
        }

        while (image.length > height)
        {
            energy = energyDeterminer.calculateEnergy(image);
            int[] seam = seamFinder.findVerticalSeam(energy);
            image = seamRemover.removeVerticalSeam(image, seam);
        }

        BufferedImage newImage = imageMaker.getBufferedImageFromArray(image);

        imageLabel.setIcon(new ImageIcon(newImage));
    }

    public static void main(String[] args) {
        new ImageFrame().setVisible(true);
    }

}
