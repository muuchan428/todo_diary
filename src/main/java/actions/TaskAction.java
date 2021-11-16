package actions;

import java.io.IOException;
import java.io.PrintWriter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.DiaryView;
import actions.views.TaskView;
import actions.views.UserView;

import services.TaskService;
import constants.AttributeConst;
import constants.ForwardConst;


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
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.DIARY, tv);//入力された日報情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト



            } else {
              //タスク一覧を表示させる
                show();
            }
    }


    /**
     * 完了登録する
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException{

        if(checkUser()) {
            //idを条件にデータを取得する
            TaskView tv = taskService.findOne(toNumber(getRequestParam(AttributeConst.TSK_ID)));
            

            //タスクデータを更新する
            List<String> errors = taskService.update(tv);

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

    private boolean checkUser() throws ServletException, IOException {


        //セッションからログイン中の従業員情報を取得
          loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USR);
          //idを条件に日記データを取得
          TaskView task = taskService.findOne(toNumber(getRequestParam(AttributeConst.TSK_ID)));
          System.out.println(task.getUser().getId() + " " + loginUser.getId() );
          //日記を作成したユーザーかどうかをチェック
        if(task.getUser().getId() != loginUser.getId()) {

              //ログインユーザーが設定されていない、またはIDと一致しない場合はエラー画面を表示
              forward(ForwardConst.FW_ERR_UNKNOWN);

              return false;
          } else {
              return true;
          }
        }


}




