package models;



import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * タスクデータのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_TSK)
//@NamedQueries({
//    @NamedQuery(
//            name = JpaConst.Q_DIA_GET_ALL_MINE,
//            query = JpaConst.Q_DIA_GET_ALL_MINE_DEF),
//    @NamedQuery(
//            name = JpaConst.Q_DIA_COUNT,
//            query = JpaConst.Q_DIA_COUNT_DEF),
//    @NamedQuery(
//            name = JpaConst.Q_DIA_GET_DATE,
//            query = JpaConst.Q_DIA_GET_DATE_DEF),
//    @NamedQuery(
//            name = JpaConst.Q_DATES_GET_DIA,
//            query = JpaConst.Q_DATES_GET_DIA_DEF),
//   @NamedQuery(
//           name = JpaConst.Q_DIA_GET_BY_ID,
//           query = JpaConst.Q_DIA_GET_BY_ID_DEF)
//
//
//})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Task {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.TSK_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * タスクを登録したユーザー
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.TSK_COL_USR, nullable = false)
    private User user;



    /**
     * タスクの内容
     */
    @Lob
    @Column(name = JpaConst.TSK_COL_CONTENT, nullable = false)
    private String content;

    /**
     * 登録日時
     */
    @Column(name = JpaConst.TSK_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 完了日時
     */
    @Column(name = JpaConst.TSK_COL_FINISHED_AT, nullable = false)
    private LocalDateTime finishedAt;
    /**
     * 完了フラグ
     */
    @Column(name = JpaConst.TSK_COL_FINISH_FLAG, nullable=false)
    private Integer finishFlag;

}