package forest;

import java.awt.event.ActionEvent;

public class ForestController extends Object {

    private ForestModel forestModel = null;
    private ForestView forestView = null;

    public ForestController() { // Exampleでインスタンス化かな
        super();
    }

    public void addMVC(ForestView forestView, ForestModel forestModel) {
        this.forestModel = forestModel;
        this.forestView = forestView;
    }

    // 3, hadleClick(event: ): void //
    // ファイル選択ボタンが押されたら実行される(メニューコンポーネントから呼ばれる)、クリックされたボタンを元にmodel.import
    // SelectedFile("ファイル名”)呼び出し
    public void handleClick(ActionEvent aEvent) {
        String command = aEvent.getActionCommand();
        forestModel.importSelectedFile(command);

        // switch (command) {
        // case "tree":
        // break;
        // case "forest":
        // break;
        // case "semilattice":
        // break;
        // }

    }
}
