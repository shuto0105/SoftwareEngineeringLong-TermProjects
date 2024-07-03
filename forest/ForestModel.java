package forest;

import java.io.File;

import forest.ForestView.MenuComponent;

public class ForestModel extends Object {

    private ForestController forestController = null;
    private ForestView forestView = null;

    public ForestModel() {
        super();
    }

    public void addMVC(ForestController forestController, ForestView forestView) {
        this.forestController = forestController;
        this.forestView = forestView;
    }

    // 4, importSelectedFile(string): void //
    // ファイルを読み込んでnodesをArrayList<string>に格納、branchesをMap<number,
    // List<number>>に格納しようかな
    // コントローラから呼び出される
    // viewのshowAnimationを呼び出す
    public void importSelectedFile(String fileName) {
        File aFile = new File("resource/data/" + fileName + ".txt");
        if (!(aFile.exists())) {
            System.err.println("'" + aFile + "' does not exist.");
            System.exit(1);
        }
        System.out.println((fileName));
        System.out.println(aFile.toString());
        this.forestView.showAnimation();
    }
}
