package forest;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.Point;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Forestに関する情報の処理を司るクラス 今んとこmodelからコントローラ、Viewを呼び出すことはない！
 */
public class ForestModel extends Object {
    public ForestModel() { // コンストラクタ(Modelがコントローラを持つかどうかはまだ決まってない(なるべく持たせたくない))
    }

    // protected ForestController controller; ForestControllerを束縛するフィールド => 使わん
    protected ForestView view; // ForestViewを束縛するフィールド
    private File selectedFile; // 選択されたテキストファイルを保持するフィールド

    private ArrayList<String> fileContentArrayList = new ArrayList<String>();// ファイルコンテンツ（全部）（配列）
    private ArrayList<String> nodesArrayList = new ArrayList<String>(); // テキストファイル内のnodesをString型のListとして保持するフィールド
    private HashMap<Integer, ArrayList<Integer>> branchesMap = new HashMap<>(); // テキストファイル内の文字列から親ノードと子ノードのマップを保持するフィールド
    private double meanChildrenHeight; // 子ノードの高さの平均を保持するフィールド
    private ArrayList<Integer> rootNodesArrayList = new ArrayList<Integer>();

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
        try { // ファイルの中身をArrayListに格納しておく
            String content = Files.readString(Paths.get(this.selectedFile.getPath()));
            this.fileContentArrayList = new ArrayList<String>(Arrays.asList(content.split("\n")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return this.selectedFile;
    }

    /**
     * 読み込んだテキストファイルから文字列のリストを作成する
     */
    public ArrayList<String> getNodesArrayList() {
        Integer startIndex = this.fileContentArrayList.indexOf("nodes:");
        Integer endIndex = this.fileContentArrayList.indexOf("branches:");
        // *注意* : nodesArrayListは ["1, Magnitude", ....] から始まるため
        // 取り出す時のインデックス -1 する！！！！
        this.nodesArrayList = new ArrayList<String>(
                this.fileContentArrayList.subList(startIndex + 1, endIndex)); // ファイル全体のlistからnodesだけを抜く
        this.nodesArrayList.replaceAll(node -> node.split(", ")[1]); // 全部変換する 例) 1, Magnitude => Magnitude
        return this.nodesArrayList;
    }

    /**
     * 読み込んだテキストファイルから親ノードと子ノードのマップを作成するメソッド
     * + 親がいないやつを探す
     */
    public HashMap<Integer, ArrayList<Integer>> getBranchesMap() {
        Integer startIndex = this.fileContentArrayList.indexOf("branches:");
        Integer endIndex = this.fileContentArrayList.size();
        ArrayList<String> branchesArrayList = new ArrayList<String>(
                this.fileContentArrayList.subList(startIndex + 1, endIndex)); // テキストファイルのbranches以降をカットする

        ArrayList<Integer> isHaveParentList = new ArrayList<>();
        IntStream.rangeClosed(0, this.nodesArrayList.size()).forEach(value -> isHaveParentList.add(value));
        System.out.println("indexで埋めたやつ！！！");
        System.out.println(isHaveParentList.toString());
        for (String value : branchesArrayList) { // 拡張for文 => todo: (forEachに変更)
            // 親と子を分離 "1, 16" => [1, 16] stringからintegerに変換する
            int[] intArrayList = Stream.of(value.split(", ")).mapToInt(Integer::parseInt).toArray();
            Integer parent = intArrayList[0];
            Integer child = intArrayList[1];
            this.branchesMap.putIfAbsent(parent, new ArrayList<>()); // キーがない場合 if
                                                                     // (!this.branchesMap.containsKey(parent))
            this.branchesMap.get(parent).add(child);
            isHaveParentList.set(child, -1);
        }

        isHaveParentList.forEach(value -> {
            if (value != -1 && value != 0)
                this.rootNodesArrayList.add(value);
        });
        System.out.println(this.rootNodesArrayList.toString());

        return this.branchesMap;
    }

    public ArrayList<Integer> getRootNodesArrayList() {
        return this.rootNodesArrayList;
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
