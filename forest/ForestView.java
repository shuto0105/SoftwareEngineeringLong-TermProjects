package forest;

import javax.swing.JFrame;
import javax.swing.JPanel;

import forest.ForestController.HandleWindowClosed;
import forest.ForestView.MenuWindowClass;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.io.File;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.GridLayout;

/**
 * Forestに関する表示を司る外部クラス
 */
public class ForestView extends Object {
	public ForestView(ForestModel forestModel) // コンストラクタ
	{
		this.forestModel = forestModel;
	}

	// protected ForestController controller; ForestControllerを束縛するフィールド => 使わん
	protected ForestModel forestModel; // Modelを束縛するフィールド ViewはModelだけ持つ(コントローラは呼び出さない)
	protected Point offset; // クリックしたところの座標を保持するフィールド
	private MenuWindowClass menuWindowClass = null;
	private ForestWindowClass forestWindowClass = null;

	public void instantiateMenuWindowClass(ForestController forestController) { // コントローラrun()から呼び出される
		// メニュークラスをインスタンス化するとメニューウィンドウが立ち上がる(メニューウィンドウクラスにやらせる)
		this.menuWindowClass = new MenuWindowClass(forestController); // ボタンを押した時にイベントを登録するためにコントローラを渡す！
	}

	public void setVisibleMenuWindow() { // controller.HandleWindowClosedから呼ばれる(アニメーションウィンドウが閉じれば呼ばれる)
		this.menuWindowClass.setVisible(true);
		this.forestWindowClass = null; // アニメーションウィンドウのインスタンスを削除する
	}

	public void instantiateForestWindowClass(HandleWindowClosed handleWindowClosed, String fileName) { // controller.handleMenuButtonCilckから呼び出される
		this.menuWindowClass.setVisible(false);
		this.forestWindowClass = new ForestWindowClass(handleWindowClosed, fileName); // インスタンス化で勝手に(自分でやらせる)アニメーションスタート
	}

	/**
	 * 設定画面の処理を司る内部クラス
	 */
	public class MenuWindowClass extends JFrame {
		public MenuWindowClass(ForestController forestController) { // コンストラクタ
			super();
			this.showMenuWindow(forestController);
		}

		/**
		 * 設定画面の表示を司るメソッド
		 */
		public void showMenuWindow(ForestController forestController) {
			// 部品配置やレイアウトは JFrameに直接ではなく、getContentPane()で取得したペインに対して行う。
			getContentPane(); // setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); // ペインにパネルを貼ってボタンやらを貼る？？？
			this.setMenuWindow(forestController); // ボタンなど追加(イベント追加のためコントローラわたす)
			setVisible(true); // 表示かな
		}

		/**
		 * 設定画面の設定を司るメソッド
		 */
		public void setMenuWindow(ForestController forestController) {
			JPanel panel = new JPanel();
			add(panel);
			panel.setLayout(new GridLayout(1, 3));
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // https://www.tohoho-web.com/java/layout.htm
			setTitle("Select Tree");
			setSize(800, 800);

			JButton treeButton = new JButton("tree"); // ファイル選択ボタンの作成
			treeButton.setActionCommand("tree");
			treeButton.addActionListener((ActionEvent e) -> forestController.handleMenuButtonCilck(e));

			JButton forestButton = new JButton("forest"); // ファイル選択ボタンの作成
			forestButton.setActionCommand("forest");
			forestButton.addActionListener((ActionEvent e) -> forestController.handleMenuButtonCilck(e));

			JButton semilatticeButton = new JButton("semilattice"); // ファイル選択ボタンの作成
			semilatticeButton.setActionCommand("semilattice");
			semilatticeButton.addActionListener((ActionEvent e) -> forestController.handleMenuButtonCilck(e));

			panel.add(treeButton);
			panel.add(forestButton);
			panel.add(semilatticeButton);
		}

	}

	/**
	 * 木構造(アニメーション)やる内部クラス
	 */
	public class ForestWindowClass extends JFrame {
		public ForestWindowClass(HandleWindowClosed handleWindowClosed, String fileName) { // コンストラクタ
			super();
			this.fileName = fileName;
			this.showAnimationWindow(handleWindowClosed);
		}

		private JPanel aPanel = null; // ここにアニメーション乗っけていく

		private String fileName = null;
		private int x; // ノードを表示するべき位置(x座標)を保持しておくフィールド
		private int y; // ノードを表示するべき位置(y座標)を保持しておくフィールド
		// map, list, fileName

		/**
		 * アニメーションの表示を司るメソッド
		 */
		public void showAnimationWindow(HandleWindowClosed handleWindowClosed) {
			getContentPane();
			this.setAnimationWindow(handleWindowClosed); // window setting
			setVisible(true); // 表示かな
		}

		/**
		 * アニメーションの設定を司るメソッド
		 */
		public void setAnimationWindow(HandleWindowClosed handleWindowClosed) {
			this.aPanel = new JPanel();
			add(this.aPanel);
			// todo: 閉じ方はなんか変更する
			setDefaultCloseOperation(DISPOSE_ON_CLOSE); // https://www.tohoho-web.com/java/layout.htm
			setTitle("Select Tree");
			setSize(800, 800);
			addWindowListener(handleWindowClosed);
		}

		/**
		 * 文字列ノード(JLabelごと)を表示すべき位置(x座標)を設定する
		 */
		public void setPointX(int x) {

		}

		/**
		 * 文字列ノード(JLabelごと)を表示すべき位置(y座標)を設定する
		 */
		public void setPointY(int y) {

		}

	}

	///////////////////////////////////////////// こっから下はスクロール関係(あとでやる)
	/**
	 * 地面(コンポーネント)の描画を行う。
	 * それはスクロール(offset)を考慮して、今まで描いてきたタイピストアート画像を指定の位置に描画することである。
	 * 
	 * @param aGraphics グラフィクス・コンテキスト
	 */
	// public void paintComponent(Graphics aGraphics) {} // グラフィクスは使わんかな。。。

	/**
	 * スクロール量(offsetの逆向きの大きさ)を応答する。
	 * 
	 * @return x軸とy軸のスクロール量を表す座標
	 */
	public Point scrollAmount() {
		int x = 0 - this.offset.x;
		int y = 0 - this.offset.y;
		return (new Point(x, y));
	}

	/**
	 * スクロール量を指定された座標分だけ相対スクロールする。
	 * 
	 * @param aPoint X軸とY軸のスクロール量を表す座標
	 */
	public void scrollBy(Point aPoint) {
		int x = this.offset.x + aPoint.x;
		int y = this.offset.y + aPoint.y;
		this.scrollTo(new Point(x, y));
	}

	/**
	 * スクロール量を指定された座標に設定(絶対スクロール)する。
	 * 
	 * @param aPoint x軸とy軸の絶対スクロール量を表す座標
	 */
	public void scrollTo(Point aPoint) {
		this.offset = aPoint;
		return;
	}
}
