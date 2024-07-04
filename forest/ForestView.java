package forest;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.io.File;

import java.awt.Graphics;
import java.awt.Point;

import mvc.View;

/**
 * Forestに関する表示を司る外部クラス
 */
public class ForestView extends View{

	/*
	 * Modelを束縛するフィールド
	 */
	protected ForestModel model;

	/**
     * ForestControllerを束縛するフィールド
     */
	protected ForestController controller;

	/**
	 * クリックしたところの座標を保持するフィールド
	 */
	protected Point offset;

	/**
	 * ノードを表示するべき位置(x座標)を保持しておくフィールド
	 */
	private int x;

	/**
	 * ノードを表示するべき位置(y座標)を保持しておくフィールド
	 */
	private int y;

	/**
	 * 設定画面の処理を司る内部クラス
	 */
	public class SettingForestView extends View{

		/**
		 * 内部クラスのコンストラクタ
		 */
		public SettingForestView(File selectedFile)
		{

		}

		/**
		 * 設定画面の表示を司るメソッド
		 */
		public void showMenuWindow()
		{

		}
		
		/**
		 * 設定画面の設定を司るメソッド
		 */
		public void setMenuWindow()
		{

		}

	}

	/**
	 * 外部クラスのコンストラクタ
	 */
	public ForestView(File selectedFile)
	{

	}

	/**
	 * アニメーションの表示を司るメソッド
	 */
	public void showAnimationWindow()
	{

	}

	/**
	 * アニメーションの設定を司るメソッド
	 */
	public void setAnimationWindow()
	{

	}

	/**
     * 地面(コンポーネント)の描画を行う。
     * それはスクロール(offset)を考慮して、今まで描いてきたタイピストアート画像を指定の位置に描画することである。
     * @param aGraphics グラフィクス・コンテキスト
     */
	public void paintComponent(Graphics aGraphics)
	{

	}

	/**
	 * 文字列ノード(JLabelごと)を表示すべき位置(x座標)を設定する
	 */
	public void setPointX(int x)
	{

	}

	/**
	 * 文字列ノード(JLabelごと)を表示すべき位置(y座標)を設定する
	 */
	public void setPointY(int y)
	{

	}

	/**
     * スクロール量(offsetの逆向きの大きさ)を応答する。
     * @return x軸とy軸のスクロール量を表す座標
     */
    public Point scrollAmount()
    {
        int x = 0 - this.offset.x;
        int y = 0 - this.offset.y;
        return (new Point(x, y));
    }

	/**
     * スクロール量を指定された座標分だけ相対スクロールする。
     * @param aPoint X軸とY軸のスクロール量を表す座標
     */
    public void scrollBy(Point aPoint)
    {
        int x = this.offset.x + aPoint.x;
        int y = this.offset.y + aPoint.y;
        this.scrollTo(new Point(x, y));
    }

	/**
     * スクロール量を指定された座標に設定(絶対スクロール)する。
     * @param aPoint x軸とy軸の絶対スクロール量を表す座標
     */
    public void scrollTo(Point aPoint)
    {
        this.offset = aPoint;
        return;
    }
}
