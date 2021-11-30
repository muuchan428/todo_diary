package constants;

/**
 * 各出力メッセージを定義するEnumクラス
 *
 */
public enum MessageConst {

    //認証
    I_LOGINED("Welcome!"),
    E_LOGINED("ログインに失敗しました"),
    I_LOGOUT("ログアウトしました"),

    //DB更新
    I_REGISTERED("saved!"),
    I_UPDATED("updated!"),
    I_DELETED("deleted!"),

    //バリデーション
    E_NONAME("ニックネームを入力してください。"),
    E_NOPASSWORD("パスワードを入力してください。"),
    E_NOUSR_CODE("ユーザーIDを入力してください。"),
    E_EMP_CODE_EXIST("入力されたユーザーIDはすでに使用されています。"),
    E_NOCONTENT("内容を入力してください。");


    /**
     * 文字列
     */
    private final String text;

    /**
     * コンストラクタ
     */
    private MessageConst(final String text) {
        this.text = text;
    }

    /**
     * 値(文字列)取得
     */
    public String getMessage() {
        return this.text;
    }
}
