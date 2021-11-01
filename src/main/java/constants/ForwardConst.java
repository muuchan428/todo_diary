package constants;

public enum ForwardConst {

    //action
    ACT("action"),
    ACT_TOP("Top"),
    ACT_USR("User"),
    ACT_DIA("Diary"),
    ACT_TSK("Task"),
    ACT_AUTH("Auth"),

    //command

    CMD("command"),
    CMD_NONE(""),
    CMD_INDEX("index"),
    CMD_SHOW("show"),
    CMD_SHOW_LOGIN("showLogin"),
    CMD_LOGIN("login"),
    CMD_LOGOUT("logout"),
    CMD_NEW("entryNew"),
    CMD_CREATE("create"),
    CMD_EDIT("edit"),
    CMD_UPDATE("update"),
    CMD_DESTROY("destroy"),
    CMD_SEARCH("search"),

    //id
    ID("id"),


    //jsp
    FW_ERR_UNKNOWN("error/unknown"),
    FW_TOP_INDEX("topPage/index"),
    FW_LOGIN("login/login"),
    FW_USR_INDEX("users/index"),
    FW_USR_SHOW("users/show"),
    FW_USR_NEW("users/new"),
    FW_USR_EDIT("users/edit"),
    FW_DIA_INDEX("diaries/index"),
    FW_DIA_SHOW("diaries/show"),
    FW_DIA_NEW("diaries/new"),
    FW_DIA_EDIT("diaries/edit"),
    FW_TSK_INDEX("tasks/index"),
    FW_TSK_SHOW("tasks/show");

    /**
     * 文字列
     */
    private final String text;

    /**
     * コンストラクタ
     */
    private ForwardConst(final String text) {
        this.text = text;
    }

    /**
     * 値(文字列)取得
     */
    public String getValue() {
        return this.text;
    }

    /**
     * 値(文字列)から、該当する定数を返却する
     * (例: "Report"→ForwardConst.ACT_REP)
     * @param 値(文字列)
     * @return ForwardConst型定数
     */
    public static ForwardConst get(String key) {
        for(ForwardConst c : values()) {
            if(c.getValue().equals(key)) {
                return c;
            }
        }
        return CMD_NONE;
    }

}
