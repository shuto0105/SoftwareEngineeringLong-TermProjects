package forest;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.io.File;

import java.awt.Point;

/**
 * Forestに関する情報の処理を司るクラス 今んとこmodelからコントローラ、Viewを呼び出すことはない！
 */
public class ForestModel extends Object {
    public ForestModel() { // コンストラクタ(Modelがコントローラを持つかどうかはまだ決まってない(なるべく持たせたくない))
    }

    // protected ForestController controller; ForestControllerを束縛するフィールド => 使わん
    protected ForestView view; // ForestViewを束縛するフィールド
    private File selectedFile; // 選択されたテキストファイルを保持するフィールド
    private List<String> textElements = new ArrayList<String>(); // テキストファイル内の文字列をString型のListとして保持するフィールド
    // private Map<>; // テキストファイル内の文字列から親ノードと子ノードのマップを保持するフィールド
    private double meanChildrenHeight; // 子ノードの高さの平均を保持するフィールド

    /**
     * 選択された木構造のテキストファイルを読み込み返すメソッド (controller.handleMenuButtonCilck()から呼び出し)
     * String(File名) => File
     */
    public File importSelectedFile(String fileName) {
        this.selectedFile = new File("resource/data/" + fileName + ".txt");
        if (!(this.selectedFile.exists())) {
            System.err.println("'" + fileName + "' does not exist.");
            System.exit(1);
        }
        return this.selectedFile;
    }

    /**
     * 読み込んだテキストファイルから文字列のリストを作成する
     */
    protected void makeTextLists() {

    }

    /**
     * 読み込んだテキストファイルから親ノードと子ノードのマ ップを作成するメソッド
     */
    protected void makeTextMaps() {

    }

    /**
     * テキストに子ノードが存在するかを確認するメソッド
     */
    public void checkNodesHaveChildren(List<String> textElements) {

    }

    /**
     * 子ノードの高さの平均値を求めるメソッド
     */
    public Double getMeanChildrenHeight() {
        return 2.20;
    }

    /**
     * 親ノードの高さを子ノードの高さの平均値に修正するメソッド
     */
    public Double modifyParentsHeight() {
        return 2.2;
    }

}
