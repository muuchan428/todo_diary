package actions;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;


import actions.views.DiaryView;

import actions.views.UserView;
import services.DiaryService;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;


public class DiaryAction extends ActionBase {

    private DiaryService diaryService;
    private UserView loginUser;
   /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        diaryService =new DiaryService();

        //メソッドを実行
        invoke();
        diaryService.close();
    }
    /**
     * 日記一覧を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //セッションからログイン中の従業員情報を取得
        loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USR);
        int page = getPage();
        List<LocalDate> dates = diaryService.getDates(loginUser, page);
        int dateCount = diaryService.getAllDate(loginUser);

        putRequestScope(AttributeConst.DATES, dates);//日記が作成された日付のリスト
        putRequestScope(AttributeConst.DATE_COUNT, dateCount); //日記の作成された日付の件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }
        //一覧画面を表示
        forward(ForwardConst.FW_DIA_INDEX);
    }
    /**
     * 新規登録画面を表示
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //日記情報の空インスタンスを作成し、日付＝今日の日付を設定する
        DiaryView dv = new DiaryView();
        dv.setDiaryDate(LocalDate.now());
        putRequestScope(AttributeConst.DIARY, dv); //日付のみ設定済みの日報インスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_DIA_NEW);

    }
    /**
     * 日記一覧を表示
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {


        //セッションからログイン中のユーザー情報を取得
        UserView loginUser = (UserView)getSessionScope(AttributeConst.LOGIN_USR);

        //リクエストパラメータから日付を取得
        LocalDate date =  LocalDate.parse(getRequestParam(AttributeConst.DATE));
    //idを条件に日記データを取得する
    List<DiaryView> diaries = diaryService.getCreatedAtDate(loginUser, date);


    putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
    putRequestScope(AttributeConst.LOGIN_USR,loginUser);//ログインしている従業員情報
    putRequestScope(AttributeConst.DIARIES, diaries); //取得した日記リスト
    putRequestScope(AttributeConst.DATE, date);

    //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
    String flush = getSessionScope(AttributeConst.FLUSH);
    if (flush != null) {
        putRequestScope(AttributeConst.FLUSH, flush);
        removeSessionScope(AttributeConst.FLUSH);
    }

    //詳細画面を表示
    forward(ForwardConst.FW_DIA_SHOW);
    }
    /**
     * 編集画面を表示
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

          //idを条件に日記データを取得
          DiaryView diary = diaryService.findOne(toNumber(getRequestParam(AttributeConst.DIA_ID)));
          //日記を作成したユーザーかどうかをチェック
        if(checkUser()) {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.DIARY, diary);//入力された日報情報

            //詳細画面を表示
            forward(ForwardConst.FW_DIA_EDIT);

        }
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

          //セッションからログイン中の従業員情報を取得
            loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USR);
            //日記の日付が入力されていなければ、今日の日付を設定
            LocalDate day = null;
            if (getRequestParam(AttributeConst.DIA_DATE) == null
                    || getRequestParam(AttributeConst.DIA_DATE).equals("")) {
                day = LocalDate.now();
            } else {
                day = LocalDate.parse(getRequestParam(AttributeConst.DIA_DATE));
            }


            //パラメータの値をもとに日記情報のインスタンスを作成する
            DiaryView dv = new DiaryView(
                    null,
                    loginUser,
                    day,
                    getRequestParam(AttributeConst.DIA_CONTENT),
                    null,
                    null);

            //日記情報登録
            List<String> errors = diaryService.create(dv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.DIARY, dv);//入力された日報情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_DIA_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_DIA, ForwardConst.CMD_INDEX);
            }
        }
    }
    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException{
        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件に日記データを取得する
            DiaryView dv = diaryService.findOne(toNumber(getRequestParam(AttributeConst.DIA_ID)));

            //入力された日記内容を設定する
            dv.setDiaryDate(toLocalDate(getRequestParam(AttributeConst.DIA_DATE)));
            dv.setContent(getRequestParam(AttributeConst.DIA_CONTENT));

            //日記データを更新する
            List<String> errors = diaryService.update(dv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.DIARY, dv); //入力された日報情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_DIA_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_DIA, ForwardConst.CMD_SHOW, dv.getDiaryDate());

            }
        }
    }
    /**
     * 削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {
        System.out.println("destroy");
        //セッションからログイン中の従業員情報を取得
        loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USR);

        System.out.println(getTokenId() + " " + getRequestParam(AttributeConst.TOKEN));
        //CSRF対策 tokenのチェック
        if (checkToken() && checkUser()) {
            System.out.println("check ok");
            //idを条件に日記を削除する
            diaryService.destroy(toNumber(getRequestParam(AttributeConst.DIA_ID)));
            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_DIA, ForwardConst.CMD_INDEX);
        }
    }
    private boolean checkUser() throws ServletException, IOException {

      //セッションからログイン中の従業員情報を取得
        loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USR);
        //idを条件に日記データを取得
        DiaryView diary = diaryService.findOne(toNumber(getRequestParam(AttributeConst.DIA_ID)));
        //日記を作成したユーザーかどうかをチェック
      if(diary.getUser().getId() != loginUser.getId()) {

            //ログインユーザーが設定されていない、またはIDと一致しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

            return false;
        } else {
            return true;
        }

    }

}




