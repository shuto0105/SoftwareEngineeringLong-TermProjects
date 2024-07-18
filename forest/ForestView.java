package forest;

import forest.ForestController.HandleWindowClosed;
import forest.ForestView.MenuWindowClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.Timer;

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

	// あぷり起動時に一回だけ実行される(メニュー画面作成(MenuWindowClassをインスタンス化))
	public void instantiateMenuWindowClass(ForestController forestController) { // コントローラrun()から呼び出される
		// メニュークラスをインスタンス化するとメニューウィンドウが立ち上がる(メニューウィンドウクラスにやらせる)
		this.menuWindowClass = new MenuWindowClass(forestController); // ボタンを押した時にイベントを登録するためにコントローラを渡す！
	}

	public void setVisibleMenuWindow(ForestModel forestModel) { // controller.HandleWindowClosedから呼ばれる(アニメーションウィンドウが閉じれば呼ばれる)
		this.menuWindowClass.setVisible(true);
		this.forestModel = forestModel;
		this.forestWindowClass = null; // アニメーションウィンドウのインスタンスを削除する
	}

	public void instantiateForestWindowClass(HandleWindowClosed handleWindowClosed, String fileName,
			ArrayList<String> nodesArrayList, HashMap<Integer, ArrayList<Integer>> branchesMap,
			ArrayList<Integer> rootNodesArrayList) { // controller.handleMenuButtonCilckから呼び出される
		this.menuWindowClass.setVisible(false); // メニューウィンドウを隠す
		this.forestWindowClass = new ForestWindowClass(handleWindowClosed, fileName, nodesArrayList, branchesMap,
				rootNodesArrayList); // インスタンス化する
		forestWindowClass.showAnimationWindow(handleWindowClosed);
	}

	// 👆👆👆👆👆上の3つのメソッドはコントローラから呼び出されて下の2つのクラス(MenuWindowClassとForestWindowClass)に指示を出すだけ
	// 👇👇👇👇👇メインの処理は全部下の二つのクラスでやる

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
	 * 木構造(アニメーション)やる内部クラス todo: extends JPanel に変更かな(JPanel)
	 */
	public class ForestWindowClass extends JFrame {
		// コンストラクタ
		public ForestWindowClass(HandleWindowClosed handleWindowClosed, String fileName,
				ArrayList<String> nodesArrayList, HashMap<Integer, ArrayList<Integer>> branchesMap,
				ArrayList<Integer> rootNodesArrayList) { // コンストラクタ
			super();
			this.fileName = fileName;
			this.nodesArrayList = nodesArrayList;
			this.branchesMap = branchesMap;
			this.rootNodesArrayList = rootNodesArrayList;
		}

		private ArrayList<String> nodesArrayList = null; // テキストファイル内のnodesをString型のListとして保持するフィールド
		private HashMap<Integer, ArrayList<Integer>> branchesMap = null; // テキストファイル内の文字列から親ノードと子ノードのマップを保持するフィールド
		private ArrayList<Integer> rootNodesArrayList = null; // rootのnodeの番号リスト
		private Container aPane = null;
		private JPanel aPanel = null; // ここにアニメーション乗っけていくかんじかな
		private String fileName = null; // ファイル名JFrameのタイトルで使う
		private int x = 0; // ノードを表示するべき位置(x座標)を保持しておくフィールド
		private int y = 0; // ノードを表示するべき位置(y座標)を保持しておくフィールド
		private ArrayList<JLabel> labelsArrayList = new ArrayList<>();
		private JLabel parentLabel = null; // paint()で使用する
		private JLabel childLabel = null; // 〃〃
		private Graphics g = null;

		/**
		 * アニメーションの表示を司るメソッド(最初に呼ばれるこっからスタート)
		 */
		public void showAnimationWindow(HandleWindowClosed handleWindowClosed) {
			this.setAnimationWindow(handleWindowClosed); // window setting(諸々設定)
			this.makeJLabelListFromNodesArrayList(); // nodesのラベルを作って縦に並べる
			this.runAnimation();
		}

		public void runAnimation() {
			// this.rootNodesArrayList ごとに searchForestかな
			Thread startThread = new Thread(() -> {
				try {
					Thread.sleep(1000);
					exploreTree(this.rootNodesArrayList, 0);
				} catch (Exception e) {
				}

			});
			startThread.start();

			// this.aPanel.revalidate(); // 再描画？？
			// this.aPanel.repaint();
		}

		/*
		 * nodeNumでルートのnodeを受け取ってアニメーション(位置を変更していく)(再帰呼び出し)
		 */
		public void exploreTree(ArrayList<Integer> currentNodeArr, Integer parentNodeNum) { // スタートは1かどうかわからんくなってきた
			System.out.println("探索中...");

			currentNodeArr.forEach(currentNodeNum -> {
				try {

					JLabel currentLabel = this.labelsArrayList.get(currentNodeNum - 1); // 0~なので node番号から-1!!
					Dimension size = currentLabel.getPreferredSize();
					// Thread thread = new Thread(() -> {
					// });
					Boolean isOldestChild = false; // child配列の一番目かどうか
					if (this.labelsArrayList.get(currentNodeArr.get(0) - 1) == currentLabel)
						isOldestChild = true;
					this.setPointX(currentNodeNum, parentNodeNum, isOldestChild);
					this.setPointY(currentNodeNum, parentNodeNum, isOldestChild);
					// isOldestChild = false;
					// thread.start();
					// Thread.sleep(500);
					try {
						// thread.join();
						currentLabel.setBounds(this.x, this.y, (int) size.getWidth(), (int) size.getHeight());
						// currentLabel.setLocation(this.x, this.y);
						// this.aPanel.revalidate(); // 再描画？？
						// this.aPanel.repaint();
						// this.paint(this.g);
						Thread sleepThread = new Thread(() -> {
							try {
								Thread.sleep(800);
							} catch (Exception e) {
							}
						});
						sleepThread.start();
						sleepThread.join();
						// this.paint(getGraphics());
						if (this.branchesMap.containsKey(currentNodeNum)) {
							this.exploreTree(this.branchesMap.get(currentNodeNum), currentNodeNum); // 再帰呼び出し
							// todo: nodeNumの位置を修正
						}
					} catch (Exception e) {

					}

					// Thread.sleep(500);
					// JLabel currentLabel = this.labelsArrayList.get(currentNodeNum - 1); // 0~なので
					// node番号から-1!!
					// Dimension size = currentLabel.getPreferredSize();
					// this.setPointX(currentNodeNum, parentNodeNum);
					// this.setPointY(currentNodeNum, parentNodeNum);
					// currentLabel.setBounds(this.x, this.y, (int) size.getWidth(), (int)
					// size.getHeight());
					// this.paint(getGraphics());

					// childがいるかどうか
					// if (this.branchesMap.containsKey(currentNodeNum)) {
					// this.exploreTree(this.branchesMap.get(currentNodeNum), currentNodeNum); //
					// 再帰呼び出し
					// // todo: nodeNumの位置を修正
					// }
					// 兄弟(弟)がいるかどうかはいらんのか
				} catch (Exception e) {
					System.out.println(e);
				}
			});
		}

		/*
		 * 全てのnodesのラベルを作って縦に並べる todo: for => foreachに変更！！
		 */
		public void makeJLabelListFromNodesArrayList() {
			for (String node : this.nodesArrayList) {
				JLabel alabel = new JLabel(node); // ラベル作成
				this.labelsArrayList.add(alabel);
				alabel.setBorder(new LineBorder(Color.BLACK, 2, false));
				Dimension size = alabel.getPreferredSize(); // 適切なサイズ？を取得する
															// https://www.javadrive.jp/tutorial/jlabel/index4.html#section1
				int index = this.labelsArrayList.size() - 1;
				alabel.setBounds(0, index * (int) size.getHeight(), (int) size.getWidth(), (int) size.getHeight()); // 位置決定
				this.aPanel.add(alabel); // パネルに追加する
			}
		}

		class JPanelComponent extends JPanel {
			JPanelComponent() {
				super();
			}

			/*
			 * 2つのラベルを線で繋ぐ (ここは再描画のたびに自動で呼ばれるぽい？
			 */
			public void paintComponent(Graphics aGraphics) {
				this.setLayout(null);
				// super.paint(aGraphics); // これがないと自動呼出されない？
				System.out.println("paintcomponent呼び出し");
				try {
					// this.aPanel = ForestWindowClass.this.aPanel
					// ForestWindowClass.this.aPanel.paintComponents(aGraphics);
					aGraphics.setColor(Color.BLACK);
					Thread thread = new Thread(() -> {
						ForestWindowClass.this.branchesMap.forEach((parentNum, childArrayList) -> { // ラベル間の線を描画
							ForestWindowClass.this.parentLabel = ForestWindowClass.this.labelsArrayList
									.get(parentNum - 1); // 親のラベルを取得する
							childArrayList.forEach(childNum -> { // MapのvalueのchildNodeの配列を回す
								ForestWindowClass.this.childLabel = ForestWindowClass.this.labelsArrayList
										.get(childNum - 1);
								int y1 = parentLabel.getY() + parentLabel.getHeight() / 2;
								int y2 = childLabel.getY() + childLabel.getHeight() / 2;
								aGraphics.drawLine(parentLabel.getX() + parentLabel.getWidth(), y1, childLabel.getX(),
										y2);
							});
						});
					});
					thread.start();
					thread.join();
					return;
				} catch (Exception e) {
				}
			}
		}

		/**
		 * アニメーションwindowの設定を司るメソッド
		 */
		public void setAnimationWindow(HandleWindowClosed handleWindowClosed) {
			setTitle(this.fileName);
			setSize(800, 800);
			setLocation(0, 0);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE); // https://www.tohoho-web.com/java/layout.htm
			addWindowListener(handleWindowClosed); // windowを閉じるときの処理を追加(コントローラにやらせる)
			// this.g = this.aPanel.getGraphics();
			this.aPane = this.getContentPane();
			// this.aPane.setLayout(null);
			this.aPanel = new JPanelComponent();
			this.aPanel.setLayout(null);
			this.aPane.add(this.aPanel);
			setVisible(true); // 表示かな
		}

		/**
		 * 文字列ノード(JLabelごと)を表示すべき位置(x座標)を設定する
		 */
		public void setPointX(Integer currentNodeNum, Integer parentNodeNum, Boolean isOldestChild) {
			if (parentNodeNum == 0) {
				return;
			}
			JLabel parentNodeLabel = this.labelsArrayList.get(parentNodeNum - 1);
			Integer parentNodeX = parentNodeLabel.getX();
			Integer parentNodeWidth = parentNodeLabel.getWidth();
			if (this.x != parentNodeX + parentNodeWidth + 25) {
				this.x = parentNodeX + parentNodeWidth + 25;
			}
		}

		/**
		 * 文字列ノード(JLabelごと)を表示すべき位置(y座標)を設定する
		 * todo:修正
		 */
		public void setPointY(Integer currentNodeNum, Integer parentNodeNum, Boolean isOldestChild) {
			if (parentNodeNum == 0) {
				return;
			}
			JLabel parentNodeLabel = this.labelsArrayList.get(parentNodeNum - 1);
			Integer parentNodeX = parentNodeLabel.getX();
			Integer parentNodeWidth = parentNodeLabel.getWidth();
			Integer parentNodeY = parentNodeLabel.getY();
			Integer parentNodeHeight = parentNodeLabel.getHeight();
			if (isOldestChild == false) {
				this.y = this.y + parentNodeHeight + 2;
			} else if (isOldestChild == true) {
			} else {
				// this.y += 10; // いらんかな？
			}
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
