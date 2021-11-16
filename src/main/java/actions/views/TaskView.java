package actions.views;


import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 日記情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
public class TaskView {

    /**
     * id
     */
    private Integer id;

    /**
     * タスクを登録したユーザー
     */
    private UserView user;



    /**
     * タスクの内容
     */
    private String content;

    /**
     * 登録日時
     */
    private LocalDateTime createdAt;

    /**
     * 完了日
     */
    private LocalDateTime finishedAt;
    /**
     * 完了フラグ
     */
    private Integer finishFlag;
}