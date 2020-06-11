
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thuy
 */
public class MyGame extends JFrame implements KeyListener {

    //init attributes
    int numOfRow = 10;
    int numOfCol = 20;
    int score = 0;
    int numOfDiamond = 10;
    int numOfTrap = 10;
    int numOfWall = 30;
    int XPlayer = 0;
    int YPlayer = 0;
    int[][] typeOfBox = new int[numOfRow][numOfCol];
    JLabel[][] box = new JLabel[numOfRow][numOfCol];
    JTextField txtMessage = new JTextField();
    JLabel lbScore = new JLabel();

    //constructor
    public MyGame(String title) {
        initComponents(title);
    }

    //init components
    public void initComponents(String title) {
        //init size
        int x = 10;
        int y = 10;
        int edgeOfBox = 50;
        int heightOfMes = 50;
        int width = y + edgeOfBox * numOfCol;
        int height = x + edgeOfBox * numOfRow + (heightOfMes + 10);

        //set frame
        this.setSize(width + 50, height + 80);
        this.setLayout(null);
        this.setTitle(title);
        this.setLocationRelativeTo(null); //set frame ra chinh giua man hinh
        this.setFocusable(true);        
        this.addKeyListener(this);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        //label
        this.add(lbScore);
        lbScore.setLocation(y, x);
        lbScore.setSize(width - y, heightOfMes);
        ImageIcon icon = new ImageIcon("images/diamondUI.png");
        lbScore.setIcon(icon);
        lbScore.setText(score + "/" + numOfDiamond);
        lbScore.setHorizontalAlignment(JLabel.CENTER);
        lbScore.setOpaque(true);
        lbScore.setBackground(Color.pink);
        y = 10;
        x += heightOfMes + 10;
        

        //box
        for (int i = 0; i < numOfRow; i++) {
            for (int j = 0; j < numOfCol; j++) {
                box[i][j] = new JLabel();
                this.add(box[i][j]);
                box[i][j].setLocation(y + edgeOfBox * j, x + edgeOfBox * i);
                box[i][j].setSize(edgeOfBox, edgeOfBox);
                box[i][j].setOpaque(true);
                typeOfBox[i][j] = 0;
            }
        }
        
        x += edgeOfBox * numOfRow;
        y += edgeOfBox * numOfCol;

        //init game
        Random rd = new Random();
        int maxSize = numOfCol * numOfRow;
        int[] stateOfNum = new int[maxSize];
        boolean randomSuccess = false;
        ///random for player
        while (!randomSuccess) {
            int n = rd.nextInt(maxSize);
            if (stateOfNum[n] == 0) {
                stateOfNum[n] = 1;
                randomSuccess = true;
                YPlayer = n % numOfCol;
                XPlayer = n / numOfCol;
                typeOfBox[XPlayer][YPlayer] = 4;
            }
        }
        ///random for wall
        for (int i = 1; i <= numOfWall; ++i) {
            randomSuccess = false;
            while (!randomSuccess) {
                int n = rd.nextInt(maxSize);
                if (stateOfNum[n] == 0) {
                    stateOfNum[n] = 1;
                    randomSuccess = true;
                    int YLoc = n % numOfCol;
                    int XLoc = n / numOfCol;
                    typeOfBox[XLoc][YLoc] = 1;
                }
            }
        }
        ///random for trap
        for (int i = 1; i <= numOfTrap; ++i) {
            randomSuccess = false;
            while (!randomSuccess) {
                int n = rd.nextInt(maxSize);
                if (stateOfNum[n] == 0) {
                    stateOfNum[n] = 1;
                    randomSuccess = true;
                    int YLoc = n % numOfCol;
                    int XLoc = n / numOfCol;
                    typeOfBox[XLoc][YLoc] = 3;
                }
            }
        }
        ///random for diamond
        for (int i = 1; i <= numOfDiamond; ++i) {
            randomSuccess = false;
            while (!randomSuccess) {
                int n = rd.nextInt(maxSize);
                if (stateOfNum[n] == 0) {
                    stateOfNum[n] = 1;
                    randomSuccess = true;
                    int YLoc = n % numOfCol;
                    int XLoc = n / numOfCol;
                    typeOfBox[XLoc][YLoc] = 2;
                }
            }
        }
        ///set image
        for (int i = 0; i < numOfRow; i++) {
            for (int j = 0; j < numOfCol; j++) {
                setColorOfBox(i, j);
            }
        }
    }

    //color of box
    public void setColorOfBox(int i, int j) {
        if (typeOfBox[i][j] == 0) {
            ImageIcon icon = new ImageIcon("images/grass.jpg");
            box[i][j].setIcon(icon);
        } else if (typeOfBox[i][j] == 1) {
            ImageIcon icon = new ImageIcon("images/wall.jpg");
            box[i][j].setIcon(icon);
        } else if (typeOfBox[i][j] == 2) {
            ImageIcon icon = new ImageIcon("images/diamond.png");
            box[i][j].setIcon(icon);
        } else if (typeOfBox[i][j] == 3) {
            ImageIcon icon = new ImageIcon("images/trap.png");
            box[i][j].setIcon(icon);
        } else if (typeOfBox[i][j] == 4) {
            ImageIcon icon = new ImageIcon("images/player.png");
            box[i][j].setIcon(icon);
        }
    }

    //action
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int[] XDirec = new int[]{-1, 1, 0, 0};
        int[] YDirec = new int[]{0, 0, -1, 1};
        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            GoToLocation(XPlayer + XDirec[0], YPlayer + YDirec[0]);
        } else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            GoToLocation(XPlayer + XDirec[1], YPlayer + YDirec[1]);
        } else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            GoToLocation(XPlayer + XDirec[2], YPlayer + YDirec[2]);
        } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            GoToLocation(XPlayer + XDirec[3], YPlayer + YDirec[3]);
        }
    }
    
    public void GoToLocation(int x, int y) {
        if (x < 0 || x >= numOfRow || y < 0 || y >= numOfCol) { //out of game's area
            return;
        } else if (typeOfBox[x][y] == 0) { // empty box
            typeOfBox[XPlayer][YPlayer] = 0;
            setColorOfBox(XPlayer, YPlayer);
            XPlayer = x;
            YPlayer = y;
            typeOfBox[XPlayer][YPlayer] = 4;
            setColorOfBox(XPlayer, YPlayer);
        } else if (typeOfBox[x][y] == 1) { // wall
            return;
        } else if (typeOfBox[x][y] == 2) { //diamond
            //change color
            typeOfBox[XPlayer][YPlayer] = 0;
            setColorOfBox(XPlayer, YPlayer);
            XPlayer = x;
            YPlayer = y;
            typeOfBox[XPlayer][YPlayer] = 4;
            setColorOfBox(XPlayer, YPlayer);
            //reset score
            score++;
            lbScore.setText(score + "/" + numOfDiamond);
            //check win
            if (score == numOfDiamond) {
                ImageIcon icon = new ImageIcon("images/congrate.png");
                int choice = JOptionPane.showConfirmDialog(this, "Congratulation!\n" + "Play again?",
                        "Woww!!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
                if (choice == JOptionPane.YES_OPTION) {
                    MyGame mygame = new MyGame("Diamond Game");
                    this.dispose();
                } else {
                    System.exit(0);
                }
            }
        } else if (typeOfBox[x][y] == 3) { //trap
            //change color
            typeOfBox[XPlayer][YPlayer] = 0;
            setColorOfBox(XPlayer, YPlayer);
            XPlayer = x;
            YPlayer = y;
            typeOfBox[XPlayer][YPlayer] = 4;
            setColorOfBox(XPlayer, YPlayer);
            //notify die
            ImageIcon icon = new ImageIcon("images/oops.png");
            int choice = JOptionPane.showConfirmDialog(this, "Poor you!\n" + "Play again?",
                    "Oops!!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
            if (choice == JOptionPane.YES_OPTION) {
                MyGame mygame = new MyGame("Diamond Game");
                this.dispose();
            } else {
                System.exit(0);
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
