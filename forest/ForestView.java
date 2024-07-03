package forest;

// import mvc.View; // mvcとは
import java.io.File;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

// 設定ウィンドウと木構造のウィンドウで2つクラス作るか
public class ForestView extends Object {

    private File aFile = null;
    private ForestController forestController = null;
    private ForestModel forestModel = null;

    private MenuComponent menuComponent = null;
    private TreeComponent treeComponent = null;

    // コンストラクタ: メニューウィンドウを開いて待機かな
    public ForestView(File aFile) {
        super();
        this.aFile = aFile;
    }

    public void addMVC(ForestController forestController, ForestModel forestModel) {
        this.forestModel = forestModel;
        this.forestController = forestController;
    }

    public void run() {
        this.menuComponent = new MenuComponent(aFile, forestController);
    }

    // 5, 木構造ファイルをimportした後、modelから呼び出される
    // => treeコンポーネントをインスタンス化して、アニメーションを開始させる！
    public void showAnimation() {
        // ファイル名とデータを渡す

        // if もし今動いてるコンポーネントがあれば消したい！！
        this.treeComponent = new TreeComponent(); // インスタンス作成で自動的(コンポーネントにアニメーションさせる)
    }

    /*
     * ***********************************コンポーネント***************************
     */
    // メニューコンポーネントクラス: ForestViewクラスのコンストラクタでインスタンス化する
    public class MenuComponent extends JComponent {
        private static final long serialVersionUID = 1L;
        private File aFile = null;
        private ForestController forestController = null;

        public MenuComponent(File aFile, ForestController forestController) {
            super();
            this.aFile = aFile;
            this.forestController = forestController;
            this.initialize(); // ボタン設置
            this.showMenuWidow();
        }

        public void initialize() { // コンポーネントにメニューウィンドウのボタンなどを設置
            JButton treeButton = new JButton("tree"); // ファイル選択ボタンの作成
            treeButton.setActionCommand("tree");
            treeButton.addActionListener((ActionEvent e) -> this.forestController.handleClick(e));

            JButton forestButton = new JButton("forest"); // ファイル選択ボタンの作成
            forestButton.setActionCommand("forest");
            forestButton.addActionListener((ActionEvent e) -> this.forestController.handleClick(e));

            JButton semilatticeButton = new JButton("semilattice"); // ファイル選択ボタンの作成
            semilatticeButton.setActionCommand("semilattice");
            semilatticeButton.addActionListener((ActionEvent e) -> this.forestController.handleClick(e));

            this.setLayout(new GridLayout(1, 3)); // 4✖️1の形に要素を表示させるための処理(パネルを４分割する)
            this.add(treeButton);
            this.add(forestButton);
            this.add(semilatticeButton);
        }

        // 2, showMenuWidow(): void // メニュー画面作成(ボタンクリックされたらメニューウィンドウ削除はしないで残す)
        public void showMenuWidow() {
            // ウィンドウを生成して開く。
            JFrame aWindow = new JFrame(this.aFile.getName()); // ファイル名からJFram作成
            MenuComponent thisObj = this;
            aWindow.getContentPane().add(thisObj); // コンポーネントを追加
            aWindow.setMinimumSize(new Dimension(600, 600));
            aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            aWindow.setSize(800, 800);
            aWindow.setLocationRelativeTo(null);
            aWindow.setVisible(true);
        }
    }

    // 木構造コンポーネント: 木構造が選択されたタイミングでForestViewクラスからインスタンス化
    public class TreeComponent extends JComponent {
        private static final long serialVersionUID = 1L;

        public TreeComponent() {
            super();
            startTreeAnimation();
        }

        // 5, showTreeWindow(ArrayList, Map): void // アニメーション開始(×が押されたら、ウ
        public void startTreeAnimation() {
            // ウィンドウを生成して開く。
            JFrame aWindow = new JFrame("ファイル名"); // ファイル名からJFram作成
            TreeComponent thisObj = this;
            aWindow.getContentPane().add(thisObj); // コンポーネントを追加
            aWindow.setMinimumSize(new Dimension(600, 600));
            aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            aWindow.setSize(800, 800);
            aWindow.setLocationRelativeTo(null);
            aWindow.setVisible(true);
        }

        public void initialize() { // コンポーネントにメニューウィンドウのボタンなどを設置
        }

    }

}
