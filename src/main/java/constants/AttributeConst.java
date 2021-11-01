package constants;
/**
 * 画面の項目値等を定義するEnumクラス
 *
 *
 *
 */
public enum AttributeConst {

    //フラッシュメッセージ
    FLUSH("flush"),

    //一覧画面共通
    MAX_ROW("maxRow"),
    PAGE("page"),
    STO_PAGE("storePage"),

    //入力フォーム共通
    TOKEN("_token"),
    ERR("errors"),

    //ログイン中のユーザー
    LOGIN_EMP("login_user"),

    //ログイン画面
    LOGIN_ERR("loginError"),

    //ユーザー管理
    USER("user"),
    USERS("users"),
    USR_COUNT("users_count"),
    USR_ID("id"),
    USR_USR_ID("user_id"),
    USR_PASS("password"),
    USRNAME("name"),

    //削除フラグ
    DEL_FLAG_TRUE(1),
    DEL_FLAG_FALSE(0),

    //日記管理
    DIARY("diary"),
    DIARIES("diaries"),
    DIA_COUNT("diaries_count"),
    DIA_ID("id"),
    DIA_DATE("diary_date"),
    DIA_CONTENT("content"),

    //タスク管理
    TASK("follow"),
    TASKS("follows"),
    TASK_COUNT("follow_count"),
    TASK_ID("id"),

    FIN_FLAG_TRUE(1),
    FIN_FLAG_FALSE(0);


    private final String text;
    private final Integer i;

    private AttributeConst(final String text) {
        this.text = text;
        this.i = null;

    }

    private AttributeConst(final Integer i) {
        this.text = null;
        this.i = i;
    }

    public String getValue() {
        return this.text;
    }

    public Integer getIntegerValue() {
        return this.i;
    }

}
