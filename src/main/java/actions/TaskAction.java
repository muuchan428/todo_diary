package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import actions.views.TaskView;
import actions.views.UserView;

import services.TaskService;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;


public class TaskAction extends ActionBase {

    private TaskService taskService;
    private UserView loginUser;
   /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        taskService =new TaskService();

        //メソッドを実行
        invoke();
        taskService.close();
    }


    /**
     * タスク一覧を表示
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {


        //セッションからログイン中のユーザー情報を取得
        UserView loginUser = (UserView)getSessionScope(AttributeConst.LOGIN_USR);

            List<TaskView> tv = taskService.getAllNotFinish(loginUser);

           List<String> tasks = new ArrayList<String>();
           for(TaskView task : tv) {
               tasks.add(task.getId().toString() +":" + task.getContent());
           }

                PrintWriter out = response.getWriter();
                out.print(tasks);
    }
    /**
     *
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //idを条件にタスクデータを取得
       TaskView task = taskService.findOne(toNumber(getRequestParam(AttributeConst.TSK_ID)));
        //タスクを作成したユーザーかどうかをチェック
      if(checkUser()) {

          putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
          putRequestScope(AttributeConst.TASK, task);//入力されたタスク情報

          //詳細画面を表示
          forward(ForwardConst.FW_TSK_EDIT);

      }
  }


    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {


          //セッションからログイン中の従業員情報を取得
            loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USR);


            //パラメータの値をもとに日記情報のインスタンスを作成する
           TaskView tv = new TaskView(
                    null,
                    loginUser,
                    getRequestParam(AttributeConst.TSK_CONTENT),
                    null,
                    null,
                    AttributeConst.FIN_FLAG_FALSE.getIntegerValue());

            //日記情報登録
            List<String> errors = taskService.create(tv);


            if (errors.size() > 0) {



            } else {
              //タスク一覧を表示させる
                show();
            }
    }

    public void update() throws ServletException, IOException{
        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件にデータを取得する
            TaskView tv = taskService.findOne(toNumber(getRequestParam(AttributeConst.TSK_ID)));

            //入力された内容を設定する
            tv.setContent(getRequestParam(AttributeConst.TSK_CONTENT));

            // タスクデータを更新する
            List<String> errors = taskService.update(tv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.TASK, tv); //入力された日報情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_TSK_EDIT);
            } else {
                //更新中にエラーがなかった場合
                YearMonth date = YearMonth.of(tv.getFinishedAt().getYear(),tv.getFinishedAt().getMonthValue());
                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLASH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_DIA, ForwardConst.CMD_SHOW, date);

            }
        }
    }

    /**
     * 完了登録する
     * @throws ServletException
     * @throws IOException
     */
    public void complete() throws ServletException, IOException{

        if(checkUser()) {
            //idを条件にデータを取得する
            TaskView tv = taskService.findOne(toNumber(getRequestParam(AttributeConst.TSK_ID)));


            //タスクデータを更新する
            List<String> errors = taskService.complete(tv);

            if (errors.size() > 0) {
            } else {
                //更新中にエラーがなかった場合タスク一覧を表示させる
                show();


            }
        }
       }

    /**
     * 削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //セッションからログイン中の従業員情報を取得
        loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USR);

        if(checkUser()) {
            //idを条件にタスクを削除する
        taskService.destroy(toNumber(getRequestParam(AttributeConst.TSK_ID)));

            show();
        }

    }

    public void delete() throws ServletException, IOException{
        //セッションからログイン中の従業員情報を取得
        loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USR);

        //CSRF対策 tokenのチェック
        if (checkToken() && checkUser()) {
            //idを条件に日記を削除する
            taskService.destroy(toNumber(getRequestParam(AttributeConst.TSK_ID)));
            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLASH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);
        }
    }


    private boolean checkUser() throws ServletException, IOException {


        //セッションからログイン中の従業員情報を取得
          loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USR);
          //idを条件に日記データを取得
          TaskView task = taskService.findOne(toNumber(getRequestParam(AttributeConst.TSK_ID)));
          System.out.println(task.getUser().getId() + " " + loginUser.getId() );
          //日記を作成したユーザーかどうかをチェック
        if(task.getUser().getId() != loginUser.getId()) {

              //ログインユーザーが設定されていない、またはIDと一致しない場合はエラー画面を表示
            //エラーがある場合エラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

              return false;
          } else {
              return true;
          }
        }


}




