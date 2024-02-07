import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Card extends JPanel {
    private final boolean special;
    private final boolean joker;
    private final int change;
    private String side;
    private boolean matched; // Flag to indicate if the card is matched
    private BufferedImage hiddenImage;
    private ArrayList<BufferedImage> images;

    Card(boolean special, int change, boolean joker) {
        this.change = change;
        this.special = special;
        this.joker = joker;
        this.side = "Hidden";
        this.matched = false; // Initialize matched flag to false
        images = new ArrayList<>();

        // Load the hidden image
        try {
            hiddenImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("cardTurn.jpg")));

            images.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("Joker.jpg"))));

            images.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("OwnBonusGreen.png"))));
            images.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("bigBonusGreen.png"))));

            images.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("OwnBonusBlue.png"))));
            images.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("bigBonusBlue.png"))));

            images.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("OwnBonusYellow.png"))));
            images.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("bigBonusYellow.png"))));

            images.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("OwnBonusRed.png"))));
            images.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("bigBonusRed.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set preferred size of the panel to match the size of the image
        if (hiddenImage != null) {
            setPreferredSize(new Dimension(hiddenImage.getWidth(), hiddenImage.getHeight()));
        }
        for (BufferedImage image : images) {
            if (image != null) {
                setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            }
        }

        // Add mouse listener to handle clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                flipCard();
            }
        });
    }

    public int getChange() {
        return change;
    }

    public String getSide() {
        return side;
    }

    public boolean isSpecial() {
        return special;
    }

    public boolean isJoker() {
        return joker;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    private int getLink() {
        return switch (this.change) {
            //JOKER
            case 0 -> 0;
            //BONUS for player
            case 50 -> 1;
            case 2000 -> 2;
            //MALUS for player
            case -100 -> 5;
            case -3000 -> 6;
            //BONUS for the opponent
            case 101 -> 3;
            case 3000 -> 4;
            //MALUS for the opponent
            case -51 -> 7;
            default -> 8;
        };
    }

    private void flipCard() {
        // Flip only if the card is not matched
        if (!matched) {
            if (side.equals("Hidden")) {
                side = "Visible";
            } else {
                side = "Hidden";
            }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the card based on its side
        if (side.equals("Hidden")) {
            // If side is hidden, draw the hidden image
            if (hiddenImage != null) {
                g.drawImage(hiddenImage, 0, 0, getWidth(), getHeight(), this);
            }
        } else {
            // If side is visible, draw the corresponding image
            BufferedImage image = images.get(getLink()); // Adjust based on the range of change values
            if (image != null) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
