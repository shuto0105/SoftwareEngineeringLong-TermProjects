import java.util.List;
import java.util.ArrayList;

import java.awt.Point;

import mvc.Model;

public class ForestModel extends Model
{
    /*
     * ForestViewのフィールド
     */
    protected ForestView view;

    /*
     * ForestControllerのフィールド
     */
    protected ForestController controller;
    
    /*
     * 選択されたテキストファイルを保持するフィールド
     */
    private File selectedFile;

    /*
     * テキストファイル内の文字列をString型のListとして保持するフィールド
     */
    private List<String> textElements = new ArrayList<String>();

    /*
     * 文字列を表示する座標を保持するフィールド
     */
    private Point nodesLocation; 

    /*
     * 子ノードの高さの平均を保持するフィールド
     */
    private double meanChildrenHeight;
    
    
    /*
     * このクラスのコンストラクタ
     */
    public ForestModel(File selectedFile){

    }

    /*
     * 選択された木構造のテキストファイルを読み込むメソッド
     */
    public void importSelectedFile()
    {

    }

    /*
     * 読み込んだテキストファイルから文字列のリストを作成するメソッド
     */
    protected void listTextElements()
    {

    }

    /*
     * テキストに子ノードが存在するかを確認するメソッド
     */
    public void checkNodesHaveChildren(List<String> textElements)
    {

    }

    /*
     * 子ノードの高さの平均値を求めるメソッド
     */
    public Double getMeanChildrenHeight()
    {

    }

    /*
     * 親ノードの高さを子ノードの高さの平均値に修正するメソッド
     */
    public Double modifyParentsHeight()
    {

    }

}
