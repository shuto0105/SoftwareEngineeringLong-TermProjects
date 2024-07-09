package forest;

import java.util.ArrayList; // データ型系
import java.util.HashMap;
import java.io.File; // ファイル系

import java.awt.event.ActionEvent; // awt, swingグラフィック系
import java.awt.event.WindowEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.Point;
import javax.swing.Action;

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
        String fileName = anEvent.getActionCommand(); // 選択されたファイル名(tree, forest,...)が取れる
        this.selectedFile = forestModel.importSelectedFile(fileName); // Modelにファイルをインポートさせる

        // ModelからArrayList<String>(nodesが入る)、HashMap(branchesが入る)、
        ArrayList<String> nodesArrayList = this.forestModel.getNodesArrayList();
        HashMap<Integer, ArrayList<Integer>> branchesMap = this.forestModel.getBranchesMap();
        ArrayList<Integer> rootNodesArrayList = this.forestModel.getRootNodesArrayList();
        this.forestView.instantiateForestWindowClass(new HandleWindowClosed(), this.selectedFile.getName(),
                nodesArrayList, branchesMap, rootNodesArrayList); // アニメーションスタートさせる(Viewに)

    }

    /*
     * アニメーションウィンドウが閉じられた時にviewから呼ばれる(多分クラスにしないとwindowイベントを取れないと思うのでクラス)
     */
    class HandleWindowClosed extends WindowAdapter { // https://www.tohoho-web.com/java/listener.htm
        public void windowClosed(WindowEvent e) {
            //
            ForestController.this.forestModel = new ForestModel(); // Modelを初期化する(ファイルのデータが残ってしまうため)
            //
            // 👇外部クラス(ForestController)のforestView(インスタンス)を呼び出している
            ForestController.this.forestView.setVisibleMenuWindow(ForestController.this.forestModel); // メニューウィンドウを出す(viewにやらせる)
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
