import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameHandler extends JFrame {
    private Card firstFlippedCard;
    private Card secondFlippedCard;
    private ArrayList<Card> allCards = new ArrayList<>();
    private Player[] players = new Player[2]; // Assuming there are two players
    private int currentPlayerIndex = 0; // Index of the current player
    private int greenYellowMatchedCount = 0;
    private int orangeRedMatchedCount = 0;

    private boolean hasMatched = false;

    private ArrayList<Integer> listRand() {
        ArrayList<Integer> all = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            all.add(50);
        }
        for (int i = 0; i < 2; i++) {
            all.add(2000);
        }
        for (int i = 0; i < 4; i++) {
            all.add(-100);
        }
        for (int i = 0; i < 2; i++) {
            all.add(-3000);
        }
        for (int i = 0; i < 4; i++) {
            all.add(101);
        }
        for (int i = 0; i < 2; i++) {
            all.add(3000);
        }
        for (int i = 0; i < 4; i++) {
            all.add(-51);
        }
        for (int i = 0; i < 2; i++) {
            all.add(-2000);
        }
        all.add(0);

        Collections.shuffle(all);
        return all;
    }

    GameHandler() {
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Battle Memory");
        this.setLayout(null);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        // Create players
        players[0] = new Player(20, 1); // You can adjust maxHP and atkStart as needed
        players[1] = new Player(20, 1); // You can adjust maxHP and atkStart as needed

        ArrayList<Integer> allValues = listRand();
        int j = 0;
        for (int i = 0; i < allValues.size(); i++) {
            Card card = new Card(false, allValues.get(i), false);
            card.setSize(100, 150);
            card.setLocation(50 + (100 * (i % 5)), (150 * j));
            allCards.add(card);
            this.add(card);
            card.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    flipCard(card);
                }
            });
            if (i % 5 == 4) {
                j++;
            }
        }
        // Hide all cards at the start
        hideAllCards();
    }

    private void flipCard(Card card) {
        if (firstFlippedCard == null && !card.isMatched()) {
            firstFlippedCard = card;
            firstFlippedCard.setSide("Visible");
            firstFlippedCard.repaint();
        } else if (secondFlippedCard == null && !card.equals(firstFlippedCard) && !card.isMatched()) {
            secondFlippedCard = card;
            secondFlippedCard.setSide("Visible");
            secondFlippedCard.repaint();
            compareCards();
        }
    }

    private void compareCards() {
        if (firstFlippedCard.getChange() == secondFlippedCard.getChange()) {
            // Cards match, perform appropriate action
            System.out.println("Match!");
            firstFlippedCard.setMatched(true);
            secondFlippedCard.setMatched(true);
            hasMatched = true;
            updatePlayerAtk(firstFlippedCard.getChange());
            updateMatchedCount(firstFlippedCard);
            switchTurn();
            // Continue current player's turn
        } else {
            // Cards don't match, perform appropriate action
            System.out.println("Not a match!");
            hasMatched = false;
            // Delay flipping back the cards
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            if (firstFlippedCard != null && !firstFlippedCard.isMatched()) {
                                firstFlippedCard.setSide("Hidden");
                                firstFlippedCard.repaint();
                            }
                            if (secondFlippedCard != null && !secondFlippedCard.isMatched()) {
                                secondFlippedCard.setSide("Hidden");
                                secondFlippedCard.repaint();
                            }
                            firstFlippedCard = null;
                            secondFlippedCard = null;
                            // Switch turn after flipping back cards
                            switchTurn();
                        }
                    },
                    1000 // Adjust the delay time as needed
            );
        }
    }

    private void updatePlayerAtk(int change) {
        int otherPlayerIndex = (currentPlayerIndex + 1) % players.length;
        boolean other = false;
        // Update current player's attack based on the color of the card
        switch (change) {
            case 50:
                players[currentPlayerIndex].setAtk(players[currentPlayerIndex].getAtk() + 5);  // Increase atk for green/yellow cards
                break;
            case 2000:
                players[currentPlayerIndex].setAtk(players[currentPlayerIndex].getAtk() * 2);  // Increase atk for green/yellow cards
                break;
            case -100:
                players[currentPlayerIndex].setAtk(players[currentPlayerIndex].getAtk() - 10);  // Increase atk for green/yellow cards
                break;
            case -3000:
                players[currentPlayerIndex].setAtk(players[currentPlayerIndex].getAtk() / 3);  // Decrease atk for orange/red cards
                break;
            case 101:
                players[otherPlayerIndex].setAtk(players[otherPlayerIndex].getAtk() + 10);
                other = true;
                break;
            case 3000:
                players[otherPlayerIndex].setAtk(players[otherPlayerIndex].getAtk() * 3);
                other = true;
                break;
            case -51:
                players[otherPlayerIndex].setAtk(players[otherPlayerIndex].getAtk() - 5);
                other = true;
                break;
            default:
                players[otherPlayerIndex].setAtk(players[otherPlayerIndex].getAtk() / 2);
                other = true;
                break;
        }
        if (other) {
            System.out.println("Player " + (otherPlayerIndex + 1) + "'s Atk: " + players[otherPlayerIndex].getAtk());

        } else {
            System.out.println("Player " + (currentPlayerIndex + 1) + "'s Atk: " + players[currentPlayerIndex].getAtk());
        }
    }

    private void updateMatchedCount(Card card) {
        // Update matched card counts based on the color of the card
        switch (card.getChange()) {
            case 50, 2000:
                greenYellowMatchedCount++;
                break;
            case -51, -2000:
                orangeRedMatchedCount++;
                break;
            // Add more cases for other colors as needed
        }
    }

    private void hideAllCards() {
        for (Card card : allCards) {
            card.setSide("Hidden");
            card.repaint();
        }
    }
    private void detectMatchOver() {
        if (greenYellowMatchedCount >= 3 && orangeRedMatchedCount >= 3) {
            if (players[0].getAtk() < 0) {
                players[0].setAtk(0);
            } else if (players[1].getAtk() < 0) {
                players[1].setAtk(0);
            }
            System.out.println("Player 1's final atk " + players[0].getAtk());
            System.out.println("Player 2's final atk " + players[1].getAtk());
            System.exit(0);
        }
    }
    private void switchTurn() {
        detectMatchOver();
        if (!hasMatched) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
            System.out.println("Player " + (currentPlayerIndex + 1) + "'s turn");
            animateRandomLine();
        } else {
            System.out.println("Player " + (currentPlayerIndex + 1) + "'s turn");
        }
        firstFlippedCard = null;
        secondFlippedCard = null;
        hasMatched = false;
    }

    private void animateRandomLine() {
        Random random = new Random();
        int row = random.nextInt(4); // Randomly select a row (0 to 3)
        animateRow(row);
    }

    private void animateRow(int row) {
        int cardHeight = allCards.get(0).getHeight();
        int startY = row * cardHeight; // Starting Y position of the row
        for (int i = 0; i < 5 / 2; i++) {
            int index1 = row * 5 + i;
            int index2 = row * 5 + (4 - i);
            int startX1 = allCards.get(index1).getX();
            int startX2 = allCards.get(index2).getX();
            for (int x = 0; x <= cardHeight; x += 10) {
                allCards.get(index1).setLocation(startX1 + x, startY);
                allCards.get(index2).setLocation(startX2 - x, startY);
                try {
                    Thread.sleep(20); // Adjust speed of animation as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            allCards.get(index1).setLocation(startX2, startY);
            allCards.get(index2).setLocation(startX1, startY);
            Collections.swap(allCards, index1, index2); // Swap the cards in the list
            this.repaint();
        }
    }
}
