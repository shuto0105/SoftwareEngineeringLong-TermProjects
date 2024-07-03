import java.awt.awt.Event.ActionEvent;
import java.awt.Event.MouseEvent;
import java.awt.Point;

import mvc.Controller

/**
 * Forestのユーザからの入力を司るクラス
 */
public class ForestController extends Controller{

	/*
	 * Modelを束縛するフィールド
	 */
	protected ForestModel model;

	/**
     * ForestViewを束縛するフィールド
     */
    protected ForestView view;

	/**
	 * 過去と現在におけるクリックされている座標を保持しておくフィールド
	 * プロ演の方でもフィールドの宣言だけしているから必要ないかも
	 */
	private Point previous;
	private Point current;


	/**
	 * このクラスのコンストラクタ
	 */
	public ForestController()
	{

	}

	/**
     * Modelのセット
     */
    public void setModel(ForestModel aModel) {
        this.model = aModel;
    }

    /**
     * Viewのセット
     */
    public void setView(ForestView aView) {
        this.view = aView;
        return;
    }

	/**
	 * ボタンが押された時の処理の全てを司るメソッド
	 */
	public void handleCilck(ActionEvent anEvent)
	{

	}

	/**
     * マウスが押された時の制御に関するメソッド。
     * それは、ForestModelのmouseButtonPressedをtrueに変更するようにメッセージ送信をすることである。
     * 
     * @param aMouseEvent マウスイベント
     */
    public void mousePressed(MouseEvent aMouseEvent) {
        super.mousePressed(aMouseEvent);
        ((TypistArtModel) this.model).mouseButtonPressed(true);
        return;
    }

    /**
     * マウスが離された時の制御に関するメソッド。
     * それは、ForestModelのmouseButtonPressedをfalseに変更するようにメッセージを送信することである。
     * 
     * @param aMouseEvent マウスイベント
     */
    public void mouseReleased(MouseEvent aMouseEvent) {
        super.mouseReleased(aMouseEvent);
        ((TypistArtModel) this.model).mouseButtonPressed(false);
        return;
    }
}
