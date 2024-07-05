package forest;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.io.File;

import javax.swing.Action;

import java.awt.Point;

/**
 * Forestのユーザからの入力を司るクラス
 */
public class ForestController extends Object {
    public ForestController(ForestView forestView, ForestModel forestModel) { // コンストラクタ
        this.forestModel = forestModel;
        this.forestView = forestView;
    }

    protected ForestModel forestModel; // Modelを束縛するフィールド
    protected ForestView forestView; // ForestViewを束縛するフィールド
    private File selectedFile; // 選択されたテキストファイルを保持するフィールド
    private Point previous; // 過去と現在におけるクリックされている座標を保持しておくフィールド
    private Point current; // プロ演の方でもフィールドの宣言だけしているから必要ないかも
    // public void setModel(ForestModel aModel) {}; Modelのセット => 使わん(コンストラクタで追加)
    // public void setView(ForestView aView) {}; Viewのセット => 使わん

    public void run() { // 処理スタート
        this.forestView.instantiateMenuWindowClass(this);
    }

    /**
     * ボタンが押された時の処理の全てを司るメソッド
     */
    public void handleMenuButtonCilck(ActionEvent anEvent) { // メニューウィンドウのボタンクリックイベントが来る

        System.out.println(anEvent);
        // todo: ここで現在動いている木構造のウィンドウがあれば閉じたい

        String fileName = anEvent.getActionCommand(); // 選択されたファイル名(tree, forest,...)が取れる
        this.selectedFile = forestModel.importSelectedFile(fileName); // Modelにファイルをインポートさせる

        // todo: forestWindow出す前にModelにデータ解析させる

        this.forestView.instantiateForestWindowClass(new HandleWindowClosed(), this.selectedFile.getName()); // ここで(ファイル名,
                                                                                                             // Map,
                                                                                                             // List)を渡したい
    }

    /*
     * アニメーションウィンドウが閉じられた時にviewから呼ばれる
     */
    class HandleWindowClosed extends WindowAdapter { // これはコントローラにおいた方がいいか =
        public void windowClosed(WindowEvent e) {
            // 外部クラス(ForestController)のforestView(インスタンス)を呼び出している
            ForestController.this.forestView.setVisibleMenuWindow(); // メニューウィンドウを出す(viewにやらせる)
        }
    }

    /**
     * マウスが押された時の制御に関するメソッド。
     * それは、ForestModelのmouseButtonPressedをtrueに変更するようにメッセージ送信をすることである。
     * 
     * @param aMouseEvent マウスイベント
     */
    public void mousePressed(MouseEvent aMouseEvent) {
        // super.mousePressed(aMouseEvent);
        // ((TypistArtModel) this.model).mouseButtonPressed(true);
        // return;
    }

    /**
     * マウスが離された時の制御に関するメソッド。
     * それは、ForestModelのmouseButtonPressedをfalseに変更するようにメッセージを送信することである。
     * 
     * @param aMouseEvent マウスイベント
     */
    public void mouseReleased(MouseEvent aMouseEvent) {
        // super.mouseReleased(aMouseEvent);
        // ((TypistArtModel) this.model).mouseButtonPressed(false);
        // return;
    }
}
