package forest;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.io.File;

import java.awt.Point;

import mvc.Model;

/**
 * Forestに関する情報の処理を司るクラス
 */
public class ForestModel extends Model {
    /**
     * ForestViewを束縛するフィールド
     */
    protected ForestView view;

    /**
     * ForestControllerを束縛するフィールド
     * 
     * protected ForestController controller;
     * 
     * /**
     * 選択されたテキストファイルを保持するフィールド
     */
    private File selectedFile;

    /**
     * テキストファイル内の文字列をString型のListとして保持するフィールド
     */
    private List<String> textElements = new ArrayList<String>();

    /**
     * テキストファイル内の文字列から親ノードと子ノードのマップを保持するフィールド
     */
    // private Map<>;

    /**
     * 子ノードの高さの平均を保持するフィールド
     * 
     * 
     * 
     * /**
     * このクラスのコンストラクタ
     */
    public ForestModel(File selectedFile) {

    }

    /**
     * 選択された木構造のテキストファイルを読み込むメソッド
     */
    public void importSelectedFile() {

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

    }

    /**
     * 親ノードの高さを子ノードの高さの平均値に修正するメソッド
     */
    public Double modifyParentsHeight() {

    }

}
