package spacebattle;

import spacebattle.battlefield.Sector;
import spacebattle.commander.Alien;
import spacebattle.commander.Commander;
import spacebattle.ship.*;
import java.util.Scanner;


public class Game {

    static Scanner input = new Scanner(System.in);

    //executes game
    public Game() {

        System.out.println("\nHello! Welcome to the game!");

        System.out.println("Please choose the size of the sectors." +
                        " Only sizes between 10 and 15 are allowed:");

        int chosenSize = getOnlyNumbers();

        while (chosenSize < 10 || chosenSize > 15) {
            System.out.println("\nUnfortunately, your chosen size is not Allowed. Choose again:");
            chosenSize = getOnlyNumbers();
        }

        Commander player = new Commander(chosenSize, chosenSize * 2 + 5);
        Alien alien = new Alien(chosenSize, chosenSize * 2 + 5);

        prepareGame(player);

        System.out.println("\nNow, you will play against an alien." +
                "\nWith help of the map, please choose a field to attack the aliens sector." +
                "\nObviously, you can only attack a field you haven`t attacked yet.\n");

        while (!player.isDefeated() && !alien.isDefeated()) {

            System.out.println(player.toString());

            playerAttack(player, alien);

            if (!alien.isDefeated()) {
                alienAttack(player, alien);
            }
        }
    }
    
    //executes the player`s attack
    private static void playerAttack(Commander player, Alien alien) {

        boolean again = true;

        while (again) {

            System.out.println("Choose row and column of the field to Attack.\n" +
                    "For example: 'A1' for the first field from upper left\nField: ");

            int [] place = getRowCol();
            int row = place[0];
            int col = place[1];

            //checks if the field is outside sector and if it was already attacked
            while (row < 0 || row >= player.getSpaceSector().getSector().length || col < 0 ||
                    col >= player.getSpaceSector().getSector()[0].length
                    || player.getEnemySector().getMap()[row][col] != 0) {

                System.out.println("\nYour chosen field is invalid! Choose again: ");
                int [] otherPlace = getRowCol();
                row = otherPlace[0];
                col = otherPlace[1];
            }

            if (alien.getSpaceSector().getSector()[row][col] != null) {
                Spaceship ship = alien.getSpaceSector().getSector()[row][col];
                System.out.println("\nHit!\n");
                alien.getSpaceSector().getSector()[row][col] = new Wreck();

                if (!alien.getSpaceSector().shipInSector(ship)) {
                    System.out.println("A " + ship.getType().toString().toLowerCase()
                            + " has been destroyed!\n");
                }

                player.updateEnemyMap(row, col, true);
                System.out.println(player.getEnemySector().toString());
                again = true;
            }

            else if (alien.getSpaceSector().getSector()[row][col] == null){
                System.out.println("\nMiss!");
                player.updateEnemyMap(row, col, false);
                again = false;
            }

            if (alien.isDefeated()) {
                System.out.println("\nCongratulations, you destroyed all of the alien`s ships!\n" +
                        "You won! :)");
                again = false;
            }
        }
    }

    //executes the computer`s attack
    private static void alienAttack(Commander player, Alien alien) {
        boolean again = true;

        while (again) {

            int[] coordinates = alien.getShotCoordinates();
            int row = coordinates[0];
            int col = coordinates[1];

            if (player.getSpaceSector().getSector()[row][col] != null) {
                System.out.println("\nThe alien has hit your ship in " + Sector.rowToLetter(row) +
                        (col + 1));
                player.getSpaceSector().getSector()[row][col] = new Wreck();
                alien.updateEnemyMap(row, col, true);
                again = true;
            }

            else if (player.getSpaceSector().getSector()[row][col] == null) {
                System.out.println("\nThe alien has missed in " + Sector.rowToLetter(row) +
                        (col + 1));
                alien.updateEnemyMap(row, col, false);
                again = false;
            }

            if (player.isDefeated()) {
                System.out.println("The Alien won. Sadly, all your ships are destroyed." +
                        "\n:(");
                again = false;
            }
        }
    }

    //lets player choose how to rotate ship
    private static void playerRotations(Spaceship ship) {

        if (ship.getType() == ShipType.SUBMARINE) {
            System.out.println("\nYou can`t rotate a submarine ship since it has only one tile." +
                    "This is how your ship looks like:\n\n" + ship.toString());
        }

        else {
            System.out.println("\nHow often do you wish to rotate following ship? "
                    + ship.getType().toString() + "\n" + ship.toString() +
                    "\nChoose between 0 and 3 times.");

            int rotations = getOnlyNumbers();

            while (rotations < 0 || rotations > 3) {
                System.out.println("\nPlease choose an accepted number: ");
                rotations = input.nextInt();
            }

            for (int x = 1; x <= rotations; x++) {
                ship.rotate();
            }

            System.out.println("\nThis is how your ship looks after rotating it:\n\n" + ship.toString());
        }
    }

    //instructions to prepare the game
    private static void prepareGame(Commander player) {

        System.out.println("\nNow you can buy Spaceships until you have no more than 2 coins left.\n" +
                "If you wish to stop buying ships earlier, type in a 0.\n" +
                "To choose your desired Ship, please type in following numbers for each type:\n" +
                "\n1 for Corvette, price: 3\n\n" + new Corvette().toString() +
                "\n2 for Battleship, price: 4\n\n"
                + new Battleship().toString() + "\n3 for Carrier, price: 5\n\n" +
                new Carrier().toString() + "\n4 for Cruiser, price: 6\n\n" +
                new Cruiser().toString() + "\n5 for Submarine, price: 4\n\n" +
               new Submarine().toString());

        System.out.println("Your coins: " + player.getCoins());

        int answer = getOnlyNumbers();

        while (answer == 0 || answer < 0 || answer > 5) {

            if (answer < 0 || answer > 5) {
                System.out.println("\nOnly numbers between 0 and 5 are accepted. Try again: ");
            }
            else {
                System.out.println("\nYou have to buy at least one Ship. Choose again:");
            }
            answer = getOnlyNumbers();
        }

        while (answer != 0 && player.getCoins() > 2) {

            if (answer < 0 || answer > 5) {
                System.out.println("\nOnly numbers between 0 and 5 are accepted. Try again: ");
            }

            switch (answer) {
                case 1:
                    player.addShipToFleet(ShipType.CORVETTE);
                    break;
                case 2:
                    if (player.getCoins() > 3) {
                        player.addShipToFleet(ShipType.BATTLESHIP);
                    } else {
                        System.out.println("\nYou don`t have enough coins for this ship!\n");
                    }
                    break;
                case 3:
                    if (player.getCoins() > 4) {
                        player.addShipToFleet(ShipType.CARRIER);
                    } else {
                        System.out.println("\nYou don`t have enough coins for this ship!\n");
                    }
                    break;
                case 4:
                    if (player.getCoins() > 5) {
                        player.addShipToFleet(ShipType.CRUISER);
                    } else {
                        System.out.println("\nYou don`t have enough coins for this ship!\n");
                    }
                    break;
                case 5:
                    if (player.getCoins() > 3) {
                        player.addShipToFleet(ShipType.SUBMARINE);
                    } else {
                        System.out.println("\nYou don`t have enough coins for this ship!\n");
                    }
                    break;
            }

            System.out.println("Your coins: " + player.getCoins());
            if (player.getCoins() > 2) {
                answer = getOnlyNumbers();
            }
        }

        System.out.println("\nYou have bought following ships:\n");

        for(Spaceship ship : player.getFleet()) {
                System.out.println(ship.getType().toString() + "  ");
        }

        System.out.println("\nNow you can rotate every ship (except for submarines) and place it in your sector.");

        while (player.getShipsToPlace().length > 0) {

            Spaceship ship = player.getShipsToPlace()[player.getShipsToPlace().length - 1];

            playerRotations(ship);

            int tries = 0;
            while (player.noPossiblePlacements(ship)) {
                System.out.println("\nThere is no space for your ship in your chosen rotation." +
                        " Please try another one: ");
                playerRotations(ship);
                if (tries == 4) {
                    ship.setPlaced(true);
                    System.out.println("\nyour ship can´t be placed, so it will be skipped.");
                }
                tries++;
            }

            System.out.println("These are the possible placements in your sector." +
                    "You can place your ship in the spaces with a '-' , but not in those with an 'X'.\n" +
                    "Your already placed ships are represented with an 'O'\n");


            System.out.println(drawPossiblePlacements(player, ship) + "\nNow you can place your ship. " +
                    "The Anchor of your ship is represented by its upper left space." +
                    "\nThe rows of your sector are represented in alphabetical order by upper case letters " +
                    "and the columns by numbers starting with '1'.");

            System.out.println("Choose the space to place your ship: ");

            int [] place = getRowCol();
            int row = place[0];
            int col = place[1];

            while (row < 0 || row >= player.getSpaceSector().getSector().length || col < 0 ||
                    col >= player.getSpaceSector().getSector()[0].length ) {
                System.out.println("\nYour chosen placement is outside your sector! Choose again: ");
                int [] otherPlace = getRowCol();
                row = otherPlace[0];
                col = otherPlace[1];
            }

            while (!player.getSpaceSector().getPossiblePlacements(ship)[row][col]) {
                System.out.println("\nYou can`t place your ship here. Choose again: ");
                int [] otherPlace = getRowCol();
                row = otherPlace[0];
                col = otherPlace[1];
            }
            player.getSpaceSector().placeShip(ship, row, col);

            System.out.println("\nThis is your updated sector:\n" + player.getSpaceSector().toString());
        }
    }

    //returns user input and ensures it contains only integers
    private static int getOnlyNumbers() {

        boolean again = true;
        String s = "";

        while (again) {
            int invalids = 0;
            s = input.next();

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) < 48 || s.charAt(i) > 57) {
                    invalids++;
                }
            }

            if (invalids == 0) {
                again = false;
            }
            else  {
                System.out.println("\nInvalid input. Try again: ");

            }
        }
        return Integer.parseInt(s);
    }

    //returns row and columns in desired format
    private static int[] getRowCol() {

        String place = input.next();

        //checks for invalid inputs
        while (place.length() < 2 || place.length() > 3 || place.charAt(0) < 65
                || 90 < place.charAt(0) && place.charAt(0) < 97|| place.charAt(0) > 122 ||
                place.charAt(1) < 48 && place.charAt(1) > 57 || place.charAt(place.length() - 1) < 48
                || place. charAt(place.length() - 1) > 57) {

            System.out.println("Invalid input. Try again: ");
            place = input.next();
        }

        int row = (Character.toUpperCase(place.charAt(0)) - 65);
        int col = (place.charAt(1) - 49);
        if (place.length() > 2) {
            String num1 = Integer.toString(place.charAt(1) - 48);
            String num2 = Integer.toString(place.charAt(2) - 48);
            String s = num1 + num2;
            col = Integer.parseInt(s) - 1;
        }
        return new int[] {row, col};
    }

    //draws possible placements to help player place ships
    private static String drawPossiblePlacements(Commander player, Spaceship ship) {
        StringBuilder bld = new StringBuilder();

        bld.append("   ");

        for (int i = 1; i <= player.getSpaceSector().getPossiblePlacements(ship)[0].length; i++) {

            if (i < 10) {
                bld.append(i).append("   ");
            }
            else {
                bld.append(i).append("  ");
            }
            if (i == player.getSpaceSector().getPossiblePlacements(ship)[0].length) {
                bld.append("\n");
            }
        }
        for (int i = 0; i < player.getSpaceSector().getPossiblePlacements(ship).length; i++) {
            bld.append(Sector.rowToLetter(i)).append("  ");
            for (int x = 0; x < player.getSpaceSector().getPossiblePlacements(ship)[0].length; x++) {
                if (player.getSpaceSector().getSector()[i][x] != null) {
                    bld.append("\u004F   ");
                }
                else if (!player.getSpaceSector().getPossiblePlacements(ship)[i][x]) {
                    bld.append("\u0058   ");
                }
                else {
                    bld.append("\u002D   ");
                }
                if (x == player.getSpaceSector().getPossiblePlacements(ship)[0].length - 1) {
                    bld.append("\n");
                }
            }
        }
        return bld.toString();
    }
}
