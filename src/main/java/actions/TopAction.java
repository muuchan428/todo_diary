package actions;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.DiaryView;
import actions.views.TaskView;
import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.DiaryService;
import services.TaskService;

/**
 * トップページに関する処理を行うActionクラス
 *
 */
public class TopAction extends ActionBase {
        TaskService taskService;
        DiaryService diaryService;


    /**
     * indexメソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

           taskService = new TaskService();
           diaryService = new DiaryService();
        //メソッドを実行
        invoke();

        taskService.close();
        diaryService.close();
    }

    /**
     * 一覧画面を表示する
     */
    public void index() throws ServletException, IOException {



        //セッションからログイン中のユーザー情報を取得
       UserView loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USR);

       //今日作成した日記、完了したタスクを取得
       List<DiaryView> diaries = diaryService.getByDay(loginUser, LocalDate.now());
       List<TaskView> tasks = taskService.getByDay(loginUser, LocalDate.now());
       //すべての日記の件数、完了したタスクの件数を取得
       long diaryCount = diaryService.countAllMine(loginUser);
       long taskCount = taskService.countAllFin(loginUser);



       putRequestScope(AttributeConst.DIA_COUNT, diaryCount);//日記、タスクの件数
       putRequestScope(AttributeConst.TSK_COUNT, taskCount);
       putRequestScope(AttributeConst.DIARIES, diaries);//取得した日記リスト
       putRequestScope(AttributeConst.TASKS, tasks);//取得したタスクリスト
       putRequestScope(AttributeConst.USER, loginUser);
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数



        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLASH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLASH, flush);
            removeSessionScope(AttributeConst.FLASH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_TOP_INDEX);
    }

}