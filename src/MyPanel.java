import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.util.Scanner;

public class MyPanel extends JPanel {
    private int lungimeLatura = 50;
    private Vector<Node> listaNoduri;
    Point pointStart = null;
    Point pointEnd = null;
    boolean isDragging = false;
    int nrLinii = 0;
    int nrColoane = 0;
    int nrIesiri = 0;
    public Vector<Vector<Integer>> matriceDeAdiacenta = new Vector<Vector<Integer>>();
    Node start = new Node();
    Queue<Node> iesiri = new LinkedList<Node>();
    void citireLabirint(String file) {
        try {
            File f = new File(file);
            Scanner s = new Scanner(f);
            nrLinii = s.nextInt();
            nrColoane = s.nextInt();
            Integer input = 1;
            for (int i = 0; i < nrLinii; i++) {
                Vector<Integer> row = new Vector<>();
                for (int j = 0; j < nrColoane; j++) {
                    input = s.nextInt();
                    if (input == 2) {
                        nrIesiri++;
                    }
                    row.add(input);
                }
                matriceDeAdiacenta.add(row);
            }
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void creareLabirint() {
        int x = lungimeLatura, y = lungimeLatura;
        for (int i = 0; i < nrLinii; i++) {
            x = lungimeLatura;
            for (int j = 0; j < nrColoane; j++) {
                addNode(x, y, matriceDeAdiacenta.elementAt(i).elementAt(j));
                listaNoduri.elementAt(i * nrColoane + j).setNeighbours(i, j, nrLinii, nrColoane);
                if (matriceDeAdiacenta.elementAt(i).elementAt(j) == 3) {
                    start = listaNoduri.elementAt(i * nrColoane + j);
                    start.i = i;
                    start.j = j;
                } else {
                    listaNoduri.elementAt(i * nrColoane + j).i = i;
                    listaNoduri.elementAt(i * nrColoane + j).j = j;
                }
                x += lungimeLatura;
            }
            y += lungimeLatura;
        }
    }

    void asociereParinti() {
        Queue<Node> coada = new LinkedList<Node>();
        coada.add(start);
        int auxIesiri = nrIesiri;
        int parinti = 0;
        start.isVisited=true;
        while (auxIesiri > 0 && !coada.isEmpty()) {
            if(coada.element().getColor()==2){
                auxIesiri--;
                iesiri.add(coada.element());
            }
            if (coada.element().hasOver) {
                Node over = listaNoduri.elementAt((coada.element().i - 1) * nrColoane + coada.element().j);
                if (over.getColor() != 0 && !over.isVisited) {
                    over.isVisited=true;
                    over.parinte = coada.element();
                    coada.add(over);
                }
            }
            if (coada.element().hasRight) {
                Node right = listaNoduri.elementAt(coada.element().i * nrColoane + coada.element().j + 1);
                if (right.getColor() != 0 && !right.isVisited) {
                    right.isVisited=true;
                    right.parinte = coada.element();
                    coada.add(right);
                }
            }
            if (coada.element().hasBelow) {
                Node below = listaNoduri.elementAt((coada.element().i + 1) * nrColoane + coada.element().j);
                if (below.getColor() != 0 && !below.isVisited) {
                    below.isVisited=true;
                    below.parinte = coada.element();
                    coada.add(below);
                }
            }
            if (coada.element().hasLeft) {
                Node left = listaNoduri.elementAt(coada.element().i * nrColoane + coada.element().j - 1);
                if (left.getColor() != 0 && !left.isVisited) {
                    left.isVisited=true;
                    left.parinte = coada.element();
                    coada.add(left);
                }
            }
            coada.remove();
        }
    }

    void afisareDrum(){
        while(!iesiri.isEmpty()) {
            Node curent = iesiri.element();
            while (curent != start) {
                curent.setColor(4);
                curent = curent.parinte;
            }
            iesiri.remove();
        }
    }

    public MyPanel() {
        listaNoduri = new Vector<Node>();
        citireLabirint("Input.txt");
        // borderul panel-ului
        setBorder(BorderFactory.createLineBorder(Color.black));
        creareLabirint();
        asociereParinti();
        afisareDrum();

    }

    private void addNode(int x, int y, int color) {
        Node node = new Node(x, y, color);
        listaNoduri.add(node);
        repaint();
    }

    //se executa atunci cand apelam repaint()
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//apelez metoda paintComponent din clasa de baza
        g.drawString("This is my Graph!", 10, 20);
        //deseneaza lista de noduri
        for (int i = 0; i < listaNoduri.size(); i++) {
            listaNoduri.elementAt(i).drawNode(g, lungimeLatura, listaNoduri.elementAt(i).getColor());
        }
    }
}
