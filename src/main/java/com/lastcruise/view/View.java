package com.lastcruise.view;


import com.lastcruise.model.GamePanel;
import com.lastcruise.model.GameText;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Map;

public class View {

    public void drawPlayerStamina(Graphics2D g2, int playerStamina) {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24));
        g2.drawString("Stamina", 24, 24);

        int rectWidth = 48 * 3;
        g2.setColor(Color.black);
        g2.fillRect(24, 28, rectWidth, 24);
        g2.setColor(Color.red);
        int staminaWidth = playerStamina * rectWidth / 100;
        g2.fillRect(24, 28, staminaWidth ,24);
    }





    // ================================ ORIGINAL GAME PLAY========================================

    private final Map<String, String> GAME_TEXT;

    public View() {
        GameText gameText = new GameText();
        GAME_TEXT = gameText.getGameText();
    }

    public void printGameBanner() {
        System.out.println(Colors.GREEN + GAME_TEXT.get("Banner") + Colors.RESET);
    }

    public void printStory() {
        System.out.println(GAME_TEXT.get("Intro"));
    }

    public void printHelpCommands() {
        System.out.println(Colors.BLUE + GAME_TEXT.get("Help") + Colors.RESET);
    }

    public void printInstructions() {
        System.out.println(GAME_TEXT.get("Instructions"));
    }

    public void printStoryIntro(String name) {
        System.out.printf(GAME_TEXT.get("StoryIntro"), name);
    }

    public void printNamePrompt() {
        System.out.print(GAME_TEXT.get("NamePrompt"));
    }

    public void printStartGamePrompt() {
        System.out.print(GAME_TEXT.get("StartGamePrompt"));
    }

    public void printStatusBanner(String location, String stamina, String inventory,
        String locationDesc,
        String locationItems, String message) {
        System.out.printf(GAME_TEXT.get("Status"), location, stamina, inventory, locationDesc,
            locationItems,
            message);
    }

    public void titleScreen(Graphics2D g2, int tileSize, int screenWidth){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String gameTitle = "Last cruise";
        int x = getXforCenteredText(gameTitle, g2, screenWidth);
        int y = tileSize*3;
        g2.setColor(Color.blue);
        g2.drawString(gameTitle, x, y);
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0, 0, screenWidth, tileSize);
        //shadow
        g2.setColor(Color.BLUE);
        g2.drawString(gameTitle, x+5, y+5);

        //MENU

        gameTitle = "NEW GAME";
        x = getXforCenteredText(gameTitle, g2, screenWidth);
        y += tileSize*4;
        g2.drawString(gameTitle, x, y);
    }

     private int getXforCenteredText(String gameTitle, Graphics2D g2, int screenWidth) {
        int length = (int)g2.getFontMetrics().getStringBounds(gameTitle, g2).getWidth();
        int x = screenWidth/2-length/2;
        return x;
    }


    //------------VIEW MESSAGES------------------------------------------
    public String getItemDescription(String description) {
        return String.format(Colors.BLUE + GAME_TEXT.get("ItemDescription") + Colors.RESET,
            description);
    }

    public String getInvalidItemMessage() {
        return Colors.RED + GAME_TEXT.get("ItemNotFound") + Colors.RESET;
    }

    public String getInvalidCommandMessage() {
        return Colors.RED + GAME_TEXT.get("InvalidCommand") + Colors.RESET;
    }

    public String getInvalidLocationMessage() {
        return Colors.RED + GAME_TEXT.get("InvalidLocation") + Colors.RESET;
    }

    public String getSuccessfulRaftBuildMessage() {
        return Colors.GREEN + GAME_TEXT.get("BuildSuccessful") + Colors.RESET;
    }

    public String getNotSuccessfulRaftBuildMessage() {
        return Colors.RED + GAME_TEXT.get("BuildNotSuccessful") + Colors.RESET;
    }

    public String getNotInRaftLocationBuildMessage() {
        return Colors.RED + GAME_TEXT.get("InvalidCraftingLocation") + Colors.RESET;
    }

    public String getHelpCommands() {
        return Colors.BLUE + GAME_TEXT.get("Help") + Colors.RESET;
    }

    public String cantGrabItem() {
        return Colors.RED + GAME_TEXT.get("CantGrabItem") + Colors.RESET;
    }

    public String getItemNotCraftable() {
        return Colors.RED + GAME_TEXT.get("ItemNotCraftable") + Colors.RESET;
    }

    public String getItemNotEdible() {
        return Colors.RED + GAME_TEXT.get("YouCantEatThat") + Colors.RESET;
    }

    public String getSleeping() {
        return Colors.BLUE + GAME_TEXT.get("Sleep") + Colors.RESET;
    }

    public String getNoPickUpStamina() {
        return Colors.RED + GAME_TEXT.get("NotEnoughPickUpStamina") + Colors.RESET;
    }

    public String getNoDropStamina() {
        return Colors.RED + GAME_TEXT.get("NotEnoughDropStamina") + Colors.RESET;
    }

    public String getGameSaved() {
        return Colors.BLUE + GAME_TEXT.get("GameSaved") + Colors.RESET;
    }

    public String getGameSaveFailed() {
        return Colors.RED + GAME_TEXT.get("GameSaveFailed") + Colors.RESET;
    }

    public String getEating() {
        return Colors.BLUE + GAME_TEXT.get("EatItem") + Colors.RESET;
    }

    public String getCantEatThat() {
        return Colors.RED + GAME_TEXT.get("YouCantEatThat") + Colors.RESET;
    }

    public void printCantLoadGame() {
        System.out.println(Colors.RED + GAME_TEXT.get("NoSavedGame") + Colors.RESET);
    }

    public String getNoStaminaToMove() {
        return Colors.RED + GAME_TEXT.get("CantMove") + Colors.RESET;
    }

    public String getCantEscape() {
        return Colors.RED + GAME_TEXT.get("CantEscape") + Colors.RESET;
    }

    public String getYouWonMessage() {
        return Colors.GREEN + GAME_TEXT.get("Win") + Colors.RESET;
    }

    public String solvedPuzzleMessage() {
        return Colors.GREEN + GAME_TEXT.get("SolvedPuzzle") + Colors.RESET;
    }

    public String unSolvedPuzzleMessage() {

        return Colors.RED + GAME_TEXT.get("UnSolvedPuzzle") + Colors.RESET;
    }

    public String puzzleMessagePrompt() {
        return Colors.RED + GAME_TEXT.get("PuzzlePrompt") + Colors.RESET;
    }

    public String pitFallPrompt() {
        return Colors.RED + GAME_TEXT.get("PitFall") + Colors.RESET;
    }

    public String pitFallEscapePrompt() {
        return Colors.GREEN + GAME_TEXT.get("PitFallEscape") + Colors.RESET;
    }

    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}