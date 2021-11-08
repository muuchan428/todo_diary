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
        String JPQL_PARM_USER = "user";
        String JPQL_PARM_PASSWORD = "password";
        String JPQL_PARM_USR_ID = "userId";
        String JPQL_PARM_DIA_DATE = "diaryDate";

        //NamedQueryの nameとquery
        //User
        //ユーザーIDとハッシュ化済パスワードを条件に未削除のユーザーを取得する
        String Q_USR_GET_BY_CODE_AND_PASS = ENTITY_USR + ".getByUserIdAndPass";
        String Q_USR_GET_BY_CODE_AND_PASS_DEF = "SELECT u FROM User AS u WHERE u.userId = :" + JPQL_PARM_USR_ID + " AND u.password = :" + JPQL_PARM_PASSWORD;
      //全てのユーザーをidの降順に取得する
        String Q_USR_GET_ALL = ENTITY_USR + ".getAll"; //name
        String Q_USR_GET_ALL_DEF = "SELECT u FROM User AS u ORDER BY u.id DESC"; //query
        //指定したユーザーIDを保持するユーザーの件数を取得する
        String Q_USR_COUNT_RESISTERED_BY_CODE = ENTITY_USR + ".countRegisteredByCode";
        String Q_USR_COUNT_RESISTERED_BY_CODE_DEF = "SELECT COUNT(u) FROM User AS u WHERE u.userId = :" + JPQL_PARM_USR_ID;
        //指定したidを保持するユーザーの情報を取得する
        String Q_USR_GET_BY_ID = ENTITY_USR + "getById";
        String Q_USR_GET_BY_ID_DEF = "SELECT u FROM User AS u WHERE u.id = : " + JPQL_PARM_USER;

        //Diary
       //ログイン中のユーザーの全ての日記をidの降順に取得する
        String Q_DIA_GET_ALL = ENTITY_DIA + ".getAll";
        String Q_DIA_GET_ALL_DEF = "SELECT d FROM Diary AS d WHERE d.user = :" + JPQL_PARM_USER + "ORDER BY d.id DESC";
      //ログイン中のユーザーの全ての日報の件数を取得する
        String Q_DIA_COUNT = ENTITY_DIA + ".count";
        String Q_DIA_COUNT_DEF = "SELECT COUNT(d) FROM Diary AS d WHERE d.user = :" + JPQL_PARM_USER;
       //ログイン中のユーザーの指定された日付の日記を取得する
        String Q_DIA_GET_MINE_DATE = ENTITY_DIA + ".getDateMine";
        String Q_DIA_GET_MINE_DATE_DEF ="SELECT d FROM Diary AS d WHERE d.user = :" + JPQL_PARM_USER + " AND d.diaryDate = :" + JPQL_PARM_DIA_DATE + "ORDER BY d.id DESC";

}
