package constants;
    /**
     * DB関連の項目値を定義するインターフェース
     * ※インターフェイスに定義した変数は public static final 修飾子がついているとみなされる
     */

public interface JpaConst {


        //persistence-unit名
        String PERSISTENCE_UNIT_NAME = "todo_diary";

        //データ取得件数の最大値
        int ROW_PER_PAGE = 15; //1ページに表示するレコードの数

        //ユーザーテーブル
        String TABLE_USR = "users";//テーブル名
        String USR_COL_ID = "id";//id
        String USR_COL_USR_ID = "user_id";//ユーザーID
        String USR_COL_NAME = "name";//名前
        String USR_COL_PASS = "password";//パスワード
        String USR_COL_CREATED_AT = "created_at";//登録日時
        String USR_COL_UPDATED_AT = "update_at";//更新日時
        String USR_COL_DELETE_FLAG = "delete_flag";//削除フラグ



        int USR_DEL_TRUE = 1; //削除フラグON(削除済み)
        int USR_DEL_FALSE = 0; //削除フラグOFF(現役)


        //日記テーブル
        String TABLE_DIA ="diaries";//テーブル名
        //日記テーブルカラム
        String DIA_COL_ID = "id";//id
        String DIA_COL_USR = "user_id";//日記を作成した人のID
        String DIA_COL_DIA_DATE = "diary_date";//いつの日記か示す日付
        String DIA_COL_CONTENT = "content";//日記の内容
        String DIA_COL_CREATED_AT = "created_at";//登録日時
        String DIA_COL_UPDATED_AT = "updated_at";//更新日時

        //タスクテーブル
        String TABLE_TSK = "tasks";//テーブル名
        //タスクテーブルカラム
        String TSK_COL_ID = "id";//id
        String TSK_COL_USR = "user_id";//タスクを作成した人のId
        String TSK_COL_CONTENT = "content";//タスクの内容
        String TSK_COL_CREATED_AT = "created_at";//登録日時
        String TSL_COL_FINISHED_AT = "finished_at";//完了日時
        String TSK_COL_FINISH_FLAG = "finish_flag";//完了フラグ

        int TSK_FIN_TRUE = 1; //完了フラグON(完了済み)
        int TSK_FIN_FALSE = 0; //完了フラグOFF(未完了)



        //Entity名
        String ENTITY_USR = "user"; //ユーザー
        String ENTITY_DIA = "diary"; //日記
        String ENTITY_TSK = "task";//タスク




        //JPQL内パラメータ


        //NamedQueryの nameとquery
}
