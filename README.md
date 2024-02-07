# BattleMemory

private Color getColor() {
    return switch (this.change) {
        //JOKER
        case 0 -> Color.gray;
        //BONUS for player
        case 50 -> Color.green;
        case 20 -> Color.green;
        case 2000 -> Color.yellow;
        //MALUS for player
        case -100 -> Color.magenta;
        case -50 -> Color.magenta;
        case -3000 -> Color.pink;
        //BONUS for the opponent
        case 101 -> Color.cyan;
        case 51 -> Color.cyan;
        case 3000 -> Color.blue;
        //MALUS for the opponent
        case -21 -> Color.orange;
        case -51 -> Color.orange;
        default -> Color.red;
    };
}



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